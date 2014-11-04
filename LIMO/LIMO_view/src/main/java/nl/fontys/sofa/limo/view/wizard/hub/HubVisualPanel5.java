/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.view.wizard.hub;

import java.util.List;
import javax.swing.JPanel;
import nl.fontys.sofa.limo.domain.component.event.Event;
import nl.fontys.sofa.limo.view.wizard.event.SubEventsPanel;

public final class HubVisualPanel5 extends JPanel {

    /**
     * Creates new form HubVisualPanel5
     */
    public HubVisualPanel5() {
        initComponents();
    }

    @Override
    public String getName() {
        return "Events";
    }

    private void initComponents() {
        sep = new SubEventsPanel();
        sep.setHubView();
        add(sep);
    }

    public void update(List<Event> events) {
        Event evt = new Event();
        evt.setEvents(events);
        sep.update(evt);
    }

    public List<Event> getEvents() {
        return sep.getEvent().getEvents();
    }

    private SubEventsPanel sep;
}
