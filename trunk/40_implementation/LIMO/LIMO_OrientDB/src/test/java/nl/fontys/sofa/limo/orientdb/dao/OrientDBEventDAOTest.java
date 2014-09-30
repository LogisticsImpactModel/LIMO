package nl.fontys.sofa.limo.orientdb.dao;

import java.util.ArrayList;
import java.util.List;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import nl.fontys.sofa.limo.api.dao.EventDAO;
import nl.fontys.sofa.limo.domain.Actor;
import nl.fontys.sofa.limo.domain.Component;
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
        // Actor
        assertEquals(event.getActor().getName(), foundEvent.getActor().getName());
        // Costs
        Entry expectedEntry = event.getCosts().get(0);
        Entry foundEntry = foundEvent.getCosts().get(0);
        assertEquals(expectedEntry.getName(), foundEntry.getName());
        assertEquals(expectedEntry.getValue().getValue(), foundEntry.getValue().getValue());
        // Delays
        expectedEntry = event.getDelays().get(0);
        foundEntry = foundEvent.getDelays().get(0);
        assertEquals(expectedEntry.getName(), foundEntry.getName());
        assertEquals(expectedEntry.getValue().getValue(), foundEntry.getValue().getValue());
        // Sub event
        List<Event> subEvents = event.getEvents();
        assertEquals(event.getEvents().size(), subEvents.size());
        Event foundSubEvent = subEvents.get(0);
        Event expectedSubEvent = event.getEvents().get(0);
        assertEquals(expectedSubEvent.getId(), foundSubEvent.getId());
        assertEquals(expectedSubEvent.getIdentifier(), foundSubEvent.getIdentifier());
        // Sub event costs
        List<Entry> subEventCosts = foundSubEvent.getCosts();
        expectedEntry = subEventCosts.get(0);
        foundEntry = foundSubEvent.getCosts().get(0);
        assertEquals(expectedEntry.getName(), foundEntry.getName());
        assertEquals(expectedEntry.getValue().getValue(), foundEntry.getValue().getValue());
        // Icon
        assertEquals(event.getIcon(), foundEvent.getIcon());
        // Lead times
        List<Entry> expectedLeadTimes = event.getLeadTimes();
        List<Entry> foundLeadTimes = foundEvent.getLeadTimes();
        assertEquals(expectedLeadTimes.size(), foundLeadTimes.size());
        Entry expectedLeadTime = expectedLeadTimes.get(0);
        Entry foundLeadTime = foundLeadTimes.get(0);
        assertEquals(expectedLeadTime.getName(), foundLeadTime.getName());
        assertEquals(expectedLeadTime.getValue().getValue(), foundLeadTime.getValue().getValue());
        // Parent
        Component expectedParent = event.getParent();
        Component foundParent = event.getParent();
        assertEquals(expectedParent.getId(), foundParent.getId());
        assertEquals(expectedParent.getIdentifier(), foundParent.getIdentifier());
        // Propability
        assertEquals(event.getProbability().getClass(), foundEvent.getProbability().getClass());
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
