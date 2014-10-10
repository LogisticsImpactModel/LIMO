package nl.fontys.sofa.limo.api.domain.distribution;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import nl.fontys.sofa.limo.domain.distribution.*;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author Dominik Kaisers <d.kaisers@student.fontys.nl>
 */
@ServiceProvider(service = DistributionTypeFactory.class)
public class DistributionTypeFactoryImpl implements DistributionTypeFactory{
    
    private static HashMap<String, Class<?>> types = null;

    public DistributionTypeFactoryImpl() {
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
    public DistributionType getDistributionTypeByName(String name) {
        initTypes();
        
        Class<?> clazz = types.get(name);
        if (clazz != null) {
            try {
                return (DistributionType) clazz.getConstructor().newInstance();
            } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                Logger.getLogger(DistributionTypeFactoryImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return null;
    }
    
    private void initTypes() {
        if (types != null)
            return;
        
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
