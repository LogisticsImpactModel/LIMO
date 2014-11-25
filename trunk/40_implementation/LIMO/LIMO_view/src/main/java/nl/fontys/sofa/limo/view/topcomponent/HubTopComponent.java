package nl.fontys.sofa.limo.view.topcomponent;

import java.awt.BorderLayout;
import javax.swing.ActionMap;
import nl.fontys.sofa.limo.api.exception.ServiceNotFoundException;
import nl.fontys.sofa.limo.view.factory.HubChildFactory;
import nl.fontys.sofa.limo.view.node.HubRootNode;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.explorer.ExplorerManager;
import org.openide.explorer.ExplorerUtils;
import org.openide.explorer.view.OutlineView;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;

@ConvertAsProperties(
        dtd = "-//nl.fontys.sofa.limo.view.topcomponent//Hub//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "HubTopComponent",
        iconBase = "icons/gui/list.gif",
        persistenceType = TopComponent.PERSISTENCE_ALWAYS
)
@TopComponent.Registration(mode = "editor", openAtStartup = false)
@ActionID(category = "Window", id = "nl.fontys.sofa.limo.view.topcomponent.HubTopComponent")
@ActionReference(path = "Menu/Master Data/Hub", position = 10)
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_HubAction",
        preferredID = "HubTopComponent"
)
@Messages({
    "CTL_HubAction=List",
    "CTL_HubTopComponent=Hub List",
    "HINT_HubTopComponent=Manage Hubs"
})
public final class HubTopComponent extends TopComponent implements ExplorerManager.Provider {

    private final ExplorerManager em = new ExplorerManager();

    public HubTopComponent() {
        initComponents();
        setName(Bundle.CTL_HubTopComponent());
        setToolTipText(Bundle.HINT_HubTopComponent());

        setLayout(new BorderLayout());

        OutlineView ov = new OutlineView("Name");
        ov.setPropertyColumns("description", "Description");
        ov.getOutline().setRootVisible(false);
        add(ov, BorderLayout.CENTER);

        try {
            Node rootNode;
            Children children = Children.create(new HubChildFactory(), true);
            rootNode = new HubRootNode(children);
            rootNode.setDisplayName("Hubs");
            em.setRootContext(rootNode);
        } catch (ServiceNotFoundException ex) {
            Exceptions.printStackTrace(ex);
            NotifyDescriptor d = new NotifyDescriptor.Message("Limo encountered "
                    + "a problem, changes made will not be saved. Please contact "
                    + "your administrator...",
                    NotifyDescriptor.ERROR_MESSAGE);
            DialogDisplayer.getDefault().notify(d);
        }

        ActionMap map = getActionMap();
        map.put("delete", ExplorerUtils.actionDelete(em, true));
        associateLookup(ExplorerUtils.createLookup(em, map));
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
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
