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
import java.util.List;
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
    private static StringBuilder sb;

    private static List<Exception> excp;

    private static final ImageIcon imgSuccess;
    private static final ImageIcon imgNone;
    private static final ImageIcon imgFail;

    private int number = 0;

    // property change support    
    static {
        imgNone = new ImageIcon(ImageUtilities.loadImage("icons/grey.png"));
        imgSuccess = new ImageIcon(ImageUtilities.loadImage("icons/green.png"));
        imgFail = new ImageIcon(ImageUtilities.loadImage("icons/red.png"));

    }

    static String[] NAME = new String[]{"", "Error occures", "Success", "Error occures"};
    static String[] TEXT = new String[]{"", "Something was going wrong: ", "Successfully ", "Could not successfully "};
    static String[] ACTION = new String[]{"", "created ", "updated ", "deleted ", "founded ", "added ", "connected "};

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
        sb = new StringBuilder("Following problem(s) occure(s): ");
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ex) {
                JTextArea jta = new JTextArea(sb.toString());
                JScrollPane jsp = new JScrollPane(jta) {
                    @Override
                    public Dimension getPreferredSize() {
                        return new Dimension(480, 320);
                    }
                };
                if (number > 0) {
                    Object[] options = {"Ok"};
                    int n = JOptionPane.showOptionDialog(null,
                            jsp, "Error",
                            JOptionPane.ERROR_MESSAGE,
                            JOptionPane.ERROR_MESSAGE,
                            null,
                            options,
                            options[0]);
                    if (n == 0) {
                        number = 0;
                        sb.setLength(0);
                        sb.append("Following problem(s) occure(s): ");
                        setMessage("", ACTION_NULL, STATE_NONE, null);
                    }
                }
            }
        });
        btn.setEnabled(false);
        panel.setLayout(new BorderLayout());
        panel.add(btn, BorderLayout.EAST);
        panel.add(label2, BorderLayout.CENTER);
    }

    @Override
    public JComponent getComponent() {
        return panel;
    }

    @Override
    public void setMessage(String msg, int action, int statusState, final Exception e) {
        if (e != null) {
            number++;
            sb.append("\n \nError: ");

            sb.append(e.getMessage());
            sb.append("\n");
            for (StackTraceElement ste : e.getStackTrace()) {
                sb.append(ste.toString());
                sb.append("\n");
            }
            btn.setEnabled(true);
        }
        this.statusState = statusState;
        Icon icon = imgNone;
        switch (this.statusState) {
            case STATE_SUCCESS:
                btn.setEnabled(true);
                icon = imgSuccess;
                break;
            case STATE_FAIL:
                btn.setEnabled(true);
                icon = imgFail;
                break;
            case STATE_NONE:
                btn.setEnabled(false);
                icon = imgNone;
                break;
            case STATE_ERROR:
                btn.setEnabled(true);
                icon = imgFail;
                break;
            default:
                btn.setEnabled(false);
                icon = imgNone;
                break;
        }
        btn.setIcon(icon);
        btn.setToolTipText(NAME[statusState]);
        if (number > 1) {
            label2.setText("[" + number + "] " + TEXT[statusState] + ACTION[action] + msg + "\t \t");
        } else {
            label2.setText(TEXT[statusState] + ACTION[action] + msg + "\t \t");
        }
    }
}
