package nl.fontys.sofa.limo.view.wizard.export.data.dialog;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import javax.swing.JDialog;
import nl.fontys.sofa.limo.domain.BaseEntity;
import nl.fontys.sofa.limo.domain.component.Component;

/**
 * This class provides a view for an object that extends BaseEntity.
 *
 * @author Matthias Brück
 */
public abstract class DataDialog<T extends BaseEntity> extends JDialog {

    private BaseEntityViewPanel baseEntityViewPanel;
    private ComponentViewPanel componentViewPanel;
    protected FormLayout layout;
    protected CellConstraints cc;

    /**
     * Creates a new Dialog for displaying data.
     *
     * @param entity The BaseEntity that has to be displayed.
     */
    public DataDialog(T entity) {
        if (entity instanceof Component) {
            componentViewPanel = new ComponentViewPanel((Component) entity);
        } else {
            baseEntityViewPanel = new BaseEntityViewPanel(entity);
        }
        cc = new CellConstraints();
        initComponents(entity);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }

    /**
     * Gets the component panel. Is null if the specified type is not a
     * component.
     *
     * @return The component panel or null if the type is not a component.
     */
    public ComponentViewPanel getComponentViewPanel() {
        return componentViewPanel;
    }

    /**
     * Gets the BaseEntitiy panel. Is null if the specified type is a component.
     *
     * @return The BaseEntity panel or null if the type is a component.
     */
    public BaseEntityViewPanel getBaseEntityViewPanel() {
        return baseEntityViewPanel;
    }

    /**
     * Initializes all components for the specified entity.
     *
     * @param entity The BaseEntity you want to display.
     */
    protected abstract void initComponents(T entity);
}
