package nl.fontys.sofa.limo.view.node;

import java.awt.Image;
import java.beans.IntrospectionException;
import java.io.IOException;
import java.net.URL;
import javax.annotation.Resource;
import javax.imageio.ImageIO;
import nl.fontys.sofa.limo.domain.category.CostCategory;
import nl.fontys.sofa.limo.view.util.IconUtil;
import org.openide.nodes.BeanNode;
import org.openide.util.Exceptions;

/**
 * View representation of the CostCategory class. 
 *
 * @author Sebastiaan Heijmann
 */
public class CostCategoryNode extends BeanNode{

	public CostCategoryNode(CostCategory bean) throws IntrospectionException {
		super(bean);
		setDisplayName(bean.getIdentifier());
		setShortDescription(bean.getDescription());
	}
	
	@Override
	public Image getIcon(int type) {
		Image icon = IconUtil.getIcon(CostCategory.class, type);
		if(icon == null){
			return super.getIcon(type);
		}
		return icon;
	}

	

}
