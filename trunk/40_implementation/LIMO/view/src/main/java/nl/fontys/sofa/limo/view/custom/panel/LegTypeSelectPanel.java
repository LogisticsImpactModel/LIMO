package nl.fontys.sofa.limo.view.custom.panel;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import nl.fontys.sofa.limo.api.exception.ServiceNotFoundException;
import nl.fontys.sofa.limo.domain.component.type.LegType;
import nl.fontys.sofa.limo.view.node.factory.LegTypeChildFactory;
import nl.fontys.sofa.limo.view.node.root.LegTypeRootNode;
import org.openide.explorer.ExplorerManager;
import org.openide.explorer.ExplorerUtils;
import org.openide.explorer.view.OutlineView;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;

/**
 * LegTypeSelectPanel is responsible for displaying a pane to select LegTypes
 * from.
 *
 * @author Sebastian Heijmann
 */
public final class LegTypeSelectPanel extends JPanel implements ExplorerManager.Provider, LookupListener {

    private final ExplorerManager em = new ExplorerManager();
    private Lookup.Result result = null;
    private final List<LegType> selectedLegTypes;

    public LegTypeSelectPanel() throws ServiceNotFoundException {
        initComponents();
        initCustomComponents();

        selectedLegTypes = new ArrayList<>();
        Lookup actionLookup = ExplorerUtils.createLookup(getExplorerManager(), getActionMap());
        result = actionLookup.lookupResult(LegType.class);
        result.addLookupListener(this);
        result.allInstances();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 208, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 187, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    private void initCustomComponents() throws ServiceNotFoundException {
        setLayout(new BorderLayout());

        OutlineView ov = new OutlineView("Legtypes");
        ov.getOutline().setRootVisible(false);
        add(ov, BorderLayout.CENTER);
        Node rootNode;
        Children children = Children.create(new LegTypeChildFactory(), true);
        rootNode = new LegTypeRootNode(children);
        rootNode.setDisplayName("Legtypes");
        em.setRootContext(rootNode);
    }

    @Override
    public ExplorerManager getExplorerManager() {
        return em;
    }

    @Override
    public void resultChanged(LookupEvent event) {
        Object[] results = ((Lookup.Result) event.getSource()).allInstances().toArray();
        for (Object r : results) {
            if (!selectedLegTypes.contains((LegType) r)) {
                selectedLegTypes.add((LegType) r);
            }
        }
    }

    /**
     * Get the selected LegType.
     *
     * @return LegType - the selected LegType.
     */
    public List<LegType> getSelectedLegTypes() {
        return selectedLegTypes;
    }
}
