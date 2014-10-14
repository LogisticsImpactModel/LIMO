package nl.fontys.sofa.limo.orientdb;

import com.orientechnologies.orient.core.db.ODatabaseRecordThreadLocal;
import com.orientechnologies.orient.core.db.record.ODatabaseRecord;
import com.orientechnologies.orient.core.entity.OEntityManager;
import com.orientechnologies.orient.core.metadata.schema.OClass;
import com.orientechnologies.orient.object.db.OObjectDatabaseTx;
import com.orientechnologies.orient.object.serialization.OObjectSerializerContext;
import com.orientechnologies.orient.object.serialization.OObjectSerializerHelper;
import java.io.File;
import nl.fontys.sofa.limo.domain.BaseEntity;
import nl.fontys.sofa.limo.domain.component.event.Event;
import nl.fontys.sofa.limo.domain.component.event.distribution.Distribution;
import nl.fontys.sofa.limo.domain.component.event.distribution.PoissonDistribution;
import nl.fontys.sofa.limo.domain.component.hub.Hub;
import nl.fontys.sofa.limo.domain.component.leg.Leg;
import nl.fontys.sofa.limo.domain.component.leg.MultiModeLeg;
import nl.fontys.sofa.limo.domain.component.leg.ScheduledLeg;
import nl.fontys.sofa.limo.domain.component.process.ProcedureCategory;
import nl.fontys.sofa.limo.domain.component.type.HubType;
import nl.fontys.sofa.limo.domain.component.type.LegType;
import nl.fontys.sofa.limo.orientdb.serialization.ContinentSerializer;
import nl.fontys.sofa.limo.orientdb.serialization.CountrySerializer;
import nl.fontys.sofa.limo.orientdb.serialization.ExecutionStateSerializer;
import nl.fontys.sofa.limo.orientdb.serialization.TimeTypeSerializer;

/**
 * Singleton connection to OrientDB file database. Maintaines schema and allows for communciation to
 * database for the OrientDB DAOs.
 *
 * @author Dominik Kaisers <d.kaisers@student.fontys.nl>
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
            this.connection.setLazyLoading(false);
        }

        // Create database if it does not exist
        if (!this.connection.exists()) {
            this.connection.create();
            this.createSchema();
        }

        // Open connection if it is closed
        if (this.connection.isClosed()) {
            this.connection.open("admin", "admin");
            this.createSchema();
        }
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
        /*entityManager.registerEntityClass(HubType.class);
        entityManager.registerEntityClass(LegType.class);
        entityManager.registerEntityClass(ProcedureCategory.class);
        entityManager.registerEntityClass(Leg.class);
        entityManager.registerEntityClass(MultiModeLeg.class);
        entityManager.registerEntityClass(ScheduledLeg.class);
        entityManager.registerEntityClass(Hub.class);
        entityManager.registerEntityClass(Event.class);
        entityManager.registerEntityClass(Distribution.class);
        entityManager.registerEntityClass(PoissonDistribution.class);*/
        entityManager.registerEntityClasses("nl.fontys.sofa.limo.domain");

        // Create indexes for unique identifier
        OClass clazz = this.connection.getMetadata().getSchema().getClass(BaseEntity.class);
        if (!clazz.areIndexed("uniqueIdentifier")) {
            clazz.createIndex("uuid", OClass.INDEX_TYPE.UNIQUE_HASH_INDEX, "uniqueIdentifier");
        }
    }

    /**
     * Generate the URL to the database. In this case to a folder on the hard drive.
     *
     * @return Database URL.
     */
    protected String getDatabaseURL() {
        if (databaseURL != null) {
            return databaseURL;
        }
        
        return "plocal:" + System.getProperty("user.home") + File.separator + "LIMO";
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

}
