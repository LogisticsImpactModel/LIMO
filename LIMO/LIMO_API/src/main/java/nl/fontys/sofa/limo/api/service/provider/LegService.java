package nl.fontys.sofa.limo.api.service.provider;

import java.util.List;
import nl.fontys.sofa.limo.domain.component.leg.Leg;
import org.openide.util.Lookup;

/**
 * Interface which defines service methods for Legs and manages a lookup. A
 * LookupListener can be attached to the LookupResult to listen for changes in
 * the datamodels.
 *
 * @author Sebastiaan Heijmann
 */
public interface LegService extends Lookup.Provider{

	Leg findLegById(int id);

	List<Leg> findAllLegs();

	Leg insertLeg(Leg leg);

	boolean updateLeg(Leg leg);

	boolean deleteLeg(Leg leg);

	@Override
	public Lookup getLookup();

}
