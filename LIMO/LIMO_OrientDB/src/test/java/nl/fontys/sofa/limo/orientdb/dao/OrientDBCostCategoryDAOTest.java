package nl.fontys.sofa.limo.orientdb.dao;

import java.util.List;
import nl.fontys.sofa.limo.api.dao.CostCategoryDAO;
import nl.fontys.sofa.limo.domain.category.CostCategory;
import nl.fontys.sofa.limo.orientdb.database.OrientDBDAOFactoryMock;
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
        assertTrue(costCategories.isEmpty());
    }

    /**
     * Test of findById method, of class OrientDBCostCategoryDAO.
     */
    @Test
    public void testFindById() {
        CostCategory costCategory = costCategoryDAO.findById("");
        assertNull(costCategory);
        costCategory = costCategoryDAO.findById("38129803980");
        assertNull(costCategory);
    }

    /**
     * Test of insert method, of class OrientDBCostCategoryDAO.
     */
    @Test
    public void testInsert() {
        CostCategory costCategory = new CostCategory("taxes");
        costCategoryDAO.insert(costCategory);
        List<CostCategory> costCategories = costCategoryDAO.findAll();
        assertEquals(1, costCategories.size());
        CostCategory foundCostCategory = costCategoryDAO.findById(costCategories.get(0).getId());
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

    /**
     * Test of getTableName method, of class OrientDBCostCategoryDAO.
     */
    @Test
    public void testGetTableName() {
        assertNotNull(costCategoryDAO.getTableName());
        assertFalse(costCategoryDAO.getTableName().isEmpty());
    }

}
