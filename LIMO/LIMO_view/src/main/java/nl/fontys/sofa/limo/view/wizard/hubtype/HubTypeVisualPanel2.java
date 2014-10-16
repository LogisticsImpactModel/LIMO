/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.view.wizard.hubtype;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import nl.fontys.sofa.limo.domain.component.Icon;
import nl.fontys.sofa.limo.domain.component.type.HubType;

public final class HubTypeVisualPanel2 extends JPanel {

    /**
     * Creates new form HubTypeVisualPanel2
     */
    public HubTypeVisualPanel2() {
        initComponents();
    }

    @Override
    public String getName() {
        return "Name and Icon";
    }

    private void initComponents() {
        lblName = new javax.swing.JLabel("Name");
        tfName = new javax.swing.JTextField();
        lblDesc = new javax.swing.JLabel("Description");
        tfDesc = new javax.swing.JTextField();
        lblIcon = new javax.swing.JLabel("Icon");
        lblPreview = new javax.swing.JLabel();
        btnSelect = new javax.swing.JButton("Choose");
        fc = new JFileChooser();
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.3;
        c.gridx = 0;
        c.gridy = 0;
        add(lblName, c);
        c.weightx = 0.7;
        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 3;
        add(tfName, c);
        c.gridwidth = 1;
        c.weightx = 0.3;
        c.gridx = 0;
        c.gridy = 1;
        add(lblDesc, c);
        c.weightx = 0.3;
        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 3;
        add(tfDesc, c);
        c.gridwidth = 1;
        c.weightx = 0.3;
        c.gridx = 0;
        c.gridy = 2;
        add(lblIcon, c);
        c.weightx = 0.4;
        c.gridx = 1;
        c.gridy = 2;
        add(lblPreview, c);
        c.weightx = 0.3;
        c.gridx = 2;
        c.gridy = 2;
        add(btnSelect, c);
        hubType = new HubType();
        btnSelect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int returnVal = fc.showOpenDialog(HubTypeVisualPanel2.this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File icon = fc.getSelectedFile();
                    Icon newIcon = new Icon(new ImageIcon(icon.getAbsolutePath()).getImage());
                    hubType.setIcon(newIcon);
                    lblPreview.setIcon((javax.swing.Icon) newIcon);
                }
            }
        });
    }

    public void updateLabel(String identifire, Icon ic) {
        if (!identifire.isEmpty()) {
            tfName.setText(identifire);
            if (ic != null && ic != null) {
                Image img = ic.getImage();
                lblPreview.setIcon(new ImageIcon(img));
            }
        }
    }

    public HubType getHubType() {
        hubType.setName(tfName.getText());
        hubType.setDescription(tfDesc.getText());
        return hubType;
    }

    private javax.swing.JButton btnSelect;
    private javax.swing.JLabel lblIcon;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblDesc;
    private javax.swing.JLabel lblPreview;
    private javax.swing.JTextField tfName;
    private javax.swing.JTextField tfDesc;
    private JFileChooser fc;
    private HubType hubType;
}
