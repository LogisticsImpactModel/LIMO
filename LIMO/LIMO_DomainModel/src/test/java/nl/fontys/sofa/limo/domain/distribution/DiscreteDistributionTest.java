package nl.fontys.sofa.limo.domain.distribution;

import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.*;
import org.junit.Test;

public class DiscreteDistributionTest {

    /**
     * Test of calculateProbability method w/ seperate parameters specified, of
     * class DiscreteDistribution. In a discrete distrib, x divided by y (x out
     * of y) represents the probability. Therefore, 1 divided by 2 should result
     * in a probability of 0.5
     */
    @Test
    public void testCalculateProbability_separateParameters() {
        System.out.println("calculateProbability w separate parameters");
        DiscreteDistribution instance = new DiscreteDistribution();
        instance.setInputValue("X", 1);
        instance.setInputValue("Y", 2);
        System.out.println("Probability is: " + instance.getProbability());
        assertEquals(0.5, instance.getProbability(), 0.01);
    }

    @Test
    public void testCalculateProbability_parameterMap() {
        System.out.println("calculateProbability w map");
        DiscreteDistribution instance = new DiscreteDistribution();

        Map<String, Number> parameters = new HashMap<>();
        parameters.put("X", 1);
        parameters.put("Y", 2);

        instance.setInputValueMap(parameters);
        System.out.println("Probability is: " + instance.getProbability());
        assertEquals(0.5, instance.getProbability(), 0.01);
    }

    /**
     * Testing whether parameterTypes are set properly in map. Necessary for
     * test coverage.
     */
    @Test
    public void testParameterTypes() {
        System.out.println("getParameterTypes");
        //call list of parameter types
        DiscreteDistribution instance = new DiscreteDistribution();
        assertEquals(java.lang.Integer.class, instance.getInputValueType("X"));
        //for a discrete distrib, at least param X should be present and integer
        assertEquals(java.lang.Integer.class, instance.getInputValueType("Y"));
        //for a discrete distrib, at least param Y should be present and integer
    }
}