package nl.fontys.sofa.limo.domain.component.event.distribution;

import nl.fontys.sofa.limo.domain.component.event.distribution.input.DoubleInputValue;

/**
 *
 * @author Matthias Brück
 */
public class CauchyDistribution extends Distribution {

    public CauchyDistribution() {
        super(new DoubleInputValue("Median", 0.0), new DoubleInputValue("Scale", 0.0), new DoubleInputValue("Lower Bound", 0.0), new DoubleInputValue("Upper Bound", 0.0));
        this.description = "Acontinuous probability distribution.\n"
                + "It is the distribution of a random variable\n"
                + "that is the ratio of two independent standard\n"
                + "normal variables"
                + "x0 -> location (real number)\n"
                + "γ > 0 scale (real number)";
    }

    @Override
    protected double calculateProbability() {
        double median = ((DoubleInputValue) inputValues.get("Median")).getValue();
        double scale = ((DoubleInputValue) inputValues.get("Scale")).getValue();
        double x0 = ((DoubleInputValue) inputValues.get("Lower Bound")).getValue();
        double x1 = ((DoubleInputValue) inputValues.get("Upper Bound")).getValue();
        return new org.apache.commons.math3.distribution.CauchyDistribution(median, scale).probability(x0, x1);
    }
}
