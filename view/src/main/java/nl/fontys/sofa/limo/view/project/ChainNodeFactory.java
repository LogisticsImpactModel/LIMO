/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.view.project;

import nl.fontys.sofa.limo.view.project.supplychain.ChainNodeList;
import org.netbeans.api.project.Project;
import org.netbeans.spi.project.ui.support.NodeFactory;
import org.netbeans.spi.project.ui.support.NodeList;

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

}
