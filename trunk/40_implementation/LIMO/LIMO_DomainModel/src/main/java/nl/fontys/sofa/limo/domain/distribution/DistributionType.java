package nl.fontys.sofa.limo.domain.distribution;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class DistributionType implements Serializable {

    protected ArrayList<String> parameterNames;
    protected ArrayList<Number> parameters;
    protected ArrayList<Class<?>> parameterTypes;

    /**
     * For caching only!
     */
    protected Double probabilityResultCache = null;

    public DistributionType(Map.Entry<String, Class<?>>... parameterTypes) {
        this.parameters = new ArrayList<>();
        this.parameterNames = new ArrayList<>();
        this.parameterTypes = new ArrayList<>();
        for (Map.Entry<String, Class<?>> entry : parameterTypes) {
            this.parameterNames.add(entry.getKey());
            this.parameterTypes.add(entry.getValue());
            try {
                this.parameters.add((Number) entry.getValue().getConstructors()[0].newInstance(0));
            } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                Logger.getLogger(DistributionType.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public Map<String, Class<?>> getParameterTypes() {
        HashMap<String, Class<?>> typeMap = new HashMap<>();
        for (int i = 0; i < parameterNames.size(); i++) {
            typeMap.put(parameterNames.get(i), parameterTypes.get(i));
        }
        return typeMap;
    }

    public void setParameter(String name, Number value) {
        int position = parameterNames.indexOf(name);
        if (position == -1) {
            return;
        }
        parameters.set(position, value);
        this.probabilityResultCache = null;
    }

    public void setParameters(Map<String, Number> parameters) {
        for (Map.Entry<String, Number> entry : parameters.entrySet()) {
            int position = parameterNames.indexOf(entry.getKey());
            if (position != -1) {
                this.parameters.set(position, entry.getValue());
            }
        }
        this.probabilityResultCache = null;
    }
    
    public int getNumberOfParameters(){
        return parameterNames.size();
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
