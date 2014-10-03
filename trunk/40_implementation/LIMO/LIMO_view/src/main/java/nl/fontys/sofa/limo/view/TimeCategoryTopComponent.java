package nl.fontys.sofa.limo.view;

import java.awt.BorderLayout;
import nl.fontys.sofa.limo.view.factory.CostCategoryChildFactory;
import nl.fontys.sofa.limo.view.node.TimeCategoryRootNode;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.explorer.ExplorerManager;
import org.openide.explorer.view.OutlineView;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;

/**
 * Top component which displays the time categories.
 */
@ConvertAsProperties(
				dtd = "-//nl.fontys.sofa.limo.view//TimeCategory//EN",
				autostore = false
)
@TopComponent.Description(
				preferredID = "TimeCategoryTopComponent",
				//iconBase="SET/PATH/TO/ICON/HERE", 
				persistenceType = TopComponent.PERSISTENCE_ALWAYS
)
@TopComponent.Registration(mode = "editor", openAtStartup = false)
@ActionID(category = "Window", id = "nl.fontys.sofa.limo.view.TimeCategoryTopComponent")
@ActionReference(path = "Menu/Data/Categories" /*, position = 333 */)
@TopComponent.OpenActionRegistration(
				displayName = "#CTL_TimeCategoryAction",
				preferredID = "TimeCategoryTopComponent"
)
@Messages({
	"CTL_TimeCategoryAction=Time Category",
	"CTL_TimeCategoryTopComponent=Time Categories",
	"HINT_TimeCategoryTopComponent=Manage time categories"
})
public final class TimeCategoryTopComponent extends TopComponent implements ExplorerManager.Provider{
	private ExplorerManager em;

	public TimeCategoryTopComponent() {
		initComponents();
		setName(Bundle.CTL_TimeCategoryTopComponent());
		setToolTipText(Bundle.HINT_TimeCategoryTopComponent());

		em = new ExplorerManager();
		OutlineView ov = new OutlineView("Categories");
		ov.setPropertyColumns("description", "Description");
		ov.getOutline().setRootVisible(false);
		add(ov, BorderLayout.CENTER);
		Children costCategoryChildren = Children.create(new CostCategoryChildFactory(), true);
		Node rootNode = new TimeCategoryRootNode(costCategoryChildren);
		rootNode.setDisplayName("Time Categories");
		em.setRootContext(rootNode);

	}

	@Override
	public ExplorerManager getExplorerManager() {
		return em;
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    setLayout(new java.awt.BorderLayout());
  }// </editor-fold>//GEN-END:initComponents

  // Variables declaration - do not modify//GEN-BEGIN:variables
  // End of variables declaration//GEN-END:variables
	@Override
	public void componentOpened() {
		// TODO add custom code on component opening
	}

	@Override
	public void componentClosed() {
		// TODO add custom code on component closing
	}

	void writeProperties(java.util.Properties p) {
		// better to version settings since initial version as advocated at
		// http://wiki.apidesign.org/wiki/PropertyFiles
		p.setProperty("version", "1.0");
		// TODO store your settings
	}

	void readProperties(java.util.Properties p) {
		String version = p.getProperty("version");
		// TODO read your settings according to their version
	}

}
