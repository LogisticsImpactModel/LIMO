package nl.fontys.sofa.limo.view.topcomponent;

import java.awt.BorderLayout;
import java.beans.IntrospectionException;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.IOException;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import nl.fontys.sofa.limo.api.exception.ServiceNotFoundException;
import nl.fontys.sofa.limo.domain.component.SupplyChain;
import nl.fontys.sofa.limo.view.chain.ChainGraphScene;
import nl.fontys.sofa.limo.view.chain.ChainGraphSceneImpl;
import nl.fontys.sofa.limo.view.chain.ChainPaletteFactory;
import nl.fontys.sofa.limo.view.chain.ChainToolbar;
import nl.fontys.sofa.limo.view.node.bean.AbstractBeanNode;
import nl.fontys.sofa.limo.view.util.ChainFileChooser;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.explorer.ExplorerManager;
import org.openide.explorer.ExplorerUtils;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.NbBundle.Messages;
import org.openide.util.lookup.Lookups;
import org.openide.util.lookup.ProxyLookup;
import org.openide.windows.TopComponent;

/**
 * Top component which loads an existing
 * {@link nl.fontys.sofa.limo.domain.component.SupplyChain} and displays a
 * GraphScene and Palette to build a chain with.
 */
@ConvertAsProperties(
        dtd = "-//nl.fontys.sofa.limo.view.topcomponent//ChainLoader//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "ChainLoaderTopComponent",
        iconBase = "icons/gui/link.gif",
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
@ActionReferences({
  @ActionReference(path = "Menu/File", position = 3),
  //@ActionReference(path = "Toolbars/File", position = 0),
  @ActionReference(path = "Shortcuts", name = "D-N")
})
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_ChainLoaderAction"
)
@Messages({
  "CTL_ChainLoaderAction=Load Supply Chain..",
  "CTL_ChainLoaderTopComponent=Load Supply Chain"
})
public final class ChainLoaderTopComponent extends TopComponent implements
        DynamicExplorerManagerProvider {

  private ExplorerManager em = new ExplorerManager();
  private ChainGraphScene graphScene;

  public ChainLoaderTopComponent() {
    initComponents();

    File chainFile = openSupplyChain();
    if (chainFile != null) {
      SupplyChain supplyChain = SupplyChain.createFromFile(chainFile);
      setName(supplyChain.getName());
      initCustomComponents(supplyChain);

      try {
        SavableComponent savable = new SavableComponent(graphScene.getChainBuilder());

        Lookup paletteLookup = Lookups.singleton(ChainPaletteFactory.createPalette());
        Lookup nodeLookup = ExplorerUtils.createLookup(em, getActionMap());
        Lookup graphLookup = Lookups.singleton(graphScene);
        Lookup savableLookup = Lookups.singleton(savable);
        ProxyLookup pl = new ProxyLookup(paletteLookup, nodeLookup, graphLookup, savableLookup);
        associateLookup(pl);
      } catch (ServiceNotFoundException ex) {
        Exceptions.printStackTrace(ex);
        NotifyDescriptor d = new NotifyDescriptor.Message(LIMOResourceBundle.getString("LIMO_ERROR"),
                NotifyDescriptor.ERROR_MESSAGE);
        DialogDisplayer.getDefault().notify(d);
      }
    }else{
      this.close();
    }
  }

  private File openSupplyChain() {
    JFileChooser fc = new ChainFileChooser();
    int result = fc.showOpenDialog(null);
    if (result == JFileChooser.APPROVE_OPTION) {
      return fc.getSelectedFile();
    } else {
      return null;
    }
  }

  void initCustomComponents(SupplyChain supplyChain) {
    setLayout(new BorderLayout());
    try {
      ChainToolbar toolbar = new ChainToolbar();
      add(toolbar, BorderLayout.NORTH);

      graphScene = new ChainGraphSceneImpl(this, supplyChain);
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
