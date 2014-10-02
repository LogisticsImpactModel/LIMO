package nl.fontys.sofa.limo.domain.distribution;

import nl.fontys.sofa.limo.domain.distribution.input.DoubleInputValue;

public class NormalDistribution extends DistributionType {

    public NormalDistribution() {
        super (new DoubleInputValue("Mean", 0.0),
               new DoubleInputValue("Standard Deviation", 0.0),
               new DoubleInputValue("Lower Bound", 0.0),
               new DoubleInputValue("Upper Bound", 0.0)
        );
    }

    @Override
    protected void calculateProbability() {
        Double mean = ((DoubleInputValue) inputValues.get("Mean")).getValue();
        Double sd = ((DoubleInputValue) inputValues.get("Standard Deviation")).getValue();
        Double x1 = ((DoubleInputValue) inputValues.get("Lower Bound")).getValue();
        Double x2 = ((DoubleInputValue) inputValues.get("Upper Bound")).getValue();
        probabilityResultCache = new org.apache.commons.math3.distribution.NormalDistribution(mean, sd).probability(x1, x2);
    }
}
