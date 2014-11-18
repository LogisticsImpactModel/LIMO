/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.view.wizard.leg;

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
import nl.fontys.sofa.limo.api.service.provider.LegTypeService;
import nl.fontys.sofa.limo.domain.component.type.LegType;
import org.openide.util.Lookup;

public final class NormalLegVisualPanel1 extends JPanel {

    /**
     * Creates new form NormalLegVisualPanel1
     */
    ButtonGroup buttonGroup1;
    JComboBox cmbLegType;
    JRadioButton rbFromLegType;
    JRadioButton rbFromScratch;
    List<LegType> htl;

    public NormalLegVisualPanel1() {
        initComponents();
    }

    @Override
    public String getName() {
        return "Create new Leg";
    }

    private void initComponents() {
        buttonGroup1 = new ButtonGroup();
        rbFromScratch = new JRadioButton();
        rbFromLegType = new JRadioButton();
        cmbLegType = new JComboBox();
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
                    cmbLegType.setEnabled(false);
                }
            }
        });

        buttonGroup1.add(rbFromLegType);
        rbFromLegType.setText("From LegType");
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 3;
        add(rbFromLegType, c);

        rbFromLegType.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (rbFromLegType.isSelected()) {
                    cmbLegType.setEnabled(true);
                }
            }
        });

        LegTypeService legTypeService = Lookup.getDefault().lookup(LegTypeService.class);
        htl = legTypeService.findAll();
        ArrayList<String> legTypeList = new ArrayList<>();
        for (LegType legType : htl) {
            legTypeList.add(legType.getName());
        }

        cmbLegType.setModel(new DefaultComboBoxModel(legTypeList.toArray()));
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 4;
        add(cmbLegType, c);
        cmbLegType.setEnabled(false);
    }
    
    public LegType getLegType(){
        if (rbFromLegType.isSelected()) {
            return htl.get(cmbLegType.getSelectedIndex());
        } else {
            return null;
        }
    }
}
