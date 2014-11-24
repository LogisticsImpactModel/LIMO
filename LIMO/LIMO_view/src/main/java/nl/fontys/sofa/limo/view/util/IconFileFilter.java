package nl.fontys.sofa.limo.view.util;

import java.io.File;
import javax.swing.filechooser.FileFilter;

public class IconFileFilter extends FileFilter {

    @Override
    public boolean accept(File f) {
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
}
