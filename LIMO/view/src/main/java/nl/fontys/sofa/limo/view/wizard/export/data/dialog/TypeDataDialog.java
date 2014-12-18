package nl.fontys.sofa.limo.view.wizard.export.data.dialog;

import com.jgoodies.forms.layout.FormLayout;
import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import nl.fontys.sofa.limo.domain.component.type.Type;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;

/**
 * This class provides a view for HubTypes and LegTypes. Also the abstract Type
 * implementation is supported. It extends the DataDialog.
 *
 * @author Matthias Brück
 */
public class TypeDataDialog extends DataDialog<Type> {

    private JLabel lblIcon, lblIconPreview;
    private ImageIcon icon;

    public TypeDataDialog(nl.fontys.sofa.limo.domain.component.type.Type entity) {
        super(entity);
        this.setSize(new Dimension(350, 750));
        this.setModal(true);
        this.setLocationRelativeTo(null);
    }

    @Override
    protected void initComponents(nl.fontys.sofa.limo.domain.component.type.Type entity) {
        layout = new FormLayout("5px, pref, 5px, pref:grow, 5px",
                "5px, pref, 5px, pref, 5px, pref, 5px");
        this.setLayout(layout);
        this.add(getComponentViewPanel(), cc.xyw(2, 2, 3));
        lblIcon = new JLabel(LIMOResourceBundle.getString("ICON"));
        this.add(lblIcon, cc.xy(2, 6));
        icon = new ImageIcon(entity.getIcon().getImage());
        lblIconPreview = new JLabel(icon);
        this.add(lblIconPreview, cc.xy(4, 6));
    }
}
