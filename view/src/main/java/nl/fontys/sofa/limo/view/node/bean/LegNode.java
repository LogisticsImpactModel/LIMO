package nl.fontys.sofa.limo.view.node.bean;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import nl.fontys.sofa.limo.domain.component.Icon;
import nl.fontys.sofa.limo.domain.component.hub.Hub;
import nl.fontys.sofa.limo.domain.component.leg.Leg;
import nl.fontys.sofa.limo.domain.component.leg.MultiModeLeg;
import nl.fontys.sofa.limo.domain.component.leg.ScheduledLeg;
import nl.fontys.sofa.limo.view.node.property.StupidProperty;
import nl.fontys.sofa.limo.view.node.property.editor.EventPropertyEditor;
import nl.fontys.sofa.limo.view.node.property.editor.ProcedurePropertyEditor;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;
import nl.fontys.sofa.limo.view.wizard.leg.multimode.MultimodeLegWizardAction;
import nl.fontys.sofa.limo.view.wizard.leg.normal.EventLegTypeWizard;
import nl.fontys.sofa.limo.view.wizard.leg.normal.NormalLegWizardAction;
import nl.fontys.sofa.limo.view.wizard.leg.normal.ProceduresLegTypeWizard;
import nl.fontys.sofa.limo.view.wizard.leg.scheduled.ScheduledLegWizardAction;
import org.openide.DialogDisplayer;
import org.openide.ErrorManager;
import org.openide.WizardDescriptor;
import org.openide.nodes.Sheet;
import org.openide.util.ImageUtilities;

/**
 * View representation of a Leg. This class is used to display a NormalLeg and
 * is used by ScheduledLegNode and MultiModeLegNode.
 *
 * @author Sebastiaan Heijmann
 */
public class LegNode extends AbstractBeanNode<Leg> implements PropertyChangeListener {

    /**
     * /**
     * constructor for LegNode, calls the second constructor with the correct
     * class name.
     *
     * @param bean
     * @throws IntrospectionException
     */
    public LegNode(Leg bean) throws IntrospectionException {
        this(bean, Leg.class);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        String s = getBean().getName();
        fireNameChange(s, s);

    }

    public Leg getLeg() {
        return bean;
    }

    /**
     * constructor for LegNode, adds the bean to the instancecontent.
     *
     * @param bean the base entity
     * @param entityClass the class name of the entity
     * @throws IntrospectionException
     */
    public LegNode(Leg bean, Class entityClass) throws IntrospectionException {
        super(bean, entityClass);
        this.bean = bean;
        this.bean.addPropertyChangeListener(this);

    }

    @Override
    protected PropertyChangeListener getListener() {
        return (PropertyChangeEvent evt) -> {
            firePropertyChange(evt.getPropertyName(), evt.getOldValue(), evt.getNewValue());
            switch (evt.getPropertyName()) {
                case "name":
                    setDisplayName((String) evt.getNewValue());
                    break;
                case "description":
                    setShortDescription((String) evt.getNewValue());
                    break;
                case "icon":
                    createProperties(getBean(), null);
                    setSheet(getSheet());
                    break;
            }
        };
    }

    /* @Override
    public boolean canDestroy() {
        return true;
    }*/
    @Override
    public AbstractBeanNode getDetachedNodeCopy() {
        throw new UnsupportedOperationException(LIMOResourceBundle.getString("COPY_NOT_SUPPORTED"));
    }

    @Override
    public String getHtmlDisplayName() {

        String name = getBean().getName();
        Leg l = getBean();

        Hub preHub = l.getPrevious();
        Hub nextHub = l.getNext();
        if (preHub != null && nextHub != null) {

            return "<font color='!textText'>" + name + "</font>"
                    + " <font color='!controlShadow'><i>" + preHub.getName() + " -> " + nextHub.getName() + "</i></font>"; //To change body of generated methods, choose Tools | Templates.
        } else {
            return name;
        }
    }

    @Override
    Class getServiceClass() {
        return null;
    }

    @Override
    protected Icon getBeanIcon() {
        return getBean().getIcon();
    }

    public void refresh() {
        createProperties(bean, null);
    }

    @Override
    public Image getIcon(int type) {
        return getBean().getIcon().getImage().getScaledInstance(16, 16, BufferedImage.SCALE_SMOOTH);
    }

