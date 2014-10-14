package nl.fontys.sofa.limo.domain.distribution;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import nl.fontys.sofa.limo.domain.distribution.input.InputValue;
import nl.fontys.sofa.limo.domain.interfaces.Copyable;

public abstract class DistributionType implements Serializable, Copyable<DistributionType> {

    protected Map<String, InputValue> inputValues;
    protected String description;

    /**
     * For caching only!
     */
    protected transient Double probabilityResultCache = null;

    public DistributionType(InputValue... inputValues) {
        this.inputValues = new HashMap<>();
        if (inputValues != null) {
            for (InputValue iv : inputValues) {
                this.inputValues.put(iv.getName(), iv);
            }
        }
        description = "";
    }

    /**
     * FOR ORIENTDB USE ONLY! USE OTHER METHODS TO GET INFORMATION!
     *
     * @return
     */
    public Map<String, InputValue> getInputValues() {
        return inputValues;
    }

    /**
     * FOR ORIENTDB USE ONLY! USE OTHER METHODS TO PUT IN VALUES!
     *
     * @param inputValues
     */
    public void setInputValues(Map<String, InputValue> inputValues) {
        this.inputValues = inputValues;
        this.probabilityResultCache = null;
    }

    /**
     * Gets the input type for a given input value.
     *
     * @param name Name of input value.
     * @return Type of input value. NULL if not known.
     */
    public Class<Number> getInputValueType(String name) {
        if (!this.inputValues.containsKey(name)) {
            return null;
        }

        return this.inputValues.get(name).getValueType();
    }

    /**
     * Gets the value for a given input value.
     *
     * @param name Name of input value.
     * @return Value of input value. NULL if not known.
     */
    public Number getInputValue(String name) {
        if (!this.inputValues.containsKey(name)) {
            return null;
        }

        return this.inputValues.get(name).getValue();
    }

    /**
     * Gets the available input values for the distribution type.
     *
     * @return Names of all available input values.
     */
    public List<String> getInputValueNames() {
        return new ArrayList<>(this.inputValues.keySet());
    }

    /**
     * Sets the value for the input value with the given name. Unknown input
     * values are ignored.
     *
     * @param name Name of input value to set.
     * @param value New value of input value.
     */
    public void setInputValue(String name, Number value) {
        if (!this.inputValues.containsKey(name)) {
            return;
        }

        this.inputValues.get(name).setValue(value);
        this.probabilityResultCache = null;
    }

    /**
     * Sets the values for the input values with the given names. Unknown input
     * values are ignored.
     *
     * @param inputValues Map of input value names and their new values.
     */
    public void setInputValueMap(Map<String, Number> inputValues) {
        for (Map.Entry<String, Number> entry : inputValues.entrySet()) {
            setInputValue(entry.getKey(), entry.getValue());
        }
    }

    /**
     * Gets the probability for the set distribution type and its parameters.
     * Result is cached as long as the parameters do not change.
     *
     * @return (Cached) proability result.
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public DistributionType copy() {
        DistributionType copied = null;
        try {
            copied = this.getClass().getConstructor().newInstance();
            copied.setDescription(description);
            HashMap<String, InputValue> inputValuesMap = new HashMap<>();
            for (Map.Entry<String, InputValue> inputValue : inputValuesMap.entrySet()) {
                inputValuesMap.put(inputValue.getKey(), inputValue.getValue().copy());
            }
            copied.setInputValues(inputValuesMap);
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(DistributionType.class.getName()).log(Level.SEVERE, null, ex);
        }
        return copied;
    }
}
