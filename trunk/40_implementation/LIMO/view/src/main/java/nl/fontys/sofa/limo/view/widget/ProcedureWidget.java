/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.view.widget;

import java.awt.Image;
import java.beans.BeanInfo;
import nl.fontys.sofa.limo.domain.component.procedure.ProcedureCategory;
import nl.fontys.sofa.limo.view.util.IconUtil;
import org.netbeans.api.visual.widget.ImageWidget;
import org.netbeans.api.visual.widget.Scene;

/**
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
