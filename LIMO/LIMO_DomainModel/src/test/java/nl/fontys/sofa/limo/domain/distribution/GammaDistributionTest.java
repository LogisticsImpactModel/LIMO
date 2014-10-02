/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.domain.distribution;

import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Matthias Brück
 */
public class GammaDistributionTest {
    
    public GammaDistributionTest() {
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
     * Test of calculateProbability method, of class GammaDistribution.
     */
    @Test
    public void testCalculateProbability() {
        System.out.println("calculateProbability");
        GammaDistribution instance = new GammaDistribution();
        instance.setInputValue("Alpha", 1.2);
        instance.setInputValue("Beta", 5.7);
        instance.setInputValue("Lower Bound", 2.0);
        instance.setInputValue("Upper Bound", 3.0);
        //try to insert a useless parameter for coverage
        instance.setInputValue("useless parameter", 2);

        System.out.println("Probability is: " + instance.getProbability());
        assertEquals(0.10440281480534197, instance.getProbability(), 0.000000000000001);
    }
}
