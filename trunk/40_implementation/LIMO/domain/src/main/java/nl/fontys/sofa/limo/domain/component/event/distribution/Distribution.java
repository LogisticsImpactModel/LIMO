package nl.fontys.sofa.limo.domain.component.event.distribution;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.Embedded;
import nl.fontys.sofa.limo.domain.component.event.distribution.input.InputValue;

/**
 * A distribution that can be used for an event to generate a probability of
 * that event happening during a simulation iteration.
 *
 * @author Dominik Kaisers <d.kaisers@student.fontys.nl>
 */
public abstract class Distribution implements Serializable {

    @Embedded
    protected Map<String, InputValue> inputValues;
    protected transient String description;

    /**
     * FOR CACHING ONLY!
     */
    private transient Double probabilityCache;

    /**
     * Construct a new distribution.
     *
     * @param inputValues Input values.
     */
    public Distribution(InputValue... inputValues) {
        this.inputValues = new HashMap<>();
        if (inputValues != null) {
            for (InputValue iv : inputValues) {
                this.inputValues.put(iv.getName(), iv);
            }
        }
    }

    /**
     * FOR ORIENTDB USE ONLY! USE OTHER METHODS TO GET INFORMATION!
     *
     * @return Input values.
     */
    public Map<String, InputValue> getInputValues() {
        return inputValues;
    }

    /**
     * FOR ORIENTDB USE ONLY! USE OTHER METHODS TO PUT IN VALUES!
     *
     * @param inputValues Input values.
     */
    public void setInputValues(Map<String, InputValue> inputValues) {
        this.inputValues = inputValues;
        this.probabilityCache = null;
    }

    /**
     * Gets the input type for a given input value.
     *
     * @param name Name of input value.
     * @return Type of input value. NULL if not known.
     */
    public Class<Number> getType(String name) {
        if (!this.inputValues.containsKey(name)) {
            return null;
        }

        return this.inputValues.get(name).getType();
    }

    /**
     * Gets the value for a given input value.
     *
     * @param name Name of input value.
     * @return Value of input value. NULL if not known.
     */
    public Number getValue(String name) {
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
    public List<String> getNames() {
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
        this.probabilityCache = null;
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
        if (this.probabilityCache == null) {
            this.probabilityCache = calculateProbability();
        }
        return this.probabilityCache;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Calculate the specific distribution probability and save it to the cache.
     *
     * @return the probability.
     */
    protected abstract double calculateProbability();

}
