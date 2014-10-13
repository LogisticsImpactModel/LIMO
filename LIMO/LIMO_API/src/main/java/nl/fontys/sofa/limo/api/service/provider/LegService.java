package nl.fontys.sofa.limo.api.service.provider;

import java.util.List;
import nl.fontys.sofa.limo.domain.component.Leg;
import nl.fontys.sofa.limo.domain.types.LegType;
import org.openide.util.Lookup;

/**
 * Interface which defines service methods for Legs and LegTypes and manages a
 * lookup.
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
