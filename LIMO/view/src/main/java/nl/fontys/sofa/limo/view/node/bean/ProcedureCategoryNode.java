package nl.fontys.sofa.limo.view.node.bean;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import nl.fontys.sofa.limo.api.service.provider.ProcedureCategoryService;
import nl.fontys.sofa.limo.domain.component.Icon;
import nl.fontys.sofa.limo.domain.component.procedure.ProcedureCategory;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;
import org.openide.nodes.Sheet;

/**
 * View representation of the CostCategory class.
 *
 * @author Sebastiaan Heijmann
 */
public class ProcedureCategoryNode extends AbstractBeanNode<ProcedureCategory> {

    public ProcedureCategoryNode(ProcedureCategory bean) throws IntrospectionException {
        super(bean, ProcedureCategory.class);
    }

    @Override
    public boolean canDestroy() {
        return true;
    }

    @Override
    protected void createProperties(ProcedureCategory bean, BeanInfo info) {
        Sheet sets = getSheet();
        Sheet.Set set = getNameDescriptionPropertySheet();
        sets.put(set);
    }

    @Override
    public AbstractBeanNode getDetachedNodeCopy() {
        throw new UnsupportedOperationException(LIMOResourceBundle.getString("COPY_NOT_SUPPORTED"));
    }

    @Override
    Class getServiceClass() {
        return ProcedureCategoryService.class;
    }

    @Override
    protected Icon getBeanIcon() {
        throw new UnsupportedOperationException(LIMOResourceBundle.getString("NOT_SUPPORTED"));
    }
}
