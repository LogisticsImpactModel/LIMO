package nl.fontys.sofa.limo.domain.distribution;

import java.util.Random;
import nl.fontys.sofa.limo.domain.distribution.input.DoubleInputValue;
import org.apache.commons.math3.random.RandomGenerator;
import org.apache.commons.math3.random.RandomGeneratorFactory;

/**
 *
 * @author Matthias Brück
 */
public class LevyDistribution extends DistributionType {

    public LevyDistribution() {
        super(new DoubleInputValue("Location", 0.0), new DoubleInputValue("Scale", 0.0), new DoubleInputValue("Lower Bound", 0.0), new DoubleInputValue("Upper Bound", 0.0));
    }

    @Override
    protected void calculateProbability() {
        RandomGenerator rand = RandomGeneratorFactory.createRandomGenerator(new Random());
        double Location = ((DoubleInputValue) inputValues.get("Location")).getValue();
        double scale = ((DoubleInputValue) inputValues.get("Scale")).getValue();
        double x0 = ((DoubleInputValue) inputValues.get("Lower Bound")).getValue();
        double x1 = ((DoubleInputValue) inputValues.get("Upper Bound")).getValue();
        probabilityResultCache = new org.apache.commons.math3.distribution.LevyDistribution(rand, Location, scale).probability(x0, x1);
    }
}
