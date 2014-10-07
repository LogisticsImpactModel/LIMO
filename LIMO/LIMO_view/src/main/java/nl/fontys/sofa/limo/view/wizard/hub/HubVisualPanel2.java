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
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import nl.fontys.sofa.limo.domain.Icon;
import nl.fontys.sofa.limo.domain.component.Hub;

public final class HubVisualPanel2 extends JPanel {

    /**
     * Creates new form HubVisualPanel2
     */
    public HubVisualPanel2() {
        initComponents();
    }

    @Override
    public String getName() {
        return "Set Name and Icon";
    }

    private void initComponents() {

        lblName = new javax.swing.JLabel("Name");
        tfName = new javax.swing.JTextField();
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
        add(tfName, c);
        c.weightx = 0.3;
        c.gridx = 0;
        c.gridy = 1;
        add(lblIcon, c);
        c.weightx = 0.4;
        c.gridx = 1;
        c.gridy = 1;
        add(lblPreview, c);
        c.weightx = 0.3;
        c.gridx = 2;
        c.gridy = 1;
        add(btnSelect, c);

        btnSelect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int returnVal = fc.showOpenDialog(HubVisualPanel2.this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    icon = fc.getSelectedFile();
                    lblPreview.setIcon(new ImageIcon(icon.getAbsolutePath()));
                }
            }
        });
    }

    public void updatePanel() {
        //Update Labels
    }

    public Hub getHub() {
        hub = new Hub();
        hub.setIdentifier(tfName.getText());
        if (icon == null) {
            hub.setIcon(new Icon(icon.getAbsolutePath()));
        }
        return hub;
    }

    public File getIcon() {
        return icon;
    }

    private javax.swing.JButton btnSelect;
    private javax.swing.JLabel lblIcon;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblPreview;
    private javax.swing.JTextField tfName;
    private JFileChooser fc;
    private Hub hub;
    private File icon;
}
