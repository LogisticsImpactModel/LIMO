package nl.fontys.sofa.limo.orientdb.dao;

import java.util.ArrayList;
import java.util.List;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import nl.fontys.sofa.limo.api.dao.EventDAO;
import nl.fontys.sofa.limo.domain.Actor;
import nl.fontys.sofa.limo.domain.Entry;
import nl.fontys.sofa.limo.domain.Icon;
import nl.fontys.sofa.limo.domain.component.Event;
import nl.fontys.sofa.limo.domain.distribution.PoissonDistribution;
import nl.fontys.sofa.limo.domain.value.RangeValue;
import nl.fontys.sofa.limo.domain.value.SingleValue;
import nl.fontys.sofa.limo.orientdb.mock.MockOrientDBAccess;
import nl.fontys.sofa.limo.orientdb.mock.OrientDBDAOFactoryMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.netbeans.junit.NbTestCase;

public class OrientDBEventDAOTest extends NbTestCase {

    private static final String EVENT_NAME = "Pirates";

    private EventDAO eventDAO;

    public OrientDBEventDAOTest(String testCase) {
        super(testCase);
    }

    @Before
    @Override
    public void setUp() {
        OrientDBDAOFactoryMock orientDBDAOFactory = new OrientDBDAOFactoryMock();
        eventDAO = orientDBDAOFactory.getEventDAO();
    }

    @After
    @Override
    public void tearDown() {
        eventDAO = null;
        MockOrientDBAccess.getInstance().closeConnection();
    }

    /**
     * Test of findAll method, of class OrientDBEventDAO.
     */
    @Test
    public void testFindAll() {
        List<Event> events = eventDAO.findAll();
        assertTrue(events.isEmpty());
    }

    /**
     * Test of findById method, of class OrientDBEventDAO.
     */
    @Test
    public void testFindById() {
        Event event = eventDAO.findById("");
        assertNull(event);
        event = eventDAO.findById("38129803980");
        assertNull(event);
    }

    /**
     * Test of insert method, of class OrientDBEventDAO.
     */
    @Test
    public void testInsert() {
        Event event = createEvent();
        eventDAO.insert(event);
        List<Event> events = eventDAO.findAll();
        assertEquals(1, events.size());
        Event foundEvent = eventDAO.findById(events.get(0).getId());
        assertEquals(event.getId(), foundEvent.getId());
        assertEquals(event.getIdentifier(), foundEvent.getIdentifier());
        assertEquals(event.getActor().getName(), foundEvent.getActor().getName());
        Entry expectedEntry = event.getCosts().get(0);
        Entry foundEntry = foundEvent.getCosts().get(0);
        assertEquals(expectedEntry.getName(), foundEntry.getName());
        assertEquals(expectedEntry.getValue().getValue(), foundEntry.getValue().getValue());
    }

    /**
     * Test of update method, of class OrientDBEventDAO.
     */
    @Test
    public void testUpdate() {
        String newEventName = "Pirate attack";
        Event event = new Event(EVENT_NAME);
        boolean updateSuccess = eventDAO.update(event);
        assertFalse(updateSuccess);
        eventDAO.insert(event);
        event = eventDAO.findById(event.getId());
        event.setIdentifier(newEventName);
        updateSuccess = eventDAO.update(event);
        assertTrue(updateSuccess);
        event = eventDAO.findById(event.getId());
        assertEquals(newEventName, event.getIdentifier());
    }

    /**
     * Test of delete method, of class OrientDBEventDAO.
     */
    @Test
    public void testDelete() {
        boolean deleteSuccess = eventDAO.delete("");
        assertFalse(deleteSuccess);
        deleteSuccess = eventDAO.delete("798319203");
        assertFalse(deleteSuccess);
        Event event = new Event(EVENT_NAME);
        eventDAO.insert(event);
        deleteSuccess = eventDAO.delete(event.getId());
        assertTrue(deleteSuccess);
    }

    private Event createEvent() {
        Event event = new Event(EVENT_NAME);

        Actor actor = new Actor("Hermes");
        event.setActor(actor);

        ArrayList<Entry> costs = new ArrayList<>();
        Entry costEntry = new Entry("Shipping", "Transport");
        costEntry.setValue(new SingleValue(20000));
        costs.add(costEntry);
        event.setCosts(costs);

        ArrayList<Entry> delays = new ArrayList<>();
        Entry delayEntry = new Entry("Pirate Attack", "Unforeseeable");
        delayEntry.setValue(new SingleValue(250000));
        event.setDelays(delays);

        Event subEvent = new Event("Repair cannonball damage");
        List<Entry> subEventCosts = new ArrayList<>();
        Entry rapairingEntry = new Entry("Repairing cannonball damage", "Repairing");
        rapairingEntry.setValue(new RangeValue(2000, 10000));
        subEventCosts.add(rapairingEntry);
        subEvent.setCosts(subEventCosts);
        event.addEvent(subEvent);

        event.setIcon(new Icon(new byte[0]));

        List<Entry> leadTimes = new ArrayList<>();
        Entry leadTimeEntry = new Entry("", "");
        leadTimeEntry.setValue(new SingleValue(3000));
        leadTimes.add(leadTimeEntry);
        event.setLeadTimes(leadTimes);

        Event parentEvent = new Event("Pirate Parent Event");
        event.setParent(parentEvent);

        event.setProbability(new PoissonDistribution());

        return event;
    }

}
