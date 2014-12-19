package nl.fontys.sofa.limo.view.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;
import nl.fontys.sofa.limo.view.topcomponent.ChainLoaderTopComponent;
import nl.fontys.sofa.limo.view.util.ChainFileChooser;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;

/**
 *
 * Just an action which does not open the top component if you discarded opening
 * a chain.
 *
 * @author Sven Mäurer
 */
@ActionID(
        category = "Window",
        id = "nl.fontys.sofa.limo.view.topcomponent.ChainLoaderTopComponent"
)
@ActionRegistration(
        iconBase = "icons/gui/link.gif",
        displayName = "#CTL_ChainLoaderAction"
)
@ActionReferences({
    @ActionReference(path = "Menu/File", position = 3),
    //@ActionReference(path = "Toolbars/File", position = 0),
    @ActionReference(path = "Shortcuts", name = "D-N")
})
@Messages({
    "CTL_ChainLoaderAction=Load Supply Chain...",
    "CTL_ChainLoaderTopComponent=Load Supply Chain"
})
public final class OpenChainAction implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        File chainFile = openSupplyChain();
        if (chainFile != null) {
            ChainLoaderTopComponent chainLoaderTopComponent = new ChainLoaderTopComponent(chainFile);
            chainLoaderTopComponent.open();
            chainLoaderTopComponent.requestActive();
        }
    }

    private File openSupplyChain() {
        JFileChooser fc = new ChainFileChooser();
        int result = fc.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            return fc.getSelectedFile();
        } else {
            return null;
        }
    }
}
