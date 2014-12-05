package nl.fontys.sofa.limo.view.wizard.importer;

import java.awt.Component;
import java.io.File;
import java.text.MessageFormat;
import java.util.ResourceBundle;
import javax.swing.event.ChangeListener;
import org.openide.WizardDescriptor;
import org.openide.WizardValidationException;
import org.openide.util.HelpCtx;

/**
 * @author Matthias Brück
 */
public class ImportFileChooser implements WizardDescriptor.Panel<WizardDescriptor>, WizardDescriptor.ValidatingPanel<WizardDescriptor> {

    private ImportFileChooserComponent component;

    @Override
    public Component getComponent() {
        if (component == null) {
            component = new ImportFileChooserComponent();
        }
        return component;
    }

    @Override
    public HelpCtx getHelp() {
        return HelpCtx.DEFAULT_HELP;
    }

    @Override
    public void readSettings(WizardDescriptor settings) {
    }

    @Override
    public void storeSettings(WizardDescriptor wiz) {
        System.out.println("PATH: " + component.getAbsoluteFilePath());
        wiz.putProperty(ImportWizardAction.PATH, component.getAbsoluteFilePath());
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
        ResourceBundle bundle = ResourceBundle.getBundle("nl/fontys/sofa/limo/view/Bundle");
        if (component.getAbsoluteFilePath().equals("")) {
            throw new WizardValidationException(null, MessageFormat.format(bundle.getString("VALUE_NOT_SET"), "Path"), null);
        }
        File f = new File(component.getAbsoluteFilePath());
        if (!f.exists()) {
            throw new WizardValidationException(null, MessageFormat.format(bundle.getString("FILE_NOT_EXISTS"), new Object[]{}), null);
        }
    }
}
