package nl.fontys.sofa.limo.view.node;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import nl.fontys.sofa.limo.api.service.provider.HubService;
import nl.fontys.sofa.limo.domain.component.Icon;
import nl.fontys.sofa.limo.domain.component.hub.Hub;
import nl.fontys.sofa.limo.domain.component.hub.Location;
import nl.fontys.sofa.limo.view.chain.ChainGraphScene;
import nl.fontys.sofa.limo.view.node.property.StupidProperty;
import nl.fontys.sofa.limo.view.node.property.editor.EventPropertyEditor;
import nl.fontys.sofa.limo.view.node.property.editor.IconPropertyEditor;
import nl.fontys.sofa.limo.view.node.property.editor.ProcedurePropertyEditor;
import nl.fontys.sofa.limo.view.widget.HubWidget;
import nl.fontys.sofa.limo.view.wizard.hub.HubWizardAction;
import org.netbeans.api.visual.graph.GraphScene;
import org.netbeans.api.visual.widget.Widget;
import org.openide.ErrorManager;
import org.openide.nodes.Sheet;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;

/**
 * View representation of Hub.
 *
 * @author Sebastiaan Heijmann
 */
public class HubNode extends AbstractBeanNode<Hub> implements WidgetableNode {

    private final Hub bean;

    public HubNode(Hub bean) throws IntrospectionException {
        super(bean, Hub.class);
        this.bean = bean;
    }

    @Override
    public boolean canDestroy() {
        return true;
    }

    @Override
    public Widget getWidget(GraphScene scene) {
        HubWidget hw = new HubWidget(scene);
        hw.setImage(bean.getIcon().getImage());
        hw.setLabel(bean.getName());
        return hw;
    }

    @Override
    public boolean isAcceptable(Widget widget, Point point) {
        if (widget instanceof ChainGraphScene) {
            return true;
        }
        return false;
    }

    @Override
    public Action[] getActions(boolean context) {
        ArrayList<Action> actionList = new ArrayList<>();
        actionList.add(new AbstractAction("Edit") {
            @Override
            public void actionPerformed(ActionEvent e) {
                HubWizardAction wiz = new HubWizardAction();
                wiz.isUpdate(bean);
                wiz.actionPerformed(e);
                createProperties(getBean(), null);
                setSheet(getSheet());
            }
        });
        actionList.add(new AbstractAction("Delete") {

            @Override
            public void actionPerformed(ActionEvent e) {
                HubService service = Lookup.getDefault().lookup(HubService.class);
                service.delete(bean);
            }
        });
        return actionList.toArray(new Action[actionList.size()]);
    }

    @Override
    protected void createProperties(Hub bean, BeanInfo info) {
        Sheet sets = getSheet();
        Sheet.Set generalSet = Sheet.createPropertiesSet();
        generalSet.setName("properties");
        generalSet.setDisplayName("Properties");

        try {
            StupidProperty name = new StupidProperty<>(getBean(), String.class, "name");
            name.addPropertyChangeListener(getListener());
            name.setDisplayName("Name");
            name.setShortDescription("The name of the procedure category.");

            StupidProperty description = new StupidProperty<>(getBean(), String.class, "description");
            description.addPropertyChangeListener(getListener());
            description.setDisplayName("Description");
            description.setShortDescription("An optional short description of the procedure category.");

            StupidProperty iconProp = new StupidProperty(getBean(), Icon.class, "icon");
            iconProp.addPropertyChangeListener(getListener());
            iconProp.setPropertyEditorClass(IconPropertyEditor.HubIconPropertyEditor.class);
            iconProp.setDisplayName("Icon");
            iconProp.setShortDescription("The icon that gets displayed with this Leg-Type.");
            iconProp.setValue("valueIcon", new ImageIcon(getBean().getIcon().getImage()));
            iconProp.setValue("canEditAsText", false);

            StupidProperty locProp = new StupidProperty(getBean(), Location.class, "location");
            locProp.addPropertyChangeListener(getListener());
            locProp.setDisplayName("Location");
            locProp.setShortDescription("The hub's location");
            locProp.setValue("canEditAsText", false);

            StupidProperty eventProp = new StupidProperty(getBean(), List.class, "events");
            eventProp.addPropertyChangeListener(getListener());
            eventProp.setPropertyEditorClass(EventPropertyEditor.class);
            eventProp.setDisplayName("Event");
            eventProp.setShortDescription("All Events stored with this Hub.");
            eventProp.setValue("canEditAsText", false);

            StupidProperty procedureProp = new StupidProperty(getBean(), List.class, "procedures");
            procedureProp.addPropertyChangeListener(getListener());
            procedureProp.setPropertyEditorClass(ProcedurePropertyEditor.class);
            procedureProp.setDisplayName("Procedure");
            procedureProp.setShortDescription("All Procedures stored with this Hub.");
            procedureProp.setValue("canEditAsText", false);

            generalSet.put(name);
            generalSet.put(description);
            generalSet.put(iconProp);
            generalSet.put(locProp);
            generalSet.put(procedureProp);
            generalSet.put(eventProp);
        } catch (NoSuchMethodException ex) {
            ErrorManager.getDefault();
        }

        sets.put(generalSet);
    }

    @Override
    public AbstractBeanNode getDetachedNodeCopy() {
        try {
            HubService service = Lookup.getDefault().lookup(HubService.class);
            Hub detachedHub = service.findById(getBean().getId());
            detachedHub.setId(null);
            return new HubNode(detachedHub);
        } catch (IntrospectionException ex) {
            Exceptions.printStackTrace(ex);
        }
        return null;
    }

    @Override
    Class getServiceClass() {
        return HubService.class;
    }
}
