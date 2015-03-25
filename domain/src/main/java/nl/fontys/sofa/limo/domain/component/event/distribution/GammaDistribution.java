package nl.fontys.sofa.limo.domain.component.event.distribution;

import nl.fontys.sofa.limo.domain.component.event.distribution.input.DoubleInputValue;

/**
 *
 * @author Matthias Brück
 */
public class GammaDistribution extends Distribution {

    public GammaDistribution() {
        super(new DoubleInputValue("Alpha", 0.0), new DoubleInputValue("Beta", 0.0), new DoubleInputValue("Lower Bound", 0.0), new DoubleInputValue("Upper Bound", 0.0));
        this.description = "The gamma distribution is a two-parameter\n"
                + "family of continuous probability distributions.\n"
                + "Alpha > 0\n"
                + "Beta > 0";
    }

    @Override
    protected double calculateProbability() {
        double alpha = ((DoubleInputValue) inputValues.get("Alpha")).getValue();
        double beta = ((DoubleInputValue) inputValues.get("Beta")).getValue();
        double x0 = ((DoubleInputValue) inputValues.get("Lower Bound")).getValue();
        double x1 = ((DoubleInputValue) inputValues.get("Upper Bound")).getValue();
        return new org.apache.commons.math3.distribution.GammaDistribution(alpha, beta).probability(x0, x1);
    }
}
