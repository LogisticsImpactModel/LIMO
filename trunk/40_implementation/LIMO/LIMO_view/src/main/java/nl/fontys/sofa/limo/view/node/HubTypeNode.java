package nl.fontys.sofa.limo.view.node;

import java.awt.event.ActionEvent;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import nl.fontys.sofa.limo.api.service.provider.HubTypeService;
import nl.fontys.sofa.limo.domain.component.Icon;
import nl.fontys.sofa.limo.domain.component.type.HubType;
import nl.fontys.sofa.limo.view.node.property.StupidProperty;
import nl.fontys.sofa.limo.view.node.property.editor.EventPropertyEditor;
import nl.fontys.sofa.limo.view.node.property.editor.IconPropertyEditor;
import nl.fontys.sofa.limo.view.node.property.editor.ProcedurePropertyEditor;
import nl.fontys.sofa.limo.view.wizard.types.hub.HubTypeWizardAction;
import org.openide.ErrorManager;
import org.openide.nodes.Sheet;
import org.openide.util.Lookup;

/**
 * View representation of HubType.
 *
 * @author Sebastiaan Heijmann
 */
public class HubTypeNode extends AbstractBeanNode<HubType> {

    private final HubType bean;

    public HubTypeNode(HubType bean) throws IntrospectionException {
        super(bean, HubType.class);
        this.bean = bean;
    }

    @Override
    public boolean canDestroy() {
        return true;
    }

    @Override
    public Action[] getActions(boolean context) {
        ArrayList<Action> actionList = new ArrayList<>();
        actionList.add(new AbstractAction("Edit") {
            @Override
            public void actionPerformed(ActionEvent e) {
                HubTypeWizardAction wiz = new HubTypeWizardAction();
                wiz.isUpdate(bean);
                wiz.actionPerformed(e);
                createProperties(getBean(), null);
                setSheet(getSheet());
            }
        });
        actionList.add(new AbstractAction("Delete") {

            @Override
            public void actionPerformed(ActionEvent e) {
                HubTypeService service = Lookup.getDefault().lookup(HubTypeService.class);
                service.delete(bean);
            }
        });
        return actionList.toArray(new Action[actionList.size()]);
    }

    @Override
    protected void createProperties(HubType bean, BeanInfo info) {
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

            StupidProperty iconProp = new StupidProperty(getBean(), Icon.class, "icon");
            iconProp.addPropertyChangeListener(getListener());
            iconProp.setPropertyEditorClass(IconPropertyEditor.HubIconPropertyEditor.class);
            iconProp.setDisplayName("Icon");
            iconProp.setShortDescription("The icon that gets displayed with this Leg-Type.");
            iconProp.setValue("valueIcon", new ImageIcon(getBean().getIcon().getImage()));
            iconProp.setValue("canEditAsText", false);

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

            set.put(name);
            set.put(description);
            set.put(iconProp);
            set.put(procedureProp);
            set.put(eventProp);
        } catch (NoSuchMethodException ex) {
            ErrorManager.getDefault();
        }
        sets.put(set);
    }

    @Override
    public AbstractBeanNode getDetachedNodeCopy() {
        throw new UnsupportedOperationException("Copying not supported for hubtype.");
    }

    @Override
    Class getServiceClass() {
        return HubTypeService.class;
    }

}
