/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.domain.value;

import java.util.Random;
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
public class RangeValueTest {
    
    public RangeValueTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getValue method, of class RangeValue.
     * initiating RangeValue w/ no params takes min=0, max=1 by default.
     */
    @Test
    public void testGetValue_no_params() {
        System.out.println("getValue");
        RangeValue instance = new RangeValue();
        assertTrue("Value should be smaller or equal than 1",instance.getValue()<=1);
        assertTrue("Value should be greater or equal to 0",instance.getValue()>=0);
    }
    /**
     * Test 10 times w/ random max. values whether generated values fall within the range.
     */
    @Test
    public void testGetValue_max_val() {
        System.out.println("getValue");
        Random rand = new Random(20);
        double max;
        for (int i = 0; i < 10; i++) {
            max = rand.nextDouble();
            RangeValue instance = new RangeValue(max);
            assertTrue("Value should be smaller or equal than "+max,instance.getValue()<=max);
            assertTrue("Value should be greater or equal to 0",instance.getValue()>=0);
        }
    }


    /**
     * Test of getMin method, of class RangeValue.
     */
    @Test
    public void testGetMin() {
        System.out.println("getMin");
        RangeValue instance = new RangeValue();
        double expResult = 0.0;
        double result = instance.getMin();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMax method, of class RangeValue.
     */
    @Test
    public void testGetMax() {
        System.out.println("getMax");
        RangeValue instance = new RangeValue();
        double expResult = 0.0;
        double result = instance.getMax();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
