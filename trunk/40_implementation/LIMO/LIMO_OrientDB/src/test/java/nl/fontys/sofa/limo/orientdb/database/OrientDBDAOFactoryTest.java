/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.orientdb.database;

import nl.fontys.sofa.limo.api.dao.CostCategoryDAO;
import nl.fontys.sofa.limo.api.dao.DelayCategoryDAO;
import nl.fontys.sofa.limo.api.dao.EventDAO;
import nl.fontys.sofa.limo.api.dao.HubDAO;
import nl.fontys.sofa.limo.api.dao.HubTypeDAO;
import nl.fontys.sofa.limo.api.dao.LegDAO;
import nl.fontys.sofa.limo.api.dao.LegTypeDAO;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class OrientDBDAOFactoryTest {
    
    public OrientDBDAOFactoryTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
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
     * Test of getCostCategoryDAO method, of class OrientDBDAOFactory.
     */
    @Test
    public void testGetCostCategoryDAO() {
        System.out.println("getCostCategoryDAO");
        OrientDBDAOFactory instance = new OrientDBDAOFactory();
        CostCategoryDAO expResult = null;
        CostCategoryDAO result = instance.getCostCategoryDAO();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDelayCategoryDAO method, of class OrientDBDAOFactory.
     */
    @Test
    public void testGetDelayCategoryDAO() {
        System.out.println("getDelayCategoryDAO");
        OrientDBDAOFactory instance = new OrientDBDAOFactory();
        DelayCategoryDAO expResult = null;
        DelayCategoryDAO result = instance.getDelayCategoryDAO();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEventDAO method, of class OrientDBDAOFactory.
     */
    @Test
    public void testGetEventDAO() {
        System.out.println("getEventDAO");
        OrientDBDAOFactory instance = new OrientDBDAOFactory();
        EventDAO expResult = null;
        EventDAO result = instance.getEventDAO();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getHubDAO method, of class OrientDBDAOFactory.
     */
    @Test
    public void testGetHubDAO() {
        System.out.println("getHubDAO");
        OrientDBDAOFactory instance = new OrientDBDAOFactory();
        HubDAO expResult = null;
        HubDAO result = instance.getHubDAO();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getHubTypeDAO method, of class OrientDBDAOFactory.
     */
    @Test
    public void testGetHubTypeDAO() {
        System.out.println("getHubTypeDAO");
        OrientDBDAOFactory instance = new OrientDBDAOFactory();
        HubTypeDAO expResult = null;
        HubTypeDAO result = instance.getHubTypeDAO();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getLegDAO method, of class OrientDBDAOFactory.
     */
    @Test
    public void testGetLegDAO() {
        System.out.println("getLegDAO");
        OrientDBDAOFactory instance = new OrientDBDAOFactory();
        LegDAO expResult = null;
        LegDAO result = instance.getLegDAO();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getLegTypeDAO method, of class OrientDBDAOFactory.
     */
    @Test
    public void testGetLegTypeDAO() {
        System.out.println("getLegTypeDAO");
        OrientDBDAOFactory instance = new OrientDBDAOFactory();
        LegTypeDAO expResult = null;
        LegTypeDAO result = instance.getLegTypeDAO();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
