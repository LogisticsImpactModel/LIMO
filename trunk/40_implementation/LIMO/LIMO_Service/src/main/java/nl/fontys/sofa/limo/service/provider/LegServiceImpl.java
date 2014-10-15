package nl.fontys.sofa.limo.service.provider;

import java.util.List;
import nl.fontys.sofa.limo.api.dao.LegDAO;
import nl.fontys.sofa.limo.api.exception.DAONotFoundException;
import nl.fontys.sofa.limo.api.service.provider.LegService;
import nl.fontys.sofa.limo.domain.component.leg.Leg;
import org.openide.util.Lookup;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;
import org.openide.util.lookup.ServiceProvider;

/**
 * Implementation of the LegService interface.
 *
 * @author Sebastiaan Heijmann
 */
@ServiceProvider(service = LegService.class)
public class LegServiceImpl implements LegService{
	private final LegDAO dao;
	private final InstanceContent instanceContent;
	private final Lookup lookup;

	public LegServiceImpl() throws DAONotFoundException {
		dao = Lookup.getDefault().lookup(LegDAO.class);
		instanceContent = new InstanceContent();
		lookup = new AbstractLookup(instanceContent);

		if(dao == null){
			throw new DAONotFoundException("LegDAO not found...");
		}else{
			instanceContent.set(dao.findAll(), null);
		}
	}

	@Override
	public Leg findLegById(int id) {
		return dao.findById(String.valueOf(id));
	}

	@Override
	public List<Leg> findAllLegs() {
		return dao.findAll();
	}

	@Override
	public Leg insertLeg(Leg leg) {
		Leg result = dao.insert(leg);
		instanceContent.add(leg);
		return result;
	}

	@Override
	public boolean updateLeg(Leg leg) {
		boolean result = dao.update(leg);
		instanceContent.add(result);
		return result;
	}

	@Override
	public boolean deleteLeg(Leg leg) {
		boolean result = dao.delete(leg);
		instanceContent.set(dao.findAll(), null);
		return result;
	}

	@Override
	public Lookup getLookup() {
		return lookup;
	}

}
