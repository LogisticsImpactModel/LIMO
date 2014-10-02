/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nl.fontys.limo.view.node;

import java.awt.Image;
import java.beans.IntrospectionException;
import javax.swing.Action;
import nl.fontys.sofa.limo.domain.category.Category;
import org.openide.nodes.BeanNode;
import org.openide.util.ImageUtilities;

/**
 *
 * @author Sebastiaan Heijmann
 */
public class CostCategoryNode extends BeanNode{

	public CostCategoryNode(Category bean) throws IntrospectionException {
		super(bean);
		setDisplayName(bean.getIdentifier());
		setIconBaseWithExtension("/nl/fontys/limo/view/icon/category.jpg");
	}

	@Override
	public Action getPreferredAction() {
		return super.getPreferredAction(); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public Action[] getActions(boolean context) {
		return super.getActions(context); //To change body of generated methods, choose Tools | Templates.
	}
}
