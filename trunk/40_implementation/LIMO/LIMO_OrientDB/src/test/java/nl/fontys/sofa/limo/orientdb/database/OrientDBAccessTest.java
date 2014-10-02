package nl.fontys.sofa.limo.orientdb.database;

import com.orientechnologies.orient.object.db.OObjectDatabaseTx;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class OrientDBAccessTest {

    private OrientDBAccess instance;

    @Before
    public void setUp() {
        instance = OrientDBAccess.getInstance();
    }

    @After
    public void tearDown() {
        instance.closeConnection();
        instance = null;
    }

    /**
     * Test of getInstance method, of class OrientDBAccess.
     */
    @Test
    public void testGetInstance() {
        assertTrue(instance instanceof OrientDBAccess);
        assertNotNull(instance.getConnection());
        assertFalse(instance.getConnection().isClosed());
    }

    /**
     * Test of closeConnection method, of class OrientDBAccess.
     */
    @Test
    public void testCloseConnection() {
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
        assertNotNull(instance.getDatabaseURL());
    }

}
