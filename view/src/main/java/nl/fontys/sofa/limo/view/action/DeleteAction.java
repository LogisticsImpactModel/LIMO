package nl.fontys.sofa.limo.view.action;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.Lookup;
import org.openide.util.NbBundle.Messages;
import org.openide.util.Utilities;
import nl.fontys.sofa.limo.view.node.Deletable;
import nl.fontys.sofa.limo.view.node.WidgetableNode;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;
import nl.fontys.sofa.limo.view.widget.BasicWidget;
import org.netbeans.spi.palette.PaletteController;

@ActionID(
        category = "File",
        id = "nl.fontys.sofa.limo.view.action.DeleteAction"
)
@ActionRegistration(
        iconBase = "icons/gui/Delete-16.png",
        displayName = "#CTL_DeleteAction"
)
@ActionReferences({
    @ActionReference(path = "Menu/File", position = 1950, separatorAfter = 2025),
    @ActionReference(path = "Toolbars/Run", position = -60),
    @ActionReference(path = "Shortcuts", name = "DELETE")
})
@Messages("CTL_DeleteAction=Delete")
public final class DeleteAction extends AbstractAction {

    private static PaletteController PALETTE;

    public DeleteAction() {
        putValue(NAME, LIMOResourceBundle.getString("DELETE"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        Lookup lkp = Utilities.actionsGlobalContext();

        BasicWidget b = lkp.lookup(BasicWidget.class);
        System.out.println(b);
        if (b != null) {
            
        }

        for (Deletable del : lkp.lookupAll(Deletable.class)) {
            if (del != null) {
                del.delete();
            }
        }
    }

    public static void setPallete(PaletteController pallete) {
        PALETTE = pallete;
    }
}
