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

public final class EventVisualPanel1 extends JPanel {

    private ButtonGroup buttonGroup1;
    private JComboBox cmbHub;
    private JRadioButton rbFromScratch;
    private JRadioButton rbCopyFrom;
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
        rbFromScratch = new javax.swing.JRadioButton();
        rbCopyFrom = new javax.swing.JRadioButton();
        cmbHub = new javax.swing.JComboBox();
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 0;
        buttonGroup1.add(rbFromScratch);
        rbFromScratch.setText("From Scratch");
        add(rbFromScratch, c);
        rbFromScratch.setSelected(true);

        rbFromScratch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (rbFromScratch.isSelected()) {
                    cmbHub.setEnabled(false);
                }
            }
        });

        buttonGroup1.add(rbCopyFrom);
        rbCopyFrom.setText("Copy Event");
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 1;
        add(rbCopyFrom, c);

        rbCopyFrom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (rbCopyFrom.isSelected()) {
                    cmbHub.setEnabled(true);

                }
            }
        });

        EventDAO eventDAO = Lookup.getDefault().lookup(EventDAO.class);
        eventList = eventDAO.findAll();
        ArrayList<String> eventList = new ArrayList<>();
        for (Event event : this.eventList) {
            eventList.add(event.getUniqueIdentifier());
        }

        cmbHub.setModel(new javax.swing.DefaultComboBoxModel(eventList.toArray()));
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 2;
        add(cmbHub, c);
        cmbHub.setEnabled(false);

    }

    public Event getEvent() {
        if (rbCopyFrom.isSelected()) {
            return eventList.get(cmbHub.getSelectedIndex());
        } else {
            return null;
        }
    }
}
