package nl.fontys.sofa.limo.view.topcomponent;

import java.awt.BorderLayout;
import java.beans.IntrospectionException;
import java.beans.PropertyVetoException;
import java.io.IOException;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import nl.fontys.sofa.limo.api.exception.ServiceNotFoundException;
import nl.fontys.sofa.limo.domain.component.SupplyChain;
import nl.fontys.sofa.limo.view.action.DeleteAction;
import nl.fontys.sofa.limo.view.chain.ChainGraphScene;
import nl.fontys.sofa.limo.view.chain.ChainGraphSceneImpl;
import nl.fontys.sofa.limo.view.chain.ChainPaletteFactory;
import nl.fontys.sofa.limo.view.chain.ChainToolbar;
import nl.fontys.sofa.limo.view.node.bean.AbstractBeanNode;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;
import org.netbeans.spi.palette.PaletteController;
import org.openide.awt.ActionID;
import org.openide.awt.UndoRedo;
import org.openide.explorer.ExplorerManager;
import org.openide.explorer.ExplorerUtils;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;
import org.openide.util.lookup.Lookups;
import org.openide.util.lookup.ProxyLookup;
import org.openide.windows.TopComponent;

/**
 * Top component which displays a GraphScene and Palette to build a chain with.
 */
@TopComponent.Description(
        preferredID = "ChainBuilderTopComponent",
        iconBase = "icons/gui/Link.png",
        persistenceType = TopComponent.PERSISTENCE_NEVER
)
@TopComponent.Registration(
        mode = "editor",
        openAtStartup = false
)
@ActionID(
        category = "Window",
        id = "nl.fontys.sofa.limo.view.topcomponent.ChainBuilderTopComponent"
)
@NbBundle.Messages({
    "CTL_ChainBuilderAction=New Supply Chain..",
    "CTL_ChainBuilderTopComponent=New Supply Chain"
})
public final class ChainBuilderTopComponent extends TopComponent
        implements DynamicExplorerManagerProvider {

    private ExplorerManager em = new ExplorerManager();
    private ChainGraphScene graphScene;
    private InstanceContent ic = new InstanceContent();
    SavableComponent savable;
    private UndoRedo.Manager undoManager = new UndoRedo.Manager();
    private PaletteController paletteController;

    /**
     * Constructor creates a new ChainBuilderTopComponent.
     *
     */
    public ChainBuilderTopComponent(String name) {
        try {
            paletteController = ChainPaletteFactory.createPalette();
            DeleteAction.setPallete(paletteController);
        } catch (ServiceNotFoundException ex) {
            Exceptions.printStackTrace(ex);
        }

        initComponents();
        initCustomComponents();

        SupplyChain chain = graphScene.getSupplyChain();
        chain.setName(name);
        setName(name);

        savable = new SavableComponent(graphScene.getChainBuilder(),ic,this);

        Lookup paletteLookup = Lookups.singleton(paletteController);
        Lookup nodeLookup = ExplorerUtils.createLookup(em, getActionMap());
        Lookup graphLookup = Lookups.singleton(graphScene);
        Lookup graphContentLookup = graphScene.getLookup();
        Lookup instanceContent = new AbstractLookup(ic);
        ProxyLookup pl = new ProxyLookup(graphContentLookup, paletteLookup, nodeLookup, graphLookup, instanceContent);
        associateLookup(pl);
    }

    /**
     * Initialize the custom placed components of this TopComponent.
     */
    private void initCustomComponents() {
        setLayout(new BorderLayout());
        SupplyChain chain = new SupplyChain();
        try {
            ChainToolbar toolbar = new ChainToolbar();
            add(toolbar, BorderLayout.NORTH);

            graphScene = new ChainGraphSceneImpl(this, chain, undoManager, paletteController);
            JScrollPane shapePane = new JScrollPane();
            JComponent createView = graphScene.createView();
            createView.putClientProperty("print.printable", Boolean.TRUE);
            createView.putClientProperty("print.name", LIMOResourceBundle.getString("SUPPLY_CHAIN") + ": " + chain.getName());
            shapePane.setViewportView(createView);

            add(shapePane, BorderLayout.CENTER);
            add(graphScene.createSatelliteView(), BorderLayout.SOUTH);
        } catch (IOException | IntrospectionException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    @Override
    public ExplorerManager getExplorerManager() {
        return em;
    }

    @Override
    public void setRootContext(AbstractBeanNode node) {
        em.setRootContext(node);
        try {
            em.setSelectedNodes(new Node[]{node});
        } catch (PropertyVetoException ex) {
            Exceptions.printStackTrace(ex);
        }
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
        // add custom code on component opening
    }

    @Override
    public void componentClosed() {

    }

    @Override
    public UndoRedo getUndoRedo() {
        return undoManager;
    }

    
    void writeProperties(java.util.Properties p) {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty("version", "1.0");
    }

    void readProperties(java.util.Properties p) {
        String version = p.getProperty("version");
    }

}
