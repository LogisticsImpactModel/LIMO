package nl.fontys.sofa.limo.view.widget;

import java.awt.Image;
import java.beans.BeanInfo;
import nl.fontys.sofa.limo.domain.component.procedure.ProcedureCategory;
import nl.fontys.sofa.limo.view.util.IconUtil;
import org.netbeans.api.visual.widget.ImageWidget;
import org.netbeans.api.visual.widget.Scene;

/**
 * ProceduresWidget is responsible for displaying the procedures inside the
 * GraphScene.
 *
 * @author Sebastiaan Heijmann
 */
public class ProcedureWidget extends ImageWidget {

    public ProcedureWidget(Scene scene) {
        super(scene);
        Image icon = IconUtil.getIcon(ProcedureCategory.class, BeanInfo.ICON_COLOR_32x32);
        setImage(icon);
    }
}
