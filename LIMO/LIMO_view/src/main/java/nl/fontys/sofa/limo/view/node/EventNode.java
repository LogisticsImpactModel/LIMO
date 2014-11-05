package nl.fontys.sofa.limo.view.node;

import java.awt.event.ActionEvent;
import java.beans.IntrospectionException;
import java.util.Arrays;
import javax.swing.AbstractAction;
import javax.swing.Action;
import nl.fontys.sofa.limo.domain.component.event.Event;
import nl.fontys.sofa.limo.view.wizard.event.EventWizardAction;

public class EventNode extends AbstractBeanNode {

    private final Event event;

    public EventNode(Event event) throws IntrospectionException {
        super(event);
        this.event = event;
    }

    @Override
    public boolean canDestroy() {
        return true;
    }

    @Override
    public Action[] getActions(boolean context) {
        Action[] actions = super.getActions(context);
        Action editAction = new AbstractAction("Edit") {
            @Override
            public void actionPerformed(ActionEvent e) {
                EventWizardAction wiz = new EventWizardAction();
                wiz.isUpdate(event);
                wiz.actionPerformed(e);
            }
        };
        Action[] result = Arrays.copyOf(actions, actions.length + 1);
        result[actions.length] = editAction;
        return result;
    }

}
