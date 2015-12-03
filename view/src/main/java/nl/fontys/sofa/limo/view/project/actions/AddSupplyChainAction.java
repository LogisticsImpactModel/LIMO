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
import nl.fontys.sofa.limo.view.project.SupplyProject;
import nl.fontys.sofa.limo.view.project.node.SupplyChainNode;
import nl.fontys.sofa.limo.view.project.supplychain.ChainNodeList;
import nl.fontys.sofa.limo.view.util.ChainFileChooser;
import org.openide.awt.ActionID;
import org.openide.awt.ActionRegistration;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataObject;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle.Messages;

/**
 *
 * @author nilsh
 */
@ActionID(
        category = "Window",
        id = "nl.fontys.sofa.limo.project.actions.AddSupplyChainAction"
)
@ActionRegistration(
        iconBase = "icons/gui/Link_Add.png",
        displayName = "#CTL_AddSupplyChainAction"
)
@Messages({
    "CTL_AddSupplyChainAction=Import Masterdata file"
})
public class AddSupplyChainAction extends AbstractAction {

    private final SupplyProject project;

    public AddSupplyChainAction(SupplyProject project) {
        this.project = project;
        putValue(NAME, "Import SupplyChain file");
    }

    private File getMasterDataFile() {
        JFileChooser fc = new ChainFileChooser();
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
                FileObject chainFile = FileUtil.createData(file);
                FileObject masterData = project.getProjectDirectory().getFileObject("chains");
                FileUtil.copyFile(chainFile, masterData, name);
                ChainNodeList lookup = project.getChainNodeList();
                DataObject find = DataObject.find(chainFile);
                lookup.getChainNode().addChild(new SupplyChainNode(find));

            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
    }

}
