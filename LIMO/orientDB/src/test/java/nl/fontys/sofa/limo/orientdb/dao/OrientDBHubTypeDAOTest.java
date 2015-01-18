package nl.fontys.sofa.limo.orientdb.dao;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import nl.fontys.sofa.limo.domain.component.Icon;
import nl.fontys.sofa.limo.domain.component.procedure.Procedure;
import nl.fontys.sofa.limo.domain.component.procedure.TimeType;
import nl.fontys.sofa.limo.domain.component.procedure.value.SingleValue;
import nl.fontys.sofa.limo.domain.component.type.HubType;
import nl.fontys.sofa.limo.orientdb.OrientDBConnector;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.netbeans.junit.NbTestCase;

/**
 *
 * @author Sven Mäurer
 */
public class OrientDBHubTypeDAOTest extends NbTestCase {

    private OrientDBHubTypeDAO dao;

    public OrientDBHubTypeDAOTest(String testCase) {
        super(testCase);
    }

    @Before
    @Override
    public void setUp() {
        try {
            Field databaseURLField = OrientDBConnector.class.getDeclaredField("databaseURL");
            databaseURLField.setAccessible(true);
            databaseURLField.set(null, "memory:tests");
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {
            Logger.getLogger(OrientProcedureCategoryDAOTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        dao = new OrientDBHubTypeDAO();
    }

    @After
    @Override
    public void tearDown() {
        for (HubType ht : dao.findAll()) {
            dao.delete(ht);
        }

        dao = null;
        OrientDBConnector.close();
    }

    /**
     * Test of findAll method, of class OrientDBHubTypeDAO.
     */
    @Test
    public void testFindAll() {
        List<HubType> hubTypes = dao.findAll();
        assertTrue(hubTypes.isEmpty());
    }

    /**
     * Test of findById method, of class OrientDBHubTypeDAO.
     */
    @Test
    public void testFindById() {
        HubType hubType = dao.findById("");
        assertNull(hubType);
        HubType hubType2 = new HubType();
        hubType2.setName("12345678");
        hubType2.setIcon(new Icon());
        List<Procedure> procedures = new ArrayList<>();
        procedures.add(new Procedure("Costs1", "Costs", new SingleValue(1), new SingleValue(2), TimeType.MINUTES));
        procedures.add(new Procedure("Costs2", "Costs", new SingleValue(3), new SingleValue(4), TimeType.MINUTES));
        hubType2.setProcedures(procedures);
        hubType2 = dao.insert(hubType2);
        hubType = dao.findById(hubType2.getId());
        assertNotNull(hubType);
        List<HubType> hubTypes = dao.findAll();
        assertEquals(1, hubTypes.size());
    }

    /**
     * Test of insert method, of class OrientDBHubTypeDAO.
     */
    @Test
    public void testInsert() {
        HubType hubType = new HubType();
        hubType.setName("112233");
        hubType = dao.insert(hubType);
        List<HubType> hubTypes = dao.findAll();
        assertEquals(1, hubTypes.size());
        HubType hubType1 = dao.findById(hubType.getId());
        assertEquals(hubType.getId(), hubType1.getId());
        assertEquals(hubType.getName(), hubType1.getName());
    }

    /**
     * Test of update method, of class OrientDBHubTypeDAO.
     */
    @Test
    public void testUpdate() {
        String newHubTypeName = "HubTypeNew";
        HubType hubType = new HubType();
        hubType.setName("112233");
        boolean updateSuccess = dao.update(hubType);
        assertFalse(updateSuccess);
        hubType = dao.insert(hubType);
        hubType = dao.findById(hubType.getId());
        hubType.setName(newHubTypeName);
        updateSuccess = dao.update(hubType);
        assertTrue(updateSuccess);
        hubType = dao.findById(hubType.getId());
        assertEquals(newHubTypeName, hubType.getName());
        List<HubType> hubTypes = dao.findAll();
        assertEquals(1, hubTypes.size());
    }

    /**
     * Test of delete method, of class OrientDBHubTypeDAO.
     */
    @Test
    public void testDelete() {
        boolean deleteSuccess = dao.delete(new HubType());
        assertFalse(deleteSuccess);
        HubType hubType = new HubType();
        hubType.setName("112233");
        hubType = dao.insert(hubType);
        deleteSuccess = dao.delete(hubType);
        assertTrue(deleteSuccess);
    }
}
