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
public class WeibullDistributionTest {
    
    public WeibullDistributionTest() {
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
     * Test of calculateProbability method, of class WeibullDistribution.
     */
    @Test
    public void testCalculateProbability() {
        System.out.println("calculateProbability");
        WeibullDistribution instance = new WeibullDistribution();
        instance.setInputValue("Alpha", 1.3);
        instance.setInputValue("Beta", 4.6);
        instance.setInputValue("Lower Bound", 1.5);
        instance.setInputValue("Upper Bound", 2.1);
        //try to insert a useless parameter for coverage
        instance.setInputValue("useless parameter", 2);

        System.out.println("Probability is: " + instance.getProbability());
        assertEquals(0.09506404116772382, instance.getProbability(), 0.000000000000001);
    }
}
