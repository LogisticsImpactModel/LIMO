package nl.fontys.sofa.limo.view.node;

import java.awt.event.ActionEvent;
import java.beans.IntrospectionException;
import java.util.Arrays;
import javax.swing.AbstractAction;
import javax.swing.Action;
import nl.fontys.sofa.limo.domain.BaseEntity;
import nl.fontys.sofa.limo.domain.component.hub.Hub;
import nl.fontys.sofa.limo.view.wizard.hub.HubWizardAction;

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
        
        public Action[] getActions(boolean context){
            Action[] actions = super.getActions(context);
            Action editAction = new AbstractAction("Edit") {
                
                @Override
                public void actionPerformed(ActionEvent e) {
                    HubWizardAction wiz = new HubWizardAction();
                    wiz.isUpdate(true, bean);
                    wiz.actionPerformed(e);
                }
            };
            Action[] result = Arrays.copyOf(actions, actions.length + 1);
                    result[actions.length] = editAction;
            return result;
        }

}
