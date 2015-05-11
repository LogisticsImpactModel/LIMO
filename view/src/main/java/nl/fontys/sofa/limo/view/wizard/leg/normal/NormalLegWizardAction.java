package nl.fontys.sofa.limo.view.wizard.leg.normal;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;
import nl.fontys.sofa.limo.api.service.status.StatusBarService;
import nl.fontys.sofa.limo.domain.component.leg.Leg;
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
    private Leg originalLeg, leg;
    private boolean update;

    public NormalLegWizardAction(MultimodeLegTablePanel.FinishedLegListener legListener) {
        this();
        this.legListener = legListener;
    }

    public NormalLegWizardAction() {
        this.originalLeg = new Leg();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        List<WizardDescriptor.Panel<WizardDescriptor>> panels = new ArrayList<>();
        if (!update) {
            panels.add(new NewOrFromTypeWizard());
            this.leg = new Leg();
        } else {
            this.leg = new Leg(originalLeg); //Creates a new leg with the same attributes. This way the original leg object keeped ontouched. 
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
        WizardDescriptor wiz = new WizardDescriptor(new WizardDescriptor.ArrayIterator<>(panels));
        wiz.putProperty(WizardDescriptor.PROP_IMAGE, ImageUtilities.loadImage("icons/limo_wizard.png", true));

        wiz.putProperty("leg", leg);
        wiz.putProperty("original_leg", originalLeg);

        wiz.setTitleFormat(new MessageFormat("{0}"));
        wiz.setTitle(LIMOResourceBundle.getString("CREATE_NORMAL_LEG"));

        if (DialogDisplayer.getDefault().notify(wiz) == WizardDescriptor.FINISH_OPTION) {
            originalLeg.deepOverwrite((Leg) wiz.getProperty("leg"));

            if (legListener != null) {
                legListener.finishedLeg(originalLeg);
                Lookup.getDefault().lookup(StatusBarService.class).setMessage(LIMOResourceBundle.getString("LEG") + " " + originalLeg.getName(), StatusBarService.ACTION_CREATE, StatusBarService.STATE_SUCCESS, null);
            }
        }
    }

    public void setUpdate(Leg leg) {
        this.originalLeg = leg;
        update = true;
    }

    public Leg getLeg() {
        return originalLeg;
    }
}