    @Override
    protected void createProperties(Leg bean, BeanInfo info) {
        Sheet sets = getSheet();
        Sheet.Set set = super.getBaseEntityPropertySheet();

        try {
            StupidProperty eventProp = new StupidProperty(getBean(), List.class, "events");
            eventProp.addPropertyChangeListener(getListener());
            eventProp
                    .setPropertyEditorClass(EventPropertyEditor.class
                    );
            eventProp.setDisplayName(LIMOResourceBundle.getString("EVENTS"));
            eventProp.setShortDescription(LIMOResourceBundle.getString("EVENTS_OF", LIMOResourceBundle.getString("HUB")));
            eventProp.setValue("canEditAsText", false);

            StupidProperty procedureProp = new StupidProperty(getBean(), List.class, "procedures");
            procedureProp.addPropertyChangeListener(getListener());
            procedureProp
                    .setPropertyEditorClass(ProcedurePropertyEditor.class
                    );
            procedureProp.setDisplayName(LIMOResourceBundle.getString("PROCEDURES"));
            procedureProp.setShortDescription(LIMOResourceBundle.getString("PROCEDURES_OF", LIMOResourceBundle.getString("HUB")));
            procedureProp.setValue("canEditAsText", false);
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
    public Action[] getActions(boolean context) {
        return new Action[]{new AbstractAction(LIMOResourceBundle.getString("EDIT")) {
            @Override
            public void actionPerformed(ActionEvent e) {
                Leg l = getBean();
                if (l instanceof ScheduledLeg) {
                    ScheduledLegWizardAction wiz = new ScheduledLegWizardAction();
                    wiz.setUpdate((ScheduledLeg) getBean());
                    wiz.actionPerformed(e);
                    createProperties(getBean(), null);
                    setSheet(getSheet());
                    setDisplayName(getBean().getName()); //Manually update the displayname

                } else if (false) {
                    MultimodeLegWizardAction wiz = new MultimodeLegWizardAction();
                    wiz.setUpdate((MultiModeLeg) getBean());
                    wiz.actionPerformed(e);
                    createProperties(getBean(), null);
                    setSheet(getSheet());
                    setDisplayName(getBean().getName()); //Manually update the displayname

                } else if (l instanceof Leg) {

                    NormalLegWizardAction wiz = new NormalLegWizardAction();

                    wiz.setUpdate(getBean());
                    wiz.actionPerformed(e);
                    createProperties(getBean(), null);
                    setSheet(getSheet());
                    setDisplayName(getBean().getName()); //Manually update the displayname
                } else {

                }
            }
        },
            new AddEventAction(), new AddProcedureAction()
        };
    }

    @Override
    public void delete() {
        /**
         * Delete is empty because the LegNodes aren't saved in the database and
         * therefore didn't need to be deleted.
         */
    }

    protected class AddEventAction extends AbstractAction {

        public AddEventAction() {
            putValue(NAME, "Add Event");
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            List<WizardDescriptor.Panel<WizardDescriptor>> panels = new ArrayList<>();
            panels.add(new EventLegTypeWizard());
            WizardDescriptor wiz = new WizardDescriptor(new WizardDescriptor.ArrayIterator<>(panels));
            wiz.putProperty(WizardDescriptor.PROP_IMAGE, ImageUtilities.loadImage("icons/limo_wizard.png", true));

            wiz.putProperty("leg", new Leg(getBean()));
            wiz.putProperty("original_leg", getBean());

            wiz.setTitleFormat(new MessageFormat("{0}"));
            wiz.setTitle(LIMOResourceBundle.getString("CREATE_NORMAL_LEG"));

            if (DialogDisplayer.getDefault().notify(wiz) == WizardDescriptor.FINISH_OPTION) {
                getBean().deepOverwrite((Leg) wiz.getProperty("leg"));
            }
        }
    }

    protected class AddProcedureAction extends AbstractAction {

        public AddProcedureAction() {
            putValue(NAME, "Add procedure");
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            List<WizardDescriptor.Panel<WizardDescriptor>> panels = new ArrayList<>();
            panels.add(new ProceduresLegTypeWizard());
            WizardDescriptor wiz = new WizardDescriptor(new WizardDescriptor.ArrayIterator<>(panels));
            wiz.putProperty(WizardDescriptor.PROP_IMAGE, ImageUtilities.loadImage("icons/limo_wizard.png", true));

            wiz.putProperty("leg", new Leg(getBean()));
            wiz.putProperty("original_leg", getBean());

            wiz.setTitleFormat(new MessageFormat("{0}"));
            wiz.setTitle(LIMOResourceBundle.getString("CREATE_NORMAL_LEG"));

            if (DialogDisplayer.getDefault().notify(wiz) == WizardDescriptor.FINISH_OPTION) {
                getBean().deepOverwrite((Leg) wiz.getProperty("leg"));
            }
        }

    }
}
