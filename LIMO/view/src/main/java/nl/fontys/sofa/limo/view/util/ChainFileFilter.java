package nl.fontys.sofa.limo.view.util;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 * Class responsible for validating the selection of a chain file from a file
 * chooser.
 *
 * @author Sebastiaan Heijmann
 */
public class ChainFileFilter extends FileFilter {

    @Override
    public boolean accept(File f) {
        if (f.getAbsolutePath().endsWith(".lsc")) {
            return true;
        }
        return false;
    }

    @Override
    public String getDescription() {
        return "Filter for supply chains.";
    }

}
