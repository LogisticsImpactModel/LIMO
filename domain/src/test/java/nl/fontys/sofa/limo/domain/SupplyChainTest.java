/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.domain;

import java.io.File;
import nl.fontys.sofa.limo.domain.component.SupplyChain;
import nl.fontys.sofa.limo.domain.component.hub.Hub;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

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
        supplyChain.setName("SupplyChain1");
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
     * Test of getFilepath method, of class SupplyChain.
     */
    @Test
    public void testGetFilepath() {
        assertNull(supplyChain.getFilepath());
        supplyChain.setFilepath(System.getProperty("user.home") + "/testa");
        assertEquals(System.getProperty("user.home") + "/testa", supplyChain.getFilepath());
    }

    /**
     * Test of setFilepath method, of class SupplyChain.
     */
    @Test
    public void testSetFilepath() {
        supplyChain.setFilepath(System.getProperty("user.home") + "/test");
        assertEquals(System.getProperty("user.home") + "/test", supplyChain.getFilepath());
    }

    /**
     * Test of getStart method, of class SupplyChain.
     */
    @Test
    public void testGetStart() {
        Hub startHub = new Hub();
        supplyChain.setStart(startHub);
        assertEquals(startHub, supplyChain.getStart());
    }

    /**
     * Test of setStart method, of class SupplyChain.
     */
    @Test
    public void testSetStart() {
        Hub startHub = new Hub();
        supplyChain.setStart(startHub);
        assertEquals(startHub, supplyChain.getStart());
    }

    /**
     * Test of createFromFile method, of class SupplyChain.
     *
     */
//    @Test
//    public void testCreateFromFile() {
//
//
//    }
//
//    /**
//     * Test of saveToFile method, of class SupplyChain.
//     *
//     */
//    @Test
//    public void testSaveToFile() {
//    }
}
