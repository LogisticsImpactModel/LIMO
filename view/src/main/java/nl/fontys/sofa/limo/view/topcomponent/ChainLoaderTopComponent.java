package nl.fontys.sofa.limo.view.topcomponent;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.beans.IntrospectionException;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.IOException;
import javax.swing.JComponent;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.OverlayLayout;
import javax.swing.border.LineBorder;
import nl.fontys.sofa.limo.api.exception.ServiceNotFoundException;
import nl.fontys.sofa.limo.domain.component.SupplyChain;
import nl.fontys.sofa.limo.view.action.DeleteAction;
import nl.fontys.sofa.limo.view.chain.ChainGraphScene;
import nl.fontys.sofa.limo.view.chain.ChainGraphSceneImpl;
import nl.fontys.sofa.limo.view.chain.ChainPaletteFactory;
import nl.fontys.sofa.limo.view.chain.ChainToolbar;
import nl.fontys.sofa.limo.view.node.bean.AbstractBeanNode;
import org.netbeans.api.actions.Savable;
import org.netbeans.spi.palette.PaletteController;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.awt.ActionID;
import org.openide.awt.UndoRedo;
import org.openide.explorer.ExplorerManager;
import org.openide.explorer.ExplorerUtils;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.lookup.AbstractLookup;
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

    private final InstanceContent ic;
    private ExplorerManager em = new ExplorerManager();
    private ChainGraphScene graphScene;
    private final SavableComponent savable;
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
            DeleteAction.setPallete(paletteController);
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
        ProxyLookup pl = new ProxyLookup(graphContentLookup, paletteLookup, nodeLookup, graphLookup, new AbstractLookup(ic));
        associateLookup(pl);
    }

    public ChainLoaderTopComponent(SupplyChain chain) {
        try {
            paletteController = ChainPaletteFactory.createPalette();
            DeleteAction.setPallete(paletteController);
        } catch (ServiceNotFoundException ex) {
            Exceptions.printStackTrace(ex);
        }

        initComponents();
        SupplyChain supplyChain = chain;

        setName(supplyChain.getName().replace(".lsc", ""));
        initCustomComponents(supplyChain);
        ic = new InstanceContent();
        savable = new SavableComponent(graphScene.getChainBuilder(), ic, this);
        Lookup paletteLookup = Lookups.singleton(paletteController);
        Lookup nodeLookup = ExplorerUtils.createLookup(em, getActionMap());
        Lookup graphLookup = Lookups.singleton(graphScene);
        Lookup graphContentLookup = graphScene.getLookup();
        ProxyLookup pl = new ProxyLookup(graphContentLookup, paletteLookup, nodeLookup, graphLookup, new AbstractLookup(ic));
        associateLookup(pl);
    }

    /**
     * Initialize the custom components of this TopComponent. Kudos to Geertjan
     * Wielenga
     *
     * @param supplyChain the supplychain which is loaded.
     */
    void initCustomComponents(SupplyChain supplyChain) {
        setLayout(new BorderLayout());
        SupplyChain chain = supplyChain;
        try {
            ChainToolbar toolbar = new ChainToolbar();
            add(toolbar, BorderLayout.NORTH);
            graphScene = new ChainGraphSceneImpl(this, chain,
                    undoManager, paletteController);
            JPanel viewPanel = new JPanel(new BorderLayout());
            JScrollPane scroll = new JScrollPane(
                    graphScene.createView(),
                    JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                    JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            viewPanel.add(scroll, BorderLayout.CENTER);
            JPanel satellitePanel = new JPanel();
            satellitePanel.setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.weightx = 1;
            gbc.weighty = 1;
            gbc.insets = new Insets(0, 0, 25, 25);
            gbc.anchor = GridBagConstraints.SOUTHEAST;
            JComponent view = graphScene.createSatelliteView();
            JPanel holder = new JPanel(new BorderLayout());
            holder.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
            holder.add(view);
            satellitePanel.add(holder, gbc);
            satellitePanel.setOpaque(false);
            JLayeredPane panel = new JLayeredPane();
            panel.setLayout(new OverlayLayout(panel));
            panel.add(viewPanel, JLayeredPane.DEFAULT_LAYER);
            panel.add(satellitePanel, JLayeredPane.PALETTE_LAYER);
            add(panel);
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

    @Override
    public boolean canClose() {
        Savable save = getLookup().lookup(Savable.class);
        if (save != null) {
            NotifyDescriptor d
                    = new NotifyDescriptor.Confirmation("This chain contains unsaved changes do you really want to close it ? ", NotifyDescriptor.YES_NO_OPTION);
            if (DialogDisplayer.getDefault().notify(d) == NotifyDescriptor.YES_OPTION) {
                savable.removeSavable();
                return true;
            } else {
                return false;
            }

        } else {
            return true;
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
