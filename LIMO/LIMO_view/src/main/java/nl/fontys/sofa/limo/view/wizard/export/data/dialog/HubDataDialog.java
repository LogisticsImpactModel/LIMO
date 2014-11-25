package nl.fontys.sofa.limo.view.wizard.export.data.dialog;

import com.jgoodies.forms.layout.FormLayout;
import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTextField;
import nl.fontys.sofa.limo.domain.component.hub.Hub;

/**
 * @author Matthias Brück
 */
public class HubDataDialog extends DataDialog<Hub> {

    
    private JLabel lblIcon, lblIconPreview, lblContinent, lblCountry, lblState, lblTown, lblPostcode, lblStreet, lblHouseNumber, lblLatitude, lblLongitude;
    private JTextField tfContinent, tfCountry, tfState, tfTown, tfPostcode, tfStreet, tfHouseNumber, tfLatitude, tfLongitude;
    private ImageIcon icon;

    public HubDataDialog(Hub entity) {
        super(entity);
        this.setSize(new Dimension(350, 750));
        this.setModal(true);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    @Override
    protected void initComponents(Hub entity) {
        layout = new FormLayout("5px, pref, 5px, pref:grow, 5px",
                "5px, pref, 5px, pref, 5px, pref, 5px, pref, 5px, pref, 5px, pref, 5px, pref, 5px, pref, 5px, pref, 5px, pref, 5px, pref, 5px");
        this.setLayout(layout);
        this.add(getComponentViewPanel(), cc.xyw(2, 2, 3));
        lblIcon = new JLabel("Icon");
        this.add(lblIcon, cc.xy(2, 4));
        icon = new ImageIcon(entity.getIcon().getImage());
        lblIconPreview = new JLabel(icon);
        this.add(lblIconPreview, cc.xy(4, 4));
        lblContinent = new JLabel("Continent");
        this.add(lblContinent, cc.xy(2, 6));
        tfContinent = new JTextField(entity.getLocation().getContinent().name());
        tfContinent.setEditable(false);
        this.add(tfContinent, cc.xy(4, 6));
        lblCountry = new JLabel("Country");
        this.add(lblCountry, cc.xy(2, 8));
        tfCountry = new JTextField();
        tfCountry.setEditable(false);
        if (entity.getLocation().getCountry() != null) {
            tfCountry.setText(entity.getLocation().getCountry().getValue());
        }
        this.add(tfCountry, cc.xy(4, 8));
        lblState = new JLabel("State");
        this.add(lblState, cc.xy(2, 10));
        tfState = new JTextField();
        tfState.setEditable(false);
        if (entity.getLocation().getState() != null) {
            tfState.setText(entity.getLocation().getState());
        }
        this.add(tfState, cc.xy(4, 10));
        lblTown = new JLabel("Town");
        this.add(lblTown, cc.xy(2, 12));
        tfTown = new JTextField();
        tfTown.setEditable(false);
        if (entity.getLocation().getTown() != null) {
            tfTown.setText(entity.getLocation().getTown());
        }
        this.add(tfTown, cc.xy(4, 12));
        lblPostcode = new JLabel("Postcode");
        this.add(lblPostcode, cc.xy(2, 14));
        tfPostcode = new JTextField();
        tfPostcode.setEditable(false);
        if (entity.getLocation().getPostcode() != null) {
            tfPostcode.setText(entity.getLocation().getPostcode());
        }
        this.add(tfPostcode, cc.xy(4, 14));
        lblStreet = new JLabel("Street");
        this.add(lblStreet, cc.xy(2, 16));
        tfStreet = new JTextField();
        tfStreet.setEditable(false);
        if (entity.getLocation().getStreet() != null) {
            tfStreet.setText(entity.getLocation().getStreet());
        }
        this.add(tfStreet, cc.xy(4, 16));
        lblHouseNumber = new JLabel("House Number");
        this.add(lblHouseNumber, cc.xy(2, 18));
        tfHouseNumber = new JTextField();
        tfHouseNumber.setEditable(false);
        if (entity.getLocation().getHousenumber() != null) {
            tfHouseNumber.setText(entity.getLocation().getHousenumber());
        }
        this.add(tfHouseNumber, cc.xy(4, 18));
        lblLatitude = new JLabel("Latitude");
        this.add(lblLatitude, cc.xy(2, 20));
        tfLatitude = new JTextField();
        tfLatitude.setEditable(false);
        if (entity.getLocation().getPosition() != null) {
            tfLatitude.setText(entity.getLocation().getPosition().getLatitude() + "");
        }
        this.add(tfLatitude, cc.xy(4, 20));
        lblLongitude = new JLabel("Longitude");
        this.add(lblLongitude, cc.xy(2, 22));
        tfLongitude = new JTextField();
        tfLongitude.setEditable(false);
        if (entity.getLocation().getPosition() != null) {
            tfLongitude.setText(entity.getLocation().getPosition().getLongitude() + "");
        }
        this.add(tfLongitude, cc.xy(4, 22));
    }
}
