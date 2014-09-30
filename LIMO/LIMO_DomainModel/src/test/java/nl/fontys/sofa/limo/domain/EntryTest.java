/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.domain;

import nl.fontys.sofa.limo.domain.value.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Ben
 */
public class EntryTest {
    Entry entry;
    String entryName = "nameTest";
    String entryCat = "catTest";
    Value entryVal;
    double entryValVal = 10.00;
    public EntryTest() {
    }
        
    @Before
    public void setUp() {
        entryVal = new SingleValue(entryValVal);
        entry = new Entry(entryName,entryCat,entryVal);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getName method, of class Entry.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        String result = entry.getName();
        assertEquals(this.entryName, result);
    }

    /**
     * Test of setName method, of class Entry.
     */
    @Test
    public void testSetName() {
        System.out.println("setName");
        String newName = "Test2";
        entry.setName(newName);
        assertEquals(entry.getName(),newName);
    }

    /**
     * Test of getCategory method, of class Entry.
     */
    @Test
    public void testGetCategory() {
        System.out.println("getCategory");
        String result = entry.getCategory();
        assertEquals(entryCat, result);
    }

    /**
     * Test of setCategory method, of class Entry.
     */
    @Test
    public void testSetCategory() {
        System.out.println("setCategory");
        String newEntryCat = "newCatName";
        entry.setCategory(newEntryCat);
        String result = entry.getCategory();
        assertEquals(newEntryCat, result);
    }

    /**
     * Test of getValue method, of class Entry.
     */
    @Test
    public void testGetValue() {
        System.out.println("getValue");
        assertEquals(entry.getValue().getValue(),this.entryValVal,0.001);
    }

    /**
     * Test of setValue method, of class Entry.
     */
    @Test
    public void testSetValue() {
        System.out.println("setValue");
        entryVal = new SingleValue(11.0);
        entry.setValue(entryVal);
        assertEquals(entry.getValue().getValue(),entryVal.getValue(),0.001);
    }
    
}
