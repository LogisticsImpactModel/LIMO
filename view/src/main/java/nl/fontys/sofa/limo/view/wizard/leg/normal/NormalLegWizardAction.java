package nl.fontys.sofa.limo.view.wizard.leg.normal;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;
import nl.fontys.sofa.limo.api.service.provider.EventService;
import nl.fontys.sofa.limo.api.service.status.StatusBarService;
import nl.fontys.sofa.limo.domain.component.Icon;
import nl.fontys.sofa.limo.domain.component.event.Event;
import nl.fontys.sofa.limo.domain.component.leg.Leg;
import nl.fontys.sofa.limo.domain.component.procedure.Procedure;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;
import nl.fontys.sofa.limo.view.wizard.leg.multimode.MultimodeLegTablePanel;
import org.openide.DialogDisplayer;
import org.openide.WizardDescriptor;
import org.openide.util.ImageUtilities;
import org.openide.util.Lookup;

/**
 * Normal Leg Wizard Action
 *
 * @author Pascal Lindner
 */
public final class NormalLegWizardAction implements ActionListener {

    private MultimodeLegTablePanel.FinishedLegListener legListener;
    private Leg leg;
    private boolean update;

    public NormalLegWizardAction(MultimodeLegTablePanel.FinishedLegListener legListener) {
        this();
        this.legListener = legListener;
    }

    public NormalLegWizardAction() {
        this.leg = new Leg();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        List<WizardDescriptor.Panel<WizardDescriptor>> panels = new ArrayList<>();
        if (!update) {
            panels.add(new NewOrFromTypeWizard());
        }
        panels.add(new NameDescriptionIconLegPanel());
        panels.add(new ProceduresLegTypeWizard());
        panels.add(new EventLegTypeWizard());

        String[] steps = new String[panels.size()];
        for (int i = 0; i < panels.size(); i++) {
            Component c = panels.get(i).getComponent();
            // Default step name to component name of panel.
            steps[i] = c.getName();
            if (c instanceof JComponent) { // assume Swing components
                JComponent jc = (JComponent) c;
                jc.putClientProperty(WizardDescriptor.PROP_CONTENT_SELECTED_INDEX, i);
                jc.putClientProperty(WizardDescriptor.PROP_CONTENT_DATA, steps);
                jc.putClientProperty(WizardDescriptor.PROP_AUTO_WIZARD_STYLE, true);
                jc.putClientProperty(WizardDescriptor.PROP_CONTENT_DISPLAYED, true);
                jc.putClientProperty(WizardDescriptor.PROP_CONTENT_NUMBERED, true);
            }
        }
        WizardDescriptor wiz = new WizardDescriptor(new WizardDescriptor.ArrayIterator<WizardDescriptor>(panels));
        wiz.putProperty(WizardDescriptor.PROP_IMAGE, ImageUtilities.loadImage("icons/limo_wizard.png", true));
        if (update) {
            wiz.putProperty("leg", leg);
        }
        // {0} will be replaced by WizardDesriptor.Panel.getComponent().getName()
        wiz.setTitleFormat(new MessageFormat("{0}"));
        wiz.setTitle(LIMOResourceBundle.getString("CREATE_NORMAL_LEG"));
        if (DialogDisplayer.getDefault().notify(wiz) == WizardDescriptor.FINISH_OPTION) {
            leg.setName((String) wiz.getProperty("name"));
            leg.setDescription((String) wiz.getProperty("description"));
            leg.setIcon((Icon) wiz.getProperty("icon"));
            leg.setEvents((List<Event>) wiz.getProperty("events"));
            leg.setProcedures((List<Procedure>) wiz.getProperty("procedures"));

            if (legListener != null) {
                legListener.finishedLeg(leg);
                Lookup.getDefault().lookup(StatusBarService.class).setMessage(LIMOResourceBundle.getString("LEG") + " " + leg.getName(), StatusBarService.ACTION_CREATE, StatusBarService.STATE_SUCCESS, null);
            }
        }
    }

    public void setUpdate(Leg leg) {
        this.leg = leg;
        update = true;
    }

    public Leg getLeg() {
        return leg;
    }
}
