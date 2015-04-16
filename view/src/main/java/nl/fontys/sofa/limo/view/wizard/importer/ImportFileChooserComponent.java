package nl.fontys.sofa.limo.view.wizard.importer;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * This Component extends JPanel and displays a Button and a Textfield. Clicking
 * the button opens a FileChooser that let's you choose LIMO import files. The
 * Textfield is uneditable and shows the selected file.
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
        fc = new nl.fontys.sofa.limo.view.util.ImportFileChooser();
    }

    public String getAbsoluteFilePath() {
        return tfFile.getText();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            tfFile.setText(fc.getSelectedFile().getPath());
        }
    }
}
