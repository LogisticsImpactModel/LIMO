/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.view.wizard.hub;

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
import nl.fontys.sofa.limo.view.util.IconUtil;
import org.apache.commons.io.filefilter.FileFileFilter;

public final class HubVisualPanel2 extends JPanel {

    /**
     * Creates new form HubVisualPanel2
     */
    public HubVisualPanel2() {
        initComponents();
    }

    @Override
    public String getName() {
        return "Name and Icon";
    }

    private void initComponents() {
        lblName = new javax.swing.JLabel("Name");
        tfName = new javax.swing.JTextField();
        lblDescription = new javax.swing.JLabel("Description");
        tfDescription = new javax.swing.JTextArea();
        tfDescription.setRows(4);
        tfDescription.setBorder(tfName.getBorder());
        // tfDescription.setSize(tfDescription.getHeight(), tfName.getWidth());
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
        c.weightx = 0.3;
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        add(lblDescription, c);
        c.gridwidth = 3;

        c.weightx = 0.7;
        c.gridx = 1;
        c.gridy = 1;
        add(tfDescription, c);
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
        btnSelect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int returnVal = fc.showOpenDialog(HubVisualPanel2.this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File icon = fc.getSelectedFile();
                    newIcon = new Icon(new ImageIcon(icon.getAbsolutePath()).getImage(), icon.getPath().split("\\.")[icon.getPath().split("\\.").length - 1]);
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
                Image image = IconUtil.getIcon(HubType.class, 2);
                Icon newIcon = new Icon((BufferedImage) image, "png");
                lblPreview.setIcon(new ImageIcon(newIcon.getImage()));
                btnRemove.setEnabled(false);
            }
        });
        Image image = IconUtil.getIcon(HubType.class, 2);
        Icon newIcon = new Icon((BufferedImage) image, "png");
        lblPreview.setIcon(new ImageIcon(newIcon.getImage()));
        btnRemove.setEnabled(false);
    }

    public void updateLabel(String identifire,String desc ,Icon ic) {
        if (!identifire.isEmpty()) {
            tfName.setText(identifire);
            if (ic != null) {
                //Why it says nullpointer?
                try {
                    Image img = ic.getImage();
                    lblPreview.setIcon(new ImageIcon(img));
                } catch (java.lang.NullPointerException ex) {
                }
            }
            tfDescription.setText(desc);
        }
    }

    public Icon getHubIcon() {
        return newIcon;
    }

    public String getHubName() {
        return tfName.getText();
    }

    public String getDescription() {
        return tfDescription.getText();
    }

    private javax.swing.JButton btnSelect, btnRemove;
    private javax.swing.JLabel lblIcon;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblPreview;
    private javax.swing.JLabel lblDescription;
    private javax.swing.JTextField tfName;
    private javax.swing.JTextArea tfDescription;
    private JFileChooser fc;
    private Icon newIcon;
}
