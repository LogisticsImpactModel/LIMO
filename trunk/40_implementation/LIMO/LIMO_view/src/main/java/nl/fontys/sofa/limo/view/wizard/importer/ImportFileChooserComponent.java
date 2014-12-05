package nl.fontys.sofa.limo.view.wizard.importer;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author Matthias Brück
 */
public class ImportFileChooserComponent extends JPanel implements ActionListener {

    private final JButton btnChooser;
    private final JTextField tfFile;
    private final CellConstraints cc;
    private final FormLayout layout;
    private final JFileChooser fc;

    public ImportFileChooserComponent() {
        cc = new CellConstraints();
        layout = new FormLayout("5px, 200, 5px, pref, 5px", "5px, pref, 5px");
        this.setLayout(layout);
        tfFile = new JTextField("");
        tfFile.setEditable(false);
        this.add(tfFile, cc.xy(2, 2));
        btnChooser = new JButton("...");
        btnChooser.addActionListener(this);
        this.add(btnChooser, cc.xy(4, 2));
        fc = new JFileChooser();
        String currentPath = fc.getFileSystemView().getDefaultDirectory().toString();
        fc.setCurrentDirectory(new File(currentPath));
        fc.setMultiSelectionEnabled(false);
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fc.setFileFilter(new FileFilter() {

            @Override
            public boolean accept(File f) {
                if (f.isDirectory()) {
                    return true;
                }
                return f.getAbsolutePath().endsWith(".lef");
            }

            @Override
            public String getDescription() {
                return "Filter for LIMO import files.";
            }
        });
    }

    public String getAbsoluteFilePath() {
        return tfFile.getText();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            tfFile.setText(fc.getSelectedFile().getPath());
        }
    }
}
