package nl.fontys.sofa.limo.view.wizard.event;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.swing.JComponent;
import nl.fontys.sofa.limo.api.service.provider.EventService;
import nl.fontys.sofa.limo.domain.component.event.Event;
import org.openide.DialogDisplayer;
import org.openide.WizardDescriptor;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.Lookup;

@ActionID(category = "Event", id = "nl.fontys.sofa.limo.view.wizard.event.EventWizardAction")
@ActionRegistration(displayName = "Add Event")
@ActionReference(path = "Menu/Data/Event", position = 20)
public final class EventWizardAction implements ActionListener {

    private Event event = null;
    private boolean isUpdate = false;
    final ResourceBundle bundle = ResourceBundle.getBundle("nl/fontys/sofa/limo/view/wizard/event/Bundle");

    @Override
    public void actionPerformed(ActionEvent e) {
        List<WizardDescriptor.Panel<WizardDescriptor>> panels = new ArrayList<>();
        panels.add(new NewOrDuplicatedEventWizard());
        panels.add(new NameDescriptionProbabilityWizard());
        panels.add(new SubEventsWizard());
        panels.add(new ProceduresWizard());
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
        final WizardDescriptor wiz = new WizardDescriptor(new WizardDescriptor.ArrayIterator<>(panels));
        wiz.setTitleFormat(new MessageFormat("{0}"));
        wiz.setTitle(bundle.getString("ADD_EVENT"));
        if (isUpdate) {
            wiz.putProperty(bundle.getString("EVENT"), event);
        }
        if (DialogDisplayer.getDefault().notify(wiz) == WizardDescriptor.FINISH_OPTION) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    EventService service = Lookup.getDefault().lookup(EventService.class);
                    if (isUpdate) {
                        service.update((Event) wiz.getProperty(bundle.getString("EVENT")));
                    } else {
                        service.insert((Event) wiz.getProperty(bundle.getString("EVENT")));
                    }
                }
            }).start();
        }
    }

    public void isUpdate(Event event) {
        this.event = event;
        this.isUpdate = true;
    }

}
