package nl.fontys.sofa.limo.view.custom.pane;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ResourceBundle;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import nl.fontys.sofa.limo.domain.component.Icon;
import nl.fontys.sofa.limo.view.util.IconFileFilter;
import nl.fontys.sofa.limo.view.util.IconUtil;

public class NameDescriptionIconPanel<T extends Class> extends JPanel {

    private final ResourceBundle bundle;
    private Icon newIcon;
    private final Class clazz;

    public NameDescriptionIconPanel(T clazz) {
        bundle = ResourceBundle.getBundle("nl/fontys/sofa/limo/view/Bundle");
        this.clazz = clazz;
        initComponents();
    }

    @Override
    public String getName() {
        return bundle.getString("NAME_DESCRIPTION_ICON");
    }

    private void initComponents() {
        lblName = new javax.swing.JLabel(bundle.getString("NAME"));
        tfName = new javax.swing.JTextField();
        lblDesc = new javax.swing.JLabel(bundle.getString("DESCRIPTION"));
        tfDesc = new javax.swing.JTextField();
        lblIcon = new javax.swing.JLabel(bundle.getString("ICON"));
        lblPreview = new javax.swing.JLabel();
        btnSelect = new javax.swing.JButton(bundle.getString("CHOOSE"));
        btnRemove = new javax.swing.JButton(bundle.getString("REMOVE"));
        btnRemove.setToolTipText(bundle.getString("REMOVE_ICON_HINT"));
        fc = new JFileChooser();
        fc.setFileFilter(new IconFileFilter());
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
        btnSelect.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int returnVal = fc.showOpenDialog(NameDescriptionIconPanel.this);
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
                resetIcon();
            }
        });
        resetIcon();
    }

    private void resetIcon() {
        Image image = IconUtil.getIcon(clazz, 2);
        newIcon = new Icon((BufferedImage) image, "png");
        lblPreview.setIcon(new ImageIcon(newIcon.getImage()));
        btnRemove.setEnabled(false);
    }

    public void update(String legName, String legDescr, Icon legIcon) {
        tfName.setText(legName);
        tfDesc.setText(legDescr);
        if (legIcon != null) {
            Image img = legIcon.getImage();
            lblPreview.setIcon(new ImageIcon(img));
        }
    }

    public String getNameInput() {
        return tfName.getText();
    }

    public String getDescriptionInput() {
        return tfDesc.getText();
    }

    public Icon getIcon() {
        return newIcon;
    }

    private javax.swing.JButton btnSelect, btnRemove;
    private javax.swing.JLabel lblIcon;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblDesc;
    private javax.swing.JLabel lblPreview;
    private javax.swing.JTextField tfName;
    private javax.swing.JTextField tfDesc;
    private JFileChooser fc;
}