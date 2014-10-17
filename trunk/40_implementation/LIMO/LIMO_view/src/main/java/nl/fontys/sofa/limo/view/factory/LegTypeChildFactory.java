package nl.fontys.sofa.limo.view.factory;

import nl.fontys.sofa.limo.api.service.provider.LegTypeService;
import nl.fontys.sofa.limo.domain.component.type.LegType;
import nl.fontys.sofa.limo.view.node.LegTypeNode;

/**
 * Factory for creating the leg type children.
 *
 * @author Sebastiaan Heijmann
 */
public class LegTypeChildFactory extends AbstractEntityChildFactory<LegType>{

	@Override
	Class getServiceClass() {
		return LegTypeService.class;
	}

	@Override
	Class getBeanClass() {
		return LegType.class;
	}

	@Override
	Class getNodeClass() {
		return LegTypeNode.class;
	}

}
