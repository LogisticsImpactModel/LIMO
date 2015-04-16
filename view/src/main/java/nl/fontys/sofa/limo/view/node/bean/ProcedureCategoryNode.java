package nl.fontys.sofa.limo.view.node.bean;

import java.awt.event.ActionEvent;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.util.ArrayList;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import nl.fontys.sofa.limo.api.service.provider.ProcedureCategoryService;
import nl.fontys.sofa.limo.domain.component.Icon;
import nl.fontys.sofa.limo.domain.component.procedure.ProcedureCategory;
import nl.fontys.sofa.limo.view.custom.panel.NameDescriptionDialogInputPanel;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;
import org.openide.nodes.Sheet;
import org.openide.util.Lookup;

/**
 * View representation of the CostCategory class.
 *
 * @author Sebastiaan Heijmann
 */
public class ProcedureCategoryNode extends AbstractBeanNode<ProcedureCategory> {

    public ProcedureCategoryNode(ProcedureCategory bean) throws IntrospectionException {
        super(bean, ProcedureCategory.class);
        this.bean = bean;
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
    private void editProcedure() {
        NameDescriptionDialogInputPanel inputPane = new NameDescriptionDialogInputPanel();
        inputPane.setBeanName(bean.getName());
        inputPane.setBeanDescription(bean.getDescription());
        int result = JOptionPane.showConfirmDialog(null, inputPane, LIMOResourceBundle.getString("ADD_PROCEDURE_CATEGORY"), JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null);
        if (result == JOptionPane.OK_OPTION) {
            bean.setName(inputPane.getNameFieldValue());
            bean.setDescription(inputPane.getDescriptionFieldValue());
            ProcedureCategoryService service = Lookup.getDefault().lookup(ProcedureCategoryService.class);
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
            ProcedureCategoryService service = Lookup.getDefault().lookup(ProcedureCategoryService.class);
            service.delete(bean);
        }
    }

    @Override
    protected void createProperties(ProcedureCategory bean, BeanInfo info) {
        Sheet sets = getSheet();
        Sheet.Set set = getNameDescriptionPropertySheet();
        sets.put(set);
    }

    @Override
    public AbstractBeanNode getDetachedNodeCopy() {
        throw new UnsupportedOperationException(LIMOResourceBundle.getString("COPY_NOT_SUPPORTED"));
    }

    @Override
    Class getServiceClass() {
        return ProcedureCategoryService.class;
    }

    @Override
    protected Icon getBeanIcon() {
        throw new UnsupportedOperationException(LIMOResourceBundle.getString("NOT_SUPPORTED"));
    }
}
