package nl.fontys.sofa.limo.domain.component.event.distribution;

import nl.fontys.sofa.limo.domain.component.event.distribution.input.DoubleInputValue;

/**
 *
 * @author Matthias Brück
 */
public class ExponentionalDistribution extends Distribution {

    public ExponentionalDistribution() {
        super(new DoubleInputValue("Lambda", 0.0), new DoubleInputValue("Lower Bound", 0.0), new DoubleInputValue("Upper Bound", 0.0));
        this.description = "The exponential distribution is the\n"
                + "probability distribution that describes the time\n"
                + "between events in a Poisson process, i.e. a process\n"
                + "in which events occur continuously and independently\n"
                + "at a constant average rate. It is the continuous\n"
                + "analogue of the geometric distribution, and it has\n"
                + "the key property of being memoryless.\n"
                + "Lambda > 0\n";
    }

    @Override
    protected double calculateProbability() {
        double lambda = ((DoubleInputValue) inputValues.get("Lambda")).getValue();
        double x0 = ((DoubleInputValue) inputValues.get("Lower Bound")).getValue();
        double x1 = ((DoubleInputValue) inputValues.get("Upper Bound")).getValue();
        return new org.apache.commons.math3.distribution.ExponentialDistribution(lambda).probability(x0, x1);
    }
}
