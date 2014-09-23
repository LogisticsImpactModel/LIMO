package nl.fontys.sofa.limo.domain.distribution;

import java.util.Map;
import java.util.Set;

/**
 *
 * @author Dominik Kaisers <d.kaisers@student.fontys.nl>
 */
public abstract class DistributionType {
    
    protected Map<String, Double> parameters;
    
    /**
     * For caching only!
     */
    protected Double probabilityResultCache = null;

    public DistributionType(String... parameterNames) {
        for (String name : parameterNames) {
            this.parameters.put(name, new Double(0));
        } 
   }
    
    public Set<String> getParameters() {
        return this.parameters.keySet();
    }
    
    public void setParameter(String name, double value) {
        if (!this.parameters.containsKey(name)) {
            return;
        }
        
        this.parameters.put(name, value);
        this.probabilityResultCache = null;
    }
    
    public void setParameters(Map<String, Double> parameters) {
        for (Map.Entry<String, Double> entry : parameters.entrySet()) {
            if (this.parameters.containsKey(entry.getKey())) {
                this.parameters.put(entry.getKey(), entry.getValue());
            }
        }
        this.probabilityResultCache = null;
    }
    
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
