package nl.fontys.sofa.limo.domain.distribution;

import nl.fontys.sofa.limo.domain.distribution.input.DoubleInputValue;

/**
 *
 * @author Matthias Brück
 */
public class TriangularDistribution extends DistributionType {

    public TriangularDistribution() {
        super(new DoubleInputValue("A", 0.0), new DoubleInputValue("B", 0.0), new DoubleInputValue("C", 0.0), new DoubleInputValue("Lower Bound", 0.0), new DoubleInputValue("Upper Bound", 0.0));

    }

    @Override
    protected void calculateProbability() {
        double a = ((DoubleInputValue) inputValues.get("A")).getValue();
        double b = ((DoubleInputValue) inputValues.get("B")).getValue();
        double c = ((DoubleInputValue) inputValues.get("C")).getValue();
        double x0 = ((DoubleInputValue) inputValues.get("Lower Bound")).getValue();
        double x1 = ((DoubleInputValue) inputValues.get("Upper Bound")).getValue();
        probabilityResultCache = new org.apache.commons.math3.distribution.TriangularDistribution(a, b, c).probability(x0, x1);
    }
}
