package nl.fontys.sofa.limo.domain.distribution;

import nl.fontys.sofa.limo.domain.distribution.input.DoubleInputValue;
import nl.fontys.sofa.limo.domain.distribution.input.IntegerInputValue;

public class PoissonDistribution extends DistributionType {

    public PoissonDistribution() {
        super(new DoubleInputValue("Lambda", 0.0), new IntegerInputValue("K", 0));
    }

    @Override
    protected void calculateProbability() {
        Double lambda = ((DoubleInputValue) inputValues.get("Lambda")).getValue();
        int k = ((IntegerInputValue) inputValues.get("K")).getValue();
        probabilityResultCache = new org.apache.commons.math3.distribution.PoissonDistribution(lambda).probability(k);
    }
}
