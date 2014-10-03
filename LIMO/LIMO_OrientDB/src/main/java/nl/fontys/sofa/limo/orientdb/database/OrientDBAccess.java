package nl.fontys.sofa.limo.orientdb.database;

import com.orientechnologies.orient.object.db.OObjectDatabaseTx;
import java.io.File;
import nl.fontys.sofa.limo.api.database.AbstractDBServer;
import nl.fontys.sofa.limo.domain.BaseEntity;

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

        if (!connection.exists()) {
            connection.create();

            connection.getEntityManager().registerEntityClasses(BaseEntity.class.getPackage().getName());
        }

        if (connection.isClosed()) {
            connection.open("admin", "admin");
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

            if (!connection.exists()) {
                connection.create();
            }

        }

        if (connection.isClosed()) {
            connection.open("admin", "admin");
        }
    }

    @Override
    public String getDatabaseURL() {
        return "plocal:" + System.getProperty("user.home") + File.separator + "LIMO";
    }

}
