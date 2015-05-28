package nl.fontys.sofa.limo.view.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import nl.fontys.sofa.limo.view.topcomponent.ChainBuilderTopComponent;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;

/**
 *
 * Just an action which does not open the top component if you discarded
 * entering the name for a chain.
 *
 * @author Sven Mäurer
 */
@ActionID(
        category = "Window",
        id = "nl.fontys.sofa.limo.view.action.NewChainAction"
)
@ActionRegistration(
        iconBase = "icons/gui/file_add.png",
        displayName = "#CTL_NewChainAction"
)
@ActionReferences({
    @ActionReference(path = "Menu/File", position = 2),
    @ActionReference(path = "Shortcuts", name = "D-N"),
    @ActionReference(path = "Toolbars/File", position = 10)
})
@Messages({
    "CTL_NewChainAction=New Supply Chain..."
})
public final class NewChainAction implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        NotifyDescriptor.InputLine dd
                = new DialogDescriptor.InputLine(
                        LIMOResourceBundle.getString("NAME"),
                        LIMOResourceBundle.getString("SET_NAME_OF",
                                LIMOResourceBundle.getString("SUPPLY_CHAIN")));

        if (DialogDisplayer.getDefault().notify(dd) == NotifyDescriptor.OK_OPTION) {
            String name = dd.getInputText();
            ChainBuilderTopComponent chainBuilderTopComponent = new ChainBuilderTopComponent(name);
            chainBuilderTopComponent.open();
            chainBuilderTopComponent.requestActive();
        }
    }

}
