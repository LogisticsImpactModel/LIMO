/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.domain.distribution;

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
public class NormalDistributionTest {
    
    public NormalDistributionTest() {
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
     * Test of calculateProbability method, of class NormalDistribution.
     * Providing parameters mean, stdev and x
     * When mean=5, stddev=1 and x=5, p should be 0.5
     * 
     * 25-9 15:30 normalDistribution seems broken, returns only 0.00
     */
    @Test
    public void testCalculateProbability() {
        System.out.println("calculateProbability");
        NormalDistribution instance = new NormalDistribution();
        instance.setInputValue("Mean",5.00);
        instance.setInputValue("Standard Deviation",1.00);
        instance.setInputValue("Lower Bound",4.00);
        instance.setInputValue("Upper Bound",6.00);
        System.out.println("Probability is: "+instance.getProbability());
        assertEquals(0.682689492137086,instance.getProbability(),0.0000000001);
    }
    
}
