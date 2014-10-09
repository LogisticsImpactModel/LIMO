/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.view.factory;

import java.util.List;
import nl.fontys.sofa.limo.api.dao.DAOFactory;
import nl.fontys.sofa.limo.api.dao.HubDAO;
import nl.fontys.sofa.limo.domain.component.Hub;
import org.openide.nodes.ChildFactory;
import org.openide.util.Lookup;

public class HubChildFactory extends ChildFactory<Hub> {

    @Override
    protected boolean createKeys(List<Hub> list) {
        DAOFactory df = Lookup.getDefault().lookup(DAOFactory.class);
        HubDAO hd = df.getHubDAO();
        List<Hub> hl = hd.findAll();
        for (Hub hu : hl) {
            list.add(hu);
        }
        return true;
    }
}
