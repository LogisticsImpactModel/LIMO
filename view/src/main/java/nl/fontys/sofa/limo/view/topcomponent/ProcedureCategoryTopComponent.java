package nl.fontys.sofa.limo.view.topcomponent;

import nl.fontys.sofa.limo.api.exception.ServiceNotFoundException;
import nl.fontys.sofa.limo.view.node.factory.ProcedureCategoryChildFactory;
import nl.fontys.sofa.limo.view.node.root.AbstractRootNode;
import nl.fontys.sofa.limo.view.node.root.ProcedureCategoryRootNode;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Children;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.TopComponent;

/**
 * BaseEntityTopComponent for procedure categories.
 *
 * @author Sven Mäurer
 */
@ConvertAsProperties(
        dtd = "-//nl.fontys.sofa.limo.view.topcomponent//ProcedureCategory//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "ProcedureCategoryTopComponent",
        iconBase = "icons/gui/list.gif",
        persistenceType = TopComponent.PERSISTENCE_ALWAYS
)
@TopComponent.Registration(
        mode = "editor",
        openAtStartup = false
)
@ActionID(
        category = "Window",
        id = "nl.fontys.sofa.limo.view.topcomponent.ProcedureCategoryTopComponent"
)
@ActionReferences({
    @ActionReference(path = "Menu/Master Data/Procedure category", position = 10),
    @ActionReference(path = "Shortcuts", name = "D-P")
})
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_ProcedureCategoryAction",
        preferredID = "ProcedureCategoryTopComponent"
)
@Messages({
    "CTL_ProcedureCategoryAction=Procedure Category Catalog",
    "CTL_ProcedureCategoryTopComponent=Procedure Category Catalog"
})
public final class ProcedureCategoryTopComponent extends BaseEntityTopComponent {

    public ProcedureCategoryTopComponent() {
        super();
    }

    @Override
    protected ChildFactory getChildFactory() {
        return new ProcedureCategoryChildFactory();
    }

    @Override
    protected AbstractRootNode createRootNode(Children children) throws ServiceNotFoundException {
        AbstractRootNode rootNode = new ProcedureCategoryRootNode(children);
        rootNode.setDisplayName(LIMOResourceBundle.getString("NAME"));
        return rootNode;
    }

    @Override
    public String getName() {
        return Bundle.CTL_ProcedureCategoryTopComponent();
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
