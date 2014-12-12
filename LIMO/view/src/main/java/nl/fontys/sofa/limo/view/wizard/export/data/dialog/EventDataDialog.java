package nl.fontys.sofa.limo.view.wizard.export.data.dialog;

import com.jgoodies.forms.layout.FormLayout;
import java.awt.Dimension;
import nl.fontys.sofa.limo.domain.component.event.Event;

/**
 * @author Matthias Brück
 */
public class EventDataDialog extends DataDialog<Event> {

    public EventDataDialog(Event event) {
        super(event);
        this.setSize(new Dimension(350, 500));
        this.setModal(true);
        this.setLocationRelativeTo(null);
    }

    @Override
    protected void initComponents(Event entity) {
        layout = new FormLayout("5px, pref, 5px, pref:grow, 5px", "5px, pref:grow, 5px");
        this.setLayout(layout);
        this.add(getComponentViewPanel(), cc.xyw(2, 2, 3));
    }
}
