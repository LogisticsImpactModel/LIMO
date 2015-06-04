package nl.fontys.sofa.limo.view.wizard.leg.scheduled;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;
import nl.fontys.sofa.limo.api.service.status.StatusBarService;
import nl.fontys.sofa.limo.domain.component.leg.ScheduledLeg;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;
import nl.fontys.sofa.limo.view.wizard.leg.multimode.MultimodeLegTablePanel;
import nl.fontys.sofa.limo.view.wizard.leg.normal.EventLegTypeWizard;
import nl.fontys.sofa.limo.view.wizard.leg.normal.ProceduresLegTypeWizard;
import org.openide.DialogDisplayer;
import org.openide.WizardDescriptor;
import org.openide.util.ImageUtilities;
import org.openide.util.Lookup;

/**
 * Wizard Action for ScheduledLeg.
 *
 * @author Pascal Lindner
 */
public final class ScheduledLegWizardAction implements ActionListener {

    private MultimodeLegTablePanel.FinishedScheduledLegListener legListener;
    private ScheduledLeg originalLeg, leg;
    private boolean update;

    public ScheduledLegWizardAction(MultimodeLegTablePanel.FinishedScheduledLegListener legListener) {
        this.legListener = legListener;
        this.originalLeg = new ScheduledLeg();
    }

    public ScheduledLegWizardAction() {
        originalLeg = new ScheduledLeg();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        List<WizardDescriptor.Panel<WizardDescriptor>> panels = new ArrayList<>();
        if (!update) {
            this.leg = new ScheduledLeg();
        } else {
            this.leg = new ScheduledLeg(originalLeg); //Creates a new leg with the same attributes. This way the original leg object keeped ontouched. 
        }

        panels.add(new NameDescriptionIconLegPanel());
        panels.add(new ScheduledLegWizard());
        panels.add(new ProceduresLegTypeWizard());
        panels.add(new EventLegTypeWizard());

        String[] steps = new String[panels.size()];
        for (int i = 0; i < panels.size(); i++) {
            Component c = panels.get(i).getComponent();
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

        WizardDescriptor wiz = new WizardDescriptor(new WizardDescriptor.ArrayIterator<>(panels));
        wiz.setTitleFormat(new MessageFormat("{0}"));
        wiz.putProperty(WizardDescriptor.PROP_IMAGE, ImageUtilities.loadImage("icons/limo_wizard.png", true));
        wiz.setTitle(LIMOResourceBundle.getString("SCHEDULED_LEG"));

        wiz.putProperty("leg", leg);
        wiz.putProperty("original_leg", originalLeg);

        if (DialogDisplayer.getDefault().notify(wiz) == WizardDescriptor.FINISH_OPTION) {
            leg = (ScheduledLeg) wiz.getProperty("leg");
            originalLeg.deepOverwrite(leg);

            if (legListener != null) {
                legListener.finishedLeg(originalLeg);
                Lookup.getDefault().lookup(StatusBarService.class).setMessage(LIMOResourceBundle.getString("SCHEDULED_LEG"), StatusBarService.ACTION_CREATE, StatusBarService.STATE_SUCCESS, null);
            }
        }
    }

    public void setUpdate(ScheduledLeg leg) {
        this.originalLeg = leg;
        update = true;
    }
}
