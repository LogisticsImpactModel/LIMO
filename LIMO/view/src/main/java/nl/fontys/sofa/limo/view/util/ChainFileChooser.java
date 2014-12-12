package nl.fontys.sofa.limo.view.util;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import org.openide.util.NbPreferences;

/**
 * File chooser for supply chain files which automatically opens the last chosen
 * directory.
 *
 * @author Sven Mäurer
 */
public class ChainFileChooser extends JFileChooser {

    /**
     * Sets the directory to be opened to the last opened one.
     */
    public ChainFileChooser() {
        super(NbPreferences.forModule(ChainFileFilter.class).get("CHAIN_PATH", ""));
        setFileFilter(new ChainFileChooser.ChainFileFilter());
        setFileSelectionMode(JFileChooser.FILES_ONLY);
        setMultiSelectionEnabled(false);
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
            NbPreferences.forModule(ChainFileChooser.class).put("CHAIN_PATH", file.getPath());
        }
        return file;
    }

    /**
     * Class responsible for validating the selection of a chain file from a
     * file chooser.
     *
     * @author Sebastiaan Heijmann
     */
    private class ChainFileFilter extends FileFilter {

        @Override
        public boolean accept(File f) {
            if (f.isDirectory()) {
                return true;
            } else if (f.getAbsolutePath().endsWith(".lsc")) {
                return true;
            }
            return false;
        }

        @Override
        public String getDescription() {
            return "Supply Chain Files (.lsc)";
        }

    }
}
