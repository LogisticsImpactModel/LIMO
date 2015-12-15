package nl.fontys.sofa.limo.view.action;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import javax.swing.AbstractAction;
import nl.fontys.sofa.limo.view.chain.ChainGraphScene;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.Lookup;
import org.openide.util.NbBundle.Messages;
import org.openide.util.Utilities;
import nl.fontys.sofa.limo.view.node.Deletable;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;
import nl.fontys.sofa.limo.view.widget.LegWidget;
import org.netbeans.spi.palette.PaletteController;


/*
This action is called when you want to delete something from the chain or catalogs
using the garbage can or the delete button
*/

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

        ChainGraphScene scene = lkp.lookup(ChainGraphScene.class);
        
        //Remove object from the scene
        if (scene != null) {
            Set<?> objectSet = scene.getSelectedObjects();
            List<Deletable> deletableItems = new ArrayList<>();

            objectSet.stream().map((object) -> scene.findWidget(object)).filter((w) -> (w != null && w instanceof Deletable)).map((w) -> (Deletable) w).forEach((del) -> {
                deletableItems.add(del);
            });

            //This sort function ensures that the selected legs will be sorted above the hubs
            //This to prevent deleting a leg which was deleted while removing a hub from the chain
            //Since deleting a hub also removes all connected legs
            Collections.sort(deletableItems, (Object o1, Object o2) -> {
                if (o1.getClass().equals(o2.getClass())) {
                    return 0;
                }
                
                if (o1 instanceof LegWidget) {
                    return -1;
                }
                
                if (o2 instanceof LegWidget) {
                    return 1;
                }
                return 0;
            });

            deletableItems.stream().forEach((del) -> {
                del.delete();
            });
            
            scene.validate();
            return;
        }

        //This part is executed when in deleting from the catalog
        lkp.lookupAll(Deletable.class).stream().filter((del) -> (del != null)).forEach((del) -> {
            del.delete();
        });
    }

    public static void setPallete(PaletteController pallete) {
        PALETTE = pallete;
    }
}
