package nl.fontys.sofa.limo.view.wizard.export.data.dialog;

import com.jgoodies.forms.layout.FormLayout;
import java.awt.Dimension;
import nl.fontys.sofa.limo.domain.component.procedure.ProcedureCategory;

/**
 * This class provides a view for Procedures. It extends the DataDialog.
 *
 * @author Matthias Brück
 */
public class ProcedureDataDialog extends DataDialog<ProcedureCategory> {

    public ProcedureDataDialog(ProcedureCategory entity) {
        super(entity);
        this.setSize(new Dimension(350, 100));
        this.setModal(true);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    @Override
    protected void initComponents(ProcedureCategory entity) {
        layout = new FormLayout("5px, pref, 5px, pref:grow, 5px",
                "5px, pref, 5px, pref, 5px, pref, 5px, pref, 5px, pref, 5px, pref, 5px, pref, 5px, pref, 5px, pref, 5px, pref, 5px, pref, 5px");
        this.setLayout(layout);
        this.add(getBaseEntityViewPanel(), cc.xyw(2, 2, 3));
    }
}
