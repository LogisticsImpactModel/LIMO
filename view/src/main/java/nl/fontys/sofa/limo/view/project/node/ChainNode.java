/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.view.project.node;

import java.awt.Image;
import org.netbeans.api.annotations.common.StaticResource;
import org.openide.loaders.DataNode;
import org.openide.loaders.DataObject;
import org.openide.nodes.Node;
import org.openide.util.ImageUtilities;

/**
 *
 * @author nilsh
 */
public class ChainNode extends DataNode {

    @StaticResource()
    public static final String CHAIN_ICON = "icons/gui/Link.png";

    public ChainNode(DataObject original, org.openide.nodes.Children children) {
        super(original, children);
        setChildren(children);
    }

    @Override
    public Image getOpenedIcon(int type) {
        return getIcon(type);
    }

    @Override
    public Image getIcon(int type) {
        return ImageUtilities.loadImage(CHAIN_ICON);
    }

    public void addChild(Node node) {
        Node[] nodes = {node};
        getChildren().add(nodes);
    }

}
