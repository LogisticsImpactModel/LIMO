package nl.fontys.sofa.limo.view.topcomponent;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.rmi.activation.ActivateFailedException;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import nl.fontys.sofa.limo.domain.component.SupplyChain;
import nl.fontys.sofa.limo.view.InvalidSupplyChainException;
import nl.fontys.sofa.limo.view.chain.ChainBuilder;
import nl.fontys.sofa.limo.view.util.ChainSaveFileChooser;
import org.apache.commons.io.FilenameUtils;
import org.netbeans.spi.actions.AbstractSavable;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.util.ImageUtilities;
import org.openide.util.lookup.InstanceContent;
import org.openide.windows.TopComponent;

/**
 * SavableComponent enables saving of a supply chain.
 *
 * @author Sebastiaan Heijmann
 */
public class SavableComponent extends AbstractSavable {

    private final ChainBuilder chainBuilder;
    private final SupplyChain supplyChain;
    private final InstanceContent ic;
    private final TopComponent parent;
    private boolean isAdded = false;

    /**
     * Constructor creates a new SavableComponent.
     *
     * @param chainBuilder the chain builder which contains the supply chains.
     */
    public SavableComponent(ChainBuilder chainBuilder, InstanceContent ic, TopComponent parent) {
        this.chainBuilder = chainBuilder;
        this.supplyChain = chainBuilder.getSupplyChain();
        this.ic = ic;
        this.parent = parent;
        chainBuilder.addListener((ActionEvent e) -> {
            if (isAdded) {
                return;
            }
            ImageIcon saveIcon = new ImageIcon(getClass().getClassLoader().getResource("icons/gui/save.png"));
            parent.setIcon(ImageUtilities.icon2Image(saveIcon));
            parent.getParent().revalidate();
            ic.add(this);
            register();
            isAdded = true;
        });

    }

    @Override
    protected String findDisplayName() {
        return supplyChain.getName().replace(".lsc", "");
    }

    private void finishSave() {
        ic.remove(this);
        unregister();
        ImageIcon link = new ImageIcon(getClass().getClassLoader().getResource("icons/gui/Link.png"));
        parent.setIcon(ImageUtilities.icon2Image(link));
        parent.getParent().revalidate();
        isAdded = false;

    }

    private void saveComponent() throws IOException {
        if (chainBuilder.validate()) {
            if (supplyChain.getFilepath() != null) {
                NotifyDescriptor dd = new NotifyDescriptor.Confirmation("Would you like to overwrite the '" + supplyChain.getName().replace(".lsc", "") + "' supply chain file?");
                dd.setMessageType(DialogDescriptor.YES_NO_CANCEL_OPTION);
                Object retval = DialogDisplayer.getDefault().notify(dd);
                if (retval.equals(DialogDescriptor.YES_OPTION)) {
                    supplyChain.setFilepath(supplyChain.getFilepath()); //
                    supplyChain.saveToFile();
                    finishSave();
                } else if (retval.equals(DialogDescriptor.NO_OPTION)) {
                    openFileChooser();
                } else if (retval.equals(DialogDescriptor.CANCEL_OPTION)) {
                    throw new ActivateFailedException("The chain should not close because of the canClose method in topComponent");
                }
            } else { //If a new supply chain needs to be saved.
                openFileChooser();
            }

        } else { //When the supply chain is invalid, display a dialog.
            DialogDescriptor dialogDescriptor = new DialogDescriptor("The supply chain " + supplyChain.getName().replace(".lsc", "")
                    + " is invalid. An invalid supply chain cannot be saved. Please close the window and make the supply chain valid.", "Error while saving");

            dialogDescriptor.setMessageType(DialogDescriptor.PLAIN_MESSAGE);
            dialogDescriptor.setOptions(new Object[]{DialogDescriptor.OK_OPTION});
            Object retval = DialogDisplayer.getDefault().notify(dialogDescriptor);
            if (retval.equals(DialogDescriptor.OK_OPTION)) { //Throw an exception when the OK-button is selected so the saving process is cancelled
                throw new InvalidSupplyChainException("The supply chain " + supplyChain.getName().replace(".lsc", "") + " is invalid.");
            }
        }
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
            if (supplyChain.getName().endsWith(".lsc")) {
                supplyChain.setFilepath(file.getParent() + File.separator + supplyChain.getName());
            } else {
                supplyChain.setFilepath(file.getParent() + File.separator + supplyChain.getName() + ".lsc");
            }
            supplyChain.saveToFile();
            finishSave();
        } else { //If no folder is selected throw an exception so the saving process is cancelled.
            throw new IOException("The supply chain " + supplyChain.getName() + " is invalid.");
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SavableComponent) {
            return ((SavableComponent) obj).supplyChain.equals(supplyChain);
        }
        return false;
    }

    /*@Override
     public String toString() {
     return supplyChain.getName().replace(".lsc", "");
     }*/
    @Override
    public int hashCode() {
        return supplyChain.hashCode();
    }

    @Override
    protected void handleSave() throws IOException {
        saveComponent();
    }

}
