package nl.fontys.sofa.limo.view.wizard.export.data.panel;

import java.awt.Component;
import java.io.File;
import java.text.MessageFormat;
import java.util.ResourceBundle;
import javax.swing.event.ChangeListener;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;
import nl.fontys.sofa.limo.view.wizard.export.ExportWizardAction;
import org.openide.WizardDescriptor;
import org.openide.WizardValidationException;
import org.openide.util.HelpCtx;

/**
 * @author Matthias Brück
 */
public class FileChooserPanel implements WizardDescriptor.Panel<WizardDescriptor>, WizardDescriptor.ValidatingPanel<WizardDescriptor> {

    private DirectoryChooserPanel component;

    @Override
    public Component getComponent() {
        if (component == null) {
            component = new DirectoryChooserPanel();
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
        wiz.putProperty(ExportWizardAction.PATH, component.getPath() + "\\" + component.getFileName() + ".lef");
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
        if (component.getPath().equals("") || component.getFileName().equals("")) {
            throw new WizardValidationException(null, MessageFormat.format(LIMOResourceBundle.getString("VALUE_NOT_SET"), "Path"), null);
        }
        File f = new File(component.getPath() + "\\" + component.getFileName() + ".lef");
        if (f.exists()) {
            throw new WizardValidationException(null, MessageFormat.format(LIMOResourceBundle.getString("FILE_ALREADY_EXISTS"), new Object[]{}), null);
        }
    }
}
