package nl.fontys.sofa.limo.domain.component;

import nl.fontys.sofa.limo.domain.Actor;
import nl.fontys.sofa.limo.domain.distribution.DiscreteDistribution;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class EventTest {
    
    private Event event;
    private Event subEvent;
    
    @Before
    public void setUp() {
        event = new Event();
        event.setIdentifier("Pirates");
        subEvent = new Event("Repairing");
    }
    
    @After
    public void tearDown() {
        event = null;
        subEvent = null;
    }

    /**
     * Test of addEvent method, of class Event.
     */
    @Test
    public void testAddEvent_Event() {
        assertTrue(event.getEvents().isEmpty());
        event.addEvent(subEvent);
        assertTrue(event.getEvents().contains(subEvent));
        assertTrue(subEvent.getDependency().equals(EventExecutionStateDependency.INDEPENDENT));
        assertEquals(event.getId(), subEvent.getParent().getId());
    }

    /**
     * Test of addEvent method, of class Event.
     */
    @Test
    public void testAddEvent_Event_EventExecutionStateDependency() {
        event.addEvent(subEvent, EventExecutionStateDependency.NOT_EXECUTED);
        assertTrue(subEvent.getDependency().equals(EventExecutionStateDependency.NOT_EXECUTED));
    }

    /**
     * Test of clearExecutionStates method, of class Event.
     */
    @Test
    public void testClearExecutionStates() {
        event.addEvent(subEvent);
        event.clearExecutionStates();
        assertFalse(event.isExecutionState());
        for (Event e : event.getEvents()) {
            assertFalse(e.isExecutionState());
        }
    }

    /**
     * Test of setParent method, of class Event.
     */
    @Test
    public void testSetParent() {
        subEvent.setParent(event);
        assertEquals(event.getId(), subEvent.getParent().getId());
    }

    /**
     * Test of setProbability method, of class Event.
     */
    @Test
    public void testSetProbability() {
        DiscreteDistribution discreteDistribution = new DiscreteDistribution();
        event.setProbability(discreteDistribution);
        assertEquals(discreteDistribution, event.getProbability());
    }

    /**
     * Test of setExecutionState method, of class Event.
     */
    @Test
    public void testSetExecutionState() {
        event.setExecutionState(true);
        assertTrue(event.isExecutionState());
    }

    /**
     * Test of getActor method, of class Event.
     */
    @Test
    public void testGetActor() {
        Actor actor = new Actor("Captain");
        event.setActor(actor);
        assertEquals(actor, event.getActor());
    }
    
}
