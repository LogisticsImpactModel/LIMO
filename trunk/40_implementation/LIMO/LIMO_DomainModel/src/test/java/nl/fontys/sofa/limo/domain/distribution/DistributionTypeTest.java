/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.domain.distribution;

import java.util.HashMap;
import java.util.List;
import nl.fontys.sofa.limo.domain.distribution.input.InputValue;
import nl.fontys.sofa.limo.domain.distribution.input.IntegerInputValue;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class DistributionTypeTest {

    private final static String INPUT_NAME = "Pirates";
    private final static int INTEGER_INPUT_VALUE = 30;

    private DistributionTypeImpl distributionType;
    private IntegerInputValue inputValue;

    @Before
    public void setUp() {
        inputValue = new IntegerInputValue(INPUT_NAME, INTEGER_INPUT_VALUE);
        distributionType = new DistributionTypeImpl(inputValue);
    }

    @After
    public void tearDown() {
        distributionType = null;
    }

    /**
     * Test of getInputValueType method, of class DistributionType.
     */
    @Test
    public void testGetInputValueType() {
        Class<Number> inputValueType = distributionType.getInputValueType("Pirates");
        assertEquals(Integer.class.getSimpleName(), inputValueType.getSimpleName());
        assertNull(distributionType.getInputValueType("NOT_EXISTING"));
    }

    @Test
    public void testGetInputValueNames() {
        List<String> inputValueNames = distributionType.getInputValueNames();
        assertTrue(inputValueNames.contains(INPUT_NAME));
    }

    /**
     * Test of getInputValue method, of class DistributionType.
     */
    @Test
    public void testGetInputValue() {
        Number iv = distributionType.getInputValue(INPUT_NAME);
        assertEquals(INTEGER_INPUT_VALUE, iv.intValue());
    }

    /**
     * Test of setInputValue method, of class DistributionType.
     */
    @Test
    public void testSetInputValue() {
        distributionType.setInputValue("Test", 10);
        assertNull(distributionType.getInputValue("Test"));
    }

    @Test
    public void testSetInputValues() {
        distributionType.setInputValues(new HashMap<String, InputValue>());
        assertTrue(distributionType.getInputValues().isEmpty());
    }

    public class DistributionTypeImpl extends DistributionType {

        private DistributionTypeImpl(InputValue... inputValues) {
            super(inputValues);
        }

        @Override
        public void calculateProbability() {
        }
    }

}
