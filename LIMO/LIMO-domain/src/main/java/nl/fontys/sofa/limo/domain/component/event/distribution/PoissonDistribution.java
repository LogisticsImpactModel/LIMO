package nl.fontys.sofa.limo.domain.component.event.distribution;

import nl.fontys.sofa.limo.domain.component.event.distribution.input.DoubleInputValue;
import nl.fontys.sofa.limo.domain.component.event.distribution.input.IntegerInputValue;

/**
 * Poisson distribution implementation.
 *
 * @author Dominik Kaisers <d.kaisers@student.fontys.nl>
 */
public class PoissonDistribution extends Distribution {

    public PoissonDistribution() {
        super(new DoubleInputValue("Lambda", 0.0), new IntegerInputValue("K", 0));
    }

    @Override
    protected double calculateProbability() {
        Double lambda = ((DoubleInputValue) inputValues.get("Lambda")).getValue();
        int k = ((IntegerInputValue) inputValues.get("K")).getValue();
        return new org.apache.commons.math3.distribution.PoissonDistribution(lambda).probability(k);
    }
}
