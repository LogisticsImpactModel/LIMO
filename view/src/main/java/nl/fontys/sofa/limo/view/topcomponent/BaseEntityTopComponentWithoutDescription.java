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
 * Abstract class which is responsible for displaying a view of a type of
 * {@link nl.fontys.sofa.limo.domain.BaseEntity}.
 * <p>
 * A {@link org.openide.windows.TopComponent} which want to display their
 * relating entity should extend this class.
 * <p>
 * Subclasses have to override the getName method for displaying the correct
 * name.
 *
 * @author Sven Mäurer
 */
public abstract class BaseEntityTopComponentWithoutDescription
        extends TopComponent
        implements ExplorerManager.Provider {

  protected final ExplorerManager entityManager;

  /**
   * Initialize the top components by creating the view components and children
   * to be displayed and each child has a delete action attached to it.
   *
   */
  public BaseEntityTopComponentWithoutDescription() {
    entityManager = new ExplorerManager();
    initComponents();
    addChildren();

    ActionMap map = getActionMap();
    map.put("delete", ExplorerUtils.actionDelete(entityManager, true));
    associateLookup(ExplorerUtils.createLookup(entityManager, map));
  }

  @Override
  public ExplorerManager getExplorerManager() {
    return entityManager;
  }

  /**
   * Get the child factory from the subclass.
   *
   * @return the belonging child factory.
   */
  protected abstract ChildFactory getChildFactory();

  /**
   * Create a root node with the given children.
   *
   * @param children the children of the root node.
   * @return the root node.
   * @throws ServiceNotFoundException
   */
  protected abstract AbstractRootNode createRootNode(Children children) throws ServiceNotFoundException;

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

    setLayout(new BorderLayout());
    OutlineView ov = new OutlineView(LIMOResourceBundle.getString("NAME"));
    //ov.setPropertyColumns("description", LIMOResourceBundle.getString("DESCRIPTION"));
    ov.getOutline().setRootVisible(false);
    add(ov, BorderLayout.CENTER);
  }

  private void addChildren() {
    try {
      Children children = Children.create(getChildFactory(), true);
      Node rootNode = createRootNode(children);
      entityManager.setRootContext(rootNode);
    } catch (ServiceNotFoundException ex) {
      Exceptions.printStackTrace(ex);
      NotifyDescriptor d = new NotifyDescriptor.Message(LIMOResourceBundle.getString("LIMO_ERROR"),
              NotifyDescriptor.ERROR_MESSAGE);
      DialogDisplayer.getDefault().notify(d);
    }
  }
}
