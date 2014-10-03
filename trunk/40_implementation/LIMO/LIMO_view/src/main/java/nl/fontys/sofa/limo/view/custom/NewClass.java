package nl.fontys.sofa.limo.view.custom;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import nl.fontys.sofa.limo.domain.Entry;
import nl.fontys.sofa.limo.domain.component.Event;
import nl.fontys.sofa.limo.domain.value.SingleValue;

public class NewClass {

    public static void main(String[] args) {
        JFrame frame = new JFrame("TabbedPaneDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
        frame.setLayout(new BorderLayout());

        frame.add(new TabbedComponentEntries(getEvent()), BorderLayout.CENTER);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    private static Event getEvent() {
        Event event = new Event("Pirates");

        ArrayList<Entry> costs = new ArrayList<>();
        Entry costEntry = new Entry("Shipping", "Transport");
        costEntry.setValue(new SingleValue(20000));
        costs.add(costEntry);
        event.setCosts(costs);

        ArrayList<Entry> delays = new ArrayList<>();
        Entry delayEntry = new Entry("Pirate Attack", "Unforeseeable");
        delayEntry.setValue(new SingleValue(250000));
        delays.add(delayEntry);
        event.setDelays(delays);

        List<Entry> leadTimes = new ArrayList<>();
        Entry leadTimeEntry = new Entry("", "");
        leadTimeEntry.setValue(new SingleValue(3000));
        leadTimes.add(leadTimeEntry);
        event.setLeadTimes(leadTimes);

        return event;
    }
}
