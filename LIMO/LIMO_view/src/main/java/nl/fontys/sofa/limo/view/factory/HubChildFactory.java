/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.view.factory;

import java.util.List;
import nl.fontys.sofa.limo.api.service.provider.HubService;
import nl.fontys.sofa.limo.domain.component.hub.Hub;
import org.openide.nodes.ChildFactory;
import org.openide.util.Lookup;

public class HubChildFactory extends ChildFactory<Hub> {

    @Override
    protected boolean createKeys(List<Hub> list) {
        HubService service = Lookup.getDefault().lookup(HubService.class);
        list.addAll(service.findAllHubs());
        return true;
    }
}
