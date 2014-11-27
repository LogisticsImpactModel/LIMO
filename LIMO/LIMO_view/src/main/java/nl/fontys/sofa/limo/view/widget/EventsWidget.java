package nl.fontys.sofa.limo.view.widget;

import java.awt.Component;
import org.netbeans.api.visual.widget.ComponentWidget;
import org.netbeans.api.visual.widget.Scene;
import org.openide.explorer.ExplorerManager;

/**
 * ProceduresWidget is responsible for displaying the procedures inside the
 * GrapgScene.
 *
 * @author Sebastiaan Heijmann
 */
public class EventsWidget extends ComponentWidget implements
        ExplorerManager.Provider {

    private ExplorerManager em = new ExplorerManager();

    public EventsWidget(Scene scene, Component component) {
        super(scene, component);
    }

    @Override
    public ExplorerManager getExplorerManager() {
        return em;
    }
}
