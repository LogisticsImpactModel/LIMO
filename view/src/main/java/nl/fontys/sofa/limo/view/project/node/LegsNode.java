/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.view.project.node;

import java.awt.Image;
import org.netbeans.api.annotations.common.StaticResource;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.ImageUtilities;

/**
 *
 * @author nilsh
 */
public class LegsNode extends AbstractNode {

    @StaticResource()
    public static final String LEGS_ICON = "icons/LegType_SW_16x16.png";

    public LegsNode(Children children) {
        super(children);
    }

    @Override
    public String getName() {
        return "Legs";
    }

    @Override
    public Image getIcon(int type) {
        return ImageUtilities.loadImage(LEGS_ICON);
    }

    @Override
    public Image getOpenedIcon(int type) {
        return getIcon(type);

    }

}
