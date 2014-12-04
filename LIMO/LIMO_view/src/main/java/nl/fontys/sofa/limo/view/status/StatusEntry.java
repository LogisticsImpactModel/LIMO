/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.view.status;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import nl.fontys.sofa.limo.api.service.status.StatusBarService;
import org.openide.util.ImageUtilities;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author lnx
 */
@ServiceProvider(service = StatusBarService.class)
public class StatusEntry implements StatusBarService {

    private int statusState = STATE_NONE;

    private static JPanel panel;
    private static JButton btn;
    private static JLabel label2;

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
    static String[] TEXT = new String[]{"", "Successfully ", "Could not successfully "};
    static String[] ACTION = new String[]{"create ", "update ", "delete ", "found "};

    private static final StatusEntry instance
            = new StatusEntry();

    public StatusEntry() {
        panel = new JPanel();
        btn = new JButton(imgNone);
        label2 = new JLabel();
        statusState = STATE_NONE;
        btn.setToolTipText(NAME[statusState]);
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        panel.setLayout(new BorderLayout());
        panel.add(btn, BorderLayout.EAST);
        panel.add(label2, BorderLayout.CENTER);
    }

    @Override
    public JComponent getComponent() {
        return panel;
    }

    @Override
    public void setMessage(String msg, int action, int statusState, Exception ee) {
       final  Exception e = ee;
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
        btn.setIcon(icon);
        if(statusState == 2){
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ex) {  
                    StringBuilder sb = new StringBuilder("Error: ");
                    sb.append(e.getMessage());
                    sb.append("\n");
                    for (StackTraceElement ste : e.getStackTrace()) {
                        sb.append(ste.toString());
                        sb.append("\n");
                    }
                    JTextArea jta = new JTextArea(sb.toString());
                    JScrollPane jsp = new JScrollPane(jta) {
                        @Override
                        public Dimension getPreferredSize() {
                            return new Dimension(480, 320);
                        }
                    };
                    JOptionPane.showMessageDialog(
                            null, jsp, "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        }
    btn.setToolTipText (NAME 

    [statusState]);
    label2.setText (TEXT 

[statusState] + ACTION[action] + msg + "\t \t");
    }
}
