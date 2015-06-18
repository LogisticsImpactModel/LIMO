package nl.fontys.sofa.limo.view.wizard.types.leg;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.swing.JComponent;
import nl.fontys.sofa.limo.api.service.provider.EventService;
import nl.fontys.sofa.limo.api.service.provider.LegTypeService;
import nl.fontys.sofa.limo.domain.component.type.LegType;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;
import nl.fontys.sofa.limo.view.wizard.types.TypeWizardAction;
import org.openide.DialogDisplayer;
import org.openide.WizardDescriptor;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.ImageUtilities;
import org.openide.util.Lookup;

/**
 * LegType Wizard Action.
 *
 * @author Pascal Lindner
 */
@ActionID(category = "LegType", id = "nl.fontys.sofa.limo.view.wizard.legtype.LegTypeWizardAction")
@ActionRegistration(displayName = "New leg template..", iconBase = "icons/gui/add.gif")
@ActionReferences({
    @ActionReference(path = "Menu/Master Data/Leg templates", position = 20),
    @ActionReference(path = "Shortcuts", name = "DOS-L")
})
public final class LegTypeWizardAction extends TypeWizardAction {

    private LegType legType;

    @Override
    public void actionPerformed(ActionEvent e) {
        List<WizardDescriptor.Panel<WizardDescriptor>> panels = new ArrayList<>();
        if (!update) {
            legType = new LegType();
            panels.add(new NewOrDuplicatedLegTypeWizard());
        }
        panels.add(new NameDescriptionIconLegTypeWizard());
        panels.add(new ProceduresLegTypeWizard());
        EventService eventService = Lookup.getDefault().lookup(EventService.class);
        if (!eventService.findAll().isEmpty()) {
            panels.add(new EventLegTypeWizard());
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
        wiz.setTitle(LIMOResourceBundle.getString("ADD_LEG_TYPE"));
        wiz.putProperty("update", update);
        wiz.putProperty("orignal_type", new LegType(legType));
        wiz.putProperty(TYPE_OLDTYPE, legType);

        if (DialogDisplayer.getDefault().notify(wiz) == WizardDescriptor.FINISH_OPTION) {
            finishWizard(wiz);
        }
    }

    //For Update LegType
    public void isUpdate(LegType legType) {
        this.legType = legType;
        this.update = true;
    }

    private void finishWizard(WizardDescriptor wiz) {
        LegTypeService service = Lookup.getDefault().lookup(LegTypeService.class);

        legType = (LegType) wiz.getProperty(TYPE_OLDTYPE); //Overwrite object (is used when copying a legtype from an existing leg type)

        if (update) {
            service.update(legType);
        } else {
            legType.setId(null);
            legType.setUniqueIdentifier(UUID.randomUUID().toString());
            legType = service.insert(legType);
        }
    }

}
