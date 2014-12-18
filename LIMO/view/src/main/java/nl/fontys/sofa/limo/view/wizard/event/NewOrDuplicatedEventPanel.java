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

/**
 * Panel to choose if you want to create a new event or take an existing one as
 * base.
 *
 * @author Sven Mäurer
 */
public final class NewOrDuplicatedEventPanel extends JPanel {

    private JComboBox eventsCb;
    private JRadioButton eventCopySelection, eventFromScratchSelection;

    private List<Event> eventList;
    private EventService service;

    public NewOrDuplicatedEventPanel() {
        initComponents();
    }

    @Override
    public String getName() {
        return LIMOResourceBundle.getString("EVENT");
    }

    /**
     * Initialize the components and add them to the layout.
     */
    private void initComponents() {
        ButtonGroup buttonGroup = assignComponents();

        initComponentProperties();

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 0;
        buttonGroup.add(eventFromScratchSelection);
        add(eventFromScratchSelection, c);

        buttonGroup.add(eventCopySelection);
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 1;
        add(eventCopySelection, c);

        initEventCopyComboBox();

        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 2;
        add(eventsCb, c);

        initActionsListener();
    }

    private void initComponentProperties() {
        eventFromScratchSelection.setText(LIMOResourceBundle.getString("FROM_SCRATCH"));
        eventCopySelection.setText(LIMOResourceBundle.getString("COPY_EXISTING"));
        eventFromScratchSelection.setSelected(true);
    }

    /**
     * Get existing events and and set the combo box model. If there is no event
     * disable it.
     */
    private void initEventCopyComboBox() {
        Lookup l = Lookup.getDefault();
        service = l.lookup(EventService.class);
        eventList = service.findAll();
        List<String> events = new ArrayList<>();
        for (Event event : eventList) {
            events.add(event.getName());
        }
        eventsCb.setModel(new DefaultComboBoxModel(events.toArray(new String[events.size()])));

        eventsCb.setEnabled(false);
        if (events.isEmpty()) {
            eventCopySelection.setEnabled(false);
            eventsCb.setEditable(false);
        }
    }

    private ButtonGroup assignComponents() {
        ButtonGroup buttonGroup1 = new ButtonGroup();
        eventFromScratchSelection = new JRadioButton();
        eventCopySelection = new JRadioButton();
        eventsCb = new JComboBox();
        return buttonGroup1;
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
