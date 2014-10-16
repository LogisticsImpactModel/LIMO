package nl.fontys.sofa.limo.api.service.provider;

import nl.fontys.sofa.limo.api.dao.DAO;
import nl.fontys.sofa.limo.domain.component.leg.Leg;
import org.openide.util.Lookup;

/**
 * Interface which defines service methods for Legs and manages a lookup. A
 * LookupListener can be attached to the LookupResult to listen for changes in
 * the datamodels.
 *
 * @author Sebastiaan Heijmann
 */
public interface LegService extends Lookup.Provider, DAO<Leg>{
}
