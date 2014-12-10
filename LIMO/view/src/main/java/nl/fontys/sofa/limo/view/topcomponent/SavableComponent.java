/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.view.topcomponent;

import java.io.File;
import java.io.IOException;
import javax.swing.JFileChooser;
import nl.fontys.sofa.limo.domain.component.SupplyChain;
import nl.fontys.sofa.limo.view.util.ChainFileFilter;
import org.netbeans.spi.actions.AbstractSavable;

/**
 *
 * @author d3vil
 */
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
//        fc.setFileFilter(new ChainFileFilter());
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
//        fc.setMultiSelectionEnabled(false);
        fc.showOpenDialog(null);

        File file = fc.getSelectedFile();
        supplyChain.setFilepath(file.getAbsolutePath() + File.separator + supplyChain.getName() + ".lsc");
        supplyChain.saveToFile();

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
