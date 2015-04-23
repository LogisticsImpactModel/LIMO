/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.view.node.property.editor;

import java.awt.Component;
import java.beans.PropertyEditorSupport;
import java.util.List;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import nl.fontys.sofa.limo.view.custom.panel.AcceptanceTimePanel;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;

/**
 * AcceptanceTimePropertyEditor creats an custome editor for acceptance times.
 * In background it used the AcceptanceTimePanel for editing the times.
 *
 * @author Christina Zenzes
 */
public class AcceptanceTimesPropertyEditor extends PropertyEditorSupport {

    @Override
    public String getAsText() {
        List<Long> events = (List<Long>) getValue();
        if (events == null) {
            return LIMOResourceBundle.getString("NUMBER_OF", LIMOResourceBundle.getString("ACCEPTANCE_TIMES"), 0);
        }
        return LIMOResourceBundle.getString("NUMBER_OF", LIMOResourceBundle.getString("ACCEPTANCE_TIMES"), events.size());
    }

    @Override
    public void setAsText(String s) {
    }

    @Override
    public Component getCustomEditor() {
        return new CustomEditor();
    }

    @Override
    public boolean supportsCustomEditor() {
        return true;
    }

    private class CustomEditor extends AcceptanceTimePanel implements TableModelListener {

        public CustomEditor() {
            super((List<Long>) getValue());
            this.model.addTableModelListener(this);
        }

        @Override
        public void tableChanged(TableModelEvent e) {
            setValue(this.getAcceptanceTimes());
        }
    }
}
