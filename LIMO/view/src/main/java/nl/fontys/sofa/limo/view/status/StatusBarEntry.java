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
import static nl.fontys.sofa.limo.api.service.status.StatusBarService.ACTION_NULL;
import static nl.fontys.sofa.limo.api.service.status.StatusBarService.STATE_NONE;
import org.openide.util.ImageUtilities;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author lnx
 */
@ServiceProvider(service = StatusBarService.class)
public class StatusBarEntry implements StatusBarService, ActionListener {

    private static final ImageIcon SUCCESS = new ImageIcon(ImageUtilities.loadImage("icons/green.png"));
    private static final ImageIcon NONE = new ImageIcon(ImageUtilities.loadImage("icons/grey.png"));
    private static final ImageIcon FAIL = new ImageIcon(ImageUtilities.loadImage("icons/red.png"));

    private static final String[] NAME = new String[]{"", "Error occures", "Success", "Error occures"};
    private static final String[] TEXT = new String[]{"", "Something was going wrong: ", "Successfully ", "Could not successfully "};
    private static final String[] ACTION = new String[]{"", "created ", "updated ", "deleted ", "founded ", "added ", "connected "};

    private final JPanel panel;
    private final JButton errorIndicatorButton;
    private final JLabel errorMessageLabel;
    private final StringBuilder errorMessageBuilder;

    private int statusState = STATE_NONE;
    private int number = 0;

    public StatusBarEntry() {
        panel = new JPanel();
        errorIndicatorButton = new JButton(NONE);
        errorMessageLabel = new JLabel();
        statusState = STATE_NONE;
        errorIndicatorButton.setToolTipText(NAME[statusState]);
        errorIndicatorButton.setOpaque(false);
        errorIndicatorButton.setContentAreaFilled(false);
        errorIndicatorButton.setBorderPainted(false);
        errorMessageBuilder = new StringBuilder("Following problem(s) occure(s): ");
        errorIndicatorButton.addActionListener(this);
        errorIndicatorButton.setEnabled(false);
        panel.setLayout(new BorderLayout());
        panel.add(errorIndicatorButton, BorderLayout.EAST);
        panel.add(errorMessageLabel, BorderLayout.CENTER);
    }

    @Override
    public JComponent getComponent() {
        return panel;
    }

    @Override
    public void setMessage(String msg, int action, int statusState, final Exception e) {
        if (e != null) {
            number++;
            errorMessageBuilder.append("\n \nError: ")
                    .append(e.getMessage())
                    .append("\n");
            for (StackTraceElement ste : e.getStackTrace()) {
                errorMessageBuilder.append(ste.toString())
                        .append("\n");
            }
            errorIndicatorButton.setEnabled(true);
        }
        this.statusState = statusState;
        setStatusIcon();
        errorIndicatorButton.setToolTipText(NAME[statusState]);
        if (number > 1) {
            errorMessageLabel.setText("[" + number + "] " + TEXT[statusState] + ACTION[action] + msg + "\t \t");
        } else {
            errorMessageLabel.setText(TEXT[statusState] + ACTION[action] + msg + "\t \t");
        }
    }

    private void setStatusIcon() {
        Icon icon;
        switch (this.statusState) {
            case STATE_SUCCESS:
                errorIndicatorButton.setEnabled(true);
                icon = SUCCESS;
                break;
            case STATE_FAIL:
                errorIndicatorButton.setEnabled(true);
                icon = FAIL;
                break;
            case STATE_NONE:
                errorIndicatorButton.setEnabled(false);
                icon = NONE;
                break;
            case STATE_ERROR:
                errorIndicatorButton.setEnabled(true);
                icon = FAIL;
                break;
            default:
                errorIndicatorButton.setEnabled(false);
                icon = NONE;
                break;
        }
        errorIndicatorButton.setIcon(icon);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JTextArea jta = new JTextArea(errorMessageBuilder.toString());
        JScrollPane jsp = new JScrollPane(jta) {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(480, 320);
            }
        };
        if (number > 0) {
            Object[] options = {"Ok"};
            int result = JOptionPane.showOptionDialog(null,
                    jsp, "Error",
                    JOptionPane.ERROR_MESSAGE,
                    JOptionPane.ERROR_MESSAGE,
                    null,
                    options,
                    options[0]);
            if (result == 0) {
                number = 0;
                errorMessageBuilder.setLength(0);
                errorMessageBuilder.append("Following problem(s) occure(s): ");
                setMessage("", ACTION_NULL, STATE_NONE, null);
            }
        }
    }

}
