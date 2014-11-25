package nl.fontys.sofa.limo.view.wizard.export.data.dialog;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import javax.swing.JDialog;
import nl.fontys.sofa.limo.domain.BaseEntity;
import nl.fontys.sofa.limo.domain.component.Component;

/**
 * @author Matthias Brück
 */
public abstract class DataDialog<T extends BaseEntity> extends JDialog {

    private BaseEntityViewPanel baseEntityViewPanel;
    private ComponentViewPanel componentViewPanel;
    protected FormLayout layout;
    protected CellConstraints cc;

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

    public ComponentViewPanel getComponentViewPanel() {
        return componentViewPanel;
    }

    public BaseEntityViewPanel getBaseEntityViewPanel() {
        return baseEntityViewPanel;
    }

    protected abstract void initComponents(T entity);
}
