/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.view.project;

import java.awt.Image;
import javax.swing.Action;
import nl.fontys.sofa.limo.view.action.NewChainAction;
import nl.fontys.sofa.limo.view.project.actions.AddMasterDataAction;
import nl.fontys.sofa.limo.view.project.actions.AddSupplyChainAction;
import org.netbeans.api.annotations.common.StaticResource;
import org.netbeans.spi.project.ui.support.CommonProjectActions;
import org.netbeans.spi.project.ui.support.NodeFactorySupport;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.nodes.FilterNode;
import org.openide.nodes.Node;
import org.openide.util.ImageUtilities;
import org.openide.util.Lookup;
import org.openide.util.lookup.Lookups;
import org.openide.util.lookup.ProxyLookup;

/**
 *
 * @author nilsh
 */
public final class ProjectNode extends FilterNode {

    @StaticResource()
    public static final String CUSTOMER_ICON = "icons/ProcedureCategory_16x16.png";
    final SupplyProject project;

    public SupplyProject getProject() {
        return project;
    }

    public ProjectNode(Node node, SupplyProject project)
            throws DataObjectNotFoundException {
        super(node,
                NodeFactorySupport.createCompositeChildren(
                        project,
                        "Projects/supply-project/Nodes"),
                new ProxyLookup(
                        new Lookup[]{
                            Lookups.singleton(project),
                            node.getLookup()
                        }));
        this.project = project;
    }

    @Override
    public Action[] getActions(boolean arg0) {
        return new Action[]{
            CommonProjectActions.newFileAction(),
            CommonProjectActions.copyProjectAction(),
            CommonProjectActions.deleteProjectAction(),
            CommonProjectActions.closeProjectAction(),
            CommonProjectActions.renameProjectAction(),
            CommonProjectActions.moveProjectAction(),
            new NewChainAction(project),
            new AddMasterDataAction(project),
            new AddSupplyChainAction(project)
        };
    }

    @Override
    public Image getIcon(int type) {
        return ImageUtilities.loadImage(CUSTOMER_ICON);
    }

    @Override
    public Image getOpenedIcon(int type) {
        return getIcon(type);
    }

    @Override
    public String getDisplayName() {
        return project.getProjectDirectory().getName();
    }

}
