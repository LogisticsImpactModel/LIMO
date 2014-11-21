package nl.fontys.sofa.limo.view.wizard.types.hub;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.swing.JComponent;
import nl.fontys.sofa.limo.api.service.provider.HubTypeService;
import nl.fontys.sofa.limo.domain.component.Icon;
import nl.fontys.sofa.limo.domain.component.event.Event;
import nl.fontys.sofa.limo.domain.component.procedure.Procedure;
import nl.fontys.sofa.limo.domain.component.type.HubType;
import nl.fontys.sofa.limo.view.wizard.types.TypeWizardAction;
import org.openide.DialogDisplayer;
import org.openide.WizardDescriptor;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.Lookup;

@ActionID(category = "HubType", id = "nl.fontys.sofa.limo.view.wizard.hubtype.HubTypeWizardAction")
@ActionRegistration(displayName = "Add")
@ActionReference(path = "Menu/Master Data/Hub Type", position = 20)
public final class HubTypeWizardAction extends TypeWizardAction {

    private HubType hubType = new HubType();

    @Override
    public void actionPerformed(ActionEvent e) {
        List<WizardDescriptor.Panel<WizardDescriptor>> panels = new ArrayList<>();
        if (!isUpdate) {
            panels.add(new NewOrDuplicatedHubTypeWizard());
        }
        panels.add(new NameDescriptionIconHubTypeWizard());
        panels.add(new EventHubTypeWizard());
        panels.add(new ProceduresHubTypeWizard());
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
        wiz.setTitle(ResourceBundle.getBundle("nl/fontys/sofa/limo/view/Bundle").getString("ADD_HUB_TYPE"));
        if (isUpdate) {
            wiz.putProperty(TYPE_NAME, hubType.getName());
            wiz.putProperty(TYPE_DESCRIPTION, hubType.getDescription());
            wiz.putProperty(TYPE_ICON, hubType.getIcon());
            wiz.putProperty(TYPE_EVENT, hubType.getEvents());
            wiz.putProperty(TYPE_PROCEDURES, hubType.getProcedures());
        }
        if (DialogDisplayer.getDefault().notify(wiz) == WizardDescriptor.FINISH_OPTION) {
            if (saveToDatabase) {
                HubTypeService service = Lookup.getDefault().lookup(HubTypeService.class);
                hubType.setDescription((String) wiz.getProperty(TYPE_DESCRIPTION));
                hubType.setIcon((Icon) wiz.getProperty(TYPE_ICON));
                hubType.setName((String) wiz.getProperty(TYPE_NAME));
                hubType.setEvents((List<Event>) wiz.getProperty(TYPE_EVENT));
                hubType.setProcedures((List<Procedure>) wiz.getProperty(TYPE_PROCEDURES));
                if (isUpdate) {
                    service.update(hubType);
                } else {
                    hubType.setId(null);
                    hubType.setUniqueIdentifier(null);
                    service.insert(hubType);
                }
            }
        }
    }

    public void setHubType(HubType hubType) {
        this.hubType = hubType;
        this.isUpdate = true;
    }

}
