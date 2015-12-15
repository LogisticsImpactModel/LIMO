/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.view.project.actions.util;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.AbstractAction;
import nl.fontys.sofa.limo.domain.component.SupplyChain;
import nl.fontys.sofa.limo.view.project.actions.SaveChainAction;
import nl.fontys.sofa.limo.view.topcomponent.ChainLoaderTopComponent;
import org.openide.windows.TopComponent;

/**
 *
 * @author nilsh
 */
public class OpenChainAction extends AbstractAction {

    TopComponent tc;
    SupplyChain chain;
    CloseChainAction close;
    private final SaveChainAction save;

    public OpenChainAction(SupplyChain chain, CloseChainAction close, SaveChainAction save) {
        this.chain = chain;
        this.close = close;
        this.save = save;
        putValue(NAME, "Open chain");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (tc == null) {
            ChainLoaderTopComponent chainLoaderTopComponent = new ChainLoaderTopComponent(chain);
            chainLoaderTopComponent.open();
            chainLoaderTopComponent.requestActive();
            tc = chainLoaderTopComponent;
            close.setTopComponent(tc);
            save.setTopComponent(chainLoaderTopComponent);
            tc.addPropertyChangeListener(new PropertyChangeListener() {
                @Override
                public void propertyChange(PropertyChangeEvent evt) {
                    if (evt.getPropertyName().equals("ancestor")) {
                        tc = null;
                        close.setTopComponent(null);
                        save.setTopComponent(null);
                    }
                }
            });

        } else {
            tc.requestActive();
        }
    }

}
