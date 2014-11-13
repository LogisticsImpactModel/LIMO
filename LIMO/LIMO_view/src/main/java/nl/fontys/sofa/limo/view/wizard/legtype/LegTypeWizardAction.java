package nl.fontys.sofa.limo.view.wizard.legtype;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.swing.JComponent;
import nl.fontys.sofa.limo.api.service.provider.LegTypeService;
import nl.fontys.sofa.limo.domain.component.Icon;
import nl.fontys.sofa.limo.domain.component.procedure.Procedure;
import nl.fontys.sofa.limo.domain.component.type.LegType;
import org.openide.DialogDisplayer;
import org.openide.WizardDescriptor;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.Lookup;

@ActionID(category = "LegType", id = "nl.fontys.sofa.limo.view.wizard.legtype.LegTypeWizardAction")
@ActionRegistration(displayName = "Add Leg Type")
@ActionReference(path = "Menu/Master Data/LegType", position = 20)
public final class LegTypeWizardAction implements ActionListener {

    public static final String LEG_TYPE_NAME = "legTypeName";
    public static final String LEG_TYPE_DESCRIPTION = "legTypeDescription";
    public static final String LEG_TYPE_ICON = "legTypeIcon";
    public static final String LEG_TYPE_PROCEDURES = "legTypeProcedures";

    private LegType legType = new LegType();
    private boolean isUpdate = false;
    private boolean saveToDatabase = true;

    @Override
    public void actionPerformed(ActionEvent e) {
        List<WizardDescriptor.Panel<WizardDescriptor>> panels = new ArrayList<>();
        panels.add(new NewOrDuplicatedLegTypeWizard());
        panels.add(new NameDescriptionIconLegTypeWizard());
        panels.add(new ProceduresLegTypeWizard());
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
        wiz.setTitle(ResourceBundle.getBundle("nl/fontys/sofa/limo/view/Bundle").getString("ADD LEG TYPE"));
        if (isUpdate) {
            wiz.putProperty(LEG_TYPE_NAME, legType.getName());
            wiz.putProperty(LEG_TYPE_DESCRIPTION, legType.getDescription());
            wiz.putProperty(LEG_TYPE_ICON, legType.getIcon());
            wiz.putProperty(LEG_TYPE_PROCEDURES, legType.getProcedures());
        }
        if (DialogDisplayer.getDefault().notify(wiz) == WizardDescriptor.FINISH_OPTION) {
            if (saveToDatabase) {
                LegTypeService service = Lookup.getDefault().lookup(LegTypeService.class);
                legType.setDescription((String) wiz.getProperty(LEG_TYPE_DESCRIPTION));
                legType.setIcon((Icon) wiz.getProperty(LEG_TYPE_ICON));
                legType.setName((String) wiz.getProperty(LEG_TYPE_NAME));
                legType.setProcedures((List<Procedure>) wiz.getProperty(LEG_TYPE_PROCEDURES));
                if (isUpdate) {
                    service.update(legType);
                } else {
                    legType.setId(null);
                    legType.setUniqueIdentifier(null);
                    service.insert(legType);
                }
            }
        }
    }

    public void setLegType(LegType legType) {
        this.legType = legType;
        this.isUpdate = true;
    }

    public void setSaveToDatabase(boolean saveToDatabase) {
        this.saveToDatabase = saveToDatabase;
    }

}
