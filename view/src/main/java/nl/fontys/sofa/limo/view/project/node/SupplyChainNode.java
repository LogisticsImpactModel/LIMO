/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.view.project.node;

import java.awt.Image;
import java.beans.IntrospectionException;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import nl.fontys.sofa.limo.domain.component.SupplyChain;
import nl.fontys.sofa.limo.domain.component.hub.Hub;
import nl.fontys.sofa.limo.domain.component.leg.Leg;
import nl.fontys.sofa.limo.view.node.bean.EventNode;
import nl.fontys.sofa.limo.view.node.bean.HubNode;
import nl.fontys.sofa.limo.view.node.bean.LegNode;
import nl.fontys.sofa.limo.view.node.bean.ProcedureNode;
import nl.fontys.sofa.limo.view.project.actions.ExportChainAction;
import nl.fontys.sofa.limo.view.project.actions.ProjectChainSimulateAction;
import nl.fontys.sofa.limo.view.project.actions.SaveChainAction;
import nl.fontys.sofa.limo.view.project.actions.util.CloseChainAction;
import nl.fontys.sofa.limo.view.project.actions.util.OpenChainAction;
import nl.fontys.sofa.limo.view.project.supplychain.LimoFilterNode;
import org.netbeans.api.annotations.common.StaticResource;
import org.openide.actions.DeleteAction;
import org.openide.actions.RenameAction;
import org.openide.loaders.DataNode;
import org.openide.loaders.DataObject;
import org.openide.nodes.Children;
import org.openide.nodes.Index;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.util.ImageUtilities;
import org.openide.util.actions.SystemAction;

/**
 *
 * @author nilsh
 */
public class SupplyChainNode extends DataNode implements PropertyChangeListener {

    @StaticResource()
    public static final String CHAIN_ICON = "icons/gui/Link_CL.png";

    private final SupplyChain chain;
    private final CloseChainAction closeAction;
    private final AbstractAction openAction;
    private final SaveChainAction saveAction;
    private Children hubChildren;
    private Children legChildren;

    public SupplyChainNode(DataObject object) {
        super(object, Children.LEAF);
        chain = SupplyChain.createFromFile(new File(object.getPrimaryFile().getPath()));
        closeAction = new CloseChainAction();
        saveAction = new SaveChainAction();
        openAction = new OpenChainAction(chain, closeAction, saveAction);
        setChildren(createChildNodes());
        chain.addListerner(this);
    }

    @Override
    public Image getIcon(int type) {
        return ImageUtilities.loadImage(CHAIN_ICON);
    }

    @Override
    public Image getOpenedIcon(int type) {
        return getIcon(type);
    }

    @Override
    public String getDisplayName() {
        String s = super.getDisplayName();
        return s.substring(0, s.length() - 4);
    }

    @Override

    public Action getPreferredAction() {
        return openAction;
    }

    @Override
    public Action[] getActions(boolean context) {
        return new Action[]{
            openAction,
            closeAction,
            saveAction,
            new ExportChainAction(chain),
            new ProjectChainSimulateAction(chain),
            SystemAction.get(RenameAction.class),
            SystemAction.get(DeleteAction.class)
        };
    }

    private Children createChildNodes() {
        List<Node> hubs = new ArrayList<>();
        List<Node> legs = new ArrayList<>();

        nl.fontys.sofa.limo.domain.component.Node node = chain.getStartHub();
        if (node != null) {
            while (node.getNext() != null) {

                if (node instanceof Hub) {
                    try {
                        Children children = createNodeChildren(node);
                        Node hubNode = new LimoFilterNode(new HubProjectNode((Hub) node), children);
                        hubs.add(hubNode);
                    } catch (IntrospectionException ex) {
                        Exceptions.printStackTrace(ex);
                    }
                } else if (node instanceof Leg) {
                    try {
                        Children children = createNodeChildren(node);
                        Node legNode = new LimoFilterNode(new LegNode((Leg) node), children);
                        legs.add(legNode);
                    } catch (IntrospectionException ex) {
                        Exceptions.printStackTrace(ex);
                    }
                }
                node = node.getNext();
            }
            try {
                Children children = createNodeChildren(node);
                Node hubNode = new LimoFilterNode(new HubNode((Hub) node), children);
                hubs.add(hubNode);
            } catch (IntrospectionException ex) {
                Exceptions.printStackTrace(ex);
            }
        }

        hubChildren = new Index.ArrayChildren();
        hubChildren.add(hubs.toArray(new Node[0]));

        legChildren = new Index.ArrayChildren();
        legChildren.add(legs.toArray(new Node[0]));

        HubsNode hubsNode = new HubsNode(hubChildren);
        LegsNode legsNode = new LegsNode(legChildren);

        Children c = new Index.ArrayChildren();
        Node[] n = {hubsNode, legsNode};
        c.add(n);
        return c;
    }

