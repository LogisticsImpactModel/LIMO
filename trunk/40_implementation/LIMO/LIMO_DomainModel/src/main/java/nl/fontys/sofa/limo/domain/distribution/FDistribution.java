package nl.fontys.sofa.limo.domain.distribution;

import nl.fontys.sofa.limo.domain.distribution.input.DoubleInputValue;

/**
 *
 * @author Matthias Brück
 */
public class FDistribution extends DistributionType {

    public FDistribution() {
        super(new DoubleInputValue("D1", 0.0), new DoubleInputValue("D2", 0.0), new DoubleInputValue("Lower Bound", 0.0), new DoubleInputValue("Upper Bound", 0.0));
    }

    @Override
    protected void calculateProbability() {
        double d1 = ((DoubleInputValue) inputValues.get("D1")).getValue();
        double d2 = ((DoubleInputValue) inputValues.get("D2")).getValue();
        double x0 = ((DoubleInputValue) inputValues.get("Lower Bound")).getValue();
        double x1 = ((DoubleInputValue) inputValues.get("Upper Bound")).getValue();
        probabilityResultCache = new org.apache.commons.math3.distribution.FDistribution(d1, d2).probability(x0, x1);
    }
}
