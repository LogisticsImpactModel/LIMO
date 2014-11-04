/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.domain.component.event.distribution;

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
public class DiscreteDistributionTest {
    DiscreteDistribution distrib;
    public DiscreteDistributionTest() {
       
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        distrib = new DiscreteDistribution();
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of calculateProbability method, of class DiscreteDistribution.
     */
    @Test
    public void testCalculateProbability() {
        System.out.println("calculateProbability");
        int x = 1;
        int y = 2;
        distrib.setInputValue("X",x);
        distrib.setInputValue("Y",y);
        assertEquals("When X=1 and Y=2, probab should be 0.5",0.5,distrib.getProbability(),0.0000001);

    }
    
}
