package nl.fontys.sofa.limo.domain.distribution;

import java.util.AbstractMap;
import java.util.Random;

/**
 * @author Matthias Brück
 */
public class DiscreteDistribution extends DistributionType{
    
    public DiscreteDistribution(){
        super(new AbstractMap.SimpleImmutableEntry<String, Class<?>>("X", Integer.class),
                new AbstractMap.SimpleImmutableEntry<String, Class<?>>("Y", Integer.class));
    }

    @Override
    protected void calculateProbability() {
        double x = (Integer) parameters.get("X");
        double y = (Integer) parameters.get("Y");
        probabilityResultCache = x/y;
    }
}