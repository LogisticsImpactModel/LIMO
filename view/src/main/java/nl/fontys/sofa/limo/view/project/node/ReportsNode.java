/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.view.project.node;

import java.awt.Image;
import org.netbeans.api.annotations.common.StaticResource;
import org.openide.nodes.FilterNode;
import org.openide.nodes.Node;
import org.openide.util.ImageUtilities;

/**
 *
 * @author nilsh
 */
public class ReportsNode extends FilterNode {

    @StaticResource()
    public static final String REPORTS_ICON = "icons/gui/Report.png";

    public ReportsNode(Node original, org.openide.nodes.Children children) {
        super(original, children);
    }

    @Override
    public Image getIcon(int type) {
        return ImageUtilities.loadImage(REPORTS_ICON);
    }

    @Override
    public Image getOpenedIcon(int type) {
        return getIcon(type);
    }

    @Override
    public String getDisplayName() {
        return "Reports";
    }

}
