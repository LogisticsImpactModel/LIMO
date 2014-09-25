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
 * @author Ben Stassen
 */
public class DiscreteDistributionTest {
    
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
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of calculateProbability method, of class DiscreteDistribution.
     * In a discrete distrib, x divided by y (x out of y) represents the probability. Therefore, 1 divided by 2 should result in a probability of 0.5
     */
    @org.junit.Test
    public void testCalculateProbability() {
        System.out.println("calculateProbability");
        DiscreteDistribution instance = new DiscreteDistribution();
        instance.setParameter("x", 1);
        instance.setParameter("y", 2);
        System.out.println("Probability is: "+instance.getProbability());
        assertEquals(0.5,instance.getProbability(),1);
    }
    
}
