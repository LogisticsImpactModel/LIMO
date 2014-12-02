/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.view.factory;

import java.beans.IntrospectionException;
import java.beans.PropertyChangeEvent;
import java.util.List;
import nl.fontys.sofa.limo.domain.component.procedure.Procedure;
import org.openide.nodes.BeanNode;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;
import org.openide.nodes.NodeEvent;
import org.openide.nodes.NodeListener;
import org.openide.nodes.NodeMemberEvent;
import org.openide.nodes.NodeReorderEvent;
import org.openide.util.Exceptions;

/**
 * Factory responsible for creating the procedure children. It listens to
 * changes in the nodes.
 *
 * @author Sebastiaan Heijmann
 */
public class ProcedureChildFactory extends ChildFactory<Procedure>
        implements NodeListener {

    private List<Procedure> procedureList;

    public ProcedureChildFactory(List<Procedure> procedures) {
        this.procedureList = procedures;
    }

    @Override
    protected boolean createKeys(List<Procedure> list) {
        list.addAll(procedureList);
        return true;
    }

    @Override
    protected Node createNodeForKey(Procedure key) {
        BeanNode node = null;
        try {
            node = new BeanNode(key);
            node.addNodeListener(this);
        } catch (IntrospectionException ex) {
            Exceptions.printStackTrace(ex);
        }
        return node;
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
    }
}
