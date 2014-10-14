package nl.fontys.sofa.limo.orientdb.dao;

import java.lang.reflect.Field;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import nl.fontys.sofa.limo.api.dao.HubDAO;
import nl.fontys.sofa.limo.domain.component.hub.Hub;
import nl.fontys.sofa.limo.orientdb.OrientDBConnector;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.netbeans.junit.NbTestCase;

public class OrientDBHubDAOTest extends NbTestCase {

    private OrientDBHubDAO dao;

    public OrientDBHubDAOTest(String testCase) {
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

        dao = new OrientDBHubDAO();
    }

    @After
    @Override
    public void tearDown() {
        for (Hub ht : dao.findAll())
            dao.delete(ht);
        dao = null;
        OrientDBConnector.close();
    }

    /**
     * Test of findAll method, of class OrientDBHubDAO.
     */
    @Test
    public void testFindAll() {
        List<Hub> hubs = dao.findAll();
        assertTrue(hubs.isEmpty());
    }

    /**
     * Test of findById method, of class OrientDBHubDAO.
     */
    @Test
    public void testFindById() {
        Hub hub = dao.findById("");
        assertNull(hub);
        hub = dao.findById("38129803980");
        assertNull(hub);
    }

    /**
     * Test of insert method, of class OrientDBHubDAO.
     */
    @Test
    public void testInsert() {
//        hubDAO.insert(hub);
//        List<Hub> hubs = hubDAO.findAll();
//        assertEquals(1, hubs.size());
//        Hub foundHub = hubDAO.findById("1");
//        assertEquals(hub, foundHub);
    }

    /**
     * Test of update method, of class OrientDBHubDAO.
     */
    @Test
    public void testUpdate() {
//        String newHubName = "pirate attack";
//        Hub hub = new Hub("pirates");
//        boolean updateSuccess = hubDAO.update(hub);
//        assertFalse(updateSuccess);
//        hubDAO.insert(hub);
//        hub = hubDAO.findById("1");
//        hub.setIdentifier(newHubName);
//        updateSuccess = hubDAO.update(hub);
//        assertTrue(updateSuccess);
//        hub = hubDAO.findById("1");
//        assertEquals(newHubName, hub.getIdentifier());
    }

    /**
     * Test of delete method, of class OrientDBHubDAO.
     */
    @Test
    public void testDelete() {
//        boolean deleteSuccess = hubDAO.delete("");
//        assertFalse(deleteSuccess);
//        deleteSuccess = hubDAO.delete("798319203");
//        assertFalse(deleteSuccess);
//        Hub hub = new Hub("pirates");
//        hubDAO.insert(hub);
//        deleteSuccess = hubDAO.delete("1");
//        assertTrue(deleteSuccess);
    }

}
