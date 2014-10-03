package nl.fontys.limo.view;

import java.awt.BorderLayout;
import nl.fontys.limo.view.factory.CostCategoryChildFactory;
import nl.fontys.limo.view.node.CostCategoryRootNode;
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
 * Top component which displays the cost categories.
 */
@ConvertAsProperties(
				dtd = "-//nl.fontys.limo.view//Category//EN",
				autostore = false
)
@TopComponent.Description(
				preferredID = "CategoryTopComponent",
				//iconBase="SET/PATH/TO/ICON/HERE", 
				persistenceType = TopComponent.PERSISTENCE_ALWAYS
)
@TopComponent.Registration(mode = "editor", openAtStartup = false)
@ActionID(category = "Window", id = "nl.fontys.limo.view.CategoryTopComponent")
@ActionReference(path = "Menu/Data/Categories" , position = 10 )
@TopComponent.OpenActionRegistration(
				displayName = "#CTL_CategoryAction",
				preferredID = "CategoryTopComponent"
)
@Messages({
	"CTL_CategoryAction=Cost Category",
	"CTL_CategoryTopComponent=Cost Categories",
	"HINT_CategoryTopComponent=Manage cost categories"
})
public final class CostCategoryTopComponent extends TopComponent implements ExplorerManager.Provider{
	private ExplorerManager em;

	public CostCategoryTopComponent() {
		initComponents();
		setName(Bundle.CTL_CategoryTopComponent());
		setToolTipText(Bundle.HINT_CategoryTopComponent());
		em = new ExplorerManager();
		OutlineView ov = new OutlineView("Categories");
		ov.setPropertyColumns("description", "Description");
		ov.getOutline().setRootVisible(false);
		add(ov, BorderLayout.CENTER);
		Children costCategoryChildren = Children.create(new CostCategoryChildFactory(), true);
		Node rootNode = new CostCategoryRootNode(costCategoryChildren);
		rootNode.setDisplayName("Categories");
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
