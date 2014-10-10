/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.view.wizard.event;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import nl.fontys.sofa.limo.api.dao.DAOFactory;
import nl.fontys.sofa.limo.api.dao.EventDAO;
import nl.fontys.sofa.limo.domain.component.Event;
import org.openide.util.Lookup;

public final class EventVisualPanel1 extends JPanel {

    /**
     * Creates new form HubVisualPanel1
     */
    public EventVisualPanel1() {
        initComponents();
    }

    @Override
    public String getName() {
        return "Hub";
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

        DAOFactory df = Lookup.getDefault().lookup(DAOFactory.class);
        Lookup.getDefault().lookup(DAOFactory.class);
        EventDAO hd = df.getEventDAO();
        hl = hd.findAll();
        ArrayList<String> hubList = new ArrayList<>();
        for (Event hub : hl) {
            hubList.add(hub.getIdentifier());
        }

        cmbHub.setModel(new javax.swing.DefaultComboBoxModel(hubList.toArray()));
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 2;
        add(cmbHub, c);
        cmbHub.setEnabled(false);

    }

    public Event getEvent() {
        if (rbCopyFrom.isSelected()) {
            return hl.get(cmbHub.getSelectedIndex());
        } else {
            return null;
        }
    }

    javax.swing.ButtonGroup buttonGroup1;
    javax.swing.JComboBox cmbHub;
    javax.swing.JRadioButton rbFromScratch;
    javax.swing.JRadioButton rbCopyFrom;
    private List<Event> hl;
}
