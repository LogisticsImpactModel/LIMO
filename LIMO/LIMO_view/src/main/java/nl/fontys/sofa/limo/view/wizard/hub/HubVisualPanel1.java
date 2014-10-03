/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.view.wizard.hub;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JPanel;
import nl.fontys.sofa.limo.domain.types.HubType;
//import org.netbeans.lib.awtextra.AbsoluteLayout;

public final class HubVisualPanel1 extends JPanel {

    /**
     * Creates new form HubVisualPanel1
     */
    public HubVisualPanel1() {
        initComponents();
    }

    @Override
    public String getName() {
        return "Add Hub";
    }

    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        rbFromScratch = new javax.swing.JRadioButton();
        rbFromHubType = new javax.swing.JRadioButton();
        cmbHubType = new javax.swing.JComboBox();

//        setLayout(new AbsoluteLayout());

        buttonGroup1.add(rbFromScratch);
        rbFromScratch.setText("From Scratch");
//        add(rbFromScratch, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 50, -1, -1));
        rbFromScratch.setSelected(true);
        rbFromScratch.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (rbFromScratch.isSelected()) {
                    cmbHubType.setEnabled(false);
                }
            }
        });

        buttonGroup1.add(rbFromHubType);
        rbFromHubType.setText("From Hub Type");
//        add(rbFromHubType, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 80, -1, -1));
        rbFromHubType.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (rbFromHubType.isSelected()) {
                    cmbHubType.setEnabled(true);
                }
            }
        });

        cmbHubType.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"Item 1", "Item 2", "Item 3", "Item 4"}));
//        add(cmbHubType, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 110, 240, -1));
        cmbHubType.setEnabled(false);
    }

    public HubType getHubType() {
        if (rbFromHubType.isSelected()) {
            return hubTypes.get(cmbHubType.getSelectedIndex());
        } else {
            return new HubType();
        }
    }

    javax.swing.ButtonGroup buttonGroup1;
    javax.swing.JComboBox cmbHubType;
    javax.swing.JRadioButton rbFromHubType;
    javax.swing.JRadioButton rbFromScratch;
    private ArrayList<HubType> hubTypes;
}
