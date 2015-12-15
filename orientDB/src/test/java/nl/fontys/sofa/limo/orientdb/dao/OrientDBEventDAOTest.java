package nl.fontys.sofa.limo.orientdb.dao;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import nl.fontys.sofa.limo.domain.component.event.Event;
import nl.fontys.sofa.limo.domain.component.event.distribution.PoissonDistribution;
import nl.fontys.sofa.limo.domain.component.procedure.Procedure;
import nl.fontys.sofa.limo.domain.component.procedure.TimeType;
import nl.fontys.sofa.limo.domain.component.procedure.value.RangeValue;
import nl.fontys.sofa.limo.domain.component.procedure.value.SingleValue;
import nl.fontys.sofa.limo.orientdb.OrientDBConnector;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.netbeans.junit.NbTestCase;

/**
 *
 * @author Sven Mäurer
 */
public class OrientDBEventDAOTest extends NbTestCase {

    private static final String EVENT_NAME = "Pirates";
    private OrientDBEventDAO dao;

    public OrientDBEventDAOTest(String testCase) {
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

        dao = new OrientDBEventDAO();
    }

    @After
    @Override
    public void tearDown() {
        for (Event ht : dao.findAll()) {
            dao.delete(ht);
        }
        dao = null;
        OrientDBConnector.close();
    }

    /**
     * Test of findAll method, of class OrientDBEventDAO.
     */
    @Test
    public void testFindAll() {
        List<Event> events = dao.findAll();
        assertTrue(events.isEmpty());
    }
<<<<<<< HEAD
//
//    /**
//     * Test of findById method, of class OrientDBEventDAO.
//     */
//    @Test
//    public void testFindById() {
//        Event event = dao.findById("");
//        assertNull(event);
//        event = dao.findById("38129803980");
//        assertNull(event);
//    }
//
//    /**
//     * Test of insert method, of class OrientDBEventDAO.
//     */
//    @Test
//    public void testInsert() {
//        List<Event> events = dao.findAll();
//        int oldSize = events.size();
//        Event event = createEvent();
//        event = dao.insert(event);
//        events = dao.findAll();
//        assertEquals(oldSize + 1, events.size());
//        Event foundEvent = null;
//        for (Event e : events) {
//            if (e.getId().equals(event.getId())) {
//                foundEvent = e;
//                break;
//            }
//        }
//        assertNotNull(foundEvent);
//        assertEquals(event.getName(), foundEvent.getName());
//
//        // PROCEDURES
//        Procedure expectedProcedure = event.getProcedures().get(0);
//        Procedure foundProcedure = foundEvent.getProcedures().get(0);
//        assertEquals(expectedProcedure.getName(), foundProcedure.getName());
//        assertEquals(expectedProcedure.getCost().getValue(), foundProcedure.getCost().getValue());
//
//        // SUBEVENT
//        List<Event> subEvents = event.getEvents();
//        assertEquals(event.getEvents().size(), subEvents.size());
//        Event foundSubEvent = subEvents.get(0);
//        Event expectedSubEvent = event.getEvents().get(0);
//        assertEquals(expectedSubEvent.getId(), foundSubEvent.getId());
//        assertEquals(expectedSubEvent.getName(), foundSubEvent.getName());
//
//        // SUBEVENT PROCEDURES
//        List<Procedure> subEventProcedures = foundSubEvent.getProcedures();
//        expectedProcedure = subEventProcedures.get(0);
//        foundProcedure = foundSubEvent.getProcedures().get(0);
//        assertEquals(expectedProcedure.getName(), foundProcedure.getName());
//        assertEquals(expectedProcedure.getCost().getMin(), foundProcedure.getCost().getMin());
//        assertEquals(expectedProcedure.getCost().getMax(), foundProcedure.getCost().getMax());
//
//        // TIMES
//        List<Procedure> expectedProcedures = event.getProcedures();
//        List<Procedure> foundProcedures = foundEvent.getProcedures();
//        assertEquals(expectedProcedures.size(), foundProcedures.size());
//        Procedure expectedLeadTime = expectedProcedures.get(0);
//        Procedure foundLeadTime = foundProcedures.get(0);
//        assertEquals(expectedLeadTime.getName(), foundLeadTime.getName());
//        assertEquals(expectedLeadTime.getTime().getValue(), foundLeadTime.getTime().getValue());
//
//        // PROBABILITY
//        assertEquals(event.getProbability().getClass(), foundEvent.getProbability().getClass());
//    }
//
//    /**
//     * Test of update method, of class OrientDBEventDAO.
//     */
//    @Test
//    public void testUpdate() {
//        String newEventName = "Pirate attack";
//        Event event = new Event();
//        event.setName(EVENT_NAME);
//        boolean updateSuccess = dao.update(event);
//        assertFalse(updateSuccess);
//        event = dao.insert(event);
//        event = dao.findById(event.getId());
//        event.setName(newEventName);
//        updateSuccess = dao.update(event);
//        assertTrue(updateSuccess);
//        event = dao.findById(event.getId());
//        assertEquals(newEventName, event.getName());
//    }
//
//    /**
//     * Test of delete method, of class OrientDBEventDAO.
//     */
//    @Test
//    public void testDelete() {
//        boolean deleteSuccess = dao.delete(new Event());
//        assertFalse(deleteSuccess);
//        Event event = new Event();
//        event.setName(EVENT_NAME);
//        event = dao.insert(event);
//        deleteSuccess = dao.delete(event);
//        assertTrue(deleteSuccess);
//    }
//
//    private Event createEvent() {
//        //EVENT
//        Event event = new Event();
//        event.setName(EVENT_NAME);
//        event.setProbability(new PoissonDistribution());
//        ArrayList<Procedure> procedures = new ArrayList<>();
//        Procedure shipping = new Procedure("Shipping", "Transport", new SingleValue(1000), new SingleValue(9000), TimeType.MINUTES, new SingleValue(0));
//        procedures.add(shipping);
//        Procedure attack = new Procedure("Pirate Attack", "Unforeseeable", new SingleValue(10000), new SingleValue(5000), TimeType.MINUTES, new SingleValue(0));
//        procedures.add(attack);
//        event.setProcedures(procedures);
//
//        //SUBEVENT
//        Event subEvent = new Event();
//        subEvent.setName("Repair cannonball damage");
//        ArrayList<Procedure> subEventProcedures = new ArrayList<>();
//        Procedure rapairingProcess = new Procedure("Repairing cannonball damage", "Repairing", new RangeValue(5000, 10000), new SingleValue(7500), TimeType.MINUTES, new SingleValue(0));
//        subEventProcedures.add(rapairingProcess);
//        subEvent.setProcedures(subEventProcedures);
//        event.getEvents().add(subEvent);
//
//        return event;
//    }
}
