package nl.fontys.sofa.limo.orientdb.dao;

import java.util.List;
import nl.fontys.sofa.limo.api.dao.CostCategoryDAO;
import nl.fontys.sofa.limo.domain.category.CostCategory;
import nl.fontys.sofa.limo.orientdb.mock.MockOrientDBAccess;
import nl.fontys.sofa.limo.orientdb.mock.OrientDBDAOFactoryMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.netbeans.junit.NbTestCase;

public class OrientDBCostCategoryDAOTest extends NbTestCase {

    private CostCategoryDAO costCategoryDAO;

    public OrientDBCostCategoryDAOTest(String testCase) {
        super(testCase);
    }

    @Before
    @Override
    public void setUp() {
        OrientDBDAOFactoryMock orientDBDAOFactory = new OrientDBDAOFactoryMock();
        costCategoryDAO = orientDBDAOFactory.getCostCategoryDAO();
    }

    @After
    @Override
    public void tearDown() {
        costCategoryDAO = null;
        MockOrientDBAccess.getInstance().closeConnection();
    }

    /**
     * Test of findAll method, of class OrientDBCostCategoryDAO.
     */
    @Test
    public void testFindAll() {
        List<CostCategory> costCategories = costCategoryDAO.findAll();
        assertTrue("CostCategories should be empty, is not",costCategories.isEmpty());
        //find all if no cat has been added yet
        //now find all if one cat is added
        CostCategory costCategory1 = new CostCategory("tadxes");
        costCategoryDAO.insert(costCategory1);
        costCategories = costCategoryDAO.findAll();
        assertTrue("CostCategories should not be empty, but is",!costCategories.isEmpty());
    }

    /**
     * Test of findById method, of class OrientDBCostCategoryDAO.
     */
    @Test
    public void testFindById() {
        //looking for unknown ID, results in null reference
        CostCategory costCategory = costCategoryDAO.findById("");
        assertNull("Should be no result (null)",costCategory);
        //after making a CostCategory and adding it to costCategoryDAO, it should be findable
        CostCategory cc1 = new CostCategory("Fritskosten");
        cc1 = costCategoryDAO.insert(cc1);
        assertNotNull(costCategoryDAO.findById(cc1.getId()));
    }

    /**
     * Test of insert method, of class OrientDBCostCategoryDAO.
     */
    @Test
    public void testInsert() {
        CostCategory costCategory = new CostCategory("taxes");
        costCategory = costCategoryDAO.insert(costCategory);
        List<CostCategory> costCategories = costCategoryDAO.findAll();
        assertTrue("There must be at least one entry, because i just inserted one",costCategories.size()>0);
        int lastInsertedCostCategoryObject = costCategories.size()-1;
        CostCategory foundCostCategory = costCategoryDAO.findById(costCategories.get(lastInsertedCostCategoryObject).getId());
        assertEquals("IDs should match of costCat and foundCostCat",costCategory.getId(), foundCostCategory.getId());
        assertEquals("Identifiers should match of costCat and foundCostCat",costCategory.getIdentifier(), foundCostCategory.getIdentifier());
    }

    /**
     * Test of update method, of class OrientDBCostCategoryDAO.
     */
    @Test
    public void testUpdate() {
        CostCategory costCategory = new CostCategory("taxes");
        boolean updateSuccess = costCategoryDAO.update(costCategory);
        assertFalse(updateSuccess);
        //if an update command is given for a CostCat which is not yet in DB, success will be false
        costCategory = costCategoryDAO.insert(costCategory);
        costCategory = costCategoryDAO.findById(costCategory.getId());
        String newCategoryName = "international taxes";
        costCategory.setIdentifier(newCategoryName);
        updateSuccess = costCategoryDAO.update(costCategory);
        assertTrue(updateSuccess);
        //..but after inserting the CC in DB, retrieving it from DB and then manipulating its name (=identifier), success should be true
        costCategory = costCategoryDAO.findById(costCategory.getId());
        assertEquals(newCategoryName, costCategory.getIdentifier());
        //and then the provided new cat name (=identifier) should be equal to the identifier set for it in DB
    }

    /**
     * Test of delete method, of class OrientDBCostCategoryDAO.
     */
    @Test
    public void testDelete() {
        boolean deleteSuccess = costCategoryDAO.delete("");
        assertFalse(deleteSuccess);
        //if there is no valid reference for a CostCat to delete, success will be false
         
        deleteSuccess = costCategoryDAO.delete("798319203");
        assertFalse(deleteSuccess);
        //bogus reference will result in success being galse
        
        CostCategory costCategory = new CostCategory("taxes");
        costCategory = costCategoryDAO.insert(costCategory);
        deleteSuccess = costCategoryDAO.delete(costCategory.getId());
        assertTrue(deleteSuccess);
        //adding one new CC to DB and afterwards removing it should result in success
    }

}
