/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.orientdb.dao;

import java.util.List;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import nl.fontys.sofa.limo.api.dao.TimeCategoryDAO;
import nl.fontys.sofa.limo.domain.category.TimeCategory;
import nl.fontys.sofa.limo.orientdb.mock.OrientDBDAOFactoryMock;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.netbeans.junit.NbTestCase;

public class OrientDBTimeCategoryDAOTest extends NbTestCase {

    private TimeCategoryDAO timeCategoryDAO;

    public OrientDBTimeCategoryDAOTest(String testCase) {
        super(testCase);
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    @Override
    public void setUp() {
        OrientDBDAOFactoryMock orientDBDAOFactory = new OrientDBDAOFactoryMock();
        timeCategoryDAO = orientDBDAOFactory.getTimeCategoryDAO();
    }

    @After
    @Override
    public void tearDown() {
        timeCategoryDAO = null;
    }

    /**
     * Test of findAll method, of class OrientDBTimeCategoryDAO.
     */
    @Test
    public void testFindAll() {
        List<TimeCategory> timeCategories = timeCategoryDAO.findAll();
        assertTrue(timeCategories.isEmpty());
    }

    /**
     * Test of findById method, of class OrientDBTimeCategoryDAO.
     */
    @Test
    public void testFindById() {
        TimeCategory timeCategory = timeCategoryDAO.findById("");
        assertNull(timeCategory);
        timeCategory = timeCategoryDAO.findById("112233");
        assertNull(timeCategory);
    }

    /**
     * Test of insert method, of class OrientDBTimeCategoryDAO.
     */
    @Test
    public void testInsert() {
        TimeCategory timeCategory = new TimeCategory("time");
        timeCategoryDAO.insert(timeCategory);
        List<TimeCategory> costCategories = timeCategoryDAO.findAll();
        assertEquals(1, costCategories.size());
        TimeCategory foundCostCategory = timeCategoryDAO.findById(costCategories.get(0).getId());
        assertEquals(timeCategory.getId(), foundCostCategory.getId());
        assertEquals(timeCategory.getIdentifier(), foundCostCategory.getIdentifier());
    }

    /**
     * Test of update method, of class OrientDBTimeCategoryDAO.
     */
    @Test
    public void testUpdate() {
        String newCategoryName = "overall timeCat";
        TimeCategory timeCategory = new TimeCategory("timeCat");
        boolean updateSuccess = timeCategoryDAO.update(timeCategory);
        assertFalse(updateSuccess);
        timeCategoryDAO.insert(timeCategory);
        timeCategory = timeCategoryDAO.findById("1");
        timeCategory.setIdentifier(newCategoryName);
        updateSuccess = timeCategoryDAO.update(timeCategory);
        assertTrue(updateSuccess);
        timeCategory = timeCategoryDAO.findById("1");
        assertEquals(newCategoryName, timeCategory.getIdentifier());
    }

    /**
     * Test of delete method, of class OrientDBTimeCategoryDAO.
     */
    @Test
    public void testDelete() {
        boolean deleteSuccess = timeCategoryDAO.delete("");
        assertFalse(deleteSuccess);
        deleteSuccess = timeCategoryDAO.delete("11445566");
        assertFalse(deleteSuccess);
        TimeCategory timeCategory = new TimeCategory("timeCat");
        timeCategoryDAO.insert(timeCategory);
        deleteSuccess = timeCategoryDAO.delete("1");
        assertTrue(deleteSuccess);
    }

    /**
     * Test of getTableName method, of class OrientDBTimeCategoryDAO.
     */
    @Test
    public void testGetTableName() {
        assertNotNull(timeCategoryDAO.getTableName());
        assertFalse(timeCategoryDAO.getTableName().isEmpty());
    }

}
