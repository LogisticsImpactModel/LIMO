package nl.fontys.sofa.limo.view.widget;

import java.awt.Image;
import java.beans.BeanInfo;
import nl.fontys.sofa.limo.domain.component.event.Event;
import nl.fontys.sofa.limo.view.util.IconUtil;
import org.netbeans.api.visual.widget.ImageWidget;
import org.netbeans.api.visual.widget.Scene;

/**
 * ProceduresWidget is responsible for displaying the procedures inside the
 * GrapgScene.
 *
 * @author Sebastiaan Heijmann
 */
public class EventsWidget extends ImageWidget {

    public EventsWidget(Scene scene) {
        super(scene);

        Image icon = IconUtil.getIcon(Event.class, BeanInfo.ICON_COLOR_32x32);
        setImage(icon);
    }

}
