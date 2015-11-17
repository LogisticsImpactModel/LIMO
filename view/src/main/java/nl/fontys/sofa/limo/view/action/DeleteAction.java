package nl.fontys.sofa.limo.view.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.Lookup;
import org.openide.util.NbBundle.Messages;
import org.openide.util.Utilities;
import nl.fontys.sofa.limo.view.node.DeletableNode;
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
public final class DeleteAction implements ActionListener {

    private static PaletteController PALETTE;

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO implement action body

        Lookup lkp = Utilities.actionsGlobalContext();
        DeletableNode del = lkp.lookup(DeletableNode.class);

        if (del != null) {
            del.delete();
        }

        if (PALETTE != null) {
            DeletableNode paletteDel = PALETTE.getSelectedItem().lookup(DeletableNode.class); //DeletableNode.class

            if (paletteDel != null) {
                System.out.println("Entered the if");
                paletteDel.delete();
            }
        }
    }

    public static void setPallete(PaletteController pallete) {
        PALETTE = pallete;
    }

}
