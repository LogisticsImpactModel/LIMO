/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.domain.value;

import java.util.Random;
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
public class SingleValueTest {

    Random rand = new Random();

    /**
     * Test of getValue method, of class SingleValue.\ Testing 10 times w/
     * random values whether all is good
     */
    @Test
    public void testGetValue() {
        //testing w/o value provided, setting it to 0
        System.out.println("getValue");
        SingleValue instance = new SingleValue();
        assertEquals("Instance should have value of 0", 0.00, instance.getValue(), 0.00);

        //testing w/ value provided
        SingleValue instance2;
        for (int i = 0; i < 10; i++) {
            double testValue = rand.nextDouble();
            instance2 = new SingleValue();
            instance2.setValue(testValue);
            assertEquals("Instance should have value of " + testValue, testValue, instance2.getValue(), 0.00);
        }
    }

    /**
     * Test of getMin method, of class SingleValue. In singleValue,
     * min=max=value
     */
    @Test
    public void testGetMin() {
        System.out.println("getMin");
        double testValue = rand.nextDouble();
        SingleValue instance = new SingleValue(testValue);
        assertEquals("Min should be equal to max, equal to value " + testValue, instance.getValue(), instance.getMin(), 0.00001);
    }

    /**
     * Test of getMax method, of class SingleValue.
     */
    @Test
    public void testGetMax() {
        System.out.println("getMax");
        double testValue = rand.nextDouble();
        SingleValue instance = new SingleValue(testValue);
        assertEquals("Max should be equal to min and equal to value " + testValue, instance.getValue(), instance.getMax(), 0.00001);
    }

}
