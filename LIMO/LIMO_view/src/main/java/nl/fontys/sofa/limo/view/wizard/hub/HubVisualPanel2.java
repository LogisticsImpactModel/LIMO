/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.view.wizard.hub;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
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

        lblName = new javax.swing.JLabel();
        tfName = new javax.swing.JTextField();
        lblIcon = new javax.swing.JLabel();
        lblPreview = new javax.swing.JLabel();
        btnSelect = new javax.swing.JButton();
        fc = new JFileChooser();
//        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        org.openide.awt.Mnemonics.setLocalizedText(lblName, org.openide.util.NbBundle.getMessage(HubVisualPanel2.class, "HubVisualPanel2.lblName.text_1")); // NOI18N
//        add(lblName, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, -1, 30));

        tfName.setText(org.openide.util.NbBundle.getMessage(HubVisualPanel2.class, "HubVisualPanel2.tfName.text_1")); // NOI18N
//        add(tfName, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 30, 200, -1));

        org.openide.awt.Mnemonics.setLocalizedText(lblIcon, org.openide.util.NbBundle.getMessage(HubVisualPanel2.class, "HubVisualPanel2.lblIcon.text_1")); // NOI18N
//        add(lblIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, -1, 30));

        org.openide.awt.Mnemonics.setLocalizedText(lblPreview, org.openide.util.NbBundle.getMessage(HubVisualPanel2.class, "HubVisualPanel2.lblPreview.text_1")); // NOI18N
//        add(lblPreview, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 100, 60, 60));

        org.openide.awt.Mnemonics.setLocalizedText(btnSelect, org.openide.util.NbBundle.getMessage(HubVisualPanel2.class, "HubVisualPanel2.btnSelect.text_1")); // NOI18N
        btnSelect.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int returnVal = fc.showOpenDialog(HubVisualPanel2.this);

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    icon = fc.getSelectedFile();
                }
            }
        });
//        add(btnSelect, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 110, 130, -1));

    }

    public void updatePanel() {
        //Update Labels
    }

    public Hub getHub() {
        hub = new Hub();
        hub.setIdentifier(tfName.getText());
        hub.setIcon(new Icon(icon.getAbsolutePath()));
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
