package nl.fontys.sofa.limo.view.util;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.openide.util.Exceptions;

/**
 * FileUtil for actions that have to do with files.
 *
 * @author Sven Mäurer
 */
public final class FileUtil {

    private FileUtil() {
    }

    /**
     * Open a PDF with the default system PDF viewer.
     *
     * @param path of the resource
     * @param name of the PDF
     */
    public static void openPDF(String path, String name) {
        if (Desktop.isDesktopSupported()) {
            FileOutputStream fos = null;
            try {
                InputStream jarPdf = FileUtil.class.getClassLoader().getResourceAsStream(path + name);
                File pdfTemp = new File(name);
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
