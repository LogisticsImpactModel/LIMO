package nl.fontys.sofa.limo.view.util;

import java.util.List;
import nl.fontys.sofa.limo.domain.BaseEntity;
import nl.fontys.sofa.limo.service.provider.AbstractService;
import org.openide.util.Lookup;

/**
 *
 * @author Mike de Roode
 */
public class BaseEntityUtil {

    /**
     * Check the first available addition for a name. This method does the same
     * as getAddition but also includes the original name.
     *
     * @param service Service which is used to find entities (e.g.
     * {@link nl.fontys.sofa.limo.api.service.provider.HubService}, {@link nl.fontys.sofa.limo.api.service.provider.LegTypeService})
     * @param name The name which should get an addition
     * @return Full unique name
     */
    public static String getUniqueName(Class<? extends Lookup.Provider> service, String name) {
        return name + findFirstAvailableAddition(getAllEntities(service), name);
    }

    /**
     * Check the first available addition for a name.
     *
     * @param service Service which is used to find entities (e.g.
     * {@link nl.fontys.sofa.limo.api.service.provider.HubService}, {@link nl.fontys.sofa.limo.api.service.provider.LegTypeService})
     * @param name The name which should get an addition
     * @return Addition of the name
     */
    public static String getAddition(Class<? extends Lookup.Provider> service, String name) {
        return findFirstAvailableAddition(getAllEntities(service), name);
    }

    /**
     * Returns all entities which are found by the service
     *
     * @param service Service which is used to find entities (e.g.
     * {@link nl.fontys.sofa.limo.api.service.provider.HubService}, {@link nl.fontys.sofa.limo.api.service.provider.LegTypeService})
     * @return All entities in a list.
     */
    public static List<BaseEntity> getAllEntities(Class<? extends Lookup.Provider> service) {
        return ((AbstractService) Lookup.getDefault().lookup(service)).findAll();
    }

    /**
     * Check the first available addition for a name. This method is used to
     * check for an unique names of {@link nl.fontys.sofa.limo.domain.component.hub.Hub}s. The addition is a number.
     *
     * @param items Items to check
     * @param name Name which should be checked
     * @return The first available addition for the name
     */
    public static String findFirstAvailableAddition(List<BaseEntity> items, String name) {
        int i = 1;
        if (!containsBaseEntityWithName(items, name)) {
            return ""; //No addition is needed
        }

        boolean found = false;
        while (!found) {
            if (!containsBaseEntityWithName(items, name + i)) {
                found = true;
            } else {
                i++;
            }
        }
        return "" + i;
    }

    /**
     * Checks if an item in a list contains a certain name
     *
     * @param items Items to check
     * @param name Name which should be checked
     * @return True if an item in the list is found with the same name
     */
    public static boolean containsBaseEntityWithName(List<BaseEntity> items, String name) {
        boolean contains = false;

        for (BaseEntity item : items) {
            if (item.getName().equals(name)) {
                contains = true;
                break;
            }
        }
        return contains;
    }
}
