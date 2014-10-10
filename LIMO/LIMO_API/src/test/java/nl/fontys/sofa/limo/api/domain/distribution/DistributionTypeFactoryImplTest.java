/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.api.domain.distribution;

import java.util.List;
import nl.fontys.sofa.limo.domain.distribution.DistributionType;
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
public class DistributionTypeFactoryImplTest {
    
    public DistributionTypeFactoryImplTest() {
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
     * Test of getDistributionTypes method, of class DistributionTypeFactoryImpl.
     * All calls of testDistributionTypeAvailability call testGetDistributionTypes and check whether the requested distribType is available in the string[]
     */
    @Test
    public void testGetDistributionTypes() {
        System.out.println("getDistributionTypes");
        testDistributionTypeAvailability("Cauchy");
        testDistributionTypeAvailability("Chi Squared");
        testDistributionTypeAvailability("Discrete");
        testDistributionTypeAvailability("Exponential");
        testDistributionTypeAvailability("F");
        testDistributionTypeAvailability("Gamma");
        testDistributionTypeAvailability("Log Normal");
        testDistributionTypeAvailability("Normal");
        testDistributionTypeAvailability("Poisson");
        testDistributionTypeAvailability("Triangular");
        testDistributionTypeAvailability("Weibull");
    }
    /**
     * needed by testGetDistributionTypes to easily check whether a distribType is avail in string[]
     * @param distribName 
     */
    private void testDistributionTypeAvailability(String distribName){
        DistributionTypeFactoryImpl instance = new DistributionTypeFactoryImpl();
        boolean found = false;
        for (String distribType : instance.getDistributionTypes()) {
            if(distribType.equals(distribName)){
                found=true;
            }
        }
        assertTrue("DistribType "+distribName+" should be found",found);
    }
    /**
     * Test of getDistributionTypeByName method, of class DistributionTypeFactoryImpl.
     */
    @Test
    public void testGetDistributionTypeByName_normal() {
        System.out.println("getDistributionTypeByName_normal");
        DistributionTypeFactoryImpl instance = new DistributionTypeFactoryImpl();

        List<String> inputValuesNormalDistrib = instance.getDistributionTypeByName("Normal").getInputValueNames();
        assertTrue("For a normal distrib, Lower Bound should be input value",inputValuesNormalDistrib.contains("Lower Bound"));
        assertTrue("For a normal distrib, Standard Deviation should be input value",inputValuesNormalDistrib.contains("Standard Deviation"));

    }
    
}
