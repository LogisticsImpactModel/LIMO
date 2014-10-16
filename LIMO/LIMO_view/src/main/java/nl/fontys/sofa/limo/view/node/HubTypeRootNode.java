package nl.fontys.sofa.limo.view.node;

import java.awt.Image;
import javax.swing.Action;
import nl.fontys.sofa.limo.api.service.provider.HubTypeService;
import nl.fontys.sofa.limo.domain.component.type.HubType;
import nl.fontys.sofa.limo.view.util.IconUtil;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.Lookup;

/**
 * Root node view representation of the HubType class.
 *
 * @author Sebastiaan Heijmann
 */
public class HubTypeRootNode extends AbstractNode{
	private HubTypeService service;

	public HubTypeRootNode(Children children) {
		super(children);
		service = Lookup.getDefault().lookup(HubTypeService.class);
	}

	@Override
	public Image getIcon(int type) {
		Image icon = IconUtil.getIcon(HubType.class, type);
		if(icon == null){
			return super.getIcon(type);
		}
		return icon;
	}

	@Override
	public Action[] getActions(boolean context) {
		Action[] actions = new Action[0];
		return actions;
	}
}
