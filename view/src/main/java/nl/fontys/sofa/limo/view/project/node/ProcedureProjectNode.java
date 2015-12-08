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
import nl.fontys.sofa.limo.domain.component.procedure.Procedure;
import nl.fontys.sofa.limo.view.node.bean.ProcedureNode;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;

/**
 *
 * @author nilsh
 */
public class ProcedureProjectNode extends ProcedureNode {

    public ProcedureProjectNode(Procedure bean) throws IntrospectionException {
        super(bean);
    }

    @Override
    public Action[] getActions(boolean context) {
        return new Action[]{new AbstractAction(LIMOResourceBundle.getString("EDIT")) {
            @Override
            public void actionPerformed(ActionEvent e) {
                editProcedure();
            }

        }
        };
    }

}
