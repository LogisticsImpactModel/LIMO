package nl.fontys.sofa.limo.view.wizard.importer;

import java.awt.Component;
import javax.swing.event.ChangeListener;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;
import nl.fontys.sofa.limo.view.wizard.export.ExportWizardAction;
import org.openide.WizardDescriptor;
import org.openide.WizardValidationException;
import org.openide.util.HelpCtx;

/**
 * @author Matthias Brück
 */
public class ImportPanel implements WizardDescriptor.Panel<WizardDescriptor>, WizardDescriptor.ValidatingPanel<WizardDescriptor> {

    private String path = "";
    private ImportConflictPanel component;

    public ImportPanel() {
        initComponent();
    }

    private void initComponent() {
        component = new ImportConflictPanel(path);
    }

    @Override
    public Component getComponent() {
        if (component == null) {
            initComponent();
        }
        component.setName(LIMOResourceBundle.getString("RESOLVE_CONFLICTS"));
        return component;
    }

    @Override
    public HelpCtx getHelp() {
        return HelpCtx.DEFAULT_HELP;
    }

    @Override
    public void readSettings(WizardDescriptor settings) {
        path = (String) settings.getProperty(ExportWizardAction.PATH);
        if (component == null) {
            getComponent();
        }
        component.updateTable(path);
    }

    @Override
    public void storeSettings(WizardDescriptor settings) {
        settings.putProperty(ImportWizardAction.LIST, component.getEntitiesToOverride());
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public void addChangeListener(ChangeListener l) {
    }

    @Override
    public void removeChangeListener(ChangeListener l) {
    }

    @Override
    public void validate() throws WizardValidationException {
    }
}
