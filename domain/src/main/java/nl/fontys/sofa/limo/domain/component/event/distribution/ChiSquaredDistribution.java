package nl.fontys.sofa.limo.domain.component.event.distribution;

import nl.fontys.sofa.limo.domain.component.event.distribution.input.DoubleInputValue;

/**
 *
 * @author Matthias Brück
 */
public class ChiSquaredDistribution extends Distribution {

    public ChiSquaredDistribution() {
        super(new DoubleInputValue("K", 0.0), new DoubleInputValue("Lower Bound", 0.0), new DoubleInputValue("Upper Bound", 0.0));
        this.description = "The chi-squared distribution with k degrees\n"
                + "of freedom is the distribution of a sum of the\n"
                + "squares of k independent standard normal random\n"
                + "variables.\n"
                + "k is element of N* (known as \"degrees of freedom\")";
    }

    @Override
    protected double calculateProbability() {
        double k = ((DoubleInputValue) inputValues.get("K")).getValue();
        double x0 = ((DoubleInputValue) inputValues.get("Lower Bound")).getValue();
        double x1 = ((DoubleInputValue) inputValues.get("Upper Bound")).getValue();
        return new org.apache.commons.math3.distribution.ChiSquaredDistribution(k).probability(x0, x1);
    }
}