    private Children createNodeChildren(nl.fontys.sofa.limo.domain.component.Node node) {
        List<Node> procedures = new ArrayList<>();
        List<Node> events = new ArrayList<>();

        node.getEvents().parallelStream().forEach((event) -> {
            try {
                EventNode e = new EventProjectNode(event);
                events.add(e);
            } catch (IntrospectionException ex) {
                Exceptions.printStackTrace(ex);
            }
        });

        node.getProcedures().stream().forEach(((procedure) -> {
            try {
                ProcedureNode p = new ProcedureProjectNode(procedure);
                procedures.add(p);
            } catch (IntrospectionException ex) {
                Exceptions.printStackTrace(ex);
            }
        }));

        Children procChildren = new Index.ArrayChildren();
        Children eventChildren = new Index.ArrayChildren();

        procChildren.add(procedures.toArray(new Node[0]));
        eventChildren.add(events.toArray(new Node[0]));
        Node[] n = {new EventsNode(eventChildren), new ProceduresNode(procChildren)};

        Children children = new Index.ArrayChildren();
        children.add(n);
        return children;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        switch (evt.getPropertyName()) {
            case ("ADD_HUB"): {
                try {
                    Hub hub = (Hub) evt.getNewValue();
                    for (Node n : hubChildren.getNodes()) {
                        LimoFilterNode f = (LimoFilterNode) n;
                        HubNode hn = (HubNode) f.getOriginal();
                        Hub h = hn.getHub();
                        if (h.equals(hub)) {
                            return;
                        }
                    }
                    Children children = createNodeChildren(hub);
                    Node hubNode = new LimoFilterNode(new HubNode(hub), children);
                    Node[] n = {hubNode};
                    hubChildren.add(n);
                } catch (IntrospectionException ex) {
                    Exceptions.printStackTrace(ex);
                }
                return;
            }

            case ("REMOVE_HUB"): {
                Hub hub = (Hub) evt.getOldValue();
                for (Node n : hubChildren.getNodes()) {
                    LimoFilterNode f = (LimoFilterNode) n;
                    HubNode hn = (HubNode) f.getOriginal();
                    Hub h = hn.getHub();
                    if (h.equals(hub)) {
                        Node[] nodes = {n};
                        hubChildren.remove(nodes);
                    }
                }
                return;
            }

            case ("ADD_LEG"): {
                try {
                    Leg leg = (Leg) evt.getNewValue();
                    for (Node n : legChildren.getNodes()) {
                        LimoFilterNode f = (LimoFilterNode) n;
                        LegNode ln = (LegNode) f.getOriginal();
                        Leg l = ln.getLeg();
                        if (l.equals(leg)) {
                            return;
                        }
                    }
                    Children children = createNodeChildren(leg);
                    Node legNode = new LimoFilterNode(new LegNode(leg), children);
                    Node[] n = {legNode};
                    legChildren.add(n);
                } catch (IntrospectionException ex) {
                    Exceptions.printStackTrace(ex);
                }
                return;
            }
            case ("REMOVE_LEG"): {
                Leg leg = (Leg) evt.getOldValue();
                for (Node n : legChildren.getNodes()) {
                    LimoFilterNode f = (LimoFilterNode) n;
                    LegNode ln = (LegNode) f.getOriginal();
                    Leg l = ln.getLeg();
                    if (l.equals(leg)) {
                        Node[] nodes = {n};
                        legChildren.remove(nodes);
                    }
                }
                return;
            }
        }

    }
}
