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
import nl.fontys.sofa.limo.domain.component.Hub;
import nl.fontys.sofa.limo.domain.component.Leg;
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
