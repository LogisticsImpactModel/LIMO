package nl.fontys.sofa.limo.view.wizard.hub;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.swing.JComponent;
import nl.fontys.sofa.limo.api.service.provider.HubService;
import nl.fontys.sofa.limo.domain.component.hub.Hub;
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
        category = "Hub",
        id = "nl.fontys.sofa.limo.view.wizzard.hub.HubWizardAction"
)
@ActionRegistration(
        displayName = "New hub template...",
        iconBase = "icons/gui/add.png"
)
@ActionReferences({
    @ActionReference(path = "Menu/Master Data/Hub templates", position = 20),
    @ActionReference(path = "Shortcuts", name = "DS-H")
})
public final class HubWizardAction implements ActionListener {

    private Hub originalHub, hub;
    private boolean update = false;

    @Override
    public void actionPerformed(ActionEvent e) {
        List<WizardDescriptor.Panel<WizardDescriptor>> panels = new ArrayList<>();
        if (!update) {
            originalHub = new Hub();
            panels.add(new NewDuplicatedOrHubTypeHubWizard());
            hub = new Hub();
        } else {
            hub = new Hub(originalHub); //Creates a new hub with the same attributes. This way the original hub object keeped ontouched. 
        }

        panels.add(new NameDescriptionIconHubWizard());
        panels.add(new LocationHubWizard());
        panels.add(new ProceduresHubWizard());
        panels.add(new EventsHubWizard());

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
        wiz.putProperty("hub", hub);
        wiz.putProperty("original_hub", originalHub);
        wiz.putProperty("update", update);

        if (update) {
            wiz.setTitle(LIMOResourceBundle.getString("ADD_HUB"));
        } else {
            wiz.setTitle(LIMOResourceBundle.getString("EDIT_HUB"));
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
        HubService hubService = Lookup.getDefault().lookup(HubService.class);

        hub = (Hub) wiz.getProperty("hub");
        originalHub.deepOverwrite(hub);

        if (update) {
            hubService.update(originalHub);
        } else {
            originalHub.setId(null);
            originalHub.setUniqueIdentifier(UUID.randomUUID().toString());
            originalHub = hubService.insert(originalHub);
        }
    }

    public void setUpdate(Hub hub) {
        this.update = true;
        this.originalHub = hub;
    }
}
