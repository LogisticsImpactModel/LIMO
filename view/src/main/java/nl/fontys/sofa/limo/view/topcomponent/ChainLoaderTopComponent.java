package nl.fontys.sofa.limo.view.topcomponent;

import java.awt.BorderLayout;
import java.beans.IntrospectionException;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.IOException;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import nl.fontys.sofa.limo.api.exception.ServiceNotFoundException;
import nl.fontys.sofa.limo.domain.component.SupplyChain;
import nl.fontys.sofa.limo.view.chain.ChainGraphScene;
import nl.fontys.sofa.limo.view.chain.ChainGraphSceneImpl;
import nl.fontys.sofa.limo.view.chain.ChainPaletteFactory;
import nl.fontys.sofa.limo.view.chain.ChainToolbar;
import nl.fontys.sofa.limo.view.node.bean.AbstractBeanNode;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;
import org.netbeans.spi.palette.PaletteController;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.awt.ActionID;
import org.openide.awt.UndoRedo;
import org.openide.explorer.ExplorerManager;
import org.openide.explorer.ExplorerUtils;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.lookup.InstanceContent;
import org.openide.util.lookup.Lookups;
import org.openide.util.lookup.ProxyLookup;
import org.openide.windows.TopComponent;

/**
 * Top component which loads an existing
 * {@link nl.fontys.sofa.limo.domain.component.SupplyChain} and displays a
 * GraphScene and Palette to build a chain with.
 */
@TopComponent.Description(
        preferredID = "ChainLoaderTopComponent",
        iconBase = "icons/gui/Link.png",
        persistenceType = TopComponent.PERSISTENCE_NEVER
)
@TopComponent.Registration(
        mode = "editor",
        openAtStartup = false
)
@ActionID(
        category = "Window",
        id = "nl.fontys.sofa.limo.view.topcomponent.ChainLoaderTopComponent"
)
public final class ChainLoaderTopComponent extends TopComponent implements
        DynamicExplorerManagerProvider {

    private InstanceContent ic;
    private ExplorerManager em = new ExplorerManager();
    private ChainGraphScene graphScene;
    private SavableComponent savable;
    private PaletteController paletteController;
    private UndoRedo.Manager undoManager = new UndoRedo.Manager();

    /**
     * Constructor creates a new ChainLoaderTopcomponent.
     *
     * @param chainFile the file where the supplychain is located.
     */
    public ChainLoaderTopComponent(File chainFile) {
        try {
            paletteController = ChainPaletteFactory.createPalette();
        } catch (ServiceNotFoundException ex) {
            Exceptions.printStackTrace(ex);
        }

        initComponents();
        SupplyChain supplyChain = SupplyChain.createFromFile(chainFile);
        supplyChain.setName(chainFile.getName());
        supplyChain.setFilepath(chainFile.getPath());

        setName(supplyChain.getName().replace(".lsc", ""));
        initCustomComponents(supplyChain);
        ic = new InstanceContent();
        savable = new SavableComponent(graphScene.getChainBuilder(), ic, this);
        Lookup paletteLookup = Lookups.singleton(paletteController);
        Lookup nodeLookup = ExplorerUtils.createLookup(em, getActionMap());
        Lookup graphLookup = Lookups.singleton(graphScene);
        Lookup graphContentLookup = graphScene.getLookup();
        ProxyLookup pl = new ProxyLookup(graphContentLookup, paletteLookup, nodeLookup, graphLookup);
        associateLookup(pl);
    }

    /**
     * Initialize the custom components of this TopComponent.
     *
     * @param supplyChain the supplychain which is loaded.
     */
    void initCustomComponents(SupplyChain supplyChain) {
        setLayout(new BorderLayout());
        try {
            ChainToolbar toolbar = new ChainToolbar();
            add(toolbar, BorderLayout.NORTH);

            graphScene = new ChainGraphSceneImpl(this, supplyChain, undoManager, paletteController);
            JScrollPane shapePane = new JScrollPane();
            JComponent createView = graphScene.createView();
            createView.putClientProperty("print.printable", Boolean.TRUE);
            createView.putClientProperty("print.name", LIMOResourceBundle.getString("SUPPLY_CHAIN") + ": " + supplyChain.getName());
            shapePane.setViewportView(createView);

            add(shapePane, BorderLayout.CENTER);
            add(graphScene.createSatelliteView(), BorderLayout.SOUTH);
        } catch (IOException | IntrospectionException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    
    @Override
    public UndoRedo getUndoRedo() {
        return undoManager;
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
