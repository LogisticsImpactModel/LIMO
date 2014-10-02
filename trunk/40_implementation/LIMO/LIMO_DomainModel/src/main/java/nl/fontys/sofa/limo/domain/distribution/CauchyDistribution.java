package nl.fontys.sofa.limo.domain.distribution;

import nl.fontys.sofa.limo.domain.distribution.input.DoubleInputValue;

/**
 *
 * @author Matthias Brück
 */
public class CauchyDistribution extends DistributionType {

    public CauchyDistribution() {
        super(new DoubleInputValue("Median", 0.0), new DoubleInputValue("Scale", 0.0), new DoubleInputValue("Lower Bound", 0.0), new DoubleInputValue("Upper Bound", 0.0));
    }

    @Override
    protected void calculateProbability() {
        double median = ((DoubleInputValue) inputValues.get("Median")).getValue();
        double scale = ((DoubleInputValue) inputValues.get("Scale")).getValue();
        double x0 = ((DoubleInputValue) inputValues.get("Lower Bound")).getValue();
        double x1 = ((DoubleInputValue) inputValues.get("Upper Bound")).getValue();
        probabilityResultCache = new org.apache.commons.math3.distribution.CauchyDistribution(median, scale).probability(x0, x1);
    }
}
