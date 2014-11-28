package nl.fontys.sofa.limo.view.wizard.export.data.panel;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * @author Matthias Brück
 */
public class ChooserPanel extends JPanel implements ActionListener {

    private JButton btnChooser;
    private final JTextField tfDirectory, tfFileName;
    private final CellConstraints cc;
    private final FormLayout layout;
    private final JFileChooser fc;

    public ChooserPanel() {
        cc = new CellConstraints();
        layout = new FormLayout("5px, 200, 5px, pref, 5px", "5px, pref, 5px, pref, 5px");
        this.setLayout(layout);
        tfDirectory = new JTextField("");
        tfDirectory.setEditable(false);
        this.add(tfDirectory, cc.xy(2, 2));
        btnChooser = new JButton("...");
        btnChooser.addActionListener(this);
        this.add(btnChooser, cc.xy(4, 2));
        tfFileName = new JTextField();
        tfFileName.setText("");
        this.add(tfFileName, cc.xy(2, 4));
        fc = new JFileChooser();
        String currentPath = fc.getFileSystemView().getDefaultDirectory().toString();
        fc.setCurrentDirectory(new File(currentPath));
        fc.setMultiSelectionEnabled(false);
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    }

    public String getPath() {
        return tfDirectory.getText();
    }

    public String getFileName() {
        return tfFileName.getText();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            tfDirectory.setText(fc.getSelectedFile().getPath());
        }
    }
}
