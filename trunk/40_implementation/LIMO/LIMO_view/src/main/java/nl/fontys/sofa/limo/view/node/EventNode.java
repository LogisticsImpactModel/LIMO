package nl.fontys.sofa.limo.view.node;

import java.awt.event.ActionEvent;
import java.beans.IntrospectionException;
import java.util.ArrayList;
import javax.swing.AbstractAction;
import javax.swing.Action;
import nl.fontys.sofa.limo.api.service.provider.EventService;
import nl.fontys.sofa.limo.view.wizard.event.EventWizardAction;
import nl.fontys.sofa.limo.domain.component.event.Event;
import org.openide.util.Lookup;

public class EventNode extends AbstractBeanNode {

    private final Event event;

    public EventNode(Event event) throws IntrospectionException {
        super(event, Event.class);
        this.event = event;
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
                EventWizardAction wiz = new EventWizardAction();
                wiz.setEvent(event);
                wiz.actionPerformed(e);
            }
        });
        actionList.add(new AbstractAction("Delete") {

            @Override
            public void actionPerformed(ActionEvent e) {
                EventService service = Lookup.getDefault().lookup(EventService.class);
                service.delete(event);
            }
        });
        return actionList.toArray(new Action[actionList.size()]);
    }

}
