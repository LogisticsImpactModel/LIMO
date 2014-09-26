package nl.fontys.sofa.limo.orientdb.dao;

import java.util.ArrayList;
import java.util.List;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import nl.fontys.sofa.limo.api.dao.HubDAO;
import nl.fontys.sofa.limo.domain.Entry;
import nl.fontys.sofa.limo.domain.component.Hub;
import nl.fontys.sofa.limo.domain.types.HubType;
import nl.fontys.sofa.limo.domain.value.SingleValue;
import nl.fontys.sofa.limo.orientdb.database.OrientDBDAOFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.netbeans.junit.NbTestCase;

public class OrientDBHubDAOTest extends NbTestCase {

    private HubDAO hubDAO;
    private static Hub hub;

    @BeforeClass
    public static void setUpClass() {
        List<Entry> costs = new ArrayList<Entry>() {
            {
                new Entry("Unloading", "Loading", new SingleValue(25000));
            }
        };
        hub = new Hub("Rotterdam");
    }

    public OrientDBHubDAOTest(String testCase) {
        super(testCase);
    }

    @Before
    @Override
    public void setUp() {
        OrientDBDAOFactory orientDBDAOFactory = OrientDBDAOFactory.getInstance();
        hubDAO = orientDBDAOFactory.getHubDAO();
    }

    @After
    @Override
    public void tearDown() {
        hubDAO = null;
    }

    /**
     * Test of findAll method, of class OrientDBHubDAO.
     */
    @Test
    public void testFindAll() {
        List<Hub> hubs = hubDAO.findAll();
        assertTrue(hubs.isEmpty());
    }

    /**
     * Test of findById method, of class OrientDBHubDAO.
     */
    @Test
    public void testFindById() {
        Hub hub = hubDAO.findById("");
        assertNull(hub);
        hub = hubDAO.findById("38129803980");
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
