package nl.fontys.sofa.limo.view.wizard.hub;

import com.sksamuel.gaia.Continent;
import com.sksamuel.gaia.Country;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import nl.fontys.sofa.limo.domain.component.hub.Location;

public final class LocationHubPanel extends JPanel {

    private final ResourceBundle bundle;

    public LocationHubPanel() {
        bundle = ResourceBundle.getBundle("nl/fontys/sofa/limo/view/Bundle");
        initComponents();
    }

    @Override
    public String getName() {
        return bundle.getString("LOCATION");
    }

    private void initComponents() {
        lblStreet = new JLabel(bundle.getString("STREET"));
        lblCity = new JLabel(bundle.getString("CITY"));
        lblState = new JLabel(bundle.getString("STATE"));
        lblCountry = new JLabel(bundle.getString("COUNTRY"));
        lblContinent = new JLabel(bundle.getString("CONTINENT"));
        tfStreet = new JTextField();
        tfCity = new JTextField();
        tfState = new JTextField();
        cmbCountry = new JComboBox();
        cmbContinent = new JComboBox();
        lblNumber = new JLabel(" " + bundle.getString("NUMBER"));
        lblZip = new JLabel(" " + bundle.getString("ZIP"));
        tfNumber = new JTextField();
        tfZip = new JTextField();

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.weightx = 0.0;
        add(lblContinent, c);
        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 4;
        c.weightx = 0.8;
        add(cmbContinent, c);

        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        c.weightx = 0.0;
        add(lblCountry, c);
        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 4;
        c.weightx = 0.8;
        add(cmbCountry, c);

        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 1;
        c.weightx = 0.0;
        add(lblStreet, c);
        c.gridx = 1;
        c.gridy = 2;
        c.gridwidth = 2;
        c.weightx = 0.4;
        add(tfStreet, c);
        c.gridx = 3;
        c.gridy = 2;
        c.gridwidth = 1;
        c.weightx = 0.0;
        add(lblNumber, c);
        c.gridx = 4;
        c.gridy = 2;
        c.gridwidth = 1;
        c.weightx = 0.2;
        add(tfNumber, c);

        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 1;
        c.weightx = 0.0;
        add(lblCity, c);
        c.gridx = 1;
        c.gridy = 3;
        c.gridwidth = 2;
        c.weightx = 0.4;
        add(tfCity, c);
        c.gridx = 3;
        c.gridy = 3;
        c.gridwidth = 1;
        c.weightx = 0.0;
        add(lblZip, c);
        c.gridx = 4;
        c.gridy = 3;
        c.gridwidth = 1;
        c.weightx = 0.2;
        add(tfZip, c);

        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 1;
        c.weightx = 0.0;
        add(lblState, c);
        c.gridx = 1;
        c.gridy = 4;
        c.gridwidth = 2;
        c.weightx = 0.4;
        add(tfState, c);

        ArrayList<String> continents = new ArrayList<>();
        continents.add(bundle.getString("NONE"));
        for (Continent continent : Continent.values()) {
            continents.add(continent.getName());
        }
        cmbContinent.setModel(new DefaultComboBoxModel(continents.toArray()));
        cmbContinent.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String selected = (String) cmbContinent.getSelectedItem();

                if (!selected.equals(bundle.getString("NONE"))) {
                    ArrayList<String> continents = new ArrayList<>();
                    for (Continent continent : Continent.values()) {
                        continents.add(continent.getName());
                    }
                    cmbContinent.setModel(new DefaultComboBoxModel(continents.toArray()));
                    cmbContinent.setSelectedItem(selected);

                    String selectedCountry = (String) cmbCountry.getSelectedItem();
                    ArrayList<String> countries = new ArrayList<>();
                    countries.add(bundle.getString("NONE"));
                    for (Country country : Continent.values()[continents.indexOf(selected)].getCountries()) {
                        countries.add(country.getName());
                    }
                    cmbCountry.setModel(new DefaultComboBoxModel(countries.toArray()));
                    if (countries.contains(selectedCountry)) {
                        cmbCountry.setSelectedItem(selectedCountry);
                    }
                    cmbCountry.setEnabled(true);
                }
            }
        });

        ArrayList<String> countryList = new ArrayList();
        countryList.add(bundle.getString("NONE"));
        cmbCountry.setModel(new DefaultComboBoxModel(countryList.toArray()));
        cmbCountry.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String selected = (String) cmbCountry.getSelectedItem();
                boolean enable = !selected.equals(bundle.getString("NONE"));

                tfStreet.setEnabled(enable);
                tfNumber.setEnabled(enable);
                tfCity.setEnabled(enable);
                tfZip.setEnabled(enable);
                tfState.setEnabled(enable);
            }
        });
        cmbCountry.setEnabled(false);

        tfStreet.setEnabled(false);
        tfNumber.setEditable(false);
        tfCity.setEnabled(false);
        tfZip.setEnabled(false);
        tfState.setEnabled(false);
    }

    public void updateLabel(Location location) {
        if (location != null) {
            tfStreet.setText(location.getStreet());
            tfNumber.setText(location.getHousenumber());
            tfCity.setText(location.getTown());
            tfZip.setText(location.getPostcode());
            tfState.setText(location.getState());

            if (location.getCountry() != null) {
                cmbCountry.setSelectedItem((location.getCountry().getName()));
            }

            cmbContinent.setSelectedItem(location.getContinent().getName());
        }
    }

    public Location getHubLocation() {
        boolean valid = !bundle.getString("NONE").equals(cmbContinent.getSelectedItem());
        if (valid) {
            location = new Location(Continent.values()[cmbContinent.getSelectedIndex()]);

            if (!tfStreet.getText().isEmpty()) {
                location.setStreet(tfStreet.getText());
            }
            if (!tfNumber.getText().isEmpty()) {
                location.setHousenumber(tfNumber.getText());
            }
            if (!tfCity.getText().isEmpty()) {
                location.setTown(tfCity.getText());
            }
            if (!tfZip.getText().isEmpty()) {
                location.setPostcode(tfZip.getText());
            }
            if (!tfState.getText().isEmpty()) {
                location.setState(tfState.getText());
            }

            if (cmbCountry.getSelectedIndex() > 0) {
                location.setCountry(location.getContinent().getCountries().get(cmbCountry.getSelectedIndex() - 1));
            }
        }
        return location;
    }

    private JComboBox cmbContinent;
    private JComboBox cmbCountry;
    private JLabel lblCity;
    private JLabel lblContinent;
    private JLabel lblCountry;
    private JLabel lblNumber;
    private JLabel lblState;
    private JLabel lblStreet;
    private JLabel lblZip;
    private JTextField tfCity;
    private JTextField tfNumber;
    private JTextField tfState;
    private JTextField tfStreet;
    private JTextField tfZip;
    private Location location;
}
