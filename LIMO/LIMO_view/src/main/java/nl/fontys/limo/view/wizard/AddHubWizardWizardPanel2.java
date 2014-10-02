/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.limo.view.wizard;

import java.util.ArrayList;
import java.util.List;
import javax.swing.event.ChangeListener;
import nl.fontys.sofa.limo.api.dao.DAO;
import nl.fontys.sofa.limo.api.dao.DAOFactory;
import nl.fontys.sofa.limo.domain.category.Category;
import nl.fontys.sofa.limo.domain.category.CostCategory;
import nl.fontys.sofa.limo.domain.category.TimeCategory;
import org.openide.WizardDescriptor;
import org.openide.util.HelpCtx;
import org.openide.util.Lookup;

public class AddHubWizardWizardPanel2 implements WizardDescriptor.Panel<WizardDescriptor> {

	/**
	 * The visual component that displays this panel. If you need to access the
	 * component from this class, just use getComponent().
	 */
	private AddHubWizardVisualPanel2 component;
	private boolean componentInitiated = false;

	// Get the visual component for the panel. In this template, the component
	// is kept separate. This can be more efficient: if the wizard is created
	// but never displayed, or not all panels are displayed, it is better to
	// create only those which really need to be visible.
	@Override
	public AddHubWizardVisualPanel2 getComponent() {
		if (component == null) {
			component = new AddHubWizardVisualPanel2();
		}
		return component;
	}

	@Override
	public HelpCtx getHelp() {
		// Show no Help button for this panel:
		return HelpCtx.DEFAULT_HELP;
		// If you have context help:
		// return new HelpCtx("help.key.here");
	}

	@Override
	public boolean isValid() {
		// If it is always OK to press Next or Finish, then:
		return true;
		// If it depends on some condition (form filled out...) and
		// this condition changes (last form field filled in...) then
		// use ChangeSupport to implement add/removeChangeListener below.
		// WizardDescriptor.ERROR/WARNING/INFORMATION_MESSAGE will also be useful.
	}

	@Override
	public void addChangeListener(ChangeListener l) {
	}

	@Override
	public void removeChangeListener(ChangeListener l) {
	}

	@Override
	public void readSettings(WizardDescriptor wiz) {
		if (!componentInitiated) {
			String hubType = (String) wiz.getProperty("hubType");
			System.out.println("Looking up the factory");
			DAOFactory factory = Lookup.getDefault().lookup(DAOFactory.class);
			if (factory != null) {
				System.out.println("Factory present");
				DAO costDAO = factory.getCostCategoryDAO();
				DAO timeDAO = factory.getTimeCategoryDAO();
				List<CostCategory> costCategories = costDAO.findAll();
				List<TimeCategory> timeCategories = timeDAO.findAll();

				initCostCategories(costCategories);
				initTimeCategories(timeCategories);
			} else {
				System.out.println("Factory missing");
				initCostCategories(new ArrayList<CostCategory>());
				initTimeCategories(new ArrayList<TimeCategory>());
//			throw new UnsupportedOperationException("Factory not present");
			}
			componentInitiated = true;
		}
	}

	@Override
	public void storeSettings(WizardDescriptor wiz) {
		// use wiz.putProperty to remember current panel state
	}

	private void initCostCategories(List<CostCategory> costCategories) {
		Category cc1 = new CostCategory("Test1");
		Category cc2 = new CostCategory("Test2");
		Category cc3 = new CostCategory("Test3");
		Category cc4 = new CostCategory("Test4");
		Category cc5 = new CostCategory("Test5");
		Category cc6 = new CostCategory("Test6");
		Category cc7 = new CostCategory("Test7");
		Category cc8 = new CostCategory("Test8");
		Category cc9 = new CostCategory("Test9");

		AddHubWizardVisualPanel2 panel = getComponent();
		for (Category cc : costCategories) {
			panel.addCostCategory(cc);
		}
		panel.addCostCategory(cc1);
		panel.addCostCategory(cc2);
		panel.addCostCategory(cc3);
		panel.addCostCategory(cc4);
		panel.addCostCategory(cc5);
		panel.addCostCategory(cc6);
		panel.addCostCategory(cc7);
		panel.addCostCategory(cc8);
		panel.addCostCategory(cc9);
	}

	private void initTimeCategories(List<TimeCategory> timeCategories) {
		Category cc1 = new CostCategory("Example time costs");
		Category cc2 = new CostCategory("Over sleeping costs");

		AddHubWizardVisualPanel2 panel = getComponent();
		for (Category tc : timeCategories) {
			panel.addTimeCategory(tc);
		}

		panel.addCostCategory(cc1);
		panel.addCostCategory(cc2);
	}
}
