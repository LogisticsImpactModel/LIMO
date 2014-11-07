/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.view.wizard.hub;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import nl.fontys.sofa.limo.api.service.provider.HubService;
import nl.fontys.sofa.limo.api.service.provider.HubTypeService;
import nl.fontys.sofa.limo.domain.component.hub.Hub;
import nl.fontys.sofa.limo.domain.component.type.HubType;
import org.openide.util.Lookup;

public final class HubVisualPanel1 extends JPanel {
    private List<Hub> hl;
    private List<HubType> htl;

    /**
     * Creates new form HubVisualPanel1
     */
    public HubVisualPanel1() {
        initComponents();
    }

    @Override
    public String getName() {
        return "Hub";
    }

    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        rbFromScratch = new javax.swing.JRadioButton();
        rbFromHubType = new javax.swing.JRadioButton();
        rbCopyFrom = new javax.swing.JRadioButton();
        cmbHubType = new javax.swing.JComboBox();
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
                    cmbHubType.setEnabled(false);
                    cmbHub.setEnabled(false);
                }
            }
        });

        buttonGroup1.add(rbCopyFrom);
        rbCopyFrom.setText("Copy Existing");
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 1;
        add(rbCopyFrom, c);

        rbCopyFrom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (rbCopyFrom.isSelected()) {
                    cmbHubType.setEnabled(false);
                    cmbHub.setEnabled(true);

                }
            }
        });

        HubService hubService = Lookup.getDefault().lookup(HubService.class);
		hl = hubService.findAll();
        List<String> hubNameList = new ArrayList<>();
		for(Hub hub : hl){
			hubNameList.add(hub.getName());
		}

        cmbHub.setModel(new javax.swing.DefaultComboBoxModel(hubNameList.toArray()));
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 2;
        add(cmbHub, c);
        cmbHub.setEnabled(false);

        buttonGroup1.add(rbFromHubType);
        rbFromHubType.setText("From Hub Type");
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 3;
        add(rbFromHubType, c);

        rbFromHubType.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (rbFromHubType.isSelected()) {
                    cmbHubType.setEnabled(true);
                    cmbHub.setEnabled(false);
                }
            }
        });

		HubTypeService hubTypeService = Lookup.getDefault().lookup(HubTypeService.class);
        htl = hubTypeService.findAll();
        ArrayList<String> hubTypeList = new ArrayList<>();
        for (HubType hubType : htl) {
            hubTypeList.add(hubType.getName());
        }

        cmbHubType.setModel(new javax.swing.DefaultComboBoxModel(hubTypeList.toArray()));
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 4;
        add(cmbHubType, c);
        cmbHubType.setEnabled(false);

    }

    public HubType getHubType() {
        if (rbFromHubType.isSelected()) {
            return htl.get(cmbHubType.getSelectedIndex());
        } else {
            return null;
        }
    }

    public Hub getHub() {
        if (rbCopyFrom.isSelected()) {
            return hl.get(cmbHub.getSelectedIndex());
        } else {
            return null;
        }
    }

    javax.swing.ButtonGroup buttonGroup1;
    javax.swing.JComboBox cmbHubType;
    javax.swing.JComboBox cmbHub;
    javax.swing.JRadioButton rbFromHubType;
    javax.swing.JRadioButton rbFromScratch;
    javax.swing.JRadioButton rbCopyFrom;
}
