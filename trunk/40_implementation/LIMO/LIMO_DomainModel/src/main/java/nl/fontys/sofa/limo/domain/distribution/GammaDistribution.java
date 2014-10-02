package nl.fontys.sofa.limo.domain.distribution;

import nl.fontys.sofa.limo.domain.distribution.input.DoubleInputValue;

/**
 *
 * @author Matthias Brück
 */
public class GammaDistribution extends DistributionType {

    public GammaDistribution() {
        super(new DoubleInputValue("Alpha", 0.0), new DoubleInputValue("Beta", 0.0), new DoubleInputValue("Lower Bound", 0.0), new DoubleInputValue("Upper Bound", 0.0));
    }

    @Override
    protected void calculateProbability() {
        double alpha = ((DoubleInputValue) inputValues.get("Alpha")).getValue();
        double beta = ((DoubleInputValue) inputValues.get("Beta")).getValue();
        double x0 = ((DoubleInputValue) inputValues.get("Lower Bound")).getValue();
        double x1 = ((DoubleInputValue) inputValues.get("Upper Bound")).getValue();
        probabilityResultCache = new org.apache.commons.math3.distribution.GammaDistribution(alpha, beta).probability(x0, x1);
    }
}
