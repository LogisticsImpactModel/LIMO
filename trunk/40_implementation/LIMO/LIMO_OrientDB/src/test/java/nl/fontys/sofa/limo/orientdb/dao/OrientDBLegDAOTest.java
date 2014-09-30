/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.orientdb.dao;

import java.util.ArrayList;
import java.util.List;
import static junit.framework.Assert.assertNull;
import nl.fontys.sofa.limo.api.dao.LegDAO;
import nl.fontys.sofa.limo.domain.Actor;
import nl.fontys.sofa.limo.domain.Entry;
import nl.fontys.sofa.limo.domain.Icon;
import nl.fontys.sofa.limo.domain.component.Hub;
import nl.fontys.sofa.limo.domain.component.Leg;
import nl.fontys.sofa.limo.domain.location.Continents;
import nl.fontys.sofa.limo.domain.location.Location;
import nl.fontys.sofa.limo.orientdb.mock.MockOrientDBAccess;
import nl.fontys.sofa.limo.orientdb.mock.OrientDBDAOFactoryMock;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.netbeans.junit.NbTestCase;

public class OrientDBLegDAOTest extends NbTestCase {

    private LegDAO legDAO;

    public OrientDBLegDAOTest(String testCase) {
        super(testCase);
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        OrientDBDAOFactoryMock orientDBDAOFactory = new OrientDBDAOFactoryMock();
        legDAO = orientDBDAOFactory.getLegDAO();
    }

    @After
    public void tearDown() {
        legDAO = null;
        MockOrientDBAccess.getInstance().closeConnection();
    }

    /**
     * Test of findAll method, of class OrientDBLegDAO.
     */
    @Test
    public void testFindAll() {
        List<Leg> hubs = legDAO.findAll();
        assertTrue(hubs.isEmpty());
    }

    /**
     * Test of findById method, of class OrientDBLegDAO.
     */
    @Test
    public void testFindById() {
        Leg leg = legDAO.findById("");
        assertNull(leg);
        leg = legDAO.findById("38129803980");
        assertNull(leg);
    }

    /**
     * Test of insert method, of class OrientDBLegDAO.
     */
    @Test
    public void testInsert() {
        Leg leg = new Leg("11112");
        leg.setActor(new Actor("Super Actor"));
        leg.setStartHub(new Hub("112233", new Location(Continents.AFRICA)));
        leg.setEndHub(new Hub("223311", new Location(Continents.EUROPE)));
        ArrayList<Entry> costs = new ArrayList<Entry>();
        costs.add(new Entry("Cost1", "Costs"));
        costs.add(new Entry("Cost2", "Costs"));
        leg.setCosts(costs);
        ArrayList<Entry> delays = new ArrayList<Entry>();
        delays.add(new Entry("Delay1", "Delay"));
        delays.add(new Entry("Delay2", "Delay"));
        leg.setDelays(delays);
        leg.setIcon(new Icon());
        ArrayList<Entry> leadTimes = new ArrayList<Entry>();
        leadTimes.add(new Entry("LeadTime1", "LeadTime"));
        leadTimes.add(new Entry("LeadTime2", "LeadTime"));
        leg.setDelays(delays);
        leg.setIcon(new Icon());
        leg.setLeadTimes(delays);

        legDAO.insert(leg);
        List<Leg> legs = legDAO.findAll();
        assertEquals(1, legs.size());
        Leg foundLeg = legDAO.findById(legs.get(0).getId());
        assertEquals(leg.getId(), foundLeg.getId());
    }

    /**
     * Test of update method, of class OrientDBLegDAO.
     */
    @Test
    public void testUpdate() {
        String newLegName = "LegXYZ";
        Leg leg = new Leg("1234");
        boolean updateSuccess = legDAO.update(leg);
        assertFalse(updateSuccess);
        legDAO.insert(leg);
        leg = legDAO.findById(leg.getId());
        leg.setIdentifier(newLegName);
        updateSuccess = legDAO.update(leg);
        assertTrue(updateSuccess);
        leg = legDAO.findById(leg.getId());
        assertEquals(newLegName, leg.getIdentifier());
    }

    /**
     * Test of delete method, of class OrientDBLegDAO.
     */
    @Test
    public void testDelete() {
        boolean deleteSuccess = legDAO.delete("");
        assertFalse(deleteSuccess);
        deleteSuccess = legDAO.delete("798319203");
        assertFalse(deleteSuccess);
        Leg leg = new Leg("112233");
        legDAO.insert(leg);
        deleteSuccess = legDAO.delete(leg.getId());
        assertTrue(deleteSuccess);
    }
}
