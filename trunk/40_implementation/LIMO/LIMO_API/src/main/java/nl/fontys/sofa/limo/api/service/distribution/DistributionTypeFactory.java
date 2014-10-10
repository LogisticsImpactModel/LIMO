package nl.fontys.sofa.limo.api.service.distribution;

import nl.fontys.sofa.limo.domain.distribution.DistributionType;

/**
 * Interface for the distribution type factory.
 *
 * @author Dominik Kaisers <d.kaisers@student.fontys.nl>
 */
public interface DistributionTypeFactory {
    
    /**
     * Get the possible distribution types by their names.
     * @return Names of possible distribution types.
     */
    String[] getDistributionTypes();
    
    /**
     * Get the name of the distribution type.
     * @param type Type of distribution to get name for.
     * @return Name of given distribution type.
     */
    String getNameForDistributionType(Class<?> type);
    
    /**
     * Creates a new distribution type object for the given name.
     * @param name Name of distribution type to create.
     * @return 
     */
    DistributionType getDistributionTypeByName(String name);
    
}
