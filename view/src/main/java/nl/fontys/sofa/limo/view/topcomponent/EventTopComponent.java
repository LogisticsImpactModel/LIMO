package nl.fontys.sofa.limo.view.topcomponent;

import nl.fontys.sofa.limo.api.exception.ServiceNotFoundException;
import nl.fontys.sofa.limo.view.node.factory.EventChildFactory;
import nl.fontys.sofa.limo.view.node.root.AbstractRootNode;
import nl.fontys.sofa.limo.view.node.root.EventRootNode;
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
 * BaseEntityTopComponent for event.
 *
 * @author Sven Mäurer
 */
@ConvertAsProperties(
        dtd = "-//nl.fontys.sofa.limo.view.topcomponent//Event//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "EventTopComponent",
        iconBase = "icons/gui/list.gif",
        persistenceType = TopComponent.PERSISTENCE_ALWAYS
)
@TopComponent.Registration(
        mode = "editor",
        openAtStartup = false
)
@ActionID(
        category = "Window",
        id = "nl.fontys.sofa.limo.view.topcomponent.EventTopComponent"
)
@ActionReferences({
    @ActionReference(path = "Menu/Master Data/Event templates", position = 10),
    @ActionReference(path = "Shortcuts", name = "D-E")
})
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_EventAction",
        preferredID = "EventTopComponent"
)
@Messages({
    "CTL_EventAction=Event Catalog",
    "CTL_EventTopComponent=Event Catalog"
})
public final class EventTopComponent extends BaseEntityTopComponent {

    public EventTopComponent() {
        super();
    }

    @Override
    protected ChildFactory getChildFactory() {
        return new EventChildFactory();
    }

    @Override
    protected AbstractRootNode createRootNode(Children children) throws ServiceNotFoundException {
        AbstractRootNode rootNode = new EventRootNode(children);
        rootNode.setDisplayName(LIMOResourceBundle.getString("EVENT"));
        return rootNode;
    }

    @Override
    public String getName() {
        return Bundle.CTL_EventTopComponent();
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
