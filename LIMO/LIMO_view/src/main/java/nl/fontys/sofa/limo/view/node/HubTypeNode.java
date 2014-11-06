package nl.fontys.sofa.limo.view.node;

import java.beans.IntrospectionException;
import nl.fontys.sofa.limo.domain.component.type.HubType;

/**
 * View representation of HubType.
 *
 * @author Sebastiaan Heijmann
 */
public class HubTypeNode extends AbstractBeanNode {

    public HubTypeNode(HubType bean) throws IntrospectionException {
        super(bean, HubType.class);
    }

    @Override
    public boolean canDestroy() {
        return true;
    }

}
