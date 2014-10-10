package nl.fontys.sofa.limo.domain.distribution;

import nl.fontys.sofa.limo.domain.distribution.input.IntegerInputValue;

public class DiscreteDistribution extends DistributionType {

    public DiscreteDistribution() {
        super(new IntegerInputValue("X", 0), new IntegerInputValue("Y", 1));
        this.description = "This distribution calculates the probability\n"
                + "of X / Y.\n"
                + "X is elemnt of (negativ infinity, positiv infinity)\n"
                + "Y is elemnt of (0, positiv infinitiy)";
    }

    @Override
    protected void calculateProbability() {
        double x = ((IntegerInputValue) inputValues.get("X")).getValue();
        double y = ((IntegerInputValue) inputValues.get("Y")).getValue();
        probabilityResultCache = x / y;
    }
}
