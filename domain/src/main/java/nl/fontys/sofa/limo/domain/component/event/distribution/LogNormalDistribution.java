package nl.fontys.sofa.limo.domain.component.event.distribution;

import nl.fontys.sofa.limo.domain.component.event.distribution.input.DoubleInputValue;

/**
 *
 * @author Matthias Brück
 */
public class LogNormalDistribution extends Distribution {

    public LogNormalDistribution() {
        super(new DoubleInputValue("Shape", 0.0), new DoubleInputValue("Log Scale", 0.0), new DoubleInputValue("Lower Bound", 0.0), new DoubleInputValue("Upper Bound", 0.0));
        this.description = "The log-normal (or lognormal) distribution\n"
                + "is a continuous probability distribution of a\n"
                + "random variable whose logarithm is normally\n"
                + "distributed.\n"
                + "Shape > 0 (real number)\n"
                + "Log Scale is element of R";
    }

    @Override
    protected double calculateProbability() {
        double shape = ((DoubleInputValue) inputValues.get("Shape")).getValue();
        double scale = ((DoubleInputValue) inputValues.get("Log Scale")).getValue();
        double x0 = ((DoubleInputValue) inputValues.get("Lower Bound")).getValue();
        double x1 = ((DoubleInputValue) inputValues.get("Upper Bound")).getValue();
        return new org.apache.commons.math3.distribution.LogNormalDistribution(scale, shape).probability(x0, x1);
    }
}
