package nl.fontys.sofa.limo.view.topcomponent;

import java.io.File;
import java.io.IOException;
import javax.swing.JFileChooser;
import nl.fontys.sofa.limo.domain.component.SupplyChain;
import org.netbeans.spi.actions.AbstractSavable;

public class SavableComponent extends AbstractSavable {

    private final SupplyChain supplyChain;

    public SavableComponent(SupplyChain supplyChain) {
        this.supplyChain = supplyChain;
        register();
    }

    @Override
    protected String findDisplayName() {
        return supplyChain.getName();
    }

    @Override
    protected void handleSave() throws IOException {
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int result = fc.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            supplyChain.setFilepath(file.getAbsolutePath() + File.separator + supplyChain.getName() + ".lsc");
            supplyChain.saveToFile();
        } else {
            System.out.println("No directory selected.");
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
