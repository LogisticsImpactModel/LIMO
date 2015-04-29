/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.view.wizard.leg.multimode;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;
import nl.fontys.sofa.limo.api.service.status.StatusBarService;
import nl.fontys.sofa.limo.domain.component.leg.MultiModeLeg;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;
import nl.fontys.sofa.limo.view.wizard.types.TypeWizardAction;
import org.openide.DialogDisplayer;
import org.openide.WizardDescriptor;
import org.openide.util.ImageUtilities;
import org.openide.util.Lookup;

/**
 * Multimode leg Wizard
 *
 * @author Pascal Lindner
 */
public final class MultimodeLegWizardAction implements ActionListener {

    private MultiModeLeg leg;

    public MultimodeLegWizardAction(MultimodeLegTablePanel.FinishedMultiModeLegListener legListener) {
        this.legListener = legListener;
        this.leg = new MultiModeLeg();
    }

    public MultimodeLegWizardAction() {
        this.leg = new MultiModeLeg();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        List<WizardDescriptor.Panel<WizardDescriptor>> panels = new ArrayList<WizardDescriptor.Panel<WizardDescriptor>>();
        panels.add(new NameDescriptionIconHubTypeWizard());
        panels.add(new MultimodeLegTableWizard());

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

        wiz.setTitleFormat(new MessageFormat("{0}"));
        wiz.putProperty(WizardDescriptor.PROP_IMAGE, ImageUtilities.loadImage("icons/limo_wizard.png", true));
        wiz.setTitle(LIMOResourceBundle.getString("MULTIMODE_LEG"));
        wiz.putProperty(TypeWizardAction.TYPE_OLDTYPE, leg);

        if (DialogDisplayer.getDefault().notify(wiz) == WizardDescriptor.FINISH_OPTION) {
            leg = (MultiModeLeg) wiz.getProperty(TypeWizardAction.TYPE_OLDTYPE);

            if (legListener != null) { //legListener can be null if the wizard is used for editing an exisitn gleg 
                legListener.finishedLeg(leg);
                Lookup.getDefault().lookup(StatusBarService.class).setMessage(LIMOResourceBundle.getString("MULTIMODE_LEG") + " ", StatusBarService.ACTION_CREATE, StatusBarService.STATE_SUCCESS, null);
            }
        }
    }

    public MultiModeLeg getLeg() {
        return leg;
    }

    /**
     * Defines whether the wizard is used for editing an existing leg or
     * creating a new leg
     *
     * @param leg Leg which should be updated
     */
    public void setUpdate(MultiModeLeg leg) {
        this.leg = leg;
    }

    private MultimodeLegTablePanel.FinishedMultiModeLegListener legListener;

}
