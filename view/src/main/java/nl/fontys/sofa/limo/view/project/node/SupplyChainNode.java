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
import nl.fontys.sofa.limo.view.node.bean.HubNode;
import nl.fontys.sofa.limo.view.node.bean.LegNode;
import org.netbeans.api.annotations.common.StaticResource;
import org.openide.loaders.DataNode;
import org.openide.loaders.DataObject;
import org.openide.nodes.Children;
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
                    Node hubNode = new HubNode((Hub) node);
                    hubs.add(hubNode);
                } catch (IntrospectionException ex) {
                    Exceptions.printStackTrace(ex);
                }
            } else if (node instanceof Leg) {
                try {
                    Node legNode = new LegNode((Leg) node);
                    legs.add(legNode);
                } catch (IntrospectionException ex) {
                    Exceptions.printStackTrace(ex);
                }
            }
            node = node.getNext();
        }
        try {
            Node hubNode = new HubNode((Hub) node);
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

}
