/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.orientdb;

import com.orientechnologies.orient.object.db.OObjectDatabaseTx;
import java.io.File;
import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;
import nl.fontys.sofa.limo.orientdb.dao.OrientProcedureCategoryDAOTest;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Ben
 */
public class OrientDBConnectorTest {
    
    public OrientDBConnectorTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
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
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getInstance method, of class OrientDBConnector.
     */
    @Test
    public void testGetInstance() {
        System.out.println("getInstance");
        //OrientDBConnector expResult = null;
        //OrientDBConnector result = OrientDBConnector.getInstance();
        //assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of connection method, of class OrientDBConnector.
     */
    @Test
    public void testConnection() {
        System.out.println("connection");
        //OObjectDatabaseTx expResult = null;
        //OObjectDatabaseTx result = OrientDBConnector.connection();
        //assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of close method, of class OrientDBConnector.
     */
    @Test
    public void testClose() {
        //System.out.println("close");
        //OrientDBConnector.close();
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of checkConnection method, of class OrientDBConnector.
     */
    @Test
    public void testCheckConnection() {
        System.out.println("checkConnection");
        //OrientDBConnector instance = new OrientDBConnector();
        //instance.checkConnection();
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of createSchema method, of class OrientDBConnector.
     */
    @Test
    public void testCreateSchema() {
        System.out.println("createSchema");
        //OrientDBConnector instance = new OrientDBConnector();
        //instance.createSchema();
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getDatabaseURL method, of class OrientDBConnector.
     */
    @Test
    public void testGetDatabaseURL() {
       System.out.println("getDatabaseURL");
       OrientDBConnector instance = new OrientDBConnector();
       String expResult = "plocal:" + System.getProperty("user.home") + File.separator + "LIMO";
       String result = instance.getDatabaseURL();
       assertEquals(expResult, result);
    }

    /**
     * Test of getConnection method, of class OrientDBConnector.
     */
    @Test
    public void testGetConnection() {
        System.out.println("getConnection");
        //fail("FINISH");
    }

    /**
     * Test of closeConnection method, of class OrientDBConnector.
     */
    @Test
    public void testCloseConnection() {
        OrientDBConnector.connection(); //initialize connection
        assertFalse("Connection should be closed but is open", OrientDBConnector.INSTANCE.connection.isClosed());
        OrientDBConnector.close();
        assertTrue("Connection should be open but is closed", OrientDBConnector.INSTANCE.connection.isClosed());
    }
    
}
