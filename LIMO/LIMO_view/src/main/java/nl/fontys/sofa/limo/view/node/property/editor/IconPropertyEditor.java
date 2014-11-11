package nl.fontys.sofa.limo.view.node.property.editor;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyEditorSupport;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.filechooser.FileFilter;
import nl.fontys.sofa.limo.domain.component.Icon;
import nl.fontys.sofa.limo.domain.component.type.LegType;
import nl.fontys.sofa.limo.view.util.IconUtil;
import org.openide.nodes.PropertyEditorRegistration;

/**
 *
 * @author Matthias Brück
 */
@PropertyEditorRegistration(targetType = Icon.class)
public class IconPropertyEditor extends PropertyEditorSupport {

    public IconPropertyEditor() {
    }

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

    private class CustomEditor extends JLabel {

        private JLabel lblPreview;
        private JButton btnSelect;
        private JButton btnRemove;
        private JFileChooser fc;
        private Icon oldIcon;

        public CustomEditor() {
            lblPreview = new JLabel();
            btnSelect = new JButton("Choose");
            btnRemove = new JButton("Remove");
            btnRemove.setToolTipText("Removes the current icon and takes the standard one.");
            fc = new JFileChooser();

            fc.setFileFilter(
                    new FileFilter() {

                        @Override
                        public boolean accept(File f
                        ) {
                            if (f.isDirectory()) {
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
            c.gridwidth = 1;
            c.weightx = 0.3;
            c.gridx = 0;
            c.gridy = 0;
            add(lblPreview, c);

            c.weightx = 0.4;
            c.gridx = 1;
            c.gridy = 0;
            add(btnSelect, c);
            btnSelect.addActionListener(
                    new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e
                        ) {
                            int returnVal = fc.showOpenDialog(CustomEditor.this);
                            if (returnVal == JFileChooser.APPROVE_OPTION) {
                                File icon = fc.getSelectedFile();
                                Icon newIcon = new Icon(new ImageIcon(icon.getAbsolutePath()).getImage(), icon.getPath().split("\\.")[icon.getPath().split("\\.").length - 1]);
                                setValue(newIcon);
                                lblPreview.setIcon(new ImageIcon(newIcon.getImage()));
                                btnRemove.setEnabled(true);
                            }
                        }
                    }
            );

            c.weightx = 0.3;
            c.gridx = 2;
            c.gridy = 0;
            add(btnRemove, c);
            btnRemove.addActionListener(
                    new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e
                        ) {
                            Image image = IconUtil.getIcon(LegType.class, 2);
                            Icon newIcon = new Icon((BufferedImage) image, "png");
                            setValue(newIcon);
                            lblPreview.setIcon(new ImageIcon(newIcon.getImage()));
                            btnRemove.setEnabled(false);
                        }
                    }
            );
            oldIcon = (Icon) getValue();
            lblPreview.setIcon(new ImageIcon(oldIcon.getImage()));
            this.setPreferredSize(new Dimension(350, 150));
        }
    }
}
