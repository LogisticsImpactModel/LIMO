package nl.fontys.sofa.limo.orientdb.database;

import com.orientechnologies.orient.core.db.ODatabaseRecordThreadLocal;
import com.orientechnologies.orient.object.db.OObjectDatabasePool;
import com.orientechnologies.orient.object.db.OObjectDatabaseTx;
import java.io.File;
import nl.fontys.sofa.limo.api.database.AbstractDBServer;
import nl.fontys.sofa.limo.domain.BaseEntity;
import nl.fontys.sofa.limo.domain.category.CostCategory;
import nl.fontys.sofa.limo.domain.category.TimeCategory;

public class OrientDBAccess extends AbstractDBServer<OObjectDatabaseTx> {

    protected static OrientDBAccess instance;

    public synchronized static OrientDBAccess getInstance() {
        if (instance == null) {
            instance = new OrientDBAccess();
        }
        return instance;
    }

    protected OrientDBAccess() {
        connection = new OObjectDatabaseTx(getDatabaseURL());
        ODatabaseRecordThreadLocal.INSTANCE.set(connection.getUnderlying());

        if (!connection.exists()) {
            connection.create();
            registerClasses();
        }

        if (connection.isClosed()) {
            connection.open("admin", "admin");
            registerClasses();
        }
    }

    @Override
    public void closeConnection() {
        connection.close();
    }

    @Override
    protected void checkConnection() {
        if (connection == null) {
            connection = new OObjectDatabaseTx(getDatabaseURL());
            ODatabaseRecordThreadLocal.INSTANCE.set(connection.getUnderlying());

            if (!connection.exists()) {
                connection.create();
            }

        }

        if (connection.isClosed()) {
            connection.open("admin", "admin");
            registerClasses();
        }
    }

    @Override
    public String getDatabaseURL() {
        return "plocal:" + System.getProperty("user.home") + File.separator + "LIMO";
    }

    protected void registerClasses() {
        connection.getEntityManager().registerEntityClasses("nl.fontys.sofa.limo.domain");
        connection.getEntityManager().registerEntityClass(TimeCategory.class);
    }

}
