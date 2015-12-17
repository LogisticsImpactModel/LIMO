package nl.fontys.sofa.limo.orientdb;

import com.orientechnologies.orient.core.config.OGlobalConfiguration;
import com.orientechnologies.orient.core.db.ODatabaseRecordThreadLocal;
import com.orientechnologies.orient.core.db.record.ODatabaseRecord;
import com.orientechnologies.orient.core.entity.OEntityManager;
import com.orientechnologies.orient.core.metadata.schema.OClass;
import com.orientechnologies.orient.core.metadata.schema.OType;
import com.orientechnologies.orient.object.db.OObjectDatabaseTx;
import com.orientechnologies.orient.object.metadata.OMetadataObject;
import com.orientechnologies.orient.object.metadata.schema.OSchemaProxyObject;
import com.orientechnologies.orient.object.serialization.OObjectSerializerContext;
import com.orientechnologies.orient.object.serialization.OObjectSerializerHelper;
import com.sun.jna.platform.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.UserPrincipal;
import java.util.logging.Level;
import java.util.logging.Logger;
import nl.fontys.sofa.limo.domain.BaseEntity;
import nl.fontys.sofa.limo.domain.component.Component;
import nl.fontys.sofa.limo.domain.component.Icon;
import nl.fontys.sofa.limo.domain.component.Node;
import nl.fontys.sofa.limo.domain.component.event.Event;
import nl.fontys.sofa.limo.domain.component.event.distribution.CauchyDistribution;
import nl.fontys.sofa.limo.domain.component.event.distribution.ChiSquaredDistribution;
import nl.fontys.sofa.limo.domain.component.event.distribution.DiscreteDistribution;
import nl.fontys.sofa.limo.domain.component.event.distribution.Distribution;
import nl.fontys.sofa.limo.domain.component.event.distribution.ExponentionalDistribution;
import nl.fontys.sofa.limo.domain.component.event.distribution.FDistribution;
import nl.fontys.sofa.limo.domain.component.event.distribution.GammaDistribution;
import nl.fontys.sofa.limo.domain.component.event.distribution.LogNormalDistribution;
import nl.fontys.sofa.limo.domain.component.event.distribution.NormalDistribution;
import nl.fontys.sofa.limo.domain.component.event.distribution.PoissonDistribution;
import nl.fontys.sofa.limo.domain.component.event.distribution.TriangularDistribution;
import nl.fontys.sofa.limo.domain.component.event.distribution.WeibullDistribution;
import nl.fontys.sofa.limo.domain.component.event.distribution.input.DoubleInputValue;
import nl.fontys.sofa.limo.domain.component.event.distribution.input.InputValue;
import nl.fontys.sofa.limo.domain.component.event.distribution.input.IntegerInputValue;
import nl.fontys.sofa.limo.domain.component.hub.Hub;
import nl.fontys.sofa.limo.domain.component.hub.Location;
import nl.fontys.sofa.limo.domain.component.leg.Leg;
import nl.fontys.sofa.limo.domain.component.leg.MultiModeLeg;
import nl.fontys.sofa.limo.domain.component.leg.ScheduledLeg;
import nl.fontys.sofa.limo.domain.component.procedure.Procedure;
import nl.fontys.sofa.limo.domain.component.procedure.ProcedureCategory;
import nl.fontys.sofa.limo.domain.component.procedure.value.RangeValue;
import nl.fontys.sofa.limo.domain.component.procedure.value.SingleValue;
import nl.fontys.sofa.limo.domain.component.procedure.value.Value;
import nl.fontys.sofa.limo.domain.component.type.HubType;
import nl.fontys.sofa.limo.domain.component.type.LegType;
import nl.fontys.sofa.limo.domain.component.type.Type;
import nl.fontys.sofa.limo.orientdb.serialization.ContinentSerializer;
import nl.fontys.sofa.limo.orientdb.serialization.CountrySerializer;
import nl.fontys.sofa.limo.orientdb.serialization.ExecutionStateSerializer;
import nl.fontys.sofa.limo.orientdb.serialization.TimeTypeSerializer;
import org.openide.modules.Places;
import org.openide.util.Exceptions;

/**
 * Singleton connection to OrientDB file database. Maintaines schema and allows
 * for communciation to database for the OrientDB DAOs.
 *
 * @author Dominik Kaisers {@literal <d.kaisers@student.fontys.nl>}
 */
public class OrientDBConnector {

