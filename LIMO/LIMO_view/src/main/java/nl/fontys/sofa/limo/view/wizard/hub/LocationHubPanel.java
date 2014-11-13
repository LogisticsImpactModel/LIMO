package nl.fontys.sofa.limo.view.wizard.hub;

import com.sksamuel.gaia.Continent;
import com.sksamuel.gaia.Country;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javax.swing.*;
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
        lblNumber = new JLabel(bundle.getString("NUMBER"));
        lblZip = new JLabel(bundle.getString("ZIP"));
        tfNumber = new JTextField();
        tfZip = new JTextField();

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.25;

        c.gridx = 0;
        c.gridy = 0;
        add(lblStreet, c);
        c.gridx = 1;
        c.gridy = 0;
        add(tfStreet, c);
        c.gridx = 2;
        c.gridy = 0;
        add(lblNumber, c);
        c.gridx = 3;
        c.gridy = 0;
        add(tfNumber, c);

        c.gridx = 0;
        c.gridy = 1;
        add(lblCity, c);
        c.gridx = 1;
        c.gridy = 1;
        add(tfCity, c);
        c.gridx = 2;
        c.gridy = 1;
        add(lblZip, c);
        c.gridx = 3;
        c.gridy = 1;
        add(tfZip, c);

        c.gridx = 0;
        c.gridy = 2;
        add(lblState, c);
        c.gridx = 1;
        c.gridy = 2;
        add(tfState, c);
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 3;
        add(lblContinent, c);
        c.gridx = 1;
        c.gridy = 3;
        c.gridwidth = 3;
        add(cmbContinent, c);
        c.gridx = 0;
        c.gridy = 4;
        add(lblCountry, c);
        c.gridx = 1;
        c.gridy = 4;
        c.gridwidth = 3;
        c.weightx = 0;
        add(cmbCountry, c);

        // codes = CountryCode.values();     
        ArrayList<String> countryList = new ArrayList();
        countryList.add(bundle.getString("NONE"));
        for (Country cou : Country.getAll()) {
            countryList.add(cou.getName());
        }
        //   countryList.addAll(CountryCode.getSortedNames());
        cmbCountry.setModel(new DefaultComboBoxModel(countryList.toArray()));

        ArrayList<String> continentList = new ArrayList();
        continentList.add(bundle.getString("NONE"));
        for (Continent cont : Continent.values()) {
            continentList.add(cont.getName());
        }
        //      for (int i = 0; i < Continents.values().length; ++i) {
        //           continentList.add(Continents.values()[i].toString());
//       }
        cmbContinent.setModel(new DefaultComboBoxModel(continentList.toArray()));
        cmbContinent.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<String> countryList = new ArrayList();
                countryList.add(bundle.getString("NONE"));
                if (Continent.values()[cmbContinent.getSelectedIndex() - 1].getCountries().size() >= 0) {
                    for (Country cou : Continent.values()[cmbContinent.getSelectedIndex() - 1].getCountries()) {
                        countryList.add(cou.getName());
                    }
                    cmbCountry.setModel(new DefaultComboBoxModel(countryList.toArray()));
                }
            }
        });
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
            cmbContinent.setSelectedItem(location.getContinent().name());
        }
    }

    public Location getHubLocation() {
        if (cmbContinent.getSelectedIndex() > 0) {
            location = new Location(Continent.values()[cmbContinent.getSelectedIndex() - 1]);

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
                location.setCountry(Country.getAll().get(cmbCountry.getSelectedIndex() - 1));
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
