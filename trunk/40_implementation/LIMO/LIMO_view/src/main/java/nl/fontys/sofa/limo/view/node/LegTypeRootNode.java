package nl.fontys.sofa.limo.view.node;

import nl.fontys.sofa.limo.domain.component.type.LegType;
import org.openide.nodes.Children;

/**
 * Root node for LegType.
 *
 * @author Sebastiaan Heijmann
 */
public class LegTypeRootNode extends AbstractRootNode{

	public LegTypeRootNode(Children children) {
		super(children);
	}

	@Override
	Class getBeanClass() {
		return LegType.class;
	}
}
