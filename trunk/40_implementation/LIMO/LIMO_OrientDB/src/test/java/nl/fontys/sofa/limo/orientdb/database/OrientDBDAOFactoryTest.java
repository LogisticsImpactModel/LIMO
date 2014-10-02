package nl.fontys.sofa.limo.orientdb.database;

import nl.fontys.sofa.limo.api.dao.CostCategoryDAO;
import nl.fontys.sofa.limo.api.dao.EventDAO;
import nl.fontys.sofa.limo.api.dao.HubDAO;
import nl.fontys.sofa.limo.api.dao.HubTypeDAO;
import nl.fontys.sofa.limo.api.dao.IconDAO;
import nl.fontys.sofa.limo.api.dao.LegDAO;
import nl.fontys.sofa.limo.api.dao.LegTypeDAO;
import nl.fontys.sofa.limo.api.dao.TimeCategoryDAO;
import nl.fontys.sofa.limo.orientdb.dao.OrientDBCostCategoryDAO;
import nl.fontys.sofa.limo.orientdb.dao.OrientDBEventDAO;
import nl.fontys.sofa.limo.orientdb.dao.OrientDBHubDAO;
import nl.fontys.sofa.limo.orientdb.dao.OrientDBHubTypeDAO;
import nl.fontys.sofa.limo.orientdb.dao.OrientDBIconDAO;
import nl.fontys.sofa.limo.orientdb.dao.OrientDBLegDAO;
import nl.fontys.sofa.limo.orientdb.dao.OrientDBLegTypeDAO;
import nl.fontys.sofa.limo.orientdb.dao.OrientDBTimeCategoryDAO;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.netbeans.junit.NbTestCase;

public class OrientDBDAOFactoryTest extends NbTestCase {

    private OrientDBDAOFactory instance;

    public OrientDBDAOFactoryTest(String testCase) {
        super(testCase);
    }

    @Before
    @Override
    public void setUp() {
        instance = OrientDBDAOFactory.getInstance();
    }

    @After
    @Override
    public void tearDown() {
        instance = null;
    }

    /**
     * Test of getCostCategoryDAO method, of class OrientDBDAOFactory.
     */
    @Test
    public void testGetCostCategoryDAO() {
        CostCategoryDAO result = instance.getCostCategoryDAO();
        assertTrue(result instanceof OrientDBCostCategoryDAO);
    }

    /**
     * Test of getEventDAO method, of class OrientDBDAOFactory.
     */
    @Test
    public void testGetEventDAO() {
        EventDAO result = instance.getEventDAO();
        assertTrue(result instanceof OrientDBEventDAO);
    }

    /**
     * Test of getHubDAO method, of class OrientDBDAOFactory.
     */
    @Test
    public void testGetHubDAO() {
        HubDAO result = instance.getHubDAO();
        assertTrue(result instanceof OrientDBHubDAO);
    }

    /**
     * Test of getHubTypeDAO method, of class OrientDBDAOFactory.
     */
    @Test
    public void testGetHubTypeDAO() {
        HubTypeDAO result = instance.getHubTypeDAO();
        assertTrue(result instanceof OrientDBHubTypeDAO);
    }

    /**
     * Test of getLegDAO method, of class OrientDBDAOFactory.
     */
    @Test
    public void testGetLegDAO() {
        LegDAO result = instance.getLegDAO();
        assertTrue(result instanceof OrientDBLegDAO);
    }

    /**
     * Test of getLegTypeDAO method, of class OrientDBDAOFactory.
     */
    @Test
    public void testGetLegTypeDAO() {
        LegTypeDAO result = instance.getLegTypeDAO();
        assertTrue(result instanceof OrientDBLegTypeDAO);
    }

    /**
     * Test of getInstance method, of class OrientDBDAOFactory.
     */
    @Test
    public void testGetInstance() {
        OrientDBDAOFactory result = OrientDBDAOFactory.getInstance();
        assertTrue(result instanceof OrientDBDAOFactory);
    }

    /**
     * Test of getTimeCategoryDAO method, of class OrientDBDAOFactory.
     */
    @Test
    public void testGetTimeCategoryDAO() {
        TimeCategoryDAO result = instance.getTimeCategoryDAO();
        assertTrue(result instanceof OrientDBTimeCategoryDAO);
    }

    /**
     * Test of getIconDAO method, of class OrientDBDAOFactory.
     */
    @Test
    public void testGetIconDAO() {
        IconDAO result = instance.getIconDAO();
        assertTrue(result instanceof OrientDBIconDAO);
    }

}
