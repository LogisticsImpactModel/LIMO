/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.orientdb.dao;

import java.util.List;
import nl.fontys.sofa.limo.domain.types.LegType;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.netbeans.junit.NbTestCase;

/**
 *
 * @author lnx
 */
public class OrientDBLegTypeDAOTest extends NbTestCase {

    private OrientDBLegTypeDAO legTypeDAO;

    public OrientDBLegTypeDAOTest(String testCase) {
        super(testCase);
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        legTypeDAO = new OrientDBLegTypeDAO();
    }

    @After
    public void tearDown() {
        legTypeDAO = null;
    }

    /**
     * Test of findAll method, of class OrientDBLegTypeDAO.
     */
    @Test
    public void testFindAll() {
        List<LegType> costCategories = legTypeDAO.findAll();
        assertTrue(costCategories.isEmpty());
    }

    /**
     * Test of findById method, of class OrientDBLegTypeDAO.
     */
    @Test
    public void testFindById() {
        LegType legType = legTypeDAO.findById("");
        assertNull(legType);
        legType = legTypeDAO.findById("12345678");
        assertNull(legType);
    }

    /**
     * Test of insert method, of class OrientDBLegTypeDAO.
     */
    @Test
    public void testInsert() {
        LegType legType = new LegType("112233", null, null, null, null);
        legTypeDAO.insert(legType);
        List<LegType> legTypes = legTypeDAO.findAll();
        assertEquals(1, legTypes.size());
        LegType foundCostCategory = legTypeDAO.findById("1");
        assertEquals(legType, foundCostCategory);
    }

    /**
     * Test of update method, of class OrientDBLegTypeDAO.
     */
    @Test
    public void testUpdate() {
        String newLegTypeName = "international taxes";
        LegType legType = new LegType("112233",null, null, null, null);
        boolean updateSuccess = legTypeDAO.update(legType);
        assertFalse(updateSuccess);
        legTypeDAO.insert(legType);
        legType = legTypeDAO.findById("1");
        legType.setIdentifier(newLegTypeName);
        updateSuccess = legTypeDAO.update(legType);
        assertTrue(updateSuccess);
        legType = legTypeDAO.findById("1");
        assertEquals(newLegTypeName, legType.getIdentifier());
    }

    /**
     * Test of delete method, of class OrientDBLegTypeDAO.
     */
    @Test
    public void testDelete() {
                boolean deleteSuccess = legTypeDAO.delete("");
        assertFalse(deleteSuccess);
        deleteSuccess = legTypeDAO.delete("112233");
        assertFalse(deleteSuccess);
        LegType legType = new LegType("1122334",null, null, null, null);
        legTypeDAO.insert(legType);
        deleteSuccess = legTypeDAO.delete("1");
        assertTrue(deleteSuccess);
    }

    /**
     * Test of getTableName method, of class OrientDBLegTypeDAO.
     */
    @Test
    public void testGetTableName() {
        assertNotNull(legTypeDAO.getTableName());
        assertFalse(legTypeDAO.getTableName().isEmpty());
    }

}
