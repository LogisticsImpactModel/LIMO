package nl.fontys.sofa.limo.view.node.bean;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import nl.fontys.sofa.limo.api.service.provider.HubService;
import nl.fontys.sofa.limo.domain.component.Icon;
import nl.fontys.sofa.limo.domain.component.event.Event;
import nl.fontys.sofa.limo.domain.component.hub.Hub;
import nl.fontys.sofa.limo.domain.component.hub.Location;
import nl.fontys.sofa.limo.domain.component.procedure.Procedure;
import nl.fontys.sofa.limo.view.chain.ChainGraphScene;
import nl.fontys.sofa.limo.view.node.WidgetableNode;
import nl.fontys.sofa.limo.view.node.property.StupidProperty;
import nl.fontys.sofa.limo.view.node.property.editor.EventPropertyEditor;
import nl.fontys.sofa.limo.view.node.property.editor.IconPropertyEditor;
import nl.fontys.sofa.limo.view.node.property.editor.ProcedurePropertyEditor;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;
import nl.fontys.sofa.limo.view.widget.HubWidget;
import nl.fontys.sofa.limo.view.wizard.hub.HubWizardAction;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.Scene;
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

    public HubNode(Hub bean) throws IntrospectionException {
        super(bean, Hub.class);
        this.bean = bean;
        for (Event e : bean.getEvents()) {
            ic.add(e);
        }
        for (Procedure p : bean.getProcedures()) {
            ic.add(p);
        }
    }
    
    @Override
    public boolean canDestroy() {
        return true;
    }
    
    @Override
    public Widget getWidget(Scene scene) {
        try {
            HubWidget hw = new HubWidget(scene, this);
            return hw;
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
            return new LabelWidget(scene, LIMOResourceBundle.getString("UNKNOWN_WIDGET"));
        }
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
        actionList.add(new AbstractAction(LIMOResourceBundle.getString("EDIT")) {
            @Override
            public void actionPerformed(ActionEvent e) {
                HubWizardAction wiz = new HubWizardAction();
                wiz.isUpdate(bean);
                wiz.actionPerformed(e);
                createProperties(getBean(), null);
                setSheet(getSheet());
            }
        });
        actionList.add(new AbstractAction(LIMOResourceBundle.getString("DELETE")) {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                int reply = JOptionPane.showConfirmDialog(null, LIMOResourceBundle.getString("DELETE_QUESTION", bean.getName()), LIMOResourceBundle.getString("ARE_YOU_SURE"), JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE
                );
                if (reply == JOptionPane.YES_OPTION) {
                    HubService service = Lookup.getDefault().lookup(HubService.class);
                    service.delete(bean);
                }
            }
        });
        return actionList.toArray(new Action[actionList.size()]);
    }
    
    @Override
    protected void createProperties(Hub bean, BeanInfo info) {
        Sheet sets = getSheet();
        Sheet.Set generalSet = Sheet.createPropertiesSet();
        generalSet.setName("properties");
        generalSet.setDisplayName(LIMOResourceBundle.getString("PROPERTIES"));
        
        try {
            StupidProperty name = new StupidProperty<>(getBean(), String.class, "name");
            name.addPropertyChangeListener(getListener());
            name.setDisplayName(LIMOResourceBundle.getString("NAME"));
            name.setShortDescription(LIMOResourceBundle.getString("NAME_OF", "Hub"));
            
            StupidProperty description = new StupidProperty<>(getBean(), String.class, "description");
            description.addPropertyChangeListener(getListener());
            description.setDisplayName(LIMOResourceBundle.getString("DESCRIPTION"));
            description.setShortDescription(LIMOResourceBundle.getString("DESCRIPTION_OF", "Hub"));
            
            StupidProperty iconProp = new StupidProperty(getBean(), Icon.class, "icon");
            iconProp.addPropertyChangeListener(getListener());
            iconProp.setPropertyEditorClass(IconPropertyEditor.HubIconPropertyEditor.class);
            iconProp.setDisplayName(LIMOResourceBundle.getString("ICON"));
            iconProp.setShortDescription(LIMOResourceBundle.getString("ICON_OF", "Hub"));
            iconProp.setValue("valueIcon", new ImageIcon(getBean().getIcon().getImage()));
            iconProp.setValue("canEditAsText", false);
            
            StupidProperty locProp = new StupidProperty(getBean(), Location.class, "location");
            locProp.addPropertyChangeListener(getListener());
            locProp.setDisplayName(LIMOResourceBundle.getString("LOCATION"));
            locProp.setShortDescription(LIMOResourceBundle.getString("LOCATION_OF", "Hub"));
            locProp.setValue("canEditAsText", false);
            
            StupidProperty eventProp = new StupidProperty(getBean(), List.class, "events");
            eventProp.addPropertyChangeListener(getListener());
            eventProp.setPropertyEditorClass(EventPropertyEditor.class);
            eventProp.setDisplayName(LIMOResourceBundle.getString("EVENTS"));
            eventProp.setShortDescription(LIMOResourceBundle.getString("EVENTS_OF", "Hub"));
            eventProp.setValue("canEditAsText", false);
            
            StupidProperty procedureProp = new StupidProperty(getBean(), List.class, "procedures");
            procedureProp.addPropertyChangeListener(getListener());
            procedureProp.setPropertyEditorClass(ProcedurePropertyEditor.class);
            procedureProp.setDisplayName(LIMOResourceBundle.getString("PROCEDURES"));
            procedureProp.setShortDescription(LIMOResourceBundle.getString("PROCEDURES_OF", "Hub"));
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
