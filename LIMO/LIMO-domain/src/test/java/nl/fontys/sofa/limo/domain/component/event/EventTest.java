/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.domain.component.event;

import nl.fontys.sofa.limo.domain.component.Component;
import nl.fontys.sofa.limo.domain.component.event.distribution.DiscreteDistribution;
import nl.fontys.sofa.limo.domain.component.event.distribution.Distribution;
import nl.fontys.sofa.limo.domain.component.hub.Hub;
import nl.fontys.sofa.limo.domain.component.leg.Leg;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author lnx
 */
public class EventTest {

    Event event;

    public EventTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        event = new Event();
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getParent method, of class Event.
     */
    @Test
    public void testGetParent() {
        assertNull(event.getParent());
    }

    /**
     * Test of setParent method, of class Event.
     */
    @Test
    public void testSetParent() {
        Event event2 = new Event();
        event.setParent(event2);
        assertEquals(event2, event.getParent());
        Hub hub = new Hub();
        event.setParent(hub);
        assertEquals(hub, event.getParent());
        Leg leg = new Leg();
        event.setParent(leg);
        assertEquals(leg, event.getParent());
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
        event.setDependency(ExecutionState.INDEPENDENT);
        assertEquals(ExecutionState.INDEPENDENT, event.getDependency());
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
        assertNull(event.getExecutionState());
    }

    /**
     * Test of setExecutionState method, of class Event.
     */
    @Test
    public void testSetExecutionState() {
        event.setExecutionState(ExecutionState.EXECUTED);
        assertEquals(ExecutionState.EXECUTED, event.getExecutionState());
    }

}
