/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.domain;

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
public class BaseEntityTest {

    BaseEntity entity;

    public BaseEntityTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        entity = new BaseEntity();
    }

    @After
    public void tearDown() {
        entity = null;
    }

    /**
     * Test of getId method, of class BaseEntity.
     */
    @Test
    public void testGetId() {
        assertNull(entity.getId());
        entity.setId("123");
        assertEquals("123", entity.getId());
    }

    /**
     * Test of setId method, of class BaseEntity.
     */
    @Test
    public void testSetId() {
        entity.setId("1234");
        assertEquals("1234", entity.getId());
    }

    /**
     * Test of getName method, of class BaseEntity.
     */
    @Test
    public void testGetName() {
        assertNull(entity.getName());
        entity.setName("testName");
        assertEquals("testName", entity.getName());
    }

    /**
     * Test of setName method, of class BaseEntity.
     */
    @Test
    public void testSetName() {
        entity.setName("testName2");
        assertEquals("testName2", entity.getName());
    }

    /**
     * Test of getLastUpdate method, of class BaseEntity.
     */
    @Test
    public void testGetLastUpdate() {
        assertEquals(0, entity.getLastUpdate());
        entity.setLastUpdate(123);
        assertEquals(123, entity.getLastUpdate());
    }

    /**
     * Test of setLastUpdate method, of class BaseEntity.
     */
    @Test
    public void testSetLastUpdate() {
        entity.setLastUpdate(1234);
        assertEquals(1234, entity.getLastUpdate());
    }

    /**
     * Test of getUniqueIdentifier method, of class BaseEntity.
     */
    @Test
    public void testGetUniqueIdentifier() {
        entity.setUniqueIdentifier("abc");
        assertEquals("abc", entity.getUniqueIdentifier());
    }

    /**
     * Test of setUniqueIdentifier method, of class BaseEntity.
     */
    @Test
    public void testSetUniqueIdentifier() {
        entity.setUniqueIdentifier("abcd");
        assertEquals("abcd", entity.getUniqueIdentifier());
    }

}
