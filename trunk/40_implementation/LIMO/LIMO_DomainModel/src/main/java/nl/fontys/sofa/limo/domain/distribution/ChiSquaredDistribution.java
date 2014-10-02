package nl.fontys.sofa.limo.domain.distribution;

import nl.fontys.sofa.limo.domain.distribution.input.DoubleInputValue;

/**
 *
 * @author Matthias Brück
 */
public class ChiSquaredDistribution extends DistributionType {

    public ChiSquaredDistribution() {
        super(new DoubleInputValue("K", 0.0), new DoubleInputValue("Lower Bound", 0.0), new DoubleInputValue("Upper Bound", 0.0));
    }

    @Override
    protected void calculateProbability() {
        double k = ((DoubleInputValue) inputValues.get("K")).getValue();
        double x0 = ((DoubleInputValue) inputValues.get("Lower Bound")).getValue();
        double x1 = ((DoubleInputValue) inputValues.get("Upper Bound")).getValue();
        probabilityResultCache = new org.apache.commons.math3.distribution.ChiSquaredDistribution(k).probability(x0, x1);
    }
}
