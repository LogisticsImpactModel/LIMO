package nl.fontys.sofa.limo.domain.distribution;

import nl.fontys.sofa.limo.domain.distribution.input.DoubleInputValue;
import nl.fontys.sofa.limo.domain.distribution.input.IntegerInputValue;

public class PoissonDistribution extends DistributionType {

    public PoissonDistribution() {
        super(new DoubleInputValue("Lambda", 0.0), new IntegerInputValue("K", 0));
        this.description = "The Poisson distribution is a discrete\n"
                + "probability distribution that expresses the\n"
                + "probability of a given number of events occurring\n"
                + "in a fixed interval of time and/or space if these\n"
                + "events occur with a known average rate and\n"
                + "independently of the time since the last event.\n"
                + "Lambda > 0 (real number)\n"
                + "K is element of {0,1,2,3,...}";
    }

    @Override
    protected void calculateProbability() {
        Double lambda = ((DoubleInputValue) inputValues.get("Lambda")).getValue();
        int k = ((IntegerInputValue) inputValues.get("K")).getValue();
        probabilityResultCache = new org.apache.commons.math3.distribution.PoissonDistribution(lambda).probability(k);
    }
}
