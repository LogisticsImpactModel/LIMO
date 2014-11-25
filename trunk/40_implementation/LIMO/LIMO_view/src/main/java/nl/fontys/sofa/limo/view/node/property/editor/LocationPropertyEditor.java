package nl.fontys.sofa.limo.view.node.property.editor;

import com.sksamuel.gaia.Continent;
import com.sksamuel.gaia.Country;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyEditorSupport;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import nl.fontys.sofa.limo.domain.component.hub.Location;
import org.openide.nodes.PropertyEditorRegistration;

/**
 *
 * @author Dominik Kaisers <d.kaisers@student.fontys.nl>
 */
@PropertyEditorRegistration(targetType = Location.class)
public class LocationPropertyEditor extends PropertyEditorSupport {

    @Override
    public boolean supportsCustomEditor() {
        return true;
    }

    @Override
    public Component getCustomEditor() {
        final LocationEditor loc = new LocationEditor((Location) getValue());
        loc.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                if (loc.shouldSafe) {
                    setValue(loc.hubLocation);
                }
            }

        });
        return loc;
    }

    @Override
    public String getAsText() {
        return "Location";
    }

    private static class LocationEditor extends JDialog implements ActionListener {

        private Location hubLocation;
        private boolean shouldSafe;

        private JButton btnOk;
        private JButton btnCancel;

        private JComboBox cmbContinent;
        private JComboBox cmbCountry;
        private JTextField tfStreet;
        private JTextField tfNumber;
        private JTextField tfCity;
        private JTextField tfZip;
        private JTextField tfState;

        public LocationEditor(Location hubLocation) {
            this.hubLocation = hubLocation;
            this.shouldSafe = false;

            initComponents();
        }

        public Location getHubLocation() {
            return hubLocation;
        }

        public boolean isShouldSafe() {
            return shouldSafe;
        }

        public final void initComponents() {
            btnOk = new JButton("OK");
            btnOk.addActionListener(this);

            btnCancel = new JButton("Cancel");
            btnCancel.addActionListener(this);

            JPanel buttonBar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            buttonBar.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
            buttonBar.add(btnCancel);
            buttonBar.add(btnOk);

            cmbContinent = new JComboBox(Continent.values());
            cmbContinent.setSelectedItem(hubLocation.getContinent());

            cmbCountry = new JComboBox(hubLocation.getContinent().getCountries().toArray());
            cmbCountry.setSelectedItem(hubLocation.getCountry());

            tfStreet = new JTextField(hubLocation.getStreet());
            tfNumber = new JTextField(hubLocation.getHousenumber());
            tfCity = new JTextField(hubLocation.getTown());
            tfZip = new JTextField(hubLocation.getPostcode());
            tfState = new JTextField(hubLocation.getState());

            setLayout(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();
            c.fill = GridBagConstraints.HORIZONTAL;

            c.gridx = 0;
            c.gridy = 0;
            c.gridwidth = 1;
            c.weightx = 0.0;
            add(new JLabel("Continent"), c);
            c.gridx = 1;
            c.gridy = 0;
            c.gridwidth = 4;
            c.weightx = 0.8;
            add(cmbContinent, c);

            c.gridx = 0;
            c.gridy = 1;
            c.gridwidth = 1;
            c.weightx = 0.0;
            add(new JLabel("Country"), c);
            c.gridx = 1;
            c.gridy = 1;
            c.gridwidth = 4;
            c.weightx = 0.8;
            add(cmbCountry, c);

            c.gridx = 0;
            c.gridy = 2;
            c.gridwidth = 1;
            c.weightx = 0.0;
            add(new JLabel("Street"), c);
            c.gridx = 1;
            c.gridy = 2;
            c.gridwidth = 2;
            c.weightx = 0.4;
            add(tfStreet, c);
            c.gridx = 3;
            c.gridy = 2;
            c.gridwidth = 1;
            c.weightx = 0.0;
            add(new JLabel("Number"), c);
            c.gridx = 4;
            c.gridy = 2;
            c.gridwidth = 1;
            c.weightx = 0.2;
            add(tfNumber, c);

            c.gridx = 0;
            c.gridy = 3;
            c.gridwidth = 1;
            c.weightx = 0.0;
            add(new JLabel("City"), c);
            c.gridx = 1;
            c.gridy = 3;
            c.gridwidth = 2;
            c.weightx = 0.4;
            add(tfCity, c);
            c.gridx = 3;
            c.gridy = 3;
            c.gridwidth = 1;
            c.weightx = 0.0;
            add(new JLabel("Postcode"), c);
            c.gridx = 4;
            c.gridy = 3;
            c.gridwidth = 1;
            c.weightx = 0.2;
            add(tfZip, c);

            c.gridx = 0;
            c.gridy = 4;
            c.gridwidth = 1;
            c.weightx = 0.0;
            add(new JLabel("State"), c);
            c.gridx = 1;
            c.gridy = 4;
            c.gridwidth = 2;
            c.weightx = 0.4;
            add(tfState, c);

            c.gridx = 0;
            c.gridy = 5;
            c.gridwidth = 6;
            c.weightx = 1;
            add(buttonBar, c);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(btnOk)) {
                shouldSafe = true;

                Location loc = new Location();
                loc.setContinent((Continent) cmbContinent.getSelectedItem());
                loc.setCountry((Country) cmbCountry.getSelectedItem());
                loc.setStreet(tfStreet.getText());
                loc.setHousenumber(tfNumber.getText());
                loc.setTown(tfCity.getText());
                loc.setPostcode(tfZip.getText());
                loc.setState(tfState.getText());
                hubLocation = loc;
            }

            for (WindowListener wl : getWindowListeners()) {
                wl.windowClosing(null);
            }
            setVisible(false);
            dispose();
        }

    }

}
