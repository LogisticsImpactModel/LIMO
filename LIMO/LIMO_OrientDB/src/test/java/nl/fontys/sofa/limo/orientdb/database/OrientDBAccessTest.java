package nl.fontys.sofa.limo.orientdb.database;

import com.orientechnologies.orient.object.db.OObjectDatabaseTx;
import org.junit.Test;
import static org.junit.Assert.*;

public class OrientDBAccessTest {

    /**
     * Test of getInstance method, of class OrientDBAccess.
     */
    @Test
    public void testGetInstance() {
        OrientDBAccess instance = OrientDBAccess.getInstance();
        assertTrue(instance instanceof OrientDBAccess);
        assertNotNull(instance.getConnection());
        assertFalse(instance.getConnection().isClosed());
    }

    /**
     * Test of closeConnection method, of class OrientDBAccess.
     */
    @Test
    public void testCloseConnection() {
        OrientDBAccess instance = OrientDBAccess.getInstance();
        OObjectDatabaseTx connection = instance.getConnection();
        assertFalse(connection.isClosed());
        instance.closeConnection();
        assertTrue(connection.isClosed());
    }

    /**
     * Test of getDatabaseURL method, of class OrientDBAccess.
     */
    @Test
    public void testGetDatabaseURL() {
        OrientDBAccess instance = OrientDBAccess.getInstance();
        String databaseURL = instance.getDatabaseURL();
        assertNotNull(databaseURL);
    }

}
