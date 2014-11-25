package nl.fontys.sofa.limo.view.wizard.export.data.dialog;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import nl.fontys.sofa.limo.domain.BaseEntity;

/**
 * @author Matthias Brück
 */
public class BaseEntityViewPanel extends JPanel {

    private final CellConstraints cc;
    private final FormLayout layout;
    private final JLabel lblName, lblDescription;
    private final JTextField tfName;
    private final JTextArea taDescription;

    public BaseEntityViewPanel(BaseEntity entity) {
        cc = new CellConstraints();
        layout = new FormLayout("5px, pref, 10px, pref:grow, 5px", "5px, pref, 5px, pref:grow, 5px");
        this.setLayout(layout);
        lblName = new JLabel("Name");
        this.add(lblName, cc.xy(2, 2));
        tfName = new JTextField(entity.getName());
        tfName.setEditable(false);
        this.add(tfName, cc.xy(4, 2));
        lblDescription = new JLabel("Description");
        this.add(lblDescription, cc.xy(2, 4));
        taDescription = new JTextArea(entity.getDescription());
        taDescription.setEditable(false);
        this.add(taDescription, cc.xy(4, 4));
    }
}
