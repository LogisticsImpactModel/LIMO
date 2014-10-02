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
public class ChiSquaredDistributionTest {

    public ChiSquaredDistributionTest() {
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
     * Test of calculateProbability method, of class ChiSquaredDistribution.
     */
    @Test
    public void testCalculateProbability() {
        System.out.println("calculateProbability");
        ChiSquaredDistribution instance = new ChiSquaredDistribution();
        instance.setInputValue("K", 2.0);
        instance.setInputValue("Lower Bound", 1.0);
        instance.setInputValue("Upper Bound", 2.0);

        //try to insert a useless parameter for coverage
        instance.setInputValue("useless parameter", 2);

        System.out.println("Probability is: " + instance.getProbability());
        assertEquals(0.23865121854119126, instance.getProbability(), 0.000000000000001);
    }
}
