package nl.fontys.sofa.limo.domain.distribution;

import java.util.AbstractMap;

/**
 *
 * @author Matthias Brück
 */
public class NormalDistribution extends DistributionType{
    
    public NormalDistribution() {
        super(new AbstractMap.SimpleImmutableEntry<String, Class<?>>("mean", Double.class),
                new AbstractMap.SimpleImmutableEntry<String, Class<?>>("standard deviation", Double.class),
                new AbstractMap.SimpleImmutableEntry<String, Class<?>>("x1", Double.class),
                new AbstractMap.SimpleImmutableEntry<String, Class<?>>("x2", Double.class));
    }
    
    @Override
    protected void calculateProbability() {
        Double mean = (Double) parameters.get("mean");
        Double sd = (Double) parameters.get("standard deviation");
        Double x1 = (Double) parameters.get("x1");
        Double x2 = (Double) parameters.get("x2");
        probabilityResultCache = new org.apache.commons.math3.distribution.NormalDistribution(mean, sd).probability(x1, x2);
    }
}