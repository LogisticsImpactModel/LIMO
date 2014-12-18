package nl.fontys.sofa.limo.view.topcomponent;

import nl.fontys.sofa.limo.api.exception.ServiceNotFoundException;
import nl.fontys.sofa.limo.view.node.factory.LegTypeChildFactory;
import nl.fontys.sofa.limo.view.node.root.AbstractRootNode;
import nl.fontys.sofa.limo.view.node.root.LegTypeRootNode;
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
 * BaseEntityTopComponent for leg type.
 *
 * @author Sven Mäurer
 */
@ConvertAsProperties(
        dtd = "-//nl.fontys.sofa.limo.view.topcomponent//LegType//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "LegTypeTopComponent",
        iconBase = "icons/gui/list.gif",
        persistenceType = TopComponent.PERSISTENCE_ALWAYS
)
@TopComponent.Registration(
        mode = "editor",
        openAtStartup = false
)
@ActionID(
        category = "Window",
        id = "nl.fontys.sofa.limo.view.topcomponent.LegTypeTopComponent"
)
@ActionReferences({
    @ActionReference(path = "Menu/Master Data/Leg Type", position = 10),
    @ActionReference(path = "Shortcuts", name = "DO-L")
})
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_LegTypeAction",
        preferredID = "LegTypeTopComponent"
)
@Messages({
    "CTL_LegTypeAction=Leg Type Catalog",
    "CTL_LegTypeTopComponent=Leg Type Catalog"})
public final class LegTypeTopComponent extends BaseEntityTopComponent {

    public LegTypeTopComponent() {
        super();
    }

    @Override
    protected ChildFactory getChildFactory() {
        return new LegTypeChildFactory();
    }

    @Override
    protected AbstractRootNode createRootNode(Children children) throws ServiceNotFoundException {
        AbstractRootNode rootNode = new LegTypeRootNode(children);
        rootNode.setDisplayName(LIMOResourceBundle.getString("LEG_TYPE"));
        return rootNode;
    }

    @Override
    public String getName() {
        return Bundle.CTL_LegTypeTopComponent();
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
