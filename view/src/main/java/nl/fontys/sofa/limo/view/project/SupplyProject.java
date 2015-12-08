/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.view.project;

import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import nl.fontys.sofa.limo.view.project.actions.util.SupplyDefaultCopyOperation;
import nl.fontys.sofa.limo.view.project.actions.util.SupplyDefaultMoveOrRenameOperation;
import nl.fontys.sofa.limo.view.project.supplychain.ChainNodeList;
import org.netbeans.api.annotations.common.StaticResource;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectInformation;
import org.netbeans.spi.project.ActionProvider;
import org.netbeans.spi.project.DeleteOperationImplementation;
import org.netbeans.spi.project.ProjectState;
import org.netbeans.spi.project.ui.LogicalViewProvider;
import org.netbeans.spi.project.ui.support.DefaultProjectOperations;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataFolder;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.util.ImageUtilities;
import org.openide.util.Lookup;
import org.openide.util.lookup.Lookups;

/**
 *
 * @author nilsh
 */
public class SupplyProject implements Project {

    private final FileObject projectDir;
    private final ProjectState state;
    private Lookup lkp;
    private ChainNodeList chainNodeList;

    public SupplyProject() {
        this.projectDir = null;
        this.state = null;
    }

    public SupplyProject(FileObject dir, ProjectState state) {
        this.projectDir = dir;
        this.state = state;
    }

    @Override
    public FileObject getProjectDirectory() {
        return projectDir;
    }

    @Override
    public Lookup getLookup() {
        if (lkp == null) {
            lkp = Lookups.fixed(new Object[]{
                this,
                new Info(),
                new SupplyProjectLogicalView(this),
                new SupplyActionProvider(),
                new SupplyDefaultMoveOrRenameOperation(),
                new SupplyDefaultCopyOperation(),
                new SupplyProjectDeleteOperation(this)
            });
        }
        return lkp;
    }

    public void addChainNodeList(ChainNodeList aThis) {
        chainNodeList = aThis;
    }

    public ChainNodeList getChainNodeList() {
        return chainNodeList;
    }

    private final class Info implements ProjectInformation {

        @StaticResource()
        public static final String CUSTOMER_ICON = "icons/ProcedureCategory_16x16.png";

        @Override
        public Icon getIcon() {
            return new ImageIcon(ImageUtilities.loadImage(CUSTOMER_ICON));
        }

        @Override
        public String getName() {
            return getProjectDirectory().getName();
        }

        @Override
        public String getDisplayName() {
            return getName();
        }

        @Override
        public void addPropertyChangeListener(PropertyChangeListener pcl) {
            //do nothing, won't change
        }

        @Override
        public void removePropertyChangeListener(PropertyChangeListener pcl) {
            //do nothing, won't change
        }

        @Override
        public Project getProject() {
            return SupplyProject.this;
        }
    }

    private class SupplyProjectLogicalView implements LogicalViewProvider {

        @StaticResource()
        public static final String CUSTOMER_ICON = "icons/ProcedureCategory_16x16.png";

        private final SupplyProject project;

        public SupplyProjectLogicalView(SupplyProject project) {
            this.project = project;
        }

        @Override
        public Node createLogicalView() {
            try {
                //Obtain the project directory's node:
                FileObject projectDirectory = project.getProjectDirectory();
                DataFolder projectFolder = DataFolder.findFolder(projectDirectory);
                Node nodeOfProjectFolder = projectFolder.getNodeDelegate();
                //Decorate the project directory's node:
                return new ProjectNode(nodeOfProjectFolder, project);
            } catch (DataObjectNotFoundException donfe) {
                Exceptions.printStackTrace(donfe);
                //Fallback-the directory couldn't be created -
                //read-only filesystem or something evil happened
                return new AbstractNode(Children.LEAF);
            }
        }

        @Override
        public Node findPath(Node root, Object target) {
            //leave unimplemented for now
            return null;
        }

    }

    private class SupplyActionProvider implements ActionProvider {

        @Override
        public String[] getSupportedActions() {
            return new String[]{
                ActionProvider.COMMAND_RENAME,
                ActionProvider.COMMAND_MOVE,
                ActionProvider.COMMAND_COPY,
                ActionProvider.COMMAND_DELETE
            };
        }

        @Override
        public void invokeAction(String string, Lookup lkp) throws IllegalArgumentException {
            if (string.equalsIgnoreCase(ActionProvider.COMMAND_RENAME)) {
                DefaultProjectOperations.performDefaultRenameOperation(
                        SupplyProject.this,
                        "");
            }
            if (string.equalsIgnoreCase(ActionProvider.COMMAND_MOVE)) {
                DefaultProjectOperations.performDefaultMoveOperation(
                        SupplyProject.this);
            }
            if (string.equalsIgnoreCase(ActionProvider.COMMAND_COPY)) {
                DefaultProjectOperations.performDefaultCopyOperation(
                        SupplyProject.this);
            }
            if (string.equalsIgnoreCase(ActionProvider.COMMAND_DELETE)) {
                DefaultProjectOperations.performDefaultDeleteOperation(
                        SupplyProject.this);
            }

        }

        @Override
        public boolean isActionEnabled(String command, Lookup lkp) throws IllegalArgumentException {
            switch (command) {
                case ActionProvider.COMMAND_RENAME:
                    return true;
                case ActionProvider.COMMAND_MOVE:
                    return true;
                case ActionProvider.COMMAND_COPY:
                    return true;
                case ActionProvider.COMMAND_DELETE:
                    return true;
                default:
                    break;
            }
            return false;
        }
    }

    private final class SupplyProjectDeleteOperation implements DeleteOperationImplementation {

        private final SupplyProject project;

        public SupplyProjectDeleteOperation(SupplyProject project) {
            this.project = project;
        }

        @Override
        public List<FileObject> getMetadataFiles() {
            return new ArrayList<>();
        }

        @Override
        public List<FileObject> getDataFiles() {
            List<FileObject> files = new ArrayList<>();
            FileObject[] projectChildren = project.getProjectDirectory().getChildren();
            for (FileObject fileObject : projectChildren) {
                addFile(project.getProjectDirectory(), fileObject.getNameExt(), files);
            }
            return files;
        }

        private void addFile(FileObject projectDirectory, String fileName, List<FileObject> result) {
            FileObject file = projectDirectory.getFileObject(fileName);
            if (file != null) {
                result.add(file);
            }
        }

        @Override
        public void notifyDeleting() throws IOException {
        }

        @Override
        public void notifyDeleted() throws IOException {
        }
    }

}
