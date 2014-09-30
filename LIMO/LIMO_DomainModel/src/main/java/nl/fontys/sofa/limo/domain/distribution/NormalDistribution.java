package nl.fontys.sofa.limo.domain.distribution;

import java.util.AbstractMap;

public class NormalDistribution extends DistributionType {

    public NormalDistribution() {
        super(new AbstractMap.SimpleImmutableEntry<String, Class<?>>("Mean", Double.class),
                new AbstractMap.SimpleImmutableEntry<String, Class<?>>("Standard Deviation", Double.class),
                new AbstractMap.SimpleImmutableEntry<String, Class<?>>("Lower Bound", Double.class),
                new AbstractMap.SimpleImmutableEntry<String, Class<?>>("Upper Bound", Double.class));
    }

    @Override
    protected void calculateProbability() {
        Double mean = (Double) parameters.get(parameterNames.indexOf("Mean"));
        Double sd = (Double) parameters.get(parameterNames.indexOf("Standard Deviation"));
        Double x1 = (Double) parameters.get(parameterNames.indexOf("Lower Bound"));
        Double x2 = (Double) parameters.get(parameterNames.indexOf("Upper Bound"));
        probabilityResultCache = new org.apache.commons.math3.distribution.NormalDistribution(mean, sd).probability(x1, x2);
    }
}
