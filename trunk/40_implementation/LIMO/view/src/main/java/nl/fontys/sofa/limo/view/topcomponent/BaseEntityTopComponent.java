package nl.fontys.sofa.limo.view.topcomponent;

import java.awt.BorderLayout;
import javax.swing.ActionMap;
import javax.swing.GroupLayout;
import nl.fontys.sofa.limo.api.exception.ServiceNotFoundException;
import nl.fontys.sofa.limo.view.node.root.AbstractRootNode;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.explorer.ExplorerManager;
import org.openide.explorer.ExplorerUtils;
import org.openide.explorer.view.OutlineView;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.windows.TopComponent;

/**
 * This top component shows a list and is the base class for other top
 * components where the shown class extends the class BeseEntity. The sub
 * classes have to override the getName method for showing the name in the tab.
 * Usually the top component name from the messages annotation like
 * CTL_EventTopComponent should be returned.
 *
 * @author Sven Mäurer
 */
public abstract class BaseEntityTopComponent extends TopComponent implements ExplorerManager.Provider {

    protected final ExplorerManager entityManager;

    /**
     * Initialize the top components with name, description columns. It also
     * enables a delete action
     */
    public BaseEntityTopComponent() {
        entityManager = new ExplorerManager();

        initComponents();
        setLayout(new BorderLayout());
        OutlineView ov = new OutlineView(LIMOResourceBundle.getString("NAME"));
        ov.setPropertyColumns("description", LIMOResourceBundle.getString("DESCRIPTION"));
        ov.getOutline().setRootVisible(false);
        add(ov, BorderLayout.CENTER);

        try {
            Children children = Children.create(getChildFactory(), true);
            Node rootNode = getRootNode(children);
            entityManager.setRootContext(rootNode);
        } catch (ServiceNotFoundException ex) {
            Exceptions.printStackTrace(ex);
            NotifyDescriptor d = new NotifyDescriptor.Message(LIMOResourceBundle.getString("LIMO_ERROR"),
                    NotifyDescriptor.ERROR_MESSAGE);
            DialogDisplayer.getDefault().notify(d);
        }

        ActionMap map = getActionMap();
        map.put("delete", ExplorerUtils.actionDelete(entityManager, true));
        associateLookup(ExplorerUtils.createLookup(entityManager, map));
    }

    protected abstract ChildFactory getChildFactory();

    protected abstract AbstractRootNode getRootNode(Children children) throws ServiceNotFoundException;

    private void initComponents() {
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGap(0, 300, Short.MAX_VALUE)
        );
    }

    @Override
    public ExplorerManager getExplorerManager() {
        return entityManager;
    }

    @Override
    public void componentOpened() {
        // TODO add custom code on component opening
    }

    @Override
    public void componentClosed() {
        // TODO add custom code on component closing
    }
}
