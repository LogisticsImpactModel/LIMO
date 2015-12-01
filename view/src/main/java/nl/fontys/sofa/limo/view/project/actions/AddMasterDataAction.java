/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.view.project.actions;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import nl.fontys.sofa.limo.view.project.SupplyProject;
import org.openide.awt.ActionID;
import org.openide.awt.ActionRegistration;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle.Messages;

/**
 *
 * @author nilsh
 */
@ActionID(
        category = "Window",
        id = "nl.fontys.sofa.limo.project.actions.AddMasterDataAction"
)
@ActionRegistration(
        iconBase = "icons/gui/Link_add.png",
        displayName = "#CTL_AddMasterDataAction"
)
@Messages({
    "CTL_AddMasterDataAction=Import Masterdata file"
})
public class AddMasterDataAction extends AbstractAction {

    private final SupplyProject project;

    public AddMasterDataAction(SupplyProject project) {
        this.project = project;
        putValue(NAME, "Import Masterdata file");
    }

    private File getMasterDataFile() {
        FileNameExtensionFilter fileNameExtensionFilter = new FileNameExtensionFilter("Master data file (.lef)", "lef");

        JFileChooser fc = new JFileChooser();
        fc.addChoosableFileFilter(fileNameExtensionFilter);
        fc.setAcceptAllFileFilterUsed(false);
        int result = fc.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            return fc.getSelectedFile();
        } else {
            return null;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        File file = getMasterDataFile();
        if (file != null) {
            try {
                String name = file.getName();
                name = name.substring(0, name.length() - 4);
                FileObject masterData = project.getProjectDirectory().getFileObject("master_data_files");
                FileUtil.copyFile(FileUtil.createData(file), masterData, name);
            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
    }

}
