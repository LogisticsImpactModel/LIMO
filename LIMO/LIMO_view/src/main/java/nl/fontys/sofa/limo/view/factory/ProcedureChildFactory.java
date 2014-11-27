/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.view.factory;

import java.beans.PropertyChangeEvent;
import java.util.List;
import nl.fontys.sofa.limo.domain.component.event.Event;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.NodeEvent;
import org.openide.nodes.NodeListener;
import org.openide.nodes.NodeMemberEvent;
import org.openide.nodes.NodeReorderEvent;

/**
 * Factory responsible for creating the procedure children. It listens to
 * changes in the nodes.
 *
 * @author Sebastiaan Heijmann
 */
public class ProcedureChildFactory extends ChildFactory<Event>
        implements NodeListener {

    @Override
    protected boolean createKeys(List<Event> toPopulate) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void childrenAdded(NodeMemberEvent ev) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void childrenRemoved(NodeMemberEvent ev) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void childrenReordered(NodeReorderEvent ev) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void nodeDestroyed(NodeEvent ev) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void propertyChange(PropertyChangeEvent pce) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
