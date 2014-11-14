package nl.fontys.sofa.limo.view.wizard.hub;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.swing.JComponent;
import nl.fontys.sofa.limo.api.service.provider.HubService;
import nl.fontys.sofa.limo.domain.component.Icon;
import nl.fontys.sofa.limo.domain.component.event.Event;
import nl.fontys.sofa.limo.domain.component.hub.Hub;
import nl.fontys.sofa.limo.domain.component.hub.Location;
import nl.fontys.sofa.limo.domain.component.procedure.Procedure;
import org.openide.DialogDisplayer;
import org.openide.WizardDescriptor;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.Lookup;

@ActionID(category = "Hub", id = "nl.fontys.limo.view.wizzard.hub.HubWizardAction")
@ActionRegistration(displayName = "Add Hub")
@ActionReference(path = "Menu/Master Data/Hub", position = 20)
public final class HubWizardAction implements ActionListener {

    static final String HUB_NAME = "hubName";
    static final String HUB_DESCRIPTION = "hubDescription";
    static final String HUB_ICON = "hubIcon";
    static final String HUB_PROCEDURES = "hubProcedures";
    static final String HUB_LOCATION = "hubLocation";
    static final String HUB_EVENTS = "hubEvents";
    static final String HUB_COPY = "hubCopy";
    static final String HUB_TYPE = "hubType";

    private boolean update = false;
    private Hub hubUpdate = new Hub();
    final ResourceBundle bundle = ResourceBundle.getBundle("nl/fontys/sofa/limo/view/Bundle");

    @Override
    public void actionPerformed(ActionEvent e) {
        List<WizardDescriptor.Panel<WizardDescriptor>> panels = new ArrayList<>();
        if (!update) {
            panels.add(new NewDuplicatedOrHubTypeHubWizard());
        }
        panels.add(new NameDescriptionIconHubWizard());
        panels.add(new LocationHubWizard());
        panels.add(new EventsHubWizard());
        panels.add(new ProceduresHubWizard());
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
        if (update) {
            wiz.setTitle(bundle.getString("ADD_HUB"));
            wiz.putProperty(HUB_COPY, hubUpdate);
        } else {
            wiz.setTitle(bundle.getString("EDIT_HUB"));
        }
        if (DialogDisplayer.getDefault().notify(wiz) == WizardDescriptor.FINISH_OPTION) {
            HubService service = Lookup.getDefault().lookup(HubService.class);
            hubUpdate.setName((String) wiz.getProperty(HUB_NAME));
            hubUpdate.setDescription((String) wiz.getProperty(HUB_DESCRIPTION));
            hubUpdate.setIcon((Icon) wiz.getProperty(HUB_ICON));
            hubUpdate.setLocation((Location) wiz.getProperty(HUB_LOCATION));
            hubUpdate.setProcedures((List<Procedure>) wiz.getProperty(HUB_PROCEDURES));
            hubUpdate.setEvents((List<Event>) wiz.getProperty(HUB_EVENTS));
            if (update) {
                service.update(hubUpdate);
            } else {
                hubUpdate.setId(null);
                hubUpdate.setUniqueIdentifier(null);
                service.insert(hubUpdate);
            }
        }

    }

    public void isUpdate(Hub hubUpdate) {
        this.update = true;
        this.hubUpdate = hubUpdate;
    }

}
