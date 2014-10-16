package nl.fontys.sofa.limo.view.node;

import java.awt.Image;
import nl.fontys.sofa.limo.api.service.provider.LegTypeService;
import nl.fontys.sofa.limo.domain.component.type.LegType;
import nl.fontys.sofa.limo.view.util.IconUtil;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.Lookup;

/**
 * Root node view representation of the LegType class.
 *
 * @author Sebastiaan Heijmann
 */
public class LegTypeRootNode extends AbstractNode{
	private LegTypeService service;

	public LegTypeRootNode(Children children) {
		super(children);
		service = Lookup.getDefault().lookup(LegTypeService.class);
	}

	@Override
	public Image getIcon(int type) {
		Image icon = IconUtil.getIcon(LegType.class, type);
		if(icon == null){
			return super.getIcon(type);
		}
		return icon;
	}
}
