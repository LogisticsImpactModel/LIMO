package nl.fontys.sofa.limo.view.node.property.editor;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyEditorSupport;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import nl.fontys.sofa.limo.domain.component.Icon;
import nl.fontys.sofa.limo.domain.component.type.HubType;
import nl.fontys.sofa.limo.domain.component.type.LegType;
import nl.fontys.sofa.limo.view.util.IconFileChooser;
import nl.fontys.sofa.limo.view.util.IconUtil;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;

/**
 * This class is the Property Editor for our custom icon. It enables you to
 * change the icon with the same component as in the wizards.
 *
 * @author Matthias Brück
 */
public abstract class IconPropertyEditor extends PropertyEditorSupport {

    protected abstract Class getBeanType();

    @Override
    public String getAsText() {
        return "";
    }

    @Override
    public void setAsText(String s) {
    }

    @Override
    public Component getCustomEditor() {
        return new CustomEditor();
    }

    @Override
    public boolean supportsCustomEditor() {
        return true;
    }

    /**
     * This CustomEditor is the actual JLabel that gets displayed as Editor.
     */
    private class CustomEditor extends JLabel implements ActionListener {

        private final JLabel lblPreview;
        private final JButton btnSelect;
        private final JButton btnRemove;
        private final JFileChooser fc;
        private final Icon oldIcon;

        public CustomEditor() {
            lblPreview = new JLabel();
            btnSelect = new JButton(LIMOResourceBundle.getString("CHOOSE"));
            btnRemove = new JButton(LIMOResourceBundle.getString("REMOVE"));
            btnRemove.setToolTipText(LIMOResourceBundle.getString("REMOVE_ICON_HINT"));
            fc = new IconFileChooser();

            setLayout(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();
            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridwidth = 1;
            c.weightx = 0.3;
            c.gridx = 0;
            c.gridy = 0;
            add(lblPreview, c);

            c.weightx = 0.4;
            c.gridx = 1;
            c.gridy = 0;
            add(btnSelect, c);
            btnSelect.addActionListener(this);

            c.weightx = 0.3;
            c.gridx = 2;
            c.gridy = 0;
            add(btnRemove, c);
            btnRemove.addActionListener(this);
            oldIcon = (Icon) getValue();
            lblPreview.setIcon(new ImageIcon(oldIcon.getImage()));
            this.setPreferredSize(new Dimension(350, 150));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(btnSelect)) {
                int returnVal = fc.showOpenDialog(CustomEditor.this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File icon = fc.getSelectedFile();
                    Icon newIcon = new Icon(new ImageIcon(icon.getAbsolutePath()).getImage(), icon.getPath().split("\\.")[icon.getPath().split("\\.").length - 1]);
                    setValue(newIcon);
                    lblPreview.setIcon(new ImageIcon(newIcon.getImage()));
                    btnRemove.setEnabled(true);
                }
            } else if (e.getSource().equals(btnRemove)) {
                Image image = IconUtil.getIcon(getBeanType(), 2);
                Icon newIcon = new Icon((BufferedImage) image, "png");
                setValue(newIcon);
                lblPreview.setIcon(new ImageIcon(newIcon.getImage()));
                btnRemove.setEnabled(false);
            }
        }
    }

    /**
     * Editor for HubIcons.
     */
    public static class HubIconPropertyEditor extends IconPropertyEditor {

        @Override
        protected Class getBeanType() {
            return HubType.class;
        }
    }

    /**
     * Editor for LegIcons.
     */
    public static class LegIconPropertyEditor extends IconPropertyEditor {

        @Override
        protected Class getBeanType() {
            return LegType.class;
        }
    }
}
