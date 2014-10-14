package nl.fontys.sofa.limo.domain.component.event.distribution;

import nl.fontys.sofa.limo.domain.component.event.distribution.input.DoubleInputValue;

/**
 *
 * @author Matthias Brück
 */
public class FDistribution extends Distribution {

    public FDistribution() {
        super(new DoubleInputValue("D1", 0.0), new DoubleInputValue("D2", 0.0), new DoubleInputValue("Lower Bound", 0.0), new DoubleInputValue("Upper Bound", 0.0));
        this.description = "The F-distribution is a continuous\n"
                + "probability distribution. The F-distribution arises\n"
                + "frequently as the null distribution of a test\n"
                + "statistic, most notably in the analysis of\n"
                + "variance.\n"
                + "D1 > 0\n"
                + "D2 > 0";
    }

    @Override
    protected double calculateProbability() {
        double d1 = ((DoubleInputValue) inputValues.get("D1")).getValue();
        double d2 = ((DoubleInputValue) inputValues.get("D2")).getValue();
        double x0 = ((DoubleInputValue) inputValues.get("Lower Bound")).getValue();
        double x1 = ((DoubleInputValue) inputValues.get("Upper Bound")).getValue();
        return new org.apache.commons.math3.distribution.FDistribution(d1, d2).probability(x0, x1);
    }
}
