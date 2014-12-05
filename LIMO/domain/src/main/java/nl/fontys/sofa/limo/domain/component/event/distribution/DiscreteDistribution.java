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
        this.description = "This distribution calculates the probability\n"
                + "of X / Y.\n"
                + "X is elemnt of (negativ infinity, positiv infinity)\n"
                + "Y is elemnt of (0, positiv infinitiy)";
    }

    @Override
    protected double calculateProbability() {
        double x = ((IntegerInputValue) inputValues.get("X")).getValue();
        double y = ((IntegerInputValue) inputValues.get("Y")).getValue();
        return x / y;
    }

}