    //<editor-fold defaultstate="collapsed" desc="SINGLETON">
    /**
     * Singleton instance holder.
     */
    protected static OrientDBConnector INSTANCE;

    /**
     * Get the singleton instance. Lazy loaded.
     *
     * @return Singleton instance.
     */
    public synchronized static OrientDBConnector getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new OrientDBConnector();
        }

        return INSTANCE;
    }
    //</editor-fold>

    /**
     * Inject different database URL for test purposes.
     */
    static String databaseURL = null;

    /**
     * Helper method for ease of use.
     *
     * @return Connection to OrientDB database.
     */
    public static OObjectDatabaseTx connection() {
        OGlobalConfiguration.STORAGE_KEEP_OPEN.setValue(false);
        return getInstance().getConnection();
    }

    /**
     * Helper method for ease of use.
     */
    public static void close() {
        getInstance().closeConnection();
    }

    protected OObjectDatabaseTx connection;

    protected OrientDBConnector() {
    }

    /**
     * Initializes everything needed for usage of OrientDB Object API if needed.
     */
    protected void checkConnection() {
        // Create new conneciton if needed and set flags
        if (this.connection == null) {
            this.connection = new OObjectDatabaseTx(this.getDatabaseURL());
        }

        // Create database if it does not exist
        if (!this.connection.exists()) {
            try {
                this.connection.create();
                this.createSchema();

                Path path = FileSystems.getDefault().getPath(Places.getUserDirectory() + File.separator + "LIMO_DB");
                String username = System.getProperty("user.name");
                UserPrincipal user = FileSystems.getDefault().getUserPrincipalLookupService().lookupPrincipalByName(username);
                Files.walk(path).forEach((Path filePath) -> {
                    if (Files.isRegularFile(filePath)) {
                        try {
                            Files.setOwner(filePath, user);
                        } catch (IOException ex) {
                            Logger.getGlobal().log(Level.WARNING, "", ex);
                        }
                    }
                });
            } catch (IOException ex) {
                Logger.getLogger(OrientDBConnector.class.getName()).log(Level.WARNING, "", ex);
            }

        }

        // Open connection if it is closed
        if (this.connection.isClosed()) {
            this.connection.open("admin", "admin");
            this.createSchema();
        }

        this.connection.setLazyLoading(false);
    }

    /**
     * Registers classes and serializers with the database.
     */
    protected void createSchema() {
        // Register serializers
        OObjectSerializerContext serializer = new OObjectSerializerContext();
        serializer.bind(new CountrySerializer());
        serializer.bind(new ContinentSerializer());
        serializer.bind(new ExecutionStateSerializer());
        serializer.bind(new TimeTypeSerializer());
        OObjectSerializerHelper.bindSerializerContext(null, serializer);

        // Register classes
        OEntityManager entityManager = this.connection.getEntityManager();
        this.connection.setAutomaticSchemaGeneration(true);
        entityManager.registerEntityClass(BaseEntity.class);
        this.connection.setAutomaticSchemaGeneration(false);
        entityManager.registerEntityClass(Component.class);
        entityManager.registerEntityClass(Icon.class);
        entityManager.registerEntityClass(Node.class);
        entityManager.registerEntityClass(Type.class);
        entityManager.registerEntityClass(Event.class);
        entityManager.registerEntityClass(Distribution.class);
        entityManager.registerEntityClass(CauchyDistribution.class);
        entityManager.registerEntityClass(ChiSquaredDistribution.class);
        entityManager.registerEntityClass(DiscreteDistribution.class);
        entityManager.registerEntityClass(ExponentionalDistribution.class);
        entityManager.registerEntityClass(FDistribution.class);
        entityManager.registerEntityClass(GammaDistribution.class);
        entityManager.registerEntityClass(LogNormalDistribution.class);
        entityManager.registerEntityClass(NormalDistribution.class);
        entityManager.registerEntityClass(PoissonDistribution.class);
        entityManager.registerEntityClass(TriangularDistribution.class);
        entityManager.registerEntityClass(WeibullDistribution.class);
        entityManager.registerEntityClass(InputValue.class);
        entityManager.registerEntityClass(DoubleInputValue.class);
        entityManager.registerEntityClass(IntegerInputValue.class);
        entityManager.registerEntityClass(Hub.class);
        entityManager.registerEntityClass(Location.class);
        entityManager.registerEntityClass(Leg.class);
        entityManager.registerEntityClass(MultiModeLeg.class);
        entityManager.registerEntityClass(ScheduledLeg.class);
        entityManager.registerEntityClass(Procedure.class);
        entityManager.registerEntityClass(ProcedureCategory.class);
        entityManager.registerEntityClass(Value.class);
        entityManager.registerEntityClass(SingleValue.class);
        entityManager.registerEntityClass(RangeValue.class);
        entityManager.registerEntityClass(Type.class);
        entityManager.registerEntityClass(HubType.class);
        entityManager.registerEntityClass(LegType.class);
        
        
        
//        entityManager.registerEntityClasses("nl.fontys.sofa.limo.domain");
        // Create indexes for unique identifier
        OMetadataObject metadata = this.connection.getMetadata();
        OSchemaProxyObject schema = metadata.getSchema();

        OClass clazz = schema.getClass(BaseEntity.class);
        if (!clazz.areIndexed("uniqueIdentifier")) {
            if (!clazz.existsProperty("uniqueIdentifier")) {
                clazz.createProperty("uniqueIdentifier", OType.STRING);
            }

            clazz.createIndex("uuid", OClass.INDEX_TYPE.UNIQUE_HASH_INDEX, "uniqueIdentifier");
        }

        // Create class and property for value
        OClass iivClass = this.connection.getMetadata().getSchema().getClass(IntegerInputValue.class);
        if (iivClass == null) {
            iivClass = this.connection.getMetadata().getSchema().createClass(InputValue.class);
        }
        if (!iivClass.existsProperty("value")) {
            iivClass.createProperty("value", OType.INTEGER);
        }
        OClass divClass = this.connection.getMetadata().getSchema().getClass(DoubleInputValue.class);
        if (divClass == null) {
            divClass = this.connection.getMetadata().getSchema().createClass(DoubleInputValue.class);
        }
        if (!divClass.existsProperty("value")) {
            divClass.createProperty("value", OType.DOUBLE);
        }
    }

    /**
     * Generate the URL to the database. In this case to a folder on the hard
     * drive.
     *
     * @return Database URL.
     */
    protected String getDatabaseURL() {
        if (databaseURL != null) {
            return databaseURL;
        }
        // Path path = FileSystems.getDefault().getPath(System.getProperty("user.home"), "/LIMO");
        Path path = FileSystems.getDefault().getPath(Places.getUserDirectory() + File.separator + "LIMO_DB");
        if (!Files.exists(path)) {
            try {
                Files.createDirectory(path);
                Files.setAttribute(path, "dos:hidden", true);
                String username = System.getProperty("user.name");

                UserPrincipal user = FileSystems.getDefault().getUserPrincipalLookupService().lookupPrincipalByName(username);
                Files.setOwner(path, user);

            } catch (IOException | UnsupportedOperationException ex) {
                // Not on windows, whateverever
                Logger.getLogger(OrientDBConnector.class.getName()).log(Level.WARNING, "Error in creating database", ex);

            }
        }

        return "plocal:" + path.toString();
    }

    /**
     * Get the instances connection object after checking that it is valid.
     *
     * @return Connection object.
     */
    protected OObjectDatabaseTx getConnection() {
        this.checkConnection();
        ODatabaseRecordThreadLocal.INSTANCE.set((ODatabaseRecord) this.connection.getUnderlying().getUnderlying());

        return this.connection;
    }

    /**
     * Close the instances connection object.
     */
    protected void closeConnection() {
        if (this.connection != null && !this.connection.isClosed()) {
            this.connection.close();
        }
    }

    /**
     * Method to delete the database files.
     */
    protected void deleteDatabase() {

        OrientDBConnector.getInstance().closeConnection();
//        String url = this.getDatabaseURL();
        String url = (FileSystems.getDefault().getPath(Places.getUserDirectory() + File.separator + "LIMO_DB")).toString();
        System.out.println("--");

        try {
            File dbFile = new File(url);

            if (dbFile.exists()) {
                File[] files = dbFile.listFiles();
                for (File f : files) {
                    System.out.println("Removing file:" + f.toString() + "|" + f.isFile());
                    f.delete();
                    System.out.println(f.exists());
                }
            }
            dbFile.delete();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Overkill way to clear the database. Since other methods did not work
     * correctly, we considered deleting the database and adding a new one. This
     * is called from the ClearDatabaseAction (limo-view -> actions)
     */
    public void emptyDatabase() {
        this.connection.drop();
        this.connection.close();
        this.connection = new OObjectDatabaseTx(this.getDatabaseURL());
        checkConnection();
    }
}
