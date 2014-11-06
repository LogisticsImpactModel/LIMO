package nl.fontys.sofa.limo.view.node;

import java.awt.event.ActionEvent;
import java.beans.IntrospectionException;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.AbstractAction;
import javax.swing.Action;
import nl.fontys.sofa.limo.api.service.provider.HubService;
import nl.fontys.sofa.limo.domain.BaseEntity;
import nl.fontys.sofa.limo.domain.component.hub.Hub;
import nl.fontys.sofa.limo.view.wizard.hub.HubWizardAction;
import org.openide.actions.EditAction;
import org.openide.util.Lookup;

/**
 * View representation of Hub.
 *
 * @author Sebastiaan Heijmann
 */
public class HubNode extends AbstractBeanNode{

        private Hub bean;
	public HubNode(Hub bean) throws IntrospectionException {
		super(bean, Hub.class);
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
                HubWizardAction wiz = new HubWizardAction();
                wiz.isUpdate(true,bean);
                wiz.actionPerformed(e);
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

}
