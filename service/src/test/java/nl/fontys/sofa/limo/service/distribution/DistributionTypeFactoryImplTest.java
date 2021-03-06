package nl.fontys.sofa.limo.service.distribution;

import java.util.List;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author Ben
 */
public class DistributionTypeFactoryImplTest {

    /**
     * Test of getDistributionTypes method, of class DistributionFactoryImpl.
     * All calls of testDistributionTypeAvailability call
     * testGetDistributionTypes and check whether the requested distribType is
     * available in the string[]
     */
    @Test
    public void testGetDistributionTypes() {
        System.out.println("getDistributionTypes");
        testDistributionTypeAvailability("Cauchy", true);
        testDistributionTypeAvailability("Chi Squared", true);
        testDistributionTypeAvailability("Discrete", true);
        testDistributionTypeAvailability("Exponential", true);
        testDistributionTypeAvailability("F", true);
        testDistributionTypeAvailability("Gamma", true);
        testDistributionTypeAvailability("Log Normal", true);
        testDistributionTypeAvailability("Normal", true);
        testDistributionTypeAvailability("Poisson", true);
        testDistributionTypeAvailability("Triangular", true);
        testDistributionTypeAvailability("Weibull", true);
        testDistributionTypeAvailability("NotAvailableDistribType", false);
    }

    /**
     * needed by testGetDistributionTypes to easily check whether a distribType
     * is avail in string[]
     *
     * @param distribName : the distribution name which is searched for
     * @param shouldBeFound : whether or not it should actually be the case that
     * a type is found based on the distribName as search input
     */
    private void testDistributionTypeAvailability(String distribName, boolean shouldBeFound) {
        DistributionFactoryImpl instance = new DistributionFactoryImpl();
        boolean found = false;
        for (String distribType : instance.getDistributionTypes()) {
            if (distribType.equals(distribName)) {
                found = true;
            }
        }
        if (shouldBeFound) {
            assertTrue("DistribType " + distribName + " should be found", found);
        } else {
            assertFalse("DistribType " + distribName + " should NOT be found", found);
        }

    }

    /**
     * Test of getDistributionTypeByName method, of class
     * DistributionFactoryImpl.
     */
    @Test
    public void testGetDistributionTypeByName_normal() {
        System.out.println("getDistributionTypeByName_normal");
        DistributionFactoryImpl instance = new DistributionFactoryImpl();

        List<String> inputValuesNormalDistrib = instance.getDistributionTypeByName("Normal").getNames();
        assertTrue("For a normal distrib, Lower Bound should be input value", inputValuesNormalDistrib.contains("Lower Bound"));
        assertTrue("For a normal distrib, Standard Deviation should be input value", inputValuesNormalDistrib.contains("Standard Deviation"));

    }

}
