/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.view.util;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import org.openide.util.NbPreferences;

/**
 * File chooser for import data which automatically opens the last chosen
 * directory.
 *
 * @author Sven Mäurer
 */
public class ImportFileChooser extends JFileChooser {

    public ImportFileChooser() {
        super(NbPreferences.forModule(ExportFileChooser.class).get("IMPORT_PATH", ""));
        setMultiSelectionEnabled(false);
        setDialogTitle("Import");
        setApproveButtonText("Import");
        setFileSelectionMode(JFileChooser.FILES_ONLY);
        setDialogType(JFileChooser.OPEN_DIALOG);
        setFileFilter(new FileFilter() {

            @Override
            public boolean accept(File f) {
                if (f.isDirectory()) {
                    return true;
                }
                return f.getAbsolutePath().endsWith(".lef");
            }

            @Override
            public String getDescription() {
                return LIMOResourceBundle.getString("IMPORT_FILES");
            }
        });
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
            NbPreferences.forModule(ChainFileChooser.class).put("IMPORT_PATH", file.getPath());
        }
        return file;
    }

}
