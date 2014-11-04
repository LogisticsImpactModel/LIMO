/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.domain.component.procedure.value;

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
    RangeValue rangeVal;
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
        rangeVal = new RangeValue();
    }
    
    @After
    public void tearDown() {
        rangeVal = null;
    }

    /**
     * Test of getValue method, of class RangeValue.
     */
    @Test
    public void testGetValue() {
        System.out.println("getValue");
        double minVal = 3.0;
        double maxVal = 4.0;
        rangeVal.setMin(minVal);
        rangeVal.setMax(maxVal);
        for (int i = 0; i < 10; i++) {//get 10 times rand no to check whether the test does not pass on coincidence
            double retrievedVal = rangeVal.getValue();
            System.out.println("Retieved val: "+retrievedVal);
            assertTrue("Value must be above or equal to "+minVal,retrievedVal>=minVal);
            assertTrue("Value must be below or equal to "+maxVal,retrievedVal<=maxVal);
        }
        
               
        
        

    }

    
}
