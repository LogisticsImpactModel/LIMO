package nl.fontys.sofa.limo.view.node.bean;

import java.awt.event.ActionEvent;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import nl.fontys.sofa.limo.api.service.provider.ProcedureService;
import nl.fontys.sofa.limo.domain.component.Icon;
import nl.fontys.sofa.limo.domain.component.procedure.Procedure;
import nl.fontys.sofa.limo.domain.component.procedure.TimeType;
import nl.fontys.sofa.limo.domain.component.procedure.value.Value;
import nl.fontys.sofa.limo.view.custom.panel.NameDescriptionDialogInputPanel;
import nl.fontys.sofa.limo.view.node.property.StupidProperty;
import nl.fontys.sofa.limo.view.node.property.editor.TimeTypePropertyEditor;
import nl.fontys.sofa.limo.view.node.property.editor.ValuePropertyEditor;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;
import org.openide.nodes.Sheet;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;

/**
 * View representation of the CostCategory class.
 *
 * @author Sebastiaan Heijmann
 */
public class ProcedureNode extends AbstractBeanNode<Procedure> implements PropertyChangeListener {

    public ProcedureNode(Procedure bean) throws IntrospectionException {
        super(bean, Procedure.class);
        this.bean = bean;
        bean.addPropertyChangeListener(this);
    }

    @Override
    public Action[] getActions(boolean context) {
        ArrayList<Action> actionList = new ArrayList<>();
        actionList.add(new AbstractAction(LIMOResourceBundle.getString("EDIT")) {
            @Override
            public void actionPerformed(ActionEvent e) {
                editProcedure();
            }

        });
        actionList.add(new AbstractAction(LIMOResourceBundle.getString("DELETE")) {

            @Override
            public void actionPerformed(ActionEvent e) {
                deleteProcedure();
            }

        });
        return actionList.toArray(new Action[actionList.size()]);
    }

    /**
     * Open an procedure panel with the procedure for editing.
     */
    protected void editProcedure() {
        NameDescriptionDialogInputPanel inputPane = new NameDescriptionDialogInputPanel();
        inputPane.setBeanName(bean.getName());
        inputPane.setBeanDescription(bean.getDescription());
        int result = JOptionPane.showConfirmDialog(null, inputPane, LIMOResourceBundle.getString("ADD_PROCEDURE"), JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null);
        if (result == JOptionPane.OK_OPTION) {
            bean.setName(inputPane.getNameFieldValue());
            bean.setDescription(inputPane.getDescriptionFieldValue());
            ProcedureService service = Lookup.getDefault().lookup(ProcedureService.class);
            service.update(bean);
            createProperties(getBean(), null);
            setSheet(getSheet());
        }
    }

    /**
     * Delete the procedure form database.
     */
    private void deleteProcedure() {
        int reply = JOptionPane.showConfirmDialog(null, LIMOResourceBundle.getString("DELETE_QUESTION", bean.getName()), LIMOResourceBundle.getString("ARE_YOU_SURE"), JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (reply == JOptionPane.YES_OPTION) {
            ProcedureService service = Lookup.getDefault().lookup(ProcedureService.class);
            service.delete(bean);
        }
    }

    @Override
    protected void createProperties(Procedure bean, BeanInfo info) {
        Sheet sets = getSheet();
        Sheet.Set set = getBaseEntityPropertySheet();

        try {
            StupidProperty catProp = new StupidProperty(getBean(), String.class, "category");
            catProp.addPropertyChangeListener(getListener());
            catProp.setDisplayName("Categories");
            catProp.setShortDescription("Category of this Procedure");
            catProp.setValue("canEditAsText", false);

            StupidProperty costProp = new StupidProperty(getBean(), Value.class, "cost");
            costProp.addPropertyChangeListener(getListener());
            costProp.setPropertyEditorClass(ValuePropertyEditor.class);
            costProp.setDisplayName("Costs");
            costProp.setShortDescription("Costs of this Procedure");
            costProp.setValue("canEditAsText", false);

            StupidProperty timeProp = new StupidProperty(getBean(), Value.class, "time");
            timeProp.addPropertyChangeListener(getListener());
            timeProp.setPropertyEditorClass(ValuePropertyEditor.class);
            timeProp.setDisplayName("Times");
            timeProp.setShortDescription("Times of this Procedure");
            timeProp.setValue("canEditAsText", false);

            StupidProperty cotwoProp = new StupidProperty(getBean(), Value.class, "cotwo");
            cotwoProp.addPropertyChangeListener(getListener());
            cotwoProp.setPropertyEditorClass(ValuePropertyEditor.class);
            cotwoProp.setDisplayName("CO2");
            cotwoProp.setShortDescription("CO2 of this Procedure");
            cotwoProp.setValue("canEditAsText", false);

            StupidProperty timeTypeProp = new StupidProperty(getBean(), TimeType.class, "timeType");
            timeTypeProp.addPropertyChangeListener(getListener());
            timeTypeProp.setPropertyEditorClass(TimeTypePropertyEditor.class);
            timeTypeProp.setDisplayName("TimeType");
            timeTypeProp.setShortDescription("TimeType of this Procedure");
            timeTypeProp.setValue("canEditAsText", false);

            set.put(catProp);
            set.put(costProp);
            set.put(timeProp);
            set.put(cotwoProp);
            set.put(timeTypeProp);
        } catch (NoSuchMethodException ex) {
            Exceptions.printStackTrace(ex);
        }
        sets.put(set);
    }

    @Override
    public AbstractBeanNode getDetachedNodeCopy() {
        throw new UnsupportedOperationException(LIMOResourceBundle.getString("COPY_NOT_SUPPORTED"));
    }

    @Override
    Class getServiceClass() {
        return ProcedureService.class;
    }

    @Override
    protected Icon getBeanIcon() {
        throw new UnsupportedOperationException(LIMOResourceBundle.getString("NOT_SUPPORTED"));
    }

    @Override
    public void delete() {
        ProcedureService service = Lookup.getDefault().lookup(ProcedureService.class);
        service.delete(bean);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        setDisplayName(bean.getName());
    }

    public Procedure getProcedure() {
        return bean;
    }
}
