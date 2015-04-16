package nl.fontys.sofa.limo.view.topcomponent;

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
    
    public void unregisterChainBuilder(){
        unregister();
    }

    @Override
    protected String findDisplayName() {
        return supplyChain.getName();
    }

    @Override
    protected void handleSave() throws IOException {
        if (chainBuilder.validate()) {
            JFileChooser fc = new ChainSaveFileChooser();
            fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int result = fc.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) { //If folder is selected than save the supply chain.
                File file = fc.getSelectedFile();
                supplyChain.setFilepath(file.getAbsolutePath() + File.separator + supplyChain.getName() + ".lsc");
                supplyChain.saveToFile();
                unregister();
            } else { //If no folder is selected throw an exception so the saving process is cancelled. 
                throw new IOException("The supply chain " + supplyChain.getName() + " is invalid.");
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
