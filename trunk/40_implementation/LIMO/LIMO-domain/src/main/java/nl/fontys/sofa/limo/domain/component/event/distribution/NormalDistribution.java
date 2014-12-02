package nl.fontys.sofa.limo.domain.component.event.distribution;

import nl.fontys.sofa.limo.domain.component.event.distribution.input.DoubleInputValue;

public class NormalDistribution extends Distribution {

    public NormalDistribution() {
        super(new DoubleInputValue("Mean", 0.0),
                new DoubleInputValue("Standard Deviation", 0.0),
                new DoubleInputValue("Lower Bound", 0.0),
                new DoubleInputValue("Upper Bound", 0.0)
        );
        this.description = "The normal (or Gaussian) distribution is\n"
                + "a function that tells the probability that any\n"
                + "real observation will fall between any two real\n"
                + "limits or real numbers, as the curve approaches\n"
                + "zero on either side.\n"
                + "Mean is a real value\n"
                + "variance > 0\n";
    }

    @Override
    protected double calculateProbability() {
        Double mean = ((DoubleInputValue) inputValues.get("Mean")).getValue();
        Double sd = ((DoubleInputValue) inputValues.get("Standard Deviation")).getValue();
        Double x1 = ((DoubleInputValue) inputValues.get("Lower Bound")).getValue();
        Double x2 = ((DoubleInputValue) inputValues.get("Upper Bound")).getValue();
        return new org.apache.commons.math3.distribution.NormalDistribution(mean, sd).probability(x1, x2);
    }
}
