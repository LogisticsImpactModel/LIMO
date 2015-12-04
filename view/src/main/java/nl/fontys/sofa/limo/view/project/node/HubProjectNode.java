/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.view.project.node;

import java.awt.event.ActionEvent;
import java.beans.IntrospectionException;
import java.util.ArrayList;
import javax.swing.AbstractAction;
import javax.swing.Action;
import nl.fontys.sofa.limo.domain.component.hub.Hub;
import nl.fontys.sofa.limo.view.node.bean.HubNode;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;
import nl.fontys.sofa.limo.view.wizard.hub.HubWizardAction;

/**
 *
 * @author nilsh
 */
public class HubProjectNode extends HubNode {

    public HubProjectNode(Hub bean) throws IntrospectionException {
        super(bean);
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
        actionList.add(new AddEventAction());
        actionList.add(new AddProcedureAction());

        return actionList.toArray(new Action[actionList.size()]);

    }

}
