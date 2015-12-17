/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.view.project.node;

import java.awt.event.ActionEvent;
import java.beans.IntrospectionException;
import javax.swing.AbstractAction;
import javax.swing.Action;
import nl.fontys.sofa.limo.domain.component.event.Event;
import nl.fontys.sofa.limo.view.node.bean.EventNode;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;
import nl.fontys.sofa.limo.view.wizard.event.EventWizardAction;

/**
 *
 * @author nilsh
 */
public class EventProjectNode extends EventNode {

    public EventProjectNode(Event event) throws IntrospectionException {
        super(event);
    }

    @Override
    public Action[] getActions(boolean context) {
        return new Action[]{
            new AbstractAction(LIMOResourceBundle.getString("EDIT")) {
                @Override
                public void actionPerformed(ActionEvent e) {
                    EventWizardAction wiz = new EventWizardAction();
                    wiz.setEvent(bean);
                    wiz.actionPerformed(null);
                    createProperties(getBean(), null);
                    setSheet(getSheet());
                }
            }
        };
    }
}
