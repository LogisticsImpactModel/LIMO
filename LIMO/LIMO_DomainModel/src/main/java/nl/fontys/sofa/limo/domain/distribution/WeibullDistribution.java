package nl.fontys.sofa.limo.domain.distribution;

import nl.fontys.sofa.limo.domain.distribution.input.DoubleInputValue;

/**
 *
 * @author Matthias Brück
 */
public class WeibullDistribution extends DistributionType {

    public WeibullDistribution() {
        super(new DoubleInputValue("Alpha", 0.0), new DoubleInputValue("Beta", 0.0), new DoubleInputValue("Lower Bound", 0.0), new DoubleInputValue("Upper Bound", 0.0));
        this.description = "The Weibull distribution is a continuous\n"
                + "probability distribution.\n"
                + "Lambda (scale) is element of (0, positiv infinity)\n"
                + "K (shape) is element of (0, positiv infinity)";
    }

    @Override
    protected void calculateProbability() {
        double alpha = ((DoubleInputValue) inputValues.get("Alpha")).getValue();
        double beta = ((DoubleInputValue) inputValues.get("Beta")).getValue();
        double x0 = ((DoubleInputValue) inputValues.get("Lower Bound")).getValue();
        double x1 = ((DoubleInputValue) inputValues.get("Upper Bound")).getValue();
        probabilityResultCache = new org.apache.commons.math3.distribution.WeibullDistribution(alpha, beta).probability(x0, x1);
    }
}
