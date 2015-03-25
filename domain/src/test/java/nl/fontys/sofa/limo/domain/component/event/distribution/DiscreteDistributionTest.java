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
    int x;
    int y;
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
        x = 1;
        y = 2;
        distrib.setInputValue("X",x);
        distrib.setInputValue("Y",y);
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
        assertEquals("When X=1 and Y=2, probab should be 0.5",0.5,distrib.getProbability(),0.0000001);
    }
    /**
     * Test getType of abstract superclass Distribution
     */
    @Test
    public void testGetDistributionType(){
        Class paramType = distrib.getType("X");//retrieve the data type of param X
        Class expectedType = java.lang.Integer.class;
        assertEquals("Param X in discrete distrib should be of type int",expectedType,paramType);
        Class paramType2 = distrib.getType("NonExistingParam");
        assertNull("Param NonExistingParam does not exist and should not therefore not have a type for it returned",paramType2);
    }
    /**
     * Test getValue of abstract superclass Distribution
     */
    @Test
    public void testGetValue(){
        int expectedValX = this.x;
        int expectedValY = this.y;
        assertEquals("Param X should be "+expectedValX,expectedValX,distrib.getValue("X"));
        assertEquals("Param Y should be "+expectedValY,expectedValY,distrib.getValue("Y"));
        assertNull("Param DoesNotExist should not return a value because it does not exist",distrib.getValue("DoesNotExist"));

    }
}
