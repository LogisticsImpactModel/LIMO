/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.view.project.actions.util;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.openide.windows.TopComponent;

/**
 *
 * @author nilsh
 */
public class CloseChainAction extends AbstractAction {

    TopComponent tc = null;

    public CloseChainAction() {
        putValue(NAME, "Close chain");
    }

    @Override
    public boolean isEnabled() {
        return tc != null;
    }

    public void setTopComponent(TopComponent tc) {
        this.tc = tc;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        tc.close();
    }

}
