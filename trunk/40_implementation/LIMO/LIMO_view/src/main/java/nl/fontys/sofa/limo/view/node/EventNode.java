package nl.fontys.sofa.limo.view.node;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import nl.fontys.sofa.limo.api.service.provider.EventService;
import nl.fontys.sofa.limo.domain.component.event.Event;
import nl.fontys.sofa.limo.domain.component.event.distribution.Distribution;
import nl.fontys.sofa.limo.view.chain.ChainGraphScene;
import nl.fontys.sofa.limo.view.node.property.StupidProperty;
import nl.fontys.sofa.limo.view.node.property.editor.EventPropertyEditor;
import nl.fontys.sofa.limo.view.node.property.editor.ProcedurePropertyEditor;
import nl.fontys.sofa.limo.view.widget.EventWidget;
import nl.fontys.sofa.limo.view.wizard.event.EventWizardAction;
import org.netbeans.api.visual.graph.GraphScene;
import org.netbeans.api.visual.widget.Widget;
import org.openide.ErrorManager;
import org.openide.nodes.Sheet;
import org.openide.util.Lookup;

public class EventNode extends AbstractBeanNode<Event> implements WidgetableNode {

    private final Event bean;

    public EventNode(Event event) throws IntrospectionException {
        super(event, Event.class);
        this.bean = event;
    }

    @Override
    public boolean canDestroy() {
        return true;

    }

    @Override
    public Widget getWidget(GraphScene scene) {
        EventWidget ew = new EventWidget(scene);
        ew.setLabel(bean.getName());
        return ew;
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
                EventWizardAction wiz = new EventWizardAction();
                wiz.setEvent(bean);
                wiz.actionPerformed(e);
            }
        });
        actionList.add(new AbstractAction("Delete") {

            @Override
            public void actionPerformed(ActionEvent e) {
                int reply = JOptionPane.showConfirmDialog(null, "Are you sure to delete " + bean.getName(), "Are you sure...?", JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION) {
                    EventService service = Lookup.getDefault().lookup(EventService.class);
                    service.delete(bean);
                }
            }
        });
        return actionList.toArray(new Action[actionList.size()]);
    }

    @Override
    protected void createProperties(Event bean, BeanInfo info) {
        Sheet sets = getSheet();
        Sheet.Set set = Sheet.createPropertiesSet();
        set.setName("properties");
        set.setDisplayName("Properties");

        try {
            StupidProperty name = new StupidProperty<>(getBean(), String.class, "name");
            name.addPropertyChangeListener(getListener());
            name.setDisplayName("Name");
            name.setShortDescription("The name of the procedure category.");

            StupidProperty description = new StupidProperty<>(getBean(), String.class, "description");
            description.addPropertyChangeListener(getListener());
            description.setDisplayName("Description");
            description.setShortDescription("An optional short description of the procedure category.");

            StupidProperty distributionProp = new StupidProperty(getBean(), Distribution.class, "probability");
            distributionProp.addPropertyChangeListener(getListener());
            distributionProp.setDisplayName("Distribution");
            distributionProp.setShortDescription("The distribution that describes this events chance of occurence.");
            distributionProp.setValue("canEditAsText", false);

            StupidProperty eventProp = new StupidProperty(getBean(), List.class, "events");
            eventProp.addPropertyChangeListener(getListener());
            eventProp.setPropertyEditorClass(EventPropertyEditor.class);
            eventProp.setDisplayName("Events");
            eventProp.setShortDescription("All Events stored with this Hub.");
            eventProp.setValue("canEditAsText", false);

            StupidProperty procedureProp = new StupidProperty(getBean(), List.class, "procedures");
            procedureProp.addPropertyChangeListener(getListener());
            procedureProp.setPropertyEditorClass(ProcedurePropertyEditor.class);
            procedureProp.setDisplayName("Procedures");
            procedureProp.setShortDescription("All Procedures stored with this Hub.");
            procedureProp.setValue("canEditAsText", false);

            set.put(name);
            set.put(description);
            set.put(distributionProp);
            set.put(procedureProp);
            set.put(eventProp);
        } catch (NoSuchMethodException ex) {
            ErrorManager.getDefault();
        }
        sets.put(set);
    }

    @Override
    public AbstractBeanNode getDetachedNodeCopy() {
        throw new UnsupportedOperationException("Copying not supported for event.");
    }

    @Override
    Class getServiceClass() {
        return EventService.class;
    }
}
