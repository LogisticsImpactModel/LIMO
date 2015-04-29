/*
 *  Created by Mike de Roode
 */
package nl.fontys.sofa.limo.view.node.property.editor;

import java.awt.Component;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyEditorSupport;
import java.util.Map;
import nl.fontys.sofa.limo.domain.component.leg.Leg;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;
import nl.fontys.sofa.limo.view.wizard.leg.multimode.MultimodeLegTablePanel;

/**
 * This class is the Property Editor for the {@code Map<Leg, Double> }attribute of a
 * MultiModeLeg. It enables you to change the legs with the build in property
 * window of NetBeans Platform.
 *
 * @author Mike
 */
public class MultiModeLegPropertyEditor extends PropertyEditorSupport implements PropertyChangeListener {

    private MultiModeLegTableEditor editor;

    @Override
    public String getAsText() {
        Map<Leg, Double> legs = (Map<Leg, Double>) getValue();
        if (legs == null) {
            return LIMOResourceBundle.getString("NUMBER_OF", LIMOResourceBundle.getString("LEGS"), 0);
        }
        return LIMOResourceBundle.getString("NUMBER_OF", LIMOResourceBundle.getString("LEGS"), legs.size());
    }

    @Override
    public void setAsText(String s) {
    }

    @Override
    public boolean supportsCustomEditor() {
        return true;
    }

    @Override
    public Component getCustomEditor() {
        if (editor == null) {
            editor = new MultiModeLegTableEditor((Map<Leg, Double>) getValue());
            editor.addPropertyChangeListener(this);
        }
        return editor;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        setValue(((MultiModeLegTableEditor) getCustomEditor()).getLegModel().getMap());
    }

    private class MultiModeLegTableEditor extends MultimodeLegTablePanel {

        public MultiModeLegTableEditor(Map<Leg, Double> legs) {
            super();
            getLegModel().addLegs(legs);
        }
    }
}
