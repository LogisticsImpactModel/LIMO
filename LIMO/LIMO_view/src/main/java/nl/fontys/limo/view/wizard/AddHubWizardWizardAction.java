/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.limo.view.wizard;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;
import org.openide.DialogDisplayer;
import org.openide.WizardDescriptor;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.ImageUtilities;

// An example action demonstrating how the wizard could be called from within
// your code. You can move the code below wherever you need, or register an action:
@ActionID(category="MasterData", id="nl.fontys.limo.view.wizard.AddHubWizardWizardAction")
@ActionRegistration(displayName="Add Hub")
@ActionReference(path="Menu/Data", position=10)
public final class AddHubWizardWizardAction implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		List<WizardDescriptor.Panel<WizardDescriptor>> panels = new ArrayList<WizardDescriptor.Panel<WizardDescriptor>>();
		panels.add(new AddHubWizardWizardPanel1());
		panels.add(new AddHubWizardWizardPanel2());
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
		// {0} will be replaced by WizardDesriptor.Panel.getComponent().getName()
		wiz.setTitleFormat(new MessageFormat("{0}"));
		wiz.setTitle("Add Hub");
		wiz.putProperty(WizardDescriptor.PROP_IMAGE, ImageUtilities.loadImage("/nl/fontys/limo/view/image/logo_small.png", true));
		if (DialogDisplayer.getDefault().notify(wiz) == WizardDescriptor.FINISH_OPTION) {
			String name = (String) wiz.getProperty("hubName");
			String location = (String) wiz.getProperty("hubLocation");
		}
	}

}
