package nl.fontys.sofa.limo.orientdb;

import java.io.File;
import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;
import nl.fontys.sofa.limo.orientdb.dao.OrientProcedureCategoryDAOTest;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Sven Mäurer and Ben Stassen
 */
public class OrientDBConnectorTest {

    public OrientDBConnectorTest() {
    }

    @BeforeClass
    public static void setUpClass() {

    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        //unset databaseUrl so that the connection string starts with pLocal
        try {
            Field databaseURLField = OrientDBConnector.class.getDeclaredField("databaseURL");
            databaseURLField.setAccessible(true);
            databaseURLField.set(null, null);
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {
            fail("Could not unset databaseUrl");
            Logger.getLogger(OrientProcedureCategoryDAOTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @After
    public void tearDown() {
    }
//
//    /**
//     * Test of getInstance method, of class OrientDBConnector. Implicitly tested
//     * by testConnection()
//     */
//    @Test
//    public void testGetInstance() {
//        System.out.println("getInstance");
//        //OrientDBConnector expResult = null;
//        //OrientDBConnector result = OrientDBConnector.getInstance();
//        //assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        //fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of connection method, of class OrientDBConnector.
//     */
//    @Test
//    public void testConnection() {
//        System.out.println("connection");
//        OrientDBConnector.connection(); //initialize connection
//        assertFalse("Connection should be closed but is open", OrientDBConnector.INSTANCE.connection.isClosed());
//    }
//
//    /**
//     * Test of close method, of class OrientDBConnector.
//     */
//    @Test
//    public void testClose() {
//        testConnection();
//        OrientDBConnector.close();//then close it by getting the instance and calling closeConnection, calling close() on actual connection
//        assertTrue("DBConn instance should be closed", OrientDBConnector.INSTANCE.connection.isClosed());//..so that it should be closed
//
//    }
//
//    /**
//     * Test of checkConnection method, of class OrientDBConnector.
//     * Checkconnection is called by getConnection, which is called by
//     * connection() on OrientDBConnector. No further testing required because of
//     * implicit testing by testClose()
//     */
//    @Test
//    public void testCheckConnection() {
//        System.out.println("checkConnection");
//
//    }
//
//    /**
//     * Test of createSchema method, of class OrientDBConnector. Implicitly
//     * tested by checkConnection
//     */
//    @Test
//    public void testCreateSchema() {
//        System.out.println("createSchema");
//        //OrientDBConnector instance = new OrientDBConnector();
//        //instance.createSchema();
//        // TODO review the generated test code and remove the default call to fail.
//        //fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getDatabaseURL method, of class OrientDBConnector.
//     */
//    @Test
//    public void testGetDatabaseURL() {
//        System.out.println("getDatabaseURL");
//        OrientDBConnector instance = new OrientDBConnector();
//        String expResult = "plocal:" + System.getProperty("user.home") + File.separator + "LIMO_DB";
//        String result = instance.getDatabaseURL();
//        //   assertEquals(expResult, result);
//    }
//
//    /**
//     * Test of getConnection method, of class OrientDBConnector. Implicitly
//     * tested by connection()
//     */
//    @Test
//    public void testGetConnection() {
//        System.out.println("getConnection");
//    }
//
//    /**
//     * Test of closeConnection method, of class OrientDBConnector.
//     */
//    @Test
//    public void testCloseConnection() {
//        OrientDBConnector.connection(); //initialize connection
//        assertFalse("Connection should be closed but is open", OrientDBConnector.INSTANCE.connection.isClosed());
//        OrientDBConnector.close();
//        assertTrue("Connection should be open but is closed", OrientDBConnector.INSTANCE.connection.isClosed());
//    }

}
