package nl.fontys.sofa.limo.view.status;

import java.awt.Component;
import nl.fontys.sofa.limo.api.service.status.StatusBarService;
import org.openide.awt.StatusLineElementProvider;
import org.openide.util.Lookup;
import org.openide.util.lookup.ServiceProvider;

/**
 * Tell the Platform that there will be a new Status line element provider.
 * @author Pascal Lindner
 */
@ServiceProvider(service = StatusLineElementProvider.class, position = -10)
public class StatusBar implements StatusLineElementProvider {

    private final Component component
            = Lookup.getDefault().lookup(StatusBarService.class).getComponent();

    @Override
    public Component getStatusLineElement() {
        return component;
    }
}
