package nl.fontys.sofa.limo.api.service.provider;

import java.util.List;
import nl.fontys.sofa.limo.domain.component.leg.Leg;
import nl.fontys.sofa.limo.domain.component.type.LegType;
import org.openide.util.Lookup;

/**
 * Interface which defines service methods for Legs and LegTypes and manages a
 * lookup. A LookupListener can be attached to the LookupResult to listen for
 * changes in the datamodels.

 *
 * @author Sebastiaan Heijmann
 */
public interface LegService extends Lookup.Provider{

	Leg findLegById(int id);

	LegType findLegTypeById(int id);

	List<Leg> findAllLegs();

	List<LegType> findAllLegTypes();

	Leg insertLeg(Leg leg);

	LegType insertLegType(LegType type);

	boolean updateLeg(Leg leg);

	boolean updateLegType(LegType type);

	boolean deleteLeg(Leg leg);

	boolean deleteLegType(LegType type);

	@Override
	public Lookup getLookup();

}
