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
import nl.fontys.sofa.limo.orientdb.mock.MockOrientDBAccess;
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
        MockOrientDBAccess.getInstance().closeConnection();
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
        timeCategory = timeCategoryDAO.insert(timeCategory);
        List<TimeCategory> timeCategories = timeCategoryDAO.findAll();
        assertEquals(1, timeCategories.size());
        TimeCategory foundTimeCategory = timeCategoryDAO.findById(timeCategories.get(0).getId());
        assertEquals(timeCategory.getId(), foundTimeCategory.getId());
        assertEquals(timeCategory.getIdentifier(), foundTimeCategory.getIdentifier());
    }

    /**
     * Test of update method, of class OrientDBTimeCategoryDAO.
     */
    @Test
    public void testUpdate() {
        String newCategoryName = "international taxes";
        TimeCategory timeCategory = new TimeCategory("taxes");
        boolean updateSuccess = timeCategoryDAO.update(timeCategory);
        assertFalse(updateSuccess);
        timeCategory = timeCategoryDAO.insert(timeCategory);
        timeCategory = timeCategoryDAO.findById(timeCategory.getId());
        timeCategory.setIdentifier(newCategoryName);
        updateSuccess = timeCategoryDAO.update(timeCategory);
        assertTrue(updateSuccess);
        timeCategory = timeCategoryDAO.findById(timeCategory.getId());
        assertEquals(newCategoryName, timeCategory.getIdentifier());
    }

    /**
     * Test of delete method, of class OrientDBTimeCategoryDAO.
     */
    @Test
    public void testDelete() {
        boolean deleteSuccess = timeCategoryDAO.delete("");
        assertFalse(deleteSuccess);
        deleteSuccess = timeCategoryDAO.delete("123345");
        assertFalse(deleteSuccess);
        TimeCategory timeCategory = new TimeCategory("TimeCat");
        timeCategory = timeCategoryDAO.insert(timeCategory);
        deleteSuccess = timeCategoryDAO.delete(timeCategory.getId());
        assertTrue(deleteSuccess);
    }

}
