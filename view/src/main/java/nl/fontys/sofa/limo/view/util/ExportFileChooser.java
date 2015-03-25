package nl.fontys.sofa.limo.view.util;

import java.io.File;
import javax.swing.JFileChooser;
import org.openide.util.NbPreferences;

/**
 * File chooser for export data which automatically opens the last chosen
 * directory.
 *
 * @author Sven Mäurer
 */
public class ExportFileChooser extends JFileChooser {

    public ExportFileChooser() {
        super(NbPreferences.forModule(ExportFileChooser.class).get("EXPORT_PATH", ""));
        setMultiSelectionEnabled(false);
        setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    }

    /**
     * Saves besides return the the selected file the location of the selected
     * file for further selections.
     *
     * @return File - the selected file.
     */
    @Override
    public File getSelectedFile() {
        File file = super.getSelectedFile();
        if (file != null) {
            NbPreferences.forModule(ChainFileChooser.class).put("EXPORT_PATH", file.getPath());
        }
        return file;
    }

}
