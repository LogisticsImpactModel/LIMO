package nl.fontys.sofa.limo.domain.distribution;

import java.util.AbstractMap;

/**
 *
 * @author Matthias Brück
 */
public class PoissonDistribution extends DistributionType {
    
    public PoissonDistribution() {
        super(new AbstractMap.SimpleImmutableEntry<String, Class<?>>("lambda", Double.class),
                new AbstractMap.SimpleImmutableEntry<String, Class<?>>("k", Integer.class));
    }

    @Override
    protected void calculateProbability() {
        Double lambda = (Double) parameters.get("lambda");
        int k = (int) parameters.get("k");
        probabilityResultCache = new org.apache.commons.math3.distribution.PoissonDistribution(lambda).probability(k);
    }
}