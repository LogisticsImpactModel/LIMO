package nl.fontys.sofa.limo.view.util;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import org.openide.util.NbPreferences;

/**
 * File chooser for icons which automatically opens the last chosen directory.
 *
 * @author Sven Mäurer
 */
public class IconFileChooser extends JFileChooser {

    public IconFileChooser() {
        super(NbPreferences.forModule(IconFileChooser.class).get("ICON_PATH", ""));
        setFileFilter(new IconFileFilter());
        setFileSelectionMode(JFileChooser.FILES_ONLY);
        setMultiSelectionEnabled(false);
    }

    @Override
    public File getSelectedFile() {
        File file = super.getSelectedFile();
        if (file != null) {
            NbPreferences.forModule(IconFileChooser.class).put("ICON_PATH", file.getPath());
        }
        return file;
    }

    public class IconFileFilter extends FileFilter {

        @Override
        public boolean accept(File f) {
            if (f.isDirectory()) {
                return true;
            } else if (f.getAbsolutePath().endsWith(".png")) {
                return true;
            } else if (f.getAbsolutePath().endsWith(".bmp")) {
                return true;
            } else if (f.getAbsolutePath().endsWith(".jpeg")) {
                return true;
            }
            return f.getAbsolutePath().endsWith(".jpg");
        }

        @Override
        public String getDescription() {
            return "Filter for Images.";
        }
    }
}
