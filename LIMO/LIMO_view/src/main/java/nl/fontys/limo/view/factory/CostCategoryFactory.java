/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nl.fontys.limo.view.factory;

import java.beans.IntrospectionException;
import java.util.List;
import nl.fontys.limo.view.node.CostCategoryNode;
import nl.fontys.sofa.limo.api.dao.CostCategoryDAO;
import nl.fontys.sofa.limo.api.dao.DAOFactory;
import nl.fontys.sofa.limo.api.dao.TimeCategoryDAO;
import nl.fontys.sofa.limo.domain.category.CostCategory;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;

/**
 *
 * @author Sebastiaan Heijmann
 */
	public class CostCategoryFactory extends ChildFactory<CostCategory>{

	@Override
	protected boolean createKeys(List<CostCategory> list) {
		DAOFactory df = Lookup.getDefault().lookup(DAOFactory.class);
		CostCategoryDAO ccd = df.getCostCategoryDAO();
		list.addAll(ccd.findAll());
		return true;
	}

	@Override
	protected Node createNodeForKey(CostCategory key) {
		CostCategoryNode node = null;
		try {
			node = new CostCategoryNode(key);
		} catch (IntrospectionException ex) {
			Exceptions.printStackTrace(ex);
		}
		return node;
	}

	

}