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
public class PoissonDistributionTest {
    
    public PoissonDistributionTest() {
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
     * Test of calculateProbability method, of class PoissonDistribution.
     * k: poisson random variable (faculty,int)
     * lambda: average rate of succes (double)
     */
    @Test
    public void testCalculateProbability() {
        System.out.println("calculateProbability");
        PoissonDistribution instance = new PoissonDistribution();
        instance.setParameter("Lambda", 0.50);
        instance.setParameter("K", 1);
        
        //try to insert a useless parameter for coverage
        instance.setParameter("useless parameter",2);
        
        System.out.println("Probability is: "+instance.getProbability());
        assertEquals(0.3032653298563167,instance.getProbability(),0.000000000000001);
    }
    
}
