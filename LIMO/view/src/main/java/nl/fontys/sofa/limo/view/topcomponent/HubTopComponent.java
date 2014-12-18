package nl.fontys.sofa.limo.view.topcomponent;

import nl.fontys.sofa.limo.api.exception.ServiceNotFoundException;
import nl.fontys.sofa.limo.view.node.factory.HubChildFactory;
import nl.fontys.sofa.limo.view.node.root.AbstractRootNode;
import nl.fontys.sofa.limo.view.node.root.HubRootNode;
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
 * BaseEntityTopComponent for hub.
 *
 * @author Sven Mäurer
 */
@ConvertAsProperties(
        dtd = "-//nl.fontys.sofa.limo.view.topcomponent//Hub//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "HubTopComponent",
        iconBase = "icons/gui/list.gif",
        persistenceType = TopComponent.PERSISTENCE_ALWAYS
)
@TopComponent.Registration(
        mode = "editor",
        openAtStartup = false
)
@ActionID(
        category = "Window",
        id = "nl.fontys.sofa.limo.view.topcomponent.HubTopComponent"
)
@ActionReferences({
    @ActionReference(path = "Menu/Master Data/Hub", position = 10),
    @ActionReference(path = "Shortcuts", name = "D-H")
})
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_HubAction",
        preferredID = "HubTopComponent"
)
@Messages({
    "CTL_HubAction=Hub Catalog",
    "CTL_HubTopComponent=Hub Catalog"
})
public final class HubTopComponent extends BaseEntityTopComponent {

    public HubTopComponent() {
        super();
    }

    @Override
    protected ChildFactory getChildFactory() {
        return new HubChildFactory();
    }

    @Override
    protected AbstractRootNode getRootNode(Children children) throws ServiceNotFoundException {
        AbstractRootNode rootNode = new HubRootNode(children);
        rootNode.setDisplayName(LIMOResourceBundle.getString("HUB"));
        return rootNode;
    }

    @Override
    public String getName() {
        return Bundle.CTL_HubTopComponent();
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
