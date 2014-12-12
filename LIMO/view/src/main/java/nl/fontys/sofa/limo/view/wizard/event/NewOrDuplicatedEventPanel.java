package nl.fontys.sofa.limo.view.wizard.event;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import nl.fontys.sofa.limo.api.service.provider.EventService;
import nl.fontys.sofa.limo.domain.component.event.Event;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;
import org.openide.util.Lookup;

public final class NewOrDuplicatedEventPanel extends JPanel {

    private JComboBox eventsCb;
    private JRadioButton eventCopySelection;
    private JRadioButton eventFromScratchSelection;

    private List<Event> eventList;
    private EventService service;

    public NewOrDuplicatedEventPanel() {
        initComponents();
    }

    @Override
    public String getName() {
        return LIMOResourceBundle.getString("EVENT");
    }

    private void initComponents() {
        ButtonGroup buttonGroup1 = new ButtonGroup();
        eventFromScratchSelection = new JRadioButton();
        eventCopySelection = new JRadioButton();
        eventsCb = new JComboBox();
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 0;
        buttonGroup1.add(eventFromScratchSelection);
        eventFromScratchSelection.setText(LIMOResourceBundle.getString("FROM_SCRATCH"));
        add(eventFromScratchSelection, c);
        eventFromScratchSelection.setSelected(true);

        buttonGroup1.add(eventCopySelection);
        eventCopySelection.setText(LIMOResourceBundle.getString("COPY_EXISTING"));
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 1;
        add(eventCopySelection, c);

        Lookup l = Lookup.getDefault();
        service = l.lookup(EventService.class);
        eventList = service.findAll();
        List<String> events = new ArrayList<>();
        for (Event event : eventList) {
            events.add(event.getName());
        }
        eventsCb.setModel(new DefaultComboBoxModel(events.toArray(new String[events.size()])));

        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 2;
        add(eventsCb, c);
        eventsCb.setEnabled(false);
        if (events.isEmpty()) {
            eventCopySelection.setEnabled(false);
            eventsCb.setEditable(false);
        }

        initActionsListener();
    }

    private void initActionsListener() {
        eventFromScratchSelection.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (eventFromScratchSelection.isSelected()) {
                    eventsCb.setEnabled(false);
                }
            }
        });
        eventCopySelection.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (eventCopySelection.isSelected()) {
                    eventsCb.setEnabled(true);
                }
            }
        });
    }

    public boolean isEventCopySelected() {
        return eventCopySelection.isSelected();
    }

    public Event getEvent() {
        Event event = null;
        if (eventCopySelection.isSelected()) {
            event = eventList.get(eventsCb.getSelectedIndex());
            event.setId(null);
        }
        return event;
    }
}
