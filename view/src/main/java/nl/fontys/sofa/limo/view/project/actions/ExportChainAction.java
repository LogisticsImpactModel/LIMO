/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.view.project.actions;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import nl.fontys.sofa.limo.domain.component.SupplyChain;
import nl.fontys.sofa.limo.view.util.ChainSaveFileChooser;
import org.apache.commons.io.FilenameUtils;
import org.openide.util.Exceptions;

/**
 *
 * @author nilsh
 */
public class ExportChainAction extends AbstractAction {

    SupplyChain supplyChain;

    public ExportChainAction(SupplyChain supplyChain) {
        this.supplyChain = supplyChain;
        putValue(NAME, "Export chain");
    }

    private void openFileChooser() throws HeadlessException, IOException {
        JFileChooser fc = new ChainSaveFileChooser();
        FileNameExtensionFilter chainFilter = new FileNameExtensionFilter(
                "Supply chains (*.lsc)", "lsc");

        if (supplyChain.getFilepath() != null) { //This happens if a supply chain is loaded.
            fc.setCurrentDirectory(new File(supplyChain.getFilepath()));
        }

        fc.setFileFilter(chainFilter);
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fc.setSelectedFile(new File(FilenameUtils.removeExtension(supplyChain.getName()))); // This sets the name without the extension
        int result = fc.showOpenDialog(null);
        String fileName = fc.getSelectedFile().getName(); //name with extension
        if (result == JFileChooser.APPROVE_OPTION) { //If folder is selected than save the supply chain.
            supplyChain.setName(fileName);
            File file = fc.getSelectedFile();
            supplyChain.saveToFile(file.getAbsolutePath() + ".lsc");
        } else { //If no folder is selected throw an exception so the saving process is cancelled.
            throw new IOException("The supply chain " + supplyChain.getName() + " is invalid.");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            openFileChooser();
        } catch (HeadlessException ex) {
            Exceptions.printStackTrace(ex);
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

}
