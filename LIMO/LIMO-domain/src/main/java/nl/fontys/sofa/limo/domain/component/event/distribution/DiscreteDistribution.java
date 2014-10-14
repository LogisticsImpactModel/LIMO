package nl.fontys.sofa.limo.domain.component.event.distribution;

import nl.fontys.sofa.limo.domain.component.event.distribution.input.IntegerInputValue;

/**
 * Discrete distribution implementation.
 *
 * @author Dominik Kaisers <d.kaisers@student.fontys.nl>
 */
public class DiscreteDistribution extends Distribution {

    public DiscreteDistribution() {
        super(new IntegerInputValue("X", 0), new IntegerInputValue("Y", 1));
    }

    @Override
    protected double calculateProbability() {
        double x = ((IntegerInputValue) inputValues.get("X")).getValue();
        double y = ((IntegerInputValue) inputValues.get("Y")).getValue();
        return x / y;
    }
    
}
