/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.view.status;

import java.awt.Component;
import nl.fontys.sofa.limo.api.service.status.StatusBarService;
import org.openide.awt.StatusLineElementProvider;
import org.openide.util.Lookup;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author lnx
 */
@ServiceProvider(service = StatusLineElementProvider.class, position=-10)
public class StatusBar implements StatusLineElementProvider {

    private Component component
            = Lookup.getDefault().lookup(StatusBarService.class).getComponent();

    @Override
    public Component getStatusLineElement() {
        return component;
    }
}