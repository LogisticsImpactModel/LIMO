package nl.fontys.sofa.limo.view.node.bean;

import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import nl.fontys.sofa.limo.api.service.provider.HubService;
import nl.fontys.sofa.limo.domain.component.Icon;
import nl.fontys.sofa.limo.domain.component.event.Event;
import nl.fontys.sofa.limo.domain.component.hub.Hub;
import nl.fontys.sofa.limo.domain.component.hub.Location;
import nl.fontys.sofa.limo.view.chain.ChainGraphScene;
import nl.fontys.sofa.limo.view.node.WidgetableNode;
import nl.fontys.sofa.limo.view.node.property.StupidProperty;
import nl.fontys.sofa.limo.view.node.property.editor.EventPropertyEditor;
import nl.fontys.sofa.limo.view.node.property.editor.ProcedurePropertyEditor;
import nl.fontys.sofa.limo.view.project.ProjectNode;
import nl.fontys.sofa.limo.view.project.SupplyProject;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;
import nl.fontys.sofa.limo.view.widget.HubWidget;
import nl.fontys.sofa.limo.view.wizard.hub.EventsHubWizard;
import nl.fontys.sofa.limo.view.wizard.hub.HubWizardAction;
import nl.fontys.sofa.limo.view.wizard.hub.ProceduresHubWizard;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;
import org.openide.DialogDisplayer;
import org.openide.ErrorManager;
import org.openide.WizardDescriptor;
import org.openide.nodes.Node;
import org.openide.nodes.Sheet;
import org.openide.util.Exceptions;
import org.openide.util.ImageUtilities;
import org.openide.util.Lookup;

/**
 * View representation of Hub.
 *
 * @author Sebastiaan Heijmann
 */
public class HubNode extends AbstractBeanNode<Hub> implements WidgetableNode, PropertyChangeListener {

    /**
     * constructor for HubNode, adds the bean, it's events and it's procedures
     * to the instancecontent.
     *
     * @param bean the hub to create a node from.
     * @throws IntrospectionException
     */
    public HubNode(Hub bean) throws IntrospectionException {
        super(bean, Hub.class);
        this.bean = bean;
        bean.getEvents().stream().forEach((e) -> {
            ic.add(e);
        });
        bean.getProcedures().stream().forEach((p) -> {
            ic.add(p);
        });
        bean.addPropertyChangeListener(this);
    }

    public Hub getHub() {
        return bean;
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
        return widget instanceof ChainGraphScene;
    }

    @Override
    public Action[] getActions(boolean context) {
        ArrayList<Action> actionList = new ArrayList<>();
        actionList.add(new AbstractAction(LIMOResourceBundle.getString("EDIT")) {
            @Override
            public void actionPerformed(ActionEvent e) {
                HubWizardAction wiz = new HubWizardAction();
                wiz.setUpdate(bean);
                wiz.actionPerformed(e);
                createProperties(getBean(), null);
                setSheet(getSheet());

                setDisplayName(getBean().getName()); //Manually update the displayname
            }
        });
        actionList.add(new AbstractAction(LIMOResourceBundle.getString("DELETE")) {

            @Override
            public void actionPerformed(ActionEvent e) {

                int reply = JOptionPane.showConfirmDialog(null, LIMOResourceBundle.getString("DELETE_QUESTION", bean.getName()), LIMOResourceBundle.getString("ARE_YOU_SURE"), JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE
                );
                if (reply == JOptionPane.YES_OPTION) {
                    HubService service = Lookup.getDefault().lookup(HubService.class);
                    boolean test = service.delete(bean);
                    if (!test) {
                        SupplyProject s = Lookup.getDefault().lookup(SupplyProject.class);
                        SupplyProject project = null;
                        Node n = HubNode.this;
                        while (project == null && n.getParentNode() != null) {
                            if (n.getParentNode() instanceof ProjectNode) {
                                project = ((ProjectNode) n.getParentNode()).getProject();
                                break;
                            } else {
                                n = n.getParentNode();
                            }
                        }

                    }
                }
            }
        });
        actionList.add(new AddEventAction());
        actionList.add(new AddProcedureAction());

        return actionList.toArray(new Action[actionList.size()]);
    }

