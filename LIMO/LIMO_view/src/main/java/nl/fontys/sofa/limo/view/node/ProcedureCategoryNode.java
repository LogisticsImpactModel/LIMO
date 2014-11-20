package nl.fontys.sofa.limo.view.node;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import nl.fontys.sofa.limo.domain.component.procedure.ProcedureCategory;
import nl.fontys.sofa.limo.view.node.property.StupidProperty;
import org.netbeans.api.visual.graph.GraphScene;
import org.netbeans.api.visual.widget.Widget;
import org.openide.ErrorManager;
import org.openide.nodes.Sheet;

/**
 * View representation of the CostCategory class.
 *
 * @author Sebastiaan Heijmann
 */
public class ProcedureCategoryNode extends AbstractBeanNode<ProcedureCategory> {

    public ProcedureCategoryNode(ProcedureCategory bean)
            throws IntrospectionException {
        super(bean, ProcedureCategory.class);
    }

    @Override
    public boolean canDestroy() {
        return true;
    }

    @Override
    protected void createProperties(ProcedureCategory bean, BeanInfo info) {
        Sheet sets = getSheet();
        Sheet.Set set = Sheet.createPropertiesSet();

        try {
            StupidProperty name = new StupidProperty<>(getBean(), String.class, "name");
            name.addPropertyChangeListener(getListener());
            name.setDisplayName("Name");
            name.setShortDescription("The name of the procedure category.");

            StupidProperty description = new StupidProperty<>(getBean(), String.class, "description");
            description.addPropertyChangeListener(getListener());
            description.setDisplayName("Description");
            description.setShortDescription("An optional short description of the procedure category.");

            set.put(name);
            set.put(description);
        } catch (NoSuchMethodException ex) {
            ErrorManager.getDefault();
        }

        sets.put(set);
    }

}
