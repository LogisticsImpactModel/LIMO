/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.view.project.node;

import java.awt.Image;
import java.beans.IntrospectionException;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import nl.fontys.sofa.limo.domain.component.SupplyChain;
import nl.fontys.sofa.limo.domain.component.hub.Hub;
import nl.fontys.sofa.limo.domain.component.leg.Leg;
import nl.fontys.sofa.limo.view.node.bean.EventNode;
import nl.fontys.sofa.limo.view.node.bean.HubNode;
import nl.fontys.sofa.limo.view.node.bean.LegNode;
import nl.fontys.sofa.limo.view.node.bean.ProcedureNode;
import org.netbeans.api.annotations.common.StaticResource;
import org.openide.loaders.DataNode;
import org.openide.loaders.DataObject;
import org.openide.nodes.Children;
import org.openide.nodes.FilterNode;
import org.openide.nodes.Index;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.util.ImageUtilities;

/**
 *
 * @author nilsh
 */
public class SupplyChainNode extends DataNode {

    @StaticResource()
    public static final String CHAIN_ICON = "icons/gui/Link.png";

    private DataObject dataObject;
    private SupplyChain chain;

    public SupplyChainNode(DataObject object) {
        super(object, Children.LEAF);
        chain = SupplyChain.createFromFile(new File(object.getPrimaryFile().getPath()));
        setChildren(createChildNodes());
    }

    @Override
    public Image getIcon(int type) {
        return ImageUtilities.loadImage(CHAIN_ICON);
    }

    @Override
    public String getDisplayName() {
        String s = super.getDisplayName();
        return s.substring(0, s.length() - 4);
    }

    private Children createChildNodes() {
        List<Node> hubs = new ArrayList<>();
        List<Node> legs = new ArrayList<>();

        nl.fontys.sofa.limo.domain.component.Node node = chain.getStartHub();
        while (node.getNext() != null) {

            if (node instanceof Hub) {
                try {
                    Children children = createNodeChildren(node);
                    Node hubNode = new FilterNode(new HubNode((Hub) node), children);
                    hubs.add(hubNode);
                } catch (IntrospectionException ex) {
                    Exceptions.printStackTrace(ex);
                }
            } else if (node instanceof Leg) {
                try {
                    Children children = createNodeChildren(node);
                    Node legNode = new FilterNode(new LegNode((Leg) node), children);
                    legs.add(legNode);
                } catch (IntrospectionException ex) {
                    Exceptions.printStackTrace(ex);
                }
            }
            node = node.getNext();
        }
        try {
            Children children = createNodeChildren(node);
            Node hubNode = new FilterNode(new HubNode((Hub) node), children);
            hubs.add(hubNode);
        } catch (IntrospectionException ex) {
            Exceptions.printStackTrace(ex);
        }

        Children hubChildren = new Index.ArrayChildren();
        hubChildren.add(hubs.toArray(new Node[0]));

        Children legChildren = new Index.ArrayChildren();
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
                EventNode e = new EventNode(event);
                events.add(e);
            } catch (IntrospectionException ex) {
                Exceptions.printStackTrace(ex);
            }
        });

        node.getProcedures().parallelStream().forEach(((procedure) -> {
            try {
                ProcedureNode p = new ProcedureNode(procedure);
                procedures.add(p);
            } catch (IntrospectionException ex) {
                Exceptions.printStackTrace(ex);
            }
        }));

        Children procChildren = new Index.ArrayChildren();
        Children eventChildren = new Index.ArrayChildren();

        procChildren.add(procedures.toArray(new Node[0]));
        eventChildren.add(events.toArray(new Node[0]));
        Node[] n = {new FilterNode(this, eventChildren), new FilterNode(this, procChildren)};

        Children children = new Index.ArrayChildren();
        children.add(n);
        return children;
    }

}
