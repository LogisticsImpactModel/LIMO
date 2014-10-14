package nl.fontys.sofa.limo.domain.component.event.distribution;

import nl.fontys.sofa.limo.domain.component.event.distribution.input.DoubleInputValue;

/**
 *
 * @author Matthias Brück
 */
public class TriangularDistribution extends Distribution {

    public TriangularDistribution() {
        super(new DoubleInputValue("A", 0.0), new DoubleInputValue("B", 0.0), new DoubleInputValue("C", 0.0), new DoubleInputValue("Lower Bound", 0.0), new DoubleInputValue("Upper Bound", 0.0));
        this.description = "The triangular distribution is a\n"
                + "continuous probability distribution with lower\n"
                + "limit a, upper limit b and mode c, where a < b\n"
                + "and a ≤ c ≤ b.\n"
                + "A is element of (negative infinity,positive infinity)\n"
                + "B is smaller than A\n"
                + "C is smaller than B";
    }

    @Override
    protected double calculateProbability() {
        double a = ((DoubleInputValue) inputValues.get("A")).getValue();
        double b = ((DoubleInputValue) inputValues.get("B")).getValue();
        double c = ((DoubleInputValue) inputValues.get("C")).getValue();
        double x0 = ((DoubleInputValue) inputValues.get("Lower Bound")).getValue();
        double x1 = ((DoubleInputValue) inputValues.get("Upper Bound")).getValue();
        return new org.apache.commons.math3.distribution.TriangularDistribution(a, b, c).probability(x0, x1);
    }
}
