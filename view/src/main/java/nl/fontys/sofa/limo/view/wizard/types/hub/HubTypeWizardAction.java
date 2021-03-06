package nl.fontys.sofa.limo.view.wizard.types.hub;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.swing.JComponent;
import nl.fontys.sofa.limo.api.service.provider.EventService;
import nl.fontys.sofa.limo.api.service.provider.HubTypeService;
import nl.fontys.sofa.limo.domain.component.type.HubType;
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
 * HubType Wizard Action.
 *
 * @author Pascal Lindner
 */
@ActionID(category = "HubType", id = "nl.fontys.sofa.limo.view.wizard.hubtype.HubTypeWizardAction")
@ActionRegistration(displayName = "New hub category..", iconBase = "icons/gui/add.png")
@ActionReferences({
    @ActionReference(path = "Menu/Master Data/Hub category", position = 20),
    @ActionReference(path = "Shortcuts", name = "DOS-H")
})
public final class HubTypeWizardAction extends TypeWizardAction {

    private HubType hubType;

    @Override
    public void actionPerformed(ActionEvent e) {
        List<WizardDescriptor.Panel<WizardDescriptor>> panels = new ArrayList<>();
        if (!update) {
            hubType = new HubType();
            panels.add(new NewOrDuplicatedHubTypeWizard());
        }
        panels.add(new NameDescriptionIconHubTypeWizard());
        panels.add(new ProceduresHubTypeWizard());
        EventService eventService = Lookup.getDefault().lookup(EventService.class);
        if (!eventService.findAll().isEmpty()) {
            panels.add(new EventHubTypeWizard());
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
        wiz.setTitle(LIMOResourceBundle.getString("ADD_HUB_TYPE"));
        wiz.putProperty("update", update);
        wiz.putProperty(TYPE_NEWTYPE, new HubType(hubType));
        wiz.putProperty("original_type", hubType);

        Object answer = DialogDisplayer.getDefault().notify(wiz);

        if (answer == WizardDescriptor.FINISH_OPTION) {
            finishWizard(wiz, true);
        } else if (answer == WizardDescriptor.CANCEL_OPTION) {
            finishWizard(wiz, false);
        }
    }

    public void setUpdate(HubType hubType) {
        this.hubType = hubType;
        this.update = true;
    }

    private void finishWizard(WizardDescriptor wiz, boolean succes) {
        HubTypeService service = Lookup.getDefault().lookup(HubTypeService.class);

        if (succes) {
            hubType = (HubType) wiz.getProperty(TYPE_NEWTYPE); //Overwrite object (is used when copying a hub type from an existing hub type)

            if (update) {
                service.update(hubType);
            } else {
                hubType.setId(null);
                hubType.setUniqueIdentifier(UUID.randomUUID().toString());
                hubType = service.insert(hubType);
            }
        } else {
            hubType = (HubType) wiz.getProperty("original_type");
            
            if (update) {
                service.update(hubType);
            }
        }

    }
}
