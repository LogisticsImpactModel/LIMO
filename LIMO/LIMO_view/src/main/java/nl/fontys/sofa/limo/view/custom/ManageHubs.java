/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.view.custom;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import nl.fontys.sofa.limo.view.custom.panel.ManageHubsPanel;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;

@ActionID(
        category = "Hub",
        id = "nl.fontys.sofa.limo.view.ManageHubs"
)
@ActionRegistration(
        displayName = "#CTL_ManageHubs"
)
//@ActionReference(path = "Menu/Data/Hub", position = 3333)
@Messages("CTL_ManageHubs=Manage Hubs")
public final class ManageHubs implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        int result = JOptionPane.showConfirmDialog(null, new ManageHubsPanel(), "Manage Hubs",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {

        }
    }
}
