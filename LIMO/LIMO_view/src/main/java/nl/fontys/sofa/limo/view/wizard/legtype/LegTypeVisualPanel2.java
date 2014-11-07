/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.view.wizard.legtype;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;
import nl.fontys.sofa.limo.domain.component.Icon;
import nl.fontys.sofa.limo.domain.component.type.HubType;
import nl.fontys.sofa.limo.domain.component.type.LegType;
import nl.fontys.sofa.limo.view.util.IconUtil;

public final class LegTypeVisualPanel2 extends JPanel {

    /**
     * Creates new form LegTypeVisualPanel2
     */
    public LegTypeVisualPanel2() {
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
        btnRemove = new javax.swing.JButton("Remove");
        btnRemove.setToolTipText("Removes the current icon and takes the standard one.");
        fc = new JFileChooser();
        fc.setFileFilter(new FileFilter() {

            @Override
            public boolean accept(File f) {
                if(f.isDirectory()){
                    return true;
                }
                if (f.getAbsolutePath().endsWith(".png")) {
                    return true;
                }
                if (f.getAbsolutePath().endsWith(".bmp")) {
                    return true;
                }
                return f.getAbsolutePath().endsWith(".jpg");
            }

            @Override
            public String getDescription() {
                return "Filter for Images.";
            }
        });
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fc.setMultiSelectionEnabled(false);
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
        legType = new LegType();
        btnSelect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int returnVal = fc.showOpenDialog(LegTypeVisualPanel2.this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File icon = fc.getSelectedFile();
                    Icon newIcon = new Icon(new ImageIcon(icon.getAbsolutePath()).getImage(), icon.getPath().split("\\.")[icon.getPath().split("\\.").length - 1]);
                    legType.setIcon(newIcon);
                    lblPreview.setIcon(new ImageIcon(newIcon.getImage()));
                    btnRemove.setEnabled(true);
                }
            }
        });
        c.weightx = 0.3;
        c.gridx = 3;
        c.gridy = 2;
        add(btnRemove, c);
        btnRemove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Image image = IconUtil.getIcon(LegType.class, 2);
                Icon newIcon = new Icon((BufferedImage) image, "png");
                legType.setIcon(newIcon);
                lblPreview.setIcon(new ImageIcon(newIcon.getImage()));
                btnRemove.setEnabled(false);
            }
        });
        Image image = IconUtil.getIcon(LegType.class, 2);
        Icon newIcon = new Icon((BufferedImage) image, "png");
        legType.setIcon(newIcon);
        lblPreview.setIcon(new ImageIcon(newIcon.getImage()));
        btnRemove.setEnabled(false);
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

    public LegType getLegType() {
        legType.setName(tfName.getText());
        legType.setDescription(tfDesc.getText());
        return legType;
    }

    private javax.swing.JButton btnSelect, btnRemove;
    private javax.swing.JLabel lblIcon;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblDesc;
    private javax.swing.JLabel lblPreview;
    private javax.swing.JTextField tfName;
    private javax.swing.JTextField tfDesc;
    private JFileChooser fc;
    private LegType legType;
}
