package nl.fontys.sofa.limo.view.factory;

import java.util.List;
import nl.fontys.sofa.limo.api.service.provider.HubService;
import nl.fontys.sofa.limo.domain.component.hub.Hub;
import org.openide.nodes.ChildFactory;
import org.openide.util.Lookup;

/**
 * Factory for creating the HubNode children.
 *
 * @author Sebastiaan Heijmann
 */
public class HubChildFactory extends ChildFactory<Hub> {

    @Override
    protected boolean createKeys(List<Hub> list) {
        HubService service = Lookup.getDefault().lookup(HubService.class);
        list.addAll(service.findAll());
        return true;
    }
}
