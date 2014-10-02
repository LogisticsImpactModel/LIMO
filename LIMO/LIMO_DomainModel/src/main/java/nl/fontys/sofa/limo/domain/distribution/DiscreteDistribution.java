package nl.fontys.sofa.limo.domain.distribution;

import nl.fontys.sofa.limo.domain.distribution.input.IntegerInputValue;

public class DiscreteDistribution extends DistributionType {

    public DiscreteDistribution() {
        super(new IntegerInputValue("X", 0), new IntegerInputValue("Y", 1));
    }

    @Override
    protected void calculateProbability() {
        double x = ((IntegerInputValue) inputValues.get("X")).getValue();
        double y = ((IntegerInputValue) inputValues.get("Y")).getValue();
        probabilityResultCache = x / y;
    }
}
