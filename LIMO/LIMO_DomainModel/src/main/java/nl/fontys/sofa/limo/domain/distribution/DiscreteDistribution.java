package nl.fontys.sofa.limo.domain.distribution;

import java.util.AbstractMap;

public class DiscreteDistribution extends DistributionType {

    public DiscreteDistribution() {
        super(new AbstractMap.SimpleImmutableEntry<String, Class<?>>("X", Integer.class),
                new AbstractMap.SimpleImmutableEntry<String, Class<?>>("Y", Integer.class));
    }

    @Override
    protected void calculateProbability() {
        double x = (Integer) parameters.get(parameterNames.indexOf("X"));
        double y = (Integer) parameters.get(parameterNames.indexOf("Y"));
        probabilityResultCache = x / y;
    }
}
