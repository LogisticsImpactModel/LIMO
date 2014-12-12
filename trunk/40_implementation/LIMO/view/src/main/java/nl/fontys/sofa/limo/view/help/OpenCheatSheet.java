package nl.fontys.sofa.limo.view.help;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import nl.fontys.sofa.limo.view.util.FileUtil;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;

/**
 * Opens cheat sheet (cheatSheet.pdf) in the systems default PDF viewer.
 *
 * @author Sven Mäurer
 * @author Pascal Lindner
 */
@ActionID(
        category = "Help",
        id = "nl.fontys.sofa.limo.view.help.OpenCheatSheet"
)
@ActionRegistration(
        displayName = "#CTL_OpenCheatSheet"
)
@ActionReferences({
    @ActionReference(path = "Menu/Help", position = 1),
    @ActionReference(path = "Shortcuts", name = "F2")
})
@Messages("CTL_OpenCheatSheet=Cheat Sheet")
public final class OpenCheatSheet implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        FileUtil.openPDF("help/", "cheatSheet.pdf");
    }
}
