/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nl.fontys.limo.view.node;

import java.beans.IntrospectionException;
import nl.fontys.sofa.limo.domain.category.CostCategory;
import org.openide.nodes.BeanNode;

/**
 *
 * @author Sebastiaan Heijmann
 */
public class CostCategoryNode extends BeanNode{

	public CostCategoryNode(CostCategory bean) throws IntrospectionException {
		super(bean);
		setDisplayName(bean.getIdentifier());
//		setShortDescription(bean.getDescription());
	}

}
