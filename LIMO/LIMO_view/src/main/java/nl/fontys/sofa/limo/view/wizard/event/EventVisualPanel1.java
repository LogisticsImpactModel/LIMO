package nl.fontys.sofa.limo.view.wizard.event;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import nl.fontys.sofa.limo.api.dao.EventDAO;
import nl.fontys.sofa.limo.domain.component.event.Event;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;

public final class EventVisualPanel1 extends JPanel {

    ButtonGroup buttonGroup1;
    JComboBox eventsCb;
    JRadioButton eventFromScratchSelection;
    JRadioButton eventCopySelection;

    private List<Event> eventList;

    public EventVisualPanel1() {
        initComponents();
    }

    @Override
    public String getName() {
        return "Event";
    }

    private void initComponents() {
        buttonGroup1 = new javax.swing.ButtonGroup();
        eventFromScratchSelection = new javax.swing.JRadioButton();
        eventCopySelection = new javax.swing.JRadioButton();
        eventsCb = new javax.swing.JComboBox();
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 0;
        buttonGroup1.add(eventFromScratchSelection);
        eventFromScratchSelection.setText("From Scratch");
        add(eventFromScratchSelection, c);
        eventFromScratchSelection.setSelected(true);

        eventFromScratchSelection.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (eventFromScratchSelection.isSelected()) {
                    eventsCb.setEnabled(false);
                }
            }
        });

        buttonGroup1.add(eventCopySelection);
        eventCopySelection.setText("Copy Existing");
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 1;
        add(eventCopySelection, c);

        eventCopySelection.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (eventCopySelection.isSelected()) {
                    eventsCb.setEnabled(true);
                }
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                EventDAO eventDAO = Lookup.getDefault().lookup(EventDAO.class);
                eventList = eventDAO.findAll();
                List<String> events = new ArrayList<>();
                for (Event event : eventList) {
                    events.add(event.getUniqueIdentifier());
                }
                eventsCb.setModel(new javax.swing.DefaultComboBoxModel(events.toArray()));
            }
        }).start();

        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 2;
        add(eventsCb, c);
        eventsCb.setEnabled(false);
    }

    public Event getEvent() {
        Event event = null;
        try {
            event = eventList.get(eventsCb.getSelectedIndex());
        } catch (IndexOutOfBoundsException ex) {
        }
        return event;
    }
}
