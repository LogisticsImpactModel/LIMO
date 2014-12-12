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
 * Opens the PDF manual (manual.pdf) in the systems default PDF viewer.
 *
 * @author Sven Mäurer
 * @author Pascal Lindner
 *
 */
@ActionID(
        category = "Help",
        id = "nl.fontys.sofa.limo.view.help.OpenManual"
)
@ActionRegistration(
        displayName = "#CTL_OpenManual"
)
@ActionReferences({
    @ActionReference(path = "Menu/Help", position = 0),
    @ActionReference(path = "Shortcuts", name = "F1")
})
@Messages("CTL_OpenManual=User Manual")
public final class OpenManual implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        FileUtil.openPDF("help/", "manual.pdf");
    }
}
