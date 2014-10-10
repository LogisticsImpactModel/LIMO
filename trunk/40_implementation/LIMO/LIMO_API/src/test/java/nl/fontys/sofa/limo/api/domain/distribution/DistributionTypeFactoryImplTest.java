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
     */
    @Test
    public void testGetDistributionTypes() {
        System.out.println("getDistributionTypes");
        DistributionTypeFactoryImpl instance = new DistributionTypeFactoryImpl();
        System.out.println("1st entry: "+instance.getDistributionTypes()[0]);

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
