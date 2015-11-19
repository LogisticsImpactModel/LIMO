package nl.fontys.sofa.limo.view.topcomponent;

import nl.fontys.sofa.limo.api.exception.ServiceNotFoundException;
import nl.fontys.sofa.limo.view.node.factory.ProcedureChildFactory;
import nl.fontys.sofa.limo.view.node.root.AbstractRootNode;
import nl.fontys.sofa.limo.view.node.root.ProcedureRootNode;
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
 * BaseEntityTopComponent for procedures.
 *
 * @author Christian Neumann
 */
@ConvertAsProperties(
        dtd = "-//nl.fontys.sofa.limo.view.topcomponent//Procedure//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "ProcedureTopComponent",
        iconBase = "icons/gui/list.png",
        persistenceType = TopComponent.PERSISTENCE_ALWAYS
)
@TopComponent.Registration(
        mode = "editor",
        openAtStartup = false
)
@ActionID(
        category = "Window",
        id = "nl.fontys.sofa.limo.view.topcomponent.ProcedureTopComponent"
)
@ActionReferences({
    @ActionReference(path = "Menu/Master Data/Procedures", position = 10),
    @ActionReference(path = "Shortcuts", name = "D-P")
})
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_ProcedureAction",
        preferredID = "ProcedureTopComponent"
)
@Messages({
    "CTL_ProcedureAction=Procedure catalog",
    "CTL_ProcedureTopComponent=Procedure catalog"
})
public final class ProcedureTopComponent extends BaseEntityTopComponentWithoutDescription {

    public ProcedureTopComponent() {
        super();
    }

    @Override
    protected ChildFactory getChildFactory() {
        return new ProcedureChildFactory();
    }

    @Override
    protected AbstractRootNode createRootNode(Children children) throws ServiceNotFoundException {
        AbstractRootNode rootNode = new ProcedureRootNode(children);
        rootNode.setDisplayName(LIMOResourceBundle.getString("NAME"));
        return rootNode;
    }

    @Override
    public String getName() {
        return Bundle.CTL_ProcedureTopComponent();
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
