package nl.fontys.sofa.limo.view.custom.panel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import nl.fontys.sofa.limo.domain.component.Icon;
import nl.fontys.sofa.limo.view.util.IconFileChooser;
import nl.fontys.sofa.limo.view.util.IconUtil;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;

/**
 * This panel can be used by every wizard or other panel which has to gather
 * name, description and icon. The class which is given as generic the standard
 * icon is chosen by default.
 *
 * @author Sven Mäurer
 * @param <T> the class whose name, description and icon should be set.
 */
public class NameDescriptionIconPanel<T extends Class> extends JPanel implements ActionListener {

    private JButton addIconButton, removeIconButton;
    private JLabel preview;
    private JTextField nameInput, descriptionInput;
    private JFileChooser iconFileChooser;

    private Icon newIcon;
    private final Class clazz;

    public NameDescriptionIconPanel(T clazz) {
        this.clazz = clazz;
        initComponents();
    }

    @Override
    public String getName() {
        return LIMOResourceBundle.getString("BASIC_DATA");
    }

    private void initComponents() {
        assignComponents();
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(3, 3, 3, 3);
        c.weightx = 0.3;
        c.gridx = 0;
        c.gridy = 0;
        add(new JLabel(LIMOResourceBundle.getString("NAME")), c);
        c.weightx = 0.7;
        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 3;
        add(nameInput, c);
        c.gridwidth = 1;
        c.weightx = 0.3;
        c.gridx = 0;
        c.gridy = 1;
        add(new JLabel(LIMOResourceBundle.getString("DESCRIPTION")), c);
        c.weightx = 0.3;
        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 3;
        add(descriptionInput, c);
        c.gridwidth = 1;
        c.weightx = 0.3;
        c.gridx = 0;
        c.gridy = 2;
        add(new JLabel(LIMOResourceBundle.getString("ICON")), c);
        c.weightx = 0.4;
        c.gridx = 1;
        c.gridy = 2;
        add(preview, c);
        c.weightx = 0.3;
        c.gridx = 2;
        c.gridy = 2;
        add(addIconButton, c);
        addIconButton.addActionListener(this);
        c.weightx = 0.3;
        c.gridx = 3;
        c.gridy = 2;
        add(removeIconButton, c);
        removeIconButton.addActionListener(this);
        resetIcon();
    }

    private void assignComponents() {
        nameInput = new JTextField();
        descriptionInput = new JTextField();
        preview = new JLabel();
        addIconButton = new JButton(LIMOResourceBundle.getString("CHOOSE"));
        removeIconButton = new JButton(LIMOResourceBundle.getString("REMOVE"));
        removeIconButton.setToolTipText(LIMOResourceBundle.getString("REMOVE_ICON_HINT"));
        iconFileChooser = new IconFileChooser();
    }

    private void resetIcon() {
        Image image = IconUtil.getIcon(clazz, 2);
        newIcon = new Icon((BufferedImage) image, "png");
        preview.setIcon(new SmallIcon(newIcon.getImage()));
        removeIconButton.setEnabled(false);
    }

    /**
     * Update the view based on name, description and icon.
     *
     * @param name to be set.
     * @param description to be set.
     * @param icon to be set.
     */
    public void update(String name, String description, Icon icon) {
        nameInput.setText(name);
        descriptionInput.setText(description);
        if (icon != null) {
            newIcon = icon;
            Image img = icon.getImage();
            preview.setIcon(new SmallIcon(img));
        } else {
            resetIcon();
        }
    }
    
    public void update(String name) {
        nameInput.setText(name);
    }

    public String getNameInput() {
        return nameInput.getText();
    }

    public String getDescriptionInput() {
        return descriptionInput.getText();
    }

    public Icon getIcon() {
        return newIcon;
    }

    /**
     * Open a file chooser and sets the icon if you want to add an icon or reset
     * the icon if choose to delete it.
     *
     * @param e the event of the calling button.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(addIconButton)) {
            int returnVal = iconFileChooser.showOpenDialog(NameDescriptionIconPanel.this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File icon = iconFileChooser.getSelectedFile();
                newIcon = new Icon(new ImageIcon(icon.getAbsolutePath()).getImage(), icon.getPath().split("\\.")[icon.getPath().split("\\.").length - 1]);
                preview.setIcon(new SmallIcon(newIcon.getImage()));
                removeIconButton.setEnabled(true);
            }
        } else if (e.getSource().equals(removeIconButton)) {
            resetIcon();
        }
    }

    /**
     * ImageIcon with the size 32 x 32.
     */
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
