package nl.fontys.sofa.limo.domain.distribution;

import nl.fontys.sofa.limo.domain.distribution.input.DoubleInputValue;

/**
 *
 * @author Matthias Brück
 */
public class LogNormalDistribution extends DistributionType {

    public LogNormalDistribution() {
        super(new DoubleInputValue("Shape", 0.0), new DoubleInputValue("Log Scale", 0.0), new DoubleInputValue("Lower Bound", 0.0), new DoubleInputValue("Upper Bound", 0.0));
    }

    @Override
    protected void calculateProbability() {
        double shape = ((DoubleInputValue) inputValues.get("Shape")).getValue();
        double scale = ((DoubleInputValue) inputValues.get("Log Scale")).getValue();
        double x0 = ((DoubleInputValue) inputValues.get("Lower Bound")).getValue();
        double x1 = ((DoubleInputValue) inputValues.get("Upper Bound")).getValue();
        probabilityResultCache = new org.apache.commons.math3.distribution.LogNormalDistribution(scale, shape).probability(x0, x1);
    }
}
