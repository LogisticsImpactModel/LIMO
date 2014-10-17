package nl.fontys.sofa.limo.view.node;

import nl.fontys.sofa.limo.domain.component.type.HubType;
import org.openide.nodes.Children;

/**
 * Root node for HubType.
 *
 * @author Sebastiaan Heijmann
 */
public class HubTypeRootNode extends AbstractRootNode{

	public HubTypeRootNode(Children children) {
		super(children);
	}

	@Override
	Class getBeanClass() {
		return HubType.class;
	}
}
