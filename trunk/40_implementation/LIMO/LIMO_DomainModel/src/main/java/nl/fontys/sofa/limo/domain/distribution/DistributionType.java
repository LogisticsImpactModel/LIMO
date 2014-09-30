package nl.fontys.sofa.limo.domain.distribution;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class DistributionType implements Serializable {

    protected Map<String, Number> parameters;
    protected Map<String, Class<?>> parameterTypes;

    /**
     * For caching only!
     */
    protected Double probabilityResultCache = null;

    public DistributionType(Map.Entry<String, Class<?>>... parameterTypes) {
        this.parameters = new HashMap<>();
        this.parameterTypes = new HashMap<>();
        for (Map.Entry<String, Class<?>> entry : parameterTypes) {
            this.parameterTypes.put(entry.getKey(), entry.getValue());
            try {
                this.parameters.put(entry.getKey(), (Number) entry.getValue().getConstructors()[0].newInstance(0));
            } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                Logger.getLogger(DistributionType.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public Map<String, Class<?>> getParameterTypes() {
        return this.parameterTypes;
    }

    public void setParameter(String name, Number value) {
        if (!this.parameters.containsKey(name)) {
            return;
        }
        this.parameters.put(name, value);
        this.probabilityResultCache = null;
    }

    public void setParameters(Map<String, Number> parameters) {
        for (Map.Entry<String, Number> entry : parameters.entrySet()) {
            if (this.parameters.containsKey(entry.getKey())) {
                this.parameters.put(entry.getKey(), entry.getValue());
            }
        }
        this.probabilityResultCache = null;
    }

    /**
     *
     * @return
     */
    public double getProbability() {
        if (this.probabilityResultCache == null) {
            calculateProbability();
        }
        return this.probabilityResultCache;
    }

    /**
     * Calculate the specific distribution probability and save it to the cache.
     */
    protected abstract void calculateProbability();
}
