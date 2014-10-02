/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.orientdb.dao;

import java.util.ArrayList;
import java.util.List;
import nl.fontys.sofa.limo.api.dao.LegTypeDAO;
import nl.fontys.sofa.limo.domain.Entry;
import nl.fontys.sofa.limo.domain.Icon;
import nl.fontys.sofa.limo.domain.types.LegType;
import nl.fontys.sofa.limo.orientdb.mock.MockOrientDBAccess;
import nl.fontys.sofa.limo.orientdb.mock.OrientDBDAOFactoryMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.netbeans.junit.NbTestCase;

public class OrientDBLegTypeDAOTest extends NbTestCase {

    private LegTypeDAO legTypeDAO;

    public OrientDBLegTypeDAOTest(String testCase) {
        super(testCase);
    }

    @Before
    @Override
    public void setUp() {
        OrientDBDAOFactoryMock orientDBDAOFactory = new OrientDBDAOFactoryMock();
        legTypeDAO = orientDBDAOFactory.getLegTypeDAO();
    }

    @After
    @Override
    public void tearDown() {
        legTypeDAO = null;
        MockOrientDBAccess.getInstance().closeConnection();
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
        LegType legType2 = new LegType();
        legType2.setIdentifier("12345678");
        legType2.setIcon(new Icon());
        List<Entry> costs = new ArrayList<>();
        costs.add(new Entry("Costs1", "Costs"));
        costs.add(new Entry("Costs2", "Costs"));
        legType2.setCosts(costs);
        List<Entry> delays = new ArrayList<>();
        delays.add(new Entry("Delay1", "Delay"));
        delays.add(new Entry("Delay2", "Delay"));
        legType2.setDelays(delays);
        List<Entry> leadTimes = new ArrayList<>();
        leadTimes.add(new Entry("LeadTime1", "LeadTime"));
        leadTimes.add(new Entry("LeadTime2", "LeadTime"));
        legType2.setLeadTimes(leadTimes);
        legType2 = legTypeDAO.insert(legType2);
        legType = legTypeDAO.findById(legType2.getId());
        assertNotNull(legType);
    }

    /**
     * Test of insert method, of class OrientDBLegTypeDAO.
     */
    @Test
    public void testInsert() {
        LegType legType = new LegType("112233", null, null, null, null);
        legType = legTypeDAO.insert(legType);
        List<LegType> legTypes = legTypeDAO.findAll();
        assertEquals(1, legTypes.size());
        LegType legType1 = legTypeDAO.findById(legType.getId());
        assertEquals(legType.getId(), legType1.getId());
        assertEquals(legType.getIdentifier(), legType1.getIdentifier());
    }

    /**
     * Test of update method, of class OrientDBLegTypeDAO.
     */
    @Test
    public void testUpdate() {
        String newLegTypeName = "LegTypeNew";
        LegType legType = new LegType("112233", null, null, null, null);
        boolean updateSuccess = legTypeDAO.update(legType);
        assertFalse(updateSuccess);
        legType = legTypeDAO.insert(legType);
        legType = legTypeDAO.findById(legType.getId());
        legType.setIdentifier(newLegTypeName);
        updateSuccess = legTypeDAO.update(legType);
        assertTrue(updateSuccess);
        legType = legTypeDAO.findById(legType.getId());
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
        LegType legType = new LegType("112233", null, null, null, null);
        legType = legTypeDAO.insert(legType);
        deleteSuccess = legTypeDAO.delete(legType.getId());
        assertTrue(deleteSuccess);
    }

}
