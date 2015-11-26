/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.view.project;

import java.util.ArrayList;
import java.util.List;
import javax.swing.event.ChangeListener;
import org.netbeans.api.project.Project;
import org.netbeans.spi.project.ui.support.NodeFactory;
import org.netbeans.spi.project.ui.support.NodeList;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.nodes.FilterNode;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;

/**
 *
 * @author nilsh
 */
@NodeFactory.Registration(projectType = "supply-project", position = 10)
public class ChainNodeFactory implements NodeFactory {

    @Override
    public NodeList<?> createNodes(Project project) {
        SupplyProject p = project.getLookup().lookup(SupplyProject.class);
        assert p != null;
        return new ChainNodeList(p);
    }

    private class ChainNodeList implements NodeList<Node> {

        SupplyProject project;

        public ChainNodeList(SupplyProject project) {
            this.project = project;
        }

        @Override
        public List<Node> keys() {
            FileObject chainsFolder
                    = project.getProjectDirectory().getFileObject("chains");
            FileObject masterDataFolder
                    = project.getProjectDirectory().getFileObject("master_data_files");
            FileObject reportDataFolder
                    = project.getProjectDirectory().getFileObject("reports");

            List<Node> result = new ArrayList<>();
            if (masterDataFolder != null) {
                try {
                    result.add(DataObject.find(masterDataFolder).getNodeDelegate());
                } catch (DataObjectNotFoundException ex) {
                    Exceptions.printStackTrace(ex);
                }
            }

            if (chainsFolder != null) {
                try {
                    result.add(DataObject.find(chainsFolder).getNodeDelegate());
                } catch (DataObjectNotFoundException ex) {
                    Exceptions.printStackTrace(ex);
                }
            }
            if (reportDataFolder != null) {
                try {
                    result.add(DataObject.find(reportDataFolder).getNodeDelegate());
                } catch (DataObjectNotFoundException ex) {
                    Exceptions.printStackTrace(ex);
                }
            }
            return result;
        }

        @Override
        public Node node(Node node) {
            return new FilterNode(node);
        }

        @Override
        public void addNotify() {
        }

        @Override
        public void removeNotify() {
        }

        @Override
        public void addChangeListener(ChangeListener cl) {
        }

        @Override
        public void removeChangeListener(ChangeListener cl) {
        }

    }

}
