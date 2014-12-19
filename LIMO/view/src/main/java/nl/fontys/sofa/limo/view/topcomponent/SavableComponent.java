package nl.fontys.sofa.limo.view.topcomponent;

import java.io.File;
import java.io.IOException;
import javax.swing.JFileChooser;
import nl.fontys.sofa.limo.domain.component.SupplyChain;
import nl.fontys.sofa.limo.view.chain.ChainBuilder;
import nl.fontys.sofa.limo.view.util.ChainSaveFileChooser;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;
import org.netbeans.spi.actions.AbstractSavable;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;

public class SavableComponent extends AbstractSavable {

    private final ChainBuilder chainBuilder;
    private final SupplyChain supplyChain;

    public SavableComponent(ChainBuilder chainBuilder) {
        this.chainBuilder = chainBuilder;
        this.supplyChain = chainBuilder.getSupplyChain();
        register();
    }

    @Override
    protected String findDisplayName() {
        return supplyChain.getName();
    }

    @Override
    protected void handleSave() throws IOException {
        if (chainBuilder.validate()) {
            JFileChooser fc = new ChainSaveFileChooser();
            int result = fc.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                supplyChain.setFilepath(file.getAbsolutePath() + File.separator + supplyChain.getName() + ".lsc");
                supplyChain.saveToFile();
            } else {
                System.out.println(LIMOResourceBundle.getString("NO_DIRECTORY_SELECTED"));
            }
        } else {
            NotifyDescriptor d = new NotifyDescriptor.Message(
                    LIMOResourceBundle.getString("CHAIN_NOT_SAVABLE"),
                    NotifyDescriptor.ERROR_MESSAGE);
            DialogDisplayer.getDefault().notify(d);
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
