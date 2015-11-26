/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.view.project;

import java.util.ArrayList;
import java.util.List;
import javax.swing.event.ChangeListener;
import nl.fontys.sofa.limo.view.project.node.ChainNode;
import nl.fontys.sofa.limo.view.project.node.FolderFilterNode;
import nl.fontys.sofa.limo.view.project.node.MasterDataNode;
import nl.fontys.sofa.limo.view.project.node.SupplyChainNode;
import org.netbeans.api.project.Project;
import org.netbeans.spi.project.ui.support.NodeFactory;
import org.netbeans.spi.project.ui.support.NodeList;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.nodes.Children;
import org.openide.nodes.FilterNode;
import org.openide.nodes.Index;
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
                    result.add(new MasterDataNode(DataObject.find(masterDataFolder).getNodeDelegate(), Children.LEAF));
                } catch (DataObjectNotFoundException ex) {
                    Exceptions.printStackTrace(ex);
                }
            }

            if (chainsFolder != null) {
                try {
                    DataObject object = DataObject.find(chainsFolder);
                    Node node = createChains(object);
                    result.add(node);
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

        private Node createChains(DataObject chainsFolder) {
            Children children = createChainFolder(chainsFolder);
            return new ChainNode(chainsFolder.getNodeDelegate(), children);
        }

        private Children createChainFolder(DataObject obj) {
            List<Node> nodes = new ArrayList<>();
            obj.files().stream().forEach((file) -> {
                if (file.isFolder()) {
                    try {
                        for (FileObject f : file.getChildren()) {
                            DataObject object = DataObject.find(f);
                            if (f.isFolder()) {
                                Children children = createChainFolder(object);
                                Node n = new FolderFilterNode(object.getNodeDelegate(), children);
                                nodes.add(n);
                            } else if (f.getExt().equals("lsc")) {
                                DataObject o = DataObject.find(f);
                                Node n = new SupplyChainNode(o.getNodeDelegate(), Children.LEAF);
                                nodes.add(n);
                            }
                        }

                    } catch (DataObjectNotFoundException ex) {
                        Exceptions.printStackTrace(ex);
                    }
                } else if (file.getExt().equals("lsc")) {
                    try {
                        DataObject object = DataObject.find(file);
                        Node n = new SupplyChainNode(object.getNodeDelegate(), Children.LEAF);
                        nodes.add(n);
                    } catch (DataObjectNotFoundException ex) {
                        Exceptions.printStackTrace(ex);
                    }
                }
            });
            Children children = new Index.ArrayChildren();
            children.add(nodes.toArray(new Node[0]));
            return children;
        }

    }

}
