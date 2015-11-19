package nl.fontys.sofa.limo.view.wizard.procedure;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.swing.JComponent;
import nl.fontys.sofa.limo.api.service.provider.ProcedureService;
import nl.fontys.sofa.limo.domain.component.procedure.Procedure;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;
import org.openide.DialogDisplayer;
import org.openide.WizardDescriptor;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.ImageUtilities;
import org.openide.util.Lookup;

/**
 * The HubWizardAction is the entry point of the hub wizard which is used for
 * creation and editing of hubs. The wizard includes the choice for new,
 * duplicate or hub from hub type, name, description, icon, location, procedures
 * and events.
 *
 * @author Pascal Lindner
 */
@ActionID(
        category = "Procedure",
        id = "nl.fontys.sofa.limo.view.wizard.procedure.ProcedureWizardAction"
)
@ActionRegistration(
        displayName = "New procedure template...",
        iconBase = "icons/gui/add.png"
)
@ActionReferences({
    @ActionReference(path = "Menu/Master Data/Procedures", position = 20),
    @ActionReference(path = "Shortcuts", name = "DS-P")
})
public final class ProcedureWizardAction implements ActionListener {

    private Procedure originalProcedure, procedure;
    private boolean update = false;

    @Override
    public void actionPerformed(ActionEvent e) {
        List<WizardDescriptor.Panel<WizardDescriptor>> panels = new ArrayList<>();
        if (!update) {
            originalProcedure = new Procedure();
            panels.add(new ProcedureWizard());
            procedure = new Procedure();
        } else {
            procedure = new Procedure(originalProcedure); //Creates a new Procedure with the same attributes. This way the original procedure object keeped ontouched. 
        }
        String[] steps = new String[panels.size()];
        for (int i = 0; i < panels.size(); i++) {
            Component c = panels.get(i).getComponent();
            steps[i] = c.getName();
            if (c instanceof JComponent) {
                JComponent jc = (JComponent) c;
                jc.putClientProperty(WizardDescriptor.PROP_CONTENT_SELECTED_INDEX, i);
                jc.putClientProperty(WizardDescriptor.PROP_CONTENT_DATA, steps);
                jc.putClientProperty(WizardDescriptor.PROP_AUTO_WIZARD_STYLE, true);
                jc.putClientProperty(WizardDescriptor.PROP_CONTENT_DISPLAYED, true);
                jc.putClientProperty(WizardDescriptor.PROP_CONTENT_NUMBERED, true);
            }
        }
        WizardDescriptor wiz = new WizardDescriptor(new WizardDescriptor.ArrayIterator<>(panels));
        wiz.setTitleFormat(new MessageFormat("{0}"));
        wiz.putProperty(WizardDescriptor.PROP_IMAGE, ImageUtilities.loadImage("icons/limo_wizard.png", true));
        wiz.putProperty("procedure", procedure);
        wiz.putProperty("original_procedure", originalProcedure);
        wiz.putProperty("update", update);

        if (update) {
            wiz.setTitle(LIMOResourceBundle.getString("ADD_PROCEDURE"));
        } else {
            wiz.setTitle(LIMOResourceBundle.getString("EDIT_PROCEDURE"));
        }

        if (DialogDisplayer.getDefault().notify(wiz) == WizardDescriptor.FINISH_OPTION) {
            handleWizardFinishClick(wiz);
        }
    }

    /**
     * Save or update the hub based on the inputs.
     *
     * @param wiz - the WizardDescriptor which contains the inputs.
     */
    private void handleWizardFinishClick(final WizardDescriptor wiz) {
        ProcedureService procedureService = Lookup.getDefault().lookup(ProcedureService.class);

        procedure = (Procedure) wiz.getProperty("procedure");
        originalProcedure.deepOverwrite(procedure);

        if (update) {
            procedureService.update(originalProcedure);
        } else {
            originalProcedure.setId(null);
            originalProcedure.setUniqueIdentifier(UUID.randomUUID().toString());
            originalProcedure = procedureService.insert(originalProcedure);
        }
    }

    public void setUpdate(Procedure procedure) {
        this.update = true;
        this.originalProcedure = procedure;
    }
}
