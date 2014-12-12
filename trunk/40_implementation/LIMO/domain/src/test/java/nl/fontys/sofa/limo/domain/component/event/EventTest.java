/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.domain.component.event;

import nl.fontys.sofa.limo.domain.component.event.distribution.DiscreteDistribution;
import nl.fontys.sofa.limo.domain.component.event.distribution.Distribution;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author lnx
 */
public class EventTest {

    private Event event;

    @Before
    public void setUp() {
        event = new Event("New Event", null, ExecutionState.INDEPENDENT, null, ExecutionState.INDEPENDENT);
    }

    @After
    public void tearDown() {
        event = null;
    }

    @Test
    public void testGetDescription() {
        assertNull(event.getDescription());
    }

    @Test
    public void testSetDescription() {
        event.setDescription("Test");
        assertEquals("Test", event.getDescription());
    }

    /**
     * Test of getDependency method, of class Event.
     */
    @Test
    public void testGetDependency() {
        assertEquals(ExecutionState.INDEPENDENT, event.getDependency());
    }

    /**
     * Test of setDependency method, of class Event.
     */
    @Test
    public void testSetDependency() {
        event.setDependency(ExecutionState.EXECUTED);
        assertEquals(ExecutionState.EXECUTED, event.getDependency());
    }

    /**
     * Test of getProbability method, of class Event.
     */
    @Test
    public void testGetProbability() {
        assertNull(event.getProbability());
    }

    /**
     * Test of setProbability method, of class Event.
     */
    @Test
    public void testSetProbability() {
        Distribution dist = new DiscreteDistribution();
        event.setProbability(dist);
        assertEquals(dist, event.getProbability());
    }

    /**
     * Test of getExecutionState method, of class Event.
     */
    @Test
    public void testGetExecutionState() {
        event.setExecutionState(ExecutionState.INDEPENDENT);
    }

    /**
     * Test of setExecutionState method, of class Event.
     */
    @Test
    public void testSetExecutionState() {
        event.setExecutionState(ExecutionState.EXECUTED);
        assertEquals(ExecutionState.EXECUTED, event.getExecutionState());
    }

    /**
     * Test of addEvent method, of class Event.
     */
    @Test
    public void testAddEvent() {
        Event newEvent = new Event();
        newEvent.setId("1");
        String newEventName = "New event which is to be added";
        newEvent.setName(newEventName);
        event.getEvents().add(newEvent);//add to list for 1st time
        boolean eventFoundInList = false;
        for (Event e : event.getEvents()) {
            if (e.getName().equals(newEventName)) {
                eventFoundInList = true;
            }
        }
        if (!eventFoundInList) {
            fail("newEvent could not be found in eventlist for event");
        }

        event.getEvents().add(newEvent);//try to add to list a 2nd time, which will not work. However, no response message will be given.
    }

}
