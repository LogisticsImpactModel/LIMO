/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.view.help;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle.Messages;

@ActionID(
        category = "Help",
        id = "nl.fontys.sofa.limo.view.help.OpenHandbook"
)
@ActionRegistration(
        displayName = "#CTL_OpenHandbook"
)
@ActionReference(path = "Menu/Help", position = 100)
@Messages("CTL_OpenHandbook=Open Handbook")
public final class OpenHandbook implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            Desktop.getDesktop().open(new File(getClass().getResource("/help/limo_manuel.pdf").getFile()));
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
    }
}
