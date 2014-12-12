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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle.Messages;

/**
 * Opens the PDF manual (manual.pdf) in the systems default PDF viewer
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
        String pdf = "help/limo_manual.pdf";
        if (Desktop.isDesktopSupported()) {
            FileOutputStream fos = null;
            try {
                InputStream jarPdf = getClass().getClassLoader().getResourceAsStream(pdf);
                File pdfTemp = new File("manual.pdf");
                fos = new FileOutputStream(pdfTemp);
                while (jarPdf.available() > 0) {
                    fos.write(jarPdf.read());
                }
                Desktop.getDesktop().open(pdfTemp);
            } catch (FileNotFoundException ex) {
                Exceptions.printStackTrace(ex);
            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
            } finally {
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException ex) {
                        Exceptions.printStackTrace(ex);
                    }
                }
            }
        }
    }
}
