package nl.fontys.sofa.limo.orientdb.mock;

import com.orientechnologies.orient.object.db.OObjectDatabaseTx;
import nl.fontys.sofa.limo.orientdb.database.OrientDBAccess;

public final class MockOrientDBAccess extends OrientDBAccess {

    public synchronized static OrientDBAccess getInstance() {
        if (instance == null) {
            instance = new MockOrientDBAccess();
        }
        return instance;
    }

    @Override
    public void closeConnection() {
        checkConnection();

        if (connection != null) {
            if (connection.exists()) {
                if (connection.isClosed()) {
                    connection.open("admin", "admin");
                }

                connection.drop();
            }
            super.closeConnection();
        }
    }

    @Override
    protected void checkConnection() {
        if (connection == null) {
            connection = new OObjectDatabaseTx(getDatabaseURL());
        }

        if (!connection.exists()) {
            connection.create();
        }

        if (connection.isClosed()) {
            connection.open("admin", "admin");
        }
    }

    @Override
    public String getDatabaseURL() {
        return "memory:limoTests";
    }

}
