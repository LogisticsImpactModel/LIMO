/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.view.status;

import java.awt.BorderLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import nl.fontys.sofa.limo.api.service.status.StatusBarService;
import org.openide.util.ImageUtilities;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author lnx
 */
@ServiceProvider(service = StatusBarService.class)
public class Status implements StatusBarService {

    private int statusState = STATE_NONE;

    private static JPanel panel;
    private static JLabel label;
    private static JLabel label2;

    public static final int STATE_NONE = 0;
    public static final int STATE_SUCCESS = 1;
    public static final int STATE_FAIL = 2;

    private static final ImageIcon imgSuccess;
    private static final ImageIcon imgNone;
    private static final ImageIcon imgFail;

    // property change support    
    static {
        imgNone = new ImageIcon(ImageUtilities.loadImage("icons/grey.png"));
        imgSuccess = new ImageIcon(ImageUtilities.loadImage("icons/green.png"));
        imgFail = new ImageIcon(ImageUtilities.loadImage("icons/red.png"));

    }

    static String[] NAME = new String[]{"", "Success", "Error occures"};
    static String[] TEXT = new String[]{"", "Successfully created ", "could not successfully created "};

    private static final Status instance
            = new Status();

    public Status() {
        panel = new JPanel();
        label = new JLabel(imgNone);
        label2 = new JLabel();
        statusState = STATE_NONE;
        label.setToolTipText(NAME[statusState]);
        panel.setLayout(new BorderLayout());
        panel.add(label, BorderLayout.EAST);
        panel.add(label2, BorderLayout.CENTER);
    }

    @Override
    public JComponent getComponent() {
        return panel;
    }

    @Override
    public void setMessage(String msg, int statusState) {
        // check parameter ...
        this.statusState = statusState;
        // propertyChangeSupport ...
        Icon icon = imgNone;
        switch (this.statusState) {
            case STATE_SUCCESS:
                icon = imgSuccess;
                break;
            case STATE_FAIL:
                icon = imgFail;
                break;
            case STATE_NONE:
                icon = imgNone;
                break;
            default:
                icon = imgNone;
                break;
        } // switch this.connectionState
        label.setIcon(icon);
        label.setToolTipText(NAME[statusState]);
        label2.setText(TEXT[statusState] + msg + "\t \t");
    }
}
