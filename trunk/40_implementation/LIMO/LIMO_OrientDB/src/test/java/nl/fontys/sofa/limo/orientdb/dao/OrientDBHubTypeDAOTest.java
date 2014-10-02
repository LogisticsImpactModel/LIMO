package nl.fontys.sofa.limo.orientdb.dao;

import java.util.ArrayList;
import java.util.List;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import nl.fontys.sofa.limo.api.dao.HubTypeDAO;
import nl.fontys.sofa.limo.domain.Entry;
import nl.fontys.sofa.limo.domain.Icon;
import nl.fontys.sofa.limo.domain.types.HubType;
import nl.fontys.sofa.limo.orientdb.mock.MockOrientDBAccess;
import nl.fontys.sofa.limo.orientdb.mock.OrientDBDAOFactoryMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.netbeans.junit.NbTestCase;

public class OrientDBHubTypeDAOTest extends NbTestCase {

    private HubTypeDAO hubTypeDAO;

    public OrientDBHubTypeDAOTest(String testCase) {
        super(testCase);
    }

    @Before
    @Override
    public void setUp() {
        OrientDBDAOFactoryMock orientDBDAOFactory = new OrientDBDAOFactoryMock();
        hubTypeDAO = orientDBDAOFactory.getHubTypeDAO();
    }

    @After
    @Override
    public void tearDown() {
        hubTypeDAO = null;
        MockOrientDBAccess.getInstance().closeConnection();
    }

    /**
     * Test of findAll method, of class OrientDBHubTypeDAO.
     */
    @Test
    public void testFindAll() {
        List<HubType> hubTypes = hubTypeDAO.findAll();
        assertTrue(hubTypes.isEmpty());
    }

    /**
     * Test of findById method, of class OrientDBHubTypeDAO.
     */
    @Test
    public void testFindById() {
        HubType hubType = hubTypeDAO.findById("");
        assertNull(hubType);
        HubType hubType2 = new HubType();
        hubType2.setIdentifier("12345678");
        hubType2.setIcon(new Icon());
        List<Entry> costs = new ArrayList<>();
        costs.add(new Entry("Costs1", "Costs"));
        costs.add(new Entry("Costs2", "Costs"));
        hubType2.setCosts(costs);
        List<Entry> delays = new ArrayList<>();
        delays.add(new Entry("Delay1", "Delay"));
        delays.add(new Entry("Delay2", "Delay"));
        hubType2.setDelays(delays);
        List<Entry> leadTimes = new ArrayList<>();
        leadTimes.add(new Entry("LeadTime1", "LeadTime"));
        leadTimes.add(new Entry("LeadTime2", "LeadTime"));
        hubType2.setLeadTimes(leadTimes);
        hubType2 = hubTypeDAO.insert(hubType2);
        hubType = hubTypeDAO.findById(hubType2.getId());
        assertNotNull(hubType);
    }

    /**
     * Test of insert method, of class OrientDBHubTypeDAO.
     */
    @Test
    public void testInsert() {
        HubType hubType = new HubType("112233", null, null, null, null);
        hubType = hubTypeDAO.insert(hubType);
        List<HubType> hubTypes = hubTypeDAO.findAll();
        assertEquals(1, hubTypes.size());
        HubType legType1 = hubTypeDAO.findById(hubType.getId());
        assertEquals(hubType.getId(), legType1.getId());
        assertEquals(hubType.getIdentifier(), legType1.getIdentifier());
    }

    /**
     * Test of update method, of class OrientDBHubTypeDAO.
     */
    @Test
    public void testUpdate() {
        String newHubTypeName = "HubTypeNew";
        HubType hubType = new HubType("112233", null, null, null, null);
        boolean updateSuccess = hubTypeDAO.update(hubType);
        assertFalse(updateSuccess);
        hubType = hubTypeDAO.insert(hubType);
        hubType = hubTypeDAO.findById(hubType.getId());
        hubType.setIdentifier(newHubTypeName);
        updateSuccess = hubTypeDAO.update(hubType);
        assertTrue(updateSuccess);
        hubType = hubTypeDAO.findById(hubType.getId());
        assertEquals(newHubTypeName, hubType.getIdentifier());
    }

    /**
     * Test of delete method, of class OrientDBHubTypeDAO.
     */
    @Test
    public void testDelete() {
        boolean deleteSuccess = hubTypeDAO.delete("");
        assertFalse(deleteSuccess);
        deleteSuccess = hubTypeDAO.delete("112233");
        assertFalse(deleteSuccess);
        HubType hubType = new HubType("112233", null, null, null, null);
        hubType = hubTypeDAO.insert(hubType);
        deleteSuccess = hubTypeDAO.delete(hubType.getId());
        assertTrue(deleteSuccess);
    }
}
