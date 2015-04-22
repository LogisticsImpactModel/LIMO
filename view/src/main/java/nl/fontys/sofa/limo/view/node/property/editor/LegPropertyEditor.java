/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nl.fontys.sofa.limo.view.node.property.editor;

import java.awt.Component;
import java.beans.PropertyEditorSupport;
import nl.fontys.sofa.limo.domain.component.leg.Leg;
import nl.fontys.sofa.limo.view.wizard.leg.normal.NormalLegWizardAction;

/**
 *
 * @author Christina Zenzes
 */
public class LegPropertyEditor extends PropertyEditorSupport {
 @Override
    public String getAsText() {
       
        return ((Leg)getValue()).getName();
    }

    @Override
    public void setAsText(String s) {
    }

    @Override
    public Component getCustomEditor() {
        NormalLegWizardAction wiz = new NormalLegWizardAction(null);
        wiz.update((Leg)getValue());
        wiz.actionPerformed(null);
        return null;
    }

    @Override
    public boolean supportsCustomEditor() {
        return true;
    }


}