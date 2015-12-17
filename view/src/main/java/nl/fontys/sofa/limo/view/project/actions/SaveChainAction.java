/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.view.project.actions;

import java.awt.event.ActionEvent;
import java.io.IOException;
import javax.swing.AbstractAction;
import nl.fontys.sofa.limo.view.topcomponent.ChainLoaderTopComponent;
import org.netbeans.api.actions.Savable;
import org.openide.util.Exceptions;
import org.openide.util.Lookup.Result;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;

/**
 *
 * @author nilsh
 */
public class SaveChainAction extends AbstractAction implements LookupListener {

    ChainLoaderTopComponent tc;
    Result<Savable> result;

    public SaveChainAction() {
        putValue(NAME, "Save chain");
    }

    public void setTopComponent(ChainLoaderTopComponent tc) {
        this.tc = tc;
        if (tc != null) {
            result = tc.getLookup().lookupResult(Savable.class);
            result.addLookupListener(this);
        } else {
            result = null;
        }

    }

    @Override
    public boolean isEnabled() {
        return result != null && result.allInstances().size() > 0;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        result.allInstances().stream().forEach((s) -> {
            try {
                s.save();
            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
            }
        });

    }

    @Override
    public void resultChanged(LookupEvent le) {

        System.out.println(result.allInstances().size());

    }

}
