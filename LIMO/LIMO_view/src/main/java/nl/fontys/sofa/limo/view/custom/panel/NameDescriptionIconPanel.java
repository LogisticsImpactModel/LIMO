package nl.fontys.sofa.limo.view.custom.panel;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ResourceBundle;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import nl.fontys.sofa.limo.domain.component.Icon;
import nl.fontys.sofa.limo.view.util.IconFileChooser;
import nl.fontys.sofa.limo.view.util.IconUtil;

public class NameDescriptionIconPanel<T extends Class> extends JPanel {

    private JButton btnSelect, btnRemove;
    private JLabel lblPreview;
    private JTextField tfName;
    private JTextField tfDesc;
    private JFileChooser fc;

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
        return bundle.getString("BASIC_DATA");
    }

    private void initComponents() {
        tfName = new JTextField();
        tfDesc = new JTextField();
        lblPreview = new JLabel();
        lblPreview.setMaximumSize(new Dimension(20, 20));
        lblPreview.setMinimumSize(new Dimension(20, 20));
        lblPreview.setSize(new Dimension(20, 20));
        btnSelect = new JButton(bundle.getString("CHOOSE"));
        btnRemove = new JButton(bundle.getString("REMOVE"));
        btnRemove.setToolTipText(bundle.getString("REMOVE_ICON_HINT"));
        fc = new IconFileChooser();
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.3;
        c.gridx = 0;
        c.gridy = 0;
        add(new JLabel(bundle.getString("NAME")), c);
        c.weightx = 0.7;
        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 3;
        add(tfName, c);
        c.gridwidth = 1;
        c.weightx = 0.3;
        c.gridx = 0;
        c.gridy = 1;
        add(new JLabel(bundle.getString("DESCRIPTION")), c);
        c.weightx = 0.3;
        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 3;
        add(tfDesc, c);
        c.gridwidth = 1;
        c.weightx = 0.3;
        c.gridx = 0;
        c.gridy = 2;
        add(new JLabel(bundle.getString("ICON")), c);
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
                    lblPreview.setIcon(new SmallIcon(newIcon.getImage()));
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
        lblPreview.setIcon(new SmallIcon(newIcon.getImage()));
        btnRemove.setEnabled(false);
    }

    public void update(String name, String description, Icon icon) {
        tfName.setText(name);
        tfDesc.setText(description);
        if (icon != null) {
            newIcon = icon;
            Image img = icon.getImage();
            lblPreview.setIcon(new SmallIcon(img));
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

    private static final class SmallIcon extends ImageIcon {

        private static final int SIZE = 32;

        private SmallIcon(Image image) {
            super(image.getScaledInstance(SIZE, SIZE, java.awt.Image.SCALE_SMOOTH));
        }

        @Override
        public int getIconHeight() {
            return SIZE;
        }

        @Override
        public int getIconWidth() {
            return SIZE;
        }

    }

}
