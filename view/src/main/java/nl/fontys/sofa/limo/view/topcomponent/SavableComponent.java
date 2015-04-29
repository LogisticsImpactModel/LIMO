package nl.fontys.sofa.limo.view.topcomponent;

import java.awt.HeadlessException;
import java.io.File;
import java.io.IOException;
import javax.swing.JFileChooser;
import nl.fontys.sofa.limo.domain.component.SupplyChain;
import nl.fontys.sofa.limo.view.InvalidSupplyChainException;
import nl.fontys.sofa.limo.view.chain.ChainBuilder;
import nl.fontys.sofa.limo.view.util.ChainSaveFileChooser;
import org.netbeans.spi.actions.AbstractSavable;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;

/**
 * SavableComponent enables saving of a supply chain.
 *
 * @author Sebastiaan Heijmann
 */
public class SavableComponent extends AbstractSavable {

    private final ChainBuilder chainBuilder;
    private final SupplyChain supplyChain;

    /**
     * Constructor creates a new SavableComponent.
     *
     * @param chainBuilder the chain builder which contains the supply chains.
     */
    public SavableComponent(ChainBuilder chainBuilder) {
        this.chainBuilder = chainBuilder;
        this.supplyChain = chainBuilder.getSupplyChain();
        register();
    }

    public void unregisterChainBuilder() {
        unregister();
    }

    @Override
    protected String findDisplayName() {
        return supplyChain.getName();
    }

    @Override
    protected void handleSave() throws IOException {
        if (chainBuilder.validate()) {
            if (supplyChain.getFilepath() != null) { 
                NotifyDescriptor dd = new NotifyDescriptor.Confirmation("Would you like to overwrite the existing supply chain file?");
                dd.setMessageType(DialogDescriptor.YES_NO_CANCEL_OPTION);
                Object retval = DialogDisplayer.getDefault().notify(dd);
                if (retval.equals(DialogDescriptor.YES_OPTION)) {
                    supplyChain.setFilepath(supplyChain.getFilepath()+ File.separator + supplyChain.getName()+".lsc" ); //
                    supplyChain.saveToFile();
                } else if (retval.equals(DialogDescriptor.NO_OPTION)) {
                    openFileChooser();
                }
            } else { //If a new supply chain needs to be saved.        
                openFileChooser();
            }

        } else { //When the supply chain is invalid, display a dialog.
            DialogDescriptor dialogDescriptor = new DialogDescriptor("The supply chain " + supplyChain.getName()
                    + " is invalid. An invalid supply chain cannot be saved. Please close the window and make the supply chain valid.", "Error while saving");

            dialogDescriptor.setMessageType(DialogDescriptor.PLAIN_MESSAGE);
            dialogDescriptor.setOptions(new Object[]{DialogDescriptor.OK_OPTION});
            Object retval = DialogDisplayer.getDefault().notify(dialogDescriptor);
            if (retval.equals(DialogDescriptor.OK_OPTION)) { //Throw an exception when the OK-button is selected so the saving process is cancelled
                throw new InvalidSupplyChainException("The supply chain " + supplyChain.getName() + " is invalid.");
            }
        }
    }

    private void openFileChooser() throws HeadlessException, IOException {
        JFileChooser fc = new ChainSaveFileChooser();
        if (supplyChain.getFilepath() != null) { //This happens if a supply chain is loaded. 
            fc.setCurrentDirectory(new File(supplyChain.getFilepath()).getParentFile());
        }
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fc.setSelectedFile(new File(supplyChain.getName()));
        int result = fc.showOpenDialog(null);
        String fileName= fc.getSelectedFile().getName();
        if (result == JFileChooser.APPROVE_OPTION) { //If folder is selected than save the supply chain.
                supplyChain.setName(fileName);
                File file = fc.getSelectedFile();
                supplyChain.setFilepath(file.getParent()+ File.separator+ supplyChain.getName() +".lsc" );
                supplyChain.saveToFile();
                unregister();
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

    @Override
    public int hashCode() {
        return supplyChain.hashCode();
    }

}
