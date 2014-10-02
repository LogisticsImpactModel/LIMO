package nl.fontys.sofa.limo.orientdb.mock;

import nl.fontys.sofa.limo.orientdb.database.OrientDBAccess;

public final class MockOrientDBAccess extends OrientDBAccess {

    public synchronized static OrientDBAccess getInstance() {
        if (instance == null) {
            instance = new MockOrientDBAccess();
        }
        return instance;
    }

    protected MockOrientDBAccess() {
        super();
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
    public String getDatabaseURL() {
        return "memory:limoTests";
    }

}
