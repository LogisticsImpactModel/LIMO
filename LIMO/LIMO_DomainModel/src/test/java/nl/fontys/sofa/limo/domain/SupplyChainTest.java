/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.domain;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import nl.fontys.sofa.limo.domain.component.Event;
import nl.fontys.sofa.limo.domain.component.EventExecutionStateDependency;
import nl.fontys.sofa.limo.domain.component.Hub;
import nl.fontys.sofa.limo.domain.component.Leg;
import nl.fontys.sofa.limo.domain.exceptions.SupplyChainException;
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
public class SupplyChainTest {

    private SupplyChain supplyChain;

    public SupplyChainTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {

    }

    @Before
    public void setUp() {
        supplyChain = new SupplyChain();
        supplyChain = null;
        supplyChain = new SupplyChain("SupplyChain1");
    }

    @After
    public void tearDown() {
        supplyChain = null;
        File file = new File(System.getProperty("user.home") + "/test");
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * Test of getName method, of class SupplyChain.
     */
    @Test
    public void testGetName() {
        assertEquals("SupplyChain1", supplyChain.getName());
    }

    /**
     * Test of setName method, of class SupplyChain.
     */
    @Test
    public void testSetName() {
        String newName = "SupplyChainNewName";
        supplyChain.setName(newName);
        assertEquals(newName, supplyChain.getName());
    }

    /**
     * Test of getStartHub method, of class SupplyChain.
     */
    @Test
    public void testGetStartHub() {
        Hub startHub = new Hub();
        supplyChain.setStartHub(startHub);
        assertEquals(startHub, supplyChain.getStartHub());
    }

    /**
     * Test of setStartHub method, of class SupplyChain.
     */
    @Test
    public void testSetStartHub() {
        Hub startHub = new Hub();
        supplyChain.setStartHub(startHub);
        assertEquals(startHub, supplyChain.getStartHub());
    }

    /**
     * Test of getLeg method, of class SupplyChain.
     */
    @Test
    public void testGetLeg() {
        assertNull(supplyChain.getLeg("xxx"));
        Hub startHub = new Hub("1", null);
        Hub middleHub = new Hub("2", null);
        Hub endHub = new Hub("3", null);

        Leg outputStartHub = new Leg("1.2");
        Leg outputMiddleHub = new Leg("2.2");

        List<Leg> legs = supplyChain.getAllLegs();
        assertEquals(0, legs.size());

        endHub.setInputLeg(outputMiddleHub);
        outputMiddleHub.setEndHub(endHub);
        middleHub.setOutputLeg(outputMiddleHub);
        middleHub.setInputLeg(outputStartHub);
        startHub.setOutputLeg(outputStartHub);

        supplyChain.setStartHub(startHub);
        assertEquals(outputMiddleHub, supplyChain.getLeg("2.2"));
    }

    /**
     * Test of getHub method, of class SupplyChain.
     */
    @Test
    public void testGetHub() {
        assertNull(supplyChain.getHub("xxx"));
        Hub hub = new Hub("1234", null);
        supplyChain.setStartHub(hub);
        assertEquals(hub, supplyChain.getHub("1234"));
    }

    /**
     * Test of getAllLegs method, of class SupplyChain.
     */
    @Test
    public void testGetAllLegs() {
        Hub startHub = new Hub("1", null);
        Hub middleHub = new Hub("2", null);
        Hub endHub = new Hub("3", null);

        Leg outputStartHub = new Leg("1.2");
        Leg outputMiddleHub = new Leg("2.2");

        List<Leg> legs = supplyChain.getAllLegs();
        assertEquals(0, legs.size());

        endHub.setInputLeg(outputMiddleHub);
        outputMiddleHub.setEndHub(endHub);
        middleHub.setOutputLeg(outputMiddleHub);
        middleHub.setInputLeg(outputStartHub);
        startHub.setOutputLeg(outputStartHub);

        supplyChain.setStartHub(startHub);
        legs = supplyChain.getAllLegs();
        assertEquals(2, legs.size());
    }

    /**
     * Test of getAllHubs method, of class SupplyChain.
     */
    @Test
    public void testGetAllHubs() {
        Hub startHub = new Hub("1", null);
        Hub middleHub = new Hub("2", null);
        Hub endHub = new Hub("3", null);

        Leg outputStartHub = new Leg("1.2");
        Leg outputMiddleHub = new Leg("2.2");

        List<Hub> hubs = supplyChain.getAllHubs();
        assertEquals(0, hubs.size());

        endHub.setInputLeg(outputMiddleHub);
        outputMiddleHub.setEndHub(endHub);
        middleHub.setOutputLeg(outputMiddleHub);
        middleHub.setInputLeg(outputStartHub);
        startHub.setOutputLeg(outputStartHub);
        supplyChain.setStartHub(startHub);
        hubs = supplyChain.getAllHubs();
        assertEquals(3, hubs.size());
    }

    /**
     * Test of getAllActors method, of class SupplyChain.
     */
    @Test
    public void testGetAllActors() {
        Hub startHub = new Hub("1", null);
        HashMap<Entry, Actor> map = new HashMap<Entry, Actor>();
        map.put(new Entry("Test", "Cat1"), new Actor("Actor3"));
        startHub.setCostResponsibilities(map);

        Hub middleHub = new Hub("2", null);
        Hub endHub = new Hub("3", null);
        Leg outputStartHub = new Leg("1.2");
        Leg outputMiddleHub = new Leg("2.2");

        outputStartHub.setActor(new Actor("Actor1"));
        outputMiddleHub.setActor(new Actor("Actor2"));

        List<Actor> actors = supplyChain.getAllActors();
        assertEquals(0, actors.size());

        endHub.setInputLeg(outputMiddleHub);
        outputMiddleHub.setEndHub(endHub);
        middleHub.setOutputLeg(outputMiddleHub);
        middleHub.setInputLeg(outputStartHub);
        startHub.setOutputLeg(outputStartHub);
        supplyChain.setStartHub(startHub);
        actors = supplyChain.getAllActors();
        assertEquals(3, actors.size());
    }

    @Test
    public void addHub() {
        Hub hub = new Hub("1", null);
        Leg leg = new Leg("L");
        Hub hub2 = new Hub("2", null);
        assertTrue(supplyChain.getAllHubs().isEmpty());
        assertTrue(supplyChain.getAllLegs().isEmpty());
        assertTrue(supplyChain.getLastComponent() == null);
        try {
            supplyChain.addHub(hub);
            assertTrue(supplyChain.getAllHubs().size() == 1);
            assertEquals(supplyChain.getHub("1"), hub);
            assertEquals(supplyChain.getStartHub(), hub);
            assertEquals(supplyChain.getLastComponent(), hub);
        } catch (SupplyChainException ex) {
            fail("Could not add a hub to an empty supply chain.");
        }
        try {
            supplyChain.addHub(hub2);
            assertEquals(supplyChain.getLastComponent(), hub);
            fail("Could add a hub when it shouldn't work");
        } catch (SupplyChainException ex) {
        }
        try {
            supplyChain.addLeg(leg);
            assertEquals(supplyChain.getLastComponent(), leg);
        } catch (SupplyChainException ex) {
            fail("Could not add a leg when it should work.");
        }
        assertEquals(supplyChain.getLastComponent(), leg);
        try {
            supplyChain.addHub(hub2);
            assertTrue(supplyChain.getAllHubs().size() == 2);
            assertEquals(supplyChain.getHub("2"), hub2);
            assertEquals(supplyChain.getLastComponent(), hub2);
        } catch (SupplyChainException ex) {
            fail("Could not add a hub when it should work");
        }
    }

    @Test
    public void addLeg() {
        Hub hub = new Hub("1", null);
        Leg leg = new Leg("L");
        Hub hub2 = new Hub("2", null);
        Leg leg2 = new Leg("L2");
        assertTrue(supplyChain.getAllHubs().isEmpty());
        assertTrue(supplyChain.getAllLegs().isEmpty());
        assertTrue(supplyChain.getLastComponent() == null);
        try {
            supplyChain.addLeg(leg);
            fail("Should not be able to add a leg");
        } catch (SupplyChainException ex) {
            assertTrue(supplyChain.getAllHubs().isEmpty());
            assertTrue(supplyChain.getAllLegs().isEmpty());
            assertTrue(supplyChain.getLastComponent() == null);
        }
        try {
            supplyChain.addHub(hub);
            assertEquals(supplyChain.getLastComponent(), hub);
        } catch (SupplyChainException ex) {
            fail("Couldn't add a starthub");
        }
        try {
            supplyChain.addLeg(leg);
            assertTrue(supplyChain.getAllLegs().size() == 1);
            assertEquals(supplyChain.getLeg("L"), leg);
            assertEquals(supplyChain.getLastComponent(), leg);
        } catch (SupplyChainException ex) {
            fail("Could not append a leg to the starthub");
        }
        try {
            supplyChain.addLeg(leg2);
            fail("Should not be able to add a leg");
        } catch (SupplyChainException ex) {
            assertTrue(supplyChain.getAllLegs().size() == 1);
            assertEquals(supplyChain.getLeg("L"), leg);
            assertEquals(supplyChain.getLastComponent(), leg);
        }
        try {
            supplyChain.addHub(hub2);
            assertEquals(supplyChain.getLastComponent(), hub2);
        } catch (SupplyChainException ex) {
            fail("Couldn't add a hub to the leg");
        }
        try {
            supplyChain.addLeg(leg2);
            assertTrue(supplyChain.getAllLegs().size() == 2);
            assertEquals(supplyChain.getLeg("L2"), leg2);
            assertEquals(supplyChain.getLastComponent(), leg2);
        } catch (SupplyChainException ex) {
            fail("Could not append a leg to the hub");
        }
    }

    @Test
    public void addEvent() {
        Hub hub = new Hub("1", null);
        Leg leg = new Leg("L");
        Hub hub2 = new Hub("2", null);
        Leg leg2 = new Leg("L2");
        Event e1 = new Event("E1");
        Event e2 = new Event("E2");
        Event e3 = new Event("E3");
        Event e4 = new Event("E4");
        Event e5 = new Event("E5");
        Event e6 = new Event("E6");
        try {
            supplyChain.addHub(hub);
            supplyChain.addLeg(leg);
            supplyChain.addHub(hub2);
            supplyChain.addLeg(leg2);
        } catch (SupplyChainException ex) {
            fail("Couldn't add hubs/legs");
        }
        assertTrue(hub.getEvents().isEmpty());
        try {
            //INDEPENDENT TO HUB
            supplyChain.addEvent(e1, EventExecutionStateDependency.INDEPENDENT);
            fail("Adding should not work.");
        } catch (SupplyChainException ex) {
        }
        e1.setParent(hub);
        try {
            supplyChain.addEvent(e1, EventExecutionStateDependency.INDEPENDENT);
        } catch (SupplyChainException ex) {
            fail("Adding should work.");
        }
        assertTrue(hub.getEvents().size() == 1);
        assertTrue(e1.getEvents().isEmpty());
        //EXECUTED TO EVENT
        e2.setParent(e1);
        try {
            supplyChain.addEvent(e2, EventExecutionStateDependency.EXECUTED);
        } catch (SupplyChainException ex) {
            fail("Adding should work.");
        }
        assertTrue(hub.getEvents().size() == 1);
        assertTrue(e1.getEvents().size() == 1);
        //INDEPENDENT TO EVENT
        e3.setParent(e1);
        try {
            supplyChain.addEvent(e2, EventExecutionStateDependency.INDEPENDENT);
        } catch (SupplyChainException ex) {
            fail("Adding should work.");
        }
        assertTrue(hub.getEvents().size() == 2);
        assertTrue(e1.getEvents().size() == 1);
        //NOT ECECUTED TO EVENT
        e4.setParent(e1);
        try {
            supplyChain.addEvent(e4, EventExecutionStateDependency.NOT_EXECUTED);
        } catch (SupplyChainException ex) {
            fail("Adding should work.");
        }
        assertTrue(hub.getEvents().size() == 2);
        assertTrue(e1.getEvents().size() == 2);
        assertTrue(hub2.getEvents().isEmpty());
        //EXECUTED TO HUB
        e5.setParent(hub2);
        try {
            supplyChain.addEvent(e5, EventExecutionStateDependency.EXECUTED);
        } catch (SupplyChainException ex) {
            fail("Adding should work.");
        }
        assertTrue(hub.getEvents().size() == 2);
        assertTrue(e1.getEvents().size() == 2);
        assertTrue(hub2.getEvents().size() == 1);
        assertTrue(e4.getEvents().isEmpty());
        //EXECUTED TO EVENT
        e6.setParent(e4);
        try {
            supplyChain.addEvent(e6, EventExecutionStateDependency.EXECUTED);
        } catch (SupplyChainException ex) {
            fail("Adding should work.");
        }
        assertTrue(hub.getEvents().size() == 2);
        assertTrue(e1.getEvents().size() == 2);
        assertTrue(hub2.getEvents().size() == 1);
        assertTrue(e4.getEvents().size() == 1);
    }

    /**
     * Test of createFromFile method, of class SupplyChain.
     *//*
     @Test
     public void testCreateFromFile() {
     SupplyChain.saveToFile(supplyChain, System.getProperty("user.home") + "/test");
     SupplyChain supplyChain2 = SupplyChain.createFromFile(System.getProperty("user.home") + "/test");
     assertEquals(supplyChain.getName(), supplyChain2.getName());

     }*/

    /**
     * Test of saveToFile method, of class SupplyChain.
     *//*
     @Test
     public void testSaveToFile() {
     SupplyChain.saveToFile(supplyChain, System.getProperty("user.home") + "/test");
     SupplyChain supplyChain2 = SupplyChain.createFromFile(System.getProperty("user.home") + "/test");
     assertEquals(supplyChain.getName(), supplyChain2.getName());
     }*/

}
