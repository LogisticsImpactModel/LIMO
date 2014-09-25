package nl.fontys.sofa.limo.domain.distribution;

import java.util.AbstractMap;
import java.util.Random;

/**
 * @author Matthias Brück
 */
public class DiscreteDistribution extends DistributionType{
    
    public DiscreteDistribution(){
        super(new AbstractMap.SimpleImmutableEntry<String, Class<?>>("x", Integer.class),
                new AbstractMap.SimpleImmutableEntry<String, Class<?>>("y", Integer.class));
    }

    @Override
    protected void calculateProbability() {
        double x = (double) parameters.get("x");
        double y = (double) parameters.get("y");
        probabilityResultCache = x/y;
    }
}