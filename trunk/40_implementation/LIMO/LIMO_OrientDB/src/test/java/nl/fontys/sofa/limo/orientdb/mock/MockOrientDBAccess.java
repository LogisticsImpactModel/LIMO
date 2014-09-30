package nl.fontys.sofa.limo.orientdb.mock;

import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import nl.fontys.sofa.limo.orientdb.database.OrientDBAccess;

public class MockOrientDBAccess extends OrientDBAccess {

    public synchronized static OrientDBAccess getInstance() {
        if (instance == null) {
            instance = new MockOrientDBAccess();
        }
        return instance;
    }

    @Override
    public void closeConnection() {
        if (connection != null) {
            if (!connection.isClosed())
                connection.drop();
            super.closeConnection();
        }
    }

    @Override
    protected void checkConnection() {
        if (connection == null) {
            connection = new ODatabaseDocumentTx("memory:tests");
        }

        if (!connection.exists()) {
            connection.create();
        }

        if (connection.isClosed()) {
            connection.open("admin", "admin");
        }
    }

}
