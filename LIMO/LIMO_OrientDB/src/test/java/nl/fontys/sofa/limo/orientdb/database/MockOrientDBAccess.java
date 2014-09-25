package nl.fontys.sofa.limo.orientdb.database;

import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;

public class MockOrientDBAccess extends OrientDBAccess {

    public MockOrientDBAccess() {
        connection = new ODatabaseDocumentTx("memory:tests");

        if (!connection.exists()) {
            connection.create();
        } else {
            connection.open("admin", "admin");
        }
    }

    public synchronized static OrientDBAccess getInstance() {
        if (instance == null) {
            instance = new MockOrientDBAccess();
        }
        return instance;
    }

}
