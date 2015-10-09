package nl.fontys.sofa.limo.view.wizard.hub;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import nl.fontys.sofa.limo.domain.component.hub.Continent;
import nl.fontys.sofa.limo.domain.component.hub.Location;
import nl.fontys.sofa.limo.domain.component.hub.SerializableCountry;
import nl.fontys.sofa.limo.validation.annotations.NotNull;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;

/**
 * Location input Panel in Hub.
 *
 * @author Pascal Lindner
 */
public final class LocationHubPanel extends JPanel {

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
    @NotNull
    private Location location;

    public LocationHubPanel() {
        initComponents();
    }

    @Override
    public String getName() {
        return LIMOResourceBundle.getString("LOCATION");
    }

    //Init Variables
    private void initComponents() {
        lblStreet = new JLabel(LIMOResourceBundle.getString("STREET"));
        lblCity = new JLabel(LIMOResourceBundle.getString("CITY"));
        lblState = new JLabel(LIMOResourceBundle.getString("STATE"));
        lblCountry = new JLabel(LIMOResourceBundle.getString("COUNTRY"));
        lblContinent = new JLabel(LIMOResourceBundle.getString("CONTINENT"));
        tfStreet = new JTextField();
        tfCity = new JTextField();
        tfState = new JTextField();
        cmbCountry = new JComboBox();
        cmbContinent = new JComboBox();
        lblNumber = new JLabel(" " + LIMOResourceBundle.getString("NUMBER"));
        lblZip = new JLabel(" " + LIMOResourceBundle.getString("ZIP"));
        tfNumber = new JTextField();
        tfZip = new JTextField();

        setLayout(new GridBagLayout());
        setLayoutConstraints();

        ArrayList<String> continents = new ArrayList<>();
        continents.add(LIMOResourceBundle.getString("NONE"));
        for (Continent continent : Continent.values()) {
            continents.add(continent.getName());
        }
        cmbContinent.setModel(new DefaultComboBoxModel(continents.toArray()));

        ArrayList<String> countryList = new ArrayList();
        countryList.add(LIMOResourceBundle.getString("NONE"));
        cmbCountry.setModel(new DefaultComboBoxModel(countryList.toArray()));
        setListener();
        cmbCountry.setEnabled(false);
        tfStreet.setEnabled(false);
        tfNumber.setEnabled(false);
        tfCity.setEnabled(false);
        tfZip.setEnabled(false);
        tfState.setEnabled(false);
    }

    //Set Layout Constraints
    public void setLayoutConstraints() {
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
    }

    //Set Listener
    public void setListener() {
        cmbContinent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selected = (String) cmbContinent.getSelectedItem();

                if (!selected.equals(LIMOResourceBundle.getString("NONE"))) {
                    ArrayList<String> continents = new ArrayList<>();
                    for (Continent continent : Continent.values()) {
                        continents.add(continent.getName());
                    }
                    cmbContinent.setModel(new DefaultComboBoxModel(continents.toArray()));
                    cmbContinent.setSelectedItem(selected);

                    String selectedCountry = (String) cmbCountry.getSelectedItem();
                    ArrayList<String> countries = new ArrayList<>();
                    countries.add(LIMOResourceBundle.getString("NONE"));
                    for (SerializableCountry country : Continent.values()[continents.indexOf(selected)].getCountries()) {
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
        cmbCountry.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selected = (String) cmbCountry.getSelectedItem();
                boolean enable = !selected.equals(LIMOResourceBundle.getString("NONE"));
                tfStreet.setEnabled(enable);
                tfNumber.setEnabled(enable);
                tfCity.setEnabled(enable);
                tfZip.setEnabled(enable);
                tfState.setEnabled(enable);
            }
        });

    }

    //Update Label for e.g. loading or chaning Hub
    public void updateLabel(Location location) {
        if (location != null) {
            tfStreet.setText(location.getStreet());
            tfNumber.setText(location.getHousenumber());
            tfCity.setText(location.getTown());
            tfZip.setText(location.getPostcode());
            tfState.setText(location.getState());
            cmbContinent.setSelectedItem(location.getContinent().getName());
            if (location.getCountry() != null) {
                cmbCountry.setSelectedItem(location.getCountry().getName());
            }
        } else {
            tfStreet.setText("");
            tfNumber.setText("");
            tfCity.setText("");
            tfZip.setText("");
            tfState.setText("");
            ArrayList<String> continents = new ArrayList<>();
            continents.clear();
            continents.add(LIMOResourceBundle.getString("NONE"));
            for (Continent continent : Continent.values()) {
                continents.add(continent.getName());
            }
            cmbContinent.setModel(new DefaultComboBoxModel(continents.toArray()));
            cmbCountry.setSelectedIndex(0);
        }
    }

    public Location getHubLocation() {
        boolean valid = !LIMOResourceBundle.getString("NONE").equals(cmbContinent.getSelectedItem());
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
                location.setCountry((SerializableCountry) location.getContinent().getCountries().get(cmbCountry.getSelectedIndex() - 1));
            }
        }
        return location;
    }

}
