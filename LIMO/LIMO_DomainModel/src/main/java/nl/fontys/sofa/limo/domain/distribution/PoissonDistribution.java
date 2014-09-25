package nl.fontys.sofa.limo.domain.distribution;

import java.util.AbstractMap;

/**
 *
 * @author Matthias Brück
 */
public class PoissonDistribution extends DistributionType {
    
    public PoissonDistribution() {
        super(new AbstractMap.SimpleImmutableEntry<String, Class<?>>("Lambda", Double.class),
                new AbstractMap.SimpleImmutableEntry<String, Class<?>>("K", Integer.class));
    }

    @Override
    protected void calculateProbability() {
        Double lambda = (Double) parameters.get("Lambda");
        int k = (int) parameters.get("K");
        probabilityResultCache = new org.apache.commons.math3.distribution.PoissonDistribution(lambda).probability(k);
    }
}