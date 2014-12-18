package nl.fontys.sofa.limo.view.topcomponent;

import nl.fontys.sofa.limo.api.exception.ServiceNotFoundException;
import nl.fontys.sofa.limo.view.node.factory.HubTypeChildFactory;
import nl.fontys.sofa.limo.view.node.root.AbstractRootNode;
import nl.fontys.sofa.limo.view.node.root.HubTypeRootNode;
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
 * BaseEntityTopComponent for hub type.
 *
 * @author Sven Mäurer
 */
@ConvertAsProperties(
        dtd = "-//nl.fontys.sofa.limo.view.topcomponent//HubType//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "HubTypeTopComponent",
        iconBase = "icons/gui/list.gif",
        persistenceType = TopComponent.PERSISTENCE_ALWAYS
)
@TopComponent.Registration(
        mode = "editor",
        openAtStartup = false
)
@ActionID(
        category = "Window",
        id = "nl.fontys.sofa.limo.view.topcomponent.HubTypeTopComponent"
)
@ActionReferences({
    @ActionReference(path = "Menu/Master Data/Hub Type", position = 10),
    @ActionReference(path = "Shortcuts", name = "DO-H")
})
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_HubTypeAction",
        preferredID = "HubTypeTopComponent"
)
@Messages({
    "CTL_HubTypeAction=Hub Type Catalog",
    "CTL_HubTypeTopComponent=Hub Type Catalog"
})
public final class HubTypeTopComponent extends BaseEntityTopComponent {

    public HubTypeTopComponent() {
        super();
    }

    @Override
    protected ChildFactory getChildFactory() {
        return new HubTypeChildFactory();
    }

    @Override
    protected AbstractRootNode getRootNode(Children children) throws ServiceNotFoundException {
        AbstractRootNode rootNode = new HubTypeRootNode(children);
        rootNode.setDisplayName(LIMOResourceBundle.getString("HUB_TYPE"));
        return rootNode;
    }

    @Override
    public String getName() {
        return Bundle.CTL_HubTypeTopComponent();
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
