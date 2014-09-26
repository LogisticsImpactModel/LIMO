package nl.fontys.sofa.limo.orientdb.dao;

import java.util.List;
import nl.fontys.sofa.limo.api.dao.CostCategoryDAO;
import nl.fontys.sofa.limo.domain.category.CostCategory;
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
        CostCategory costCategory = costCategoryDAO.findById("");
        assertNull("Should be no result (null)",costCategory);
    }

    /**
     * Test of insert method, of class OrientDBCostCategoryDAO.
     */
    @Test
    public void testInsert() {
        CostCategory costCategory = new CostCategory("taxes");
        costCategoryDAO.insert(costCategory);
        List<CostCategory> costCategories = costCategoryDAO.findAll();
        assertTrue("There must be at least one entry, because i just inserted one",costCategories.size()>0);
        int lastInsertedCostCategoryObject = costCategories.size()-1;
        CostCategory foundCostCategory = costCategoryDAO.findById(costCategories.get(lastInsertedCostCategoryObject).getId());
        assertEquals(costCategory.getId(), foundCostCategory.getId());
        assertEquals(costCategory.getIdentifier(), foundCostCategory.getIdentifier());
    }

    /**
     * Test of update method, of class OrientDBCostCategoryDAO.
     */
    @Test
    public void testUpdate() {
        String newCategoryName = "international taxes";
        CostCategory costCategory = new CostCategory("taxes");
        boolean updateSuccess = costCategoryDAO.update(costCategory);
        assertFalse(updateSuccess);
        costCategoryDAO.insert(costCategory);
        costCategory = costCategoryDAO.findById(costCategory.getId());
        costCategory.setIdentifier(newCategoryName);
        updateSuccess = costCategoryDAO.update(costCategory);
        assertTrue(updateSuccess);
        costCategory = costCategoryDAO.findById(costCategory.getId());
        assertEquals(newCategoryName, costCategory.getIdentifier());
    }

    /**
     * Test of delete method, of class OrientDBCostCategoryDAO.
     */
    @Test
    public void testDelete() {
        boolean deleteSuccess = costCategoryDAO.delete("");
        assertFalse(deleteSuccess);
        deleteSuccess = costCategoryDAO.delete("798319203");
        assertFalse(deleteSuccess);
        CostCategory costCategory = new CostCategory("taxes");
        costCategoryDAO.insert(costCategory);
        deleteSuccess = costCategoryDAO.delete(costCategory.getId());
        assertTrue(deleteSuccess);
    }

}
