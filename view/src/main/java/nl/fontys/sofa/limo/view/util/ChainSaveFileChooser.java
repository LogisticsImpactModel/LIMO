package nl.fontys.sofa.limo.view.util;

import javax.swing.JFileChooser;

/**
 * Open a folder selection dialog with the title to select a folder.
 *
 * @author Sven Mäurer
 */
public class ChainSaveFileChooser extends JFileChooser {

    public ChainSaveFileChooser() {
        setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    }

    @Override
    public String getDialogTitle() {
        return LIMOResourceBundle.getString("SELECT FOLDER");
    }

}