    @Override
    protected void createProperties(Hub bean, BeanInfo info) {
        Sheet sets = getSheet();
        Sheet.Set set = super.getBaseEntityPropertySheet();

        try {
            StupidProperty locProp = new StupidProperty(getBean(), Location.class, "location");
            locProp.addPropertyChangeListener(getListener());
            locProp.setDisplayName(LIMOResourceBundle.getString("LOCATION"));
            locProp.setShortDescription(LIMOResourceBundle.getString("LOCATION_OF", LIMOResourceBundle.getString("HUB")));
            locProp.setValue("canEditAsText", false);

            StupidProperty eventProp = new StupidProperty(getBean(), List.class, "events");

            eventProp.addPropertyChangeListener(getListener());
            eventProp.setPropertyEditorClass(EventPropertyEditor.class
            );
            eventProp.setDisplayName(LIMOResourceBundle.getString("EVENTS"));
            eventProp.setShortDescription(LIMOResourceBundle.getString("EVENTS_OF", LIMOResourceBundle.getString("HUB")));
            eventProp.setValue("canEditAsText", false);

            StupidProperty procedureProp = new StupidProperty(getBean(), List.class, "procedures");

            procedureProp.addPropertyChangeListener(getListener());
            procedureProp.setPropertyEditorClass(ProcedurePropertyEditor.class
            );
            procedureProp.setDisplayName(LIMOResourceBundle.getString("PROCEDURES"));
            procedureProp.setShortDescription(LIMOResourceBundle.getString("PROCEDURES_OF", LIMOResourceBundle.getString("HUB")));
            procedureProp.setValue("canEditAsText", false);

            set.put(locProp);

            set.put(procedureProp);

            set.put(eventProp);
        } catch (NoSuchMethodException ex) {
            ErrorManager.getDefault();
        }

        set.addPropertyChangeListener((PropertyChangeEvent evt) -> {
            getListener().propertyChange(evt);
        });
        sets.put(set);
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

    @Override
    protected Icon getBeanIcon() {
        return getBean().getIcon();
    }

    @Override
    public Image getIcon(int type) {
        return bean.getIcon().getImage().getScaledInstance(16, 16, BufferedImage.SCALE_SMOOTH);
    }

    @Override
    public void delete() {
        HubService service = Lookup.getDefault().lookup(HubService.class);
        service.delete(bean);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        setDisplayName(bean.getName());

    }

    protected class AddEventAction extends AbstractAction {

        WizardDescriptor wiz;
        Hub originalHub;
        boolean update = false;

        public AddEventAction() {
            putValue(NAME, "Add Event");
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            originalHub = bean;
            List<WizardDescriptor.Panel<WizardDescriptor>> panels = new ArrayList<>();
            panels.add(new EventsHubWizard());
            wiz = new WizardDescriptor(new WizardDescriptor.ArrayIterator<>(panels));
            wiz.setTitleFormat(new MessageFormat("{0}"));
            wiz.putProperty(WizardDescriptor.PROP_IMAGE, ImageUtilities.loadImage("icons/limo_wizard.png", true));
            wiz.putProperty("hub", new Hub(bean));
            wiz.setTitle("Add Event");
            wiz.putProperty("update", update);

            if (DialogDisplayer.getDefault().notify(wiz) == WizardDescriptor.FINISH_OPTION) {
                handleWizardFinishClick(wiz);
            }

        }

        private void handleWizardFinishClick(final WizardDescriptor wiz) {
            HubService hubService = Lookup.getDefault().lookup(HubService.class);
            List<Event> events = originalHub.getEvents();
            Hub hub = (Hub) wiz.getProperty("hub");
            originalHub.deepOverwrite(hub);

            if (update) {
                hubService.update(originalHub);
            } else {
                originalHub.setId(null);
                originalHub.setUniqueIdentifier(UUID.randomUUID().toString());
                originalHub = hubService.insert(originalHub);
            }

        }

    }

    protected class AddProcedureAction extends AbstractAction {

        WizardDescriptor wiz;
        Hub originalHub;
        boolean update = false;

        public AddProcedureAction() {
            putValue(NAME, "Add Procedures");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            originalHub = bean;
            List<WizardDescriptor.Panel<WizardDescriptor>> panels = new ArrayList<>();
            panels.add(new ProceduresHubWizard());
            wiz = new WizardDescriptor(new WizardDescriptor.ArrayIterator<>(panels));
            wiz.setTitleFormat(new MessageFormat("{0}"));
            wiz.putProperty(WizardDescriptor.PROP_IMAGE, ImageUtilities.loadImage("icons/limo_wizard.png", true));
            wiz.putProperty("hub", new Hub(bean));
            wiz.setTitle("Add Event");
            wiz.putProperty("update", update);

            if (DialogDisplayer.getDefault().notify(wiz) == WizardDescriptor.FINISH_OPTION) {
                handleWizardFinishClick(wiz);
            }

        }

        private void handleWizardFinishClick(final WizardDescriptor wiz) {
            HubService hubService = Lookup.getDefault().lookup(HubService.class);
            List<Event> events = originalHub.getEvents();
            Hub hub = (Hub) wiz.getProperty("hub");
            originalHub.deepOverwrite(hub);

            if (update) {
                hubService.update(originalHub);
            } else {
                originalHub.setId(null);
                originalHub.setUniqueIdentifier(UUID.randomUUID().toString());
                originalHub = hubService.insert(originalHub);
            }

        }

    }

}
