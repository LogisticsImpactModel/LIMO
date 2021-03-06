package nl.fontys.sofa.limo.service.distribution;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import nl.fontys.sofa.limo.api.service.distribution.DistributionFactory;
import nl.fontys.sofa.limo.domain.component.event.distribution.*;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author Dominik Kaisers {@literal <d.kaisers@student.fontys.nl>}
 */
@ServiceProvider(service = DistributionFactory.class)
public class DistributionFactoryImpl implements DistributionFactory {

    private static HashMap<String, Class<?>> types = null;

    public DistributionFactoryImpl() {
        initTypes();
    }

    @Override
    public String[] getDistributionTypes() {
        initTypes();

        String[] returnValue = new String[types.size()];
        types.keySet().toArray(returnValue);
        return returnValue;
    }

    @Override
    public String getNameForDistributionType(Class<?> type) {
        for (Map.Entry<String, Class<?>> entry : types.entrySet()) {
            if (type == entry.getValue()) {
                return entry.getKey();
            }
        }
        return null;
    }

    @Override
    public Distribution getDistributionTypeByName(String name) {
        initTypes();

        Class<?> clazz = types.get(name);
        if (clazz != null) {
            try {
                return (Distribution) clazz.getConstructor().newInstance();
            } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                Logger.getLogger(DistributionFactoryImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return null;
    }

    /**
     * Puts all distribution types that are defined in classes with their name
     * (as string) into hashMap
     */
    private void initTypes() {
        if (types != null) {
            return;
        }

        types = new HashMap<>();
        types.put("Cauchy", CauchyDistribution.class);
        types.put("Chi Squared", ChiSquaredDistribution.class);
        types.put("Discrete", DiscreteDistribution.class);
        types.put("Exponential", ExponentionalDistribution.class);
        types.put("F", FDistribution.class);
        types.put("Gamma", GammaDistribution.class);
        types.put("Log Normal", LogNormalDistribution.class);
        types.put("Normal", NormalDistribution.class);
        types.put("Poisson", PoissonDistribution.class);
        types.put("Triangular", TriangularDistribution.class);
        types.put("Weibull", WeibullDistribution.class);
    }

}
