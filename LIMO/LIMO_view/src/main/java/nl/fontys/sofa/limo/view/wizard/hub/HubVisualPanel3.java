/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.view.wizard.hub;

import com.sksamuel.gaia.Continent;
import com.sksamuel.gaia.Country;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import javax.swing.JPanel;
import nl.fontys.sofa.limo.domain.component.hub.Location;

public final class HubVisualPanel3 extends JPanel {
	private Object CountryCode;

    /**
     * Creates new form HubVisualPanel3
     */
    public HubVisualPanel3() {
        initComponents();
    }

    @Override
    public String getName() {
        return "Location";
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    private void initComponents() {

        lblStreet = new javax.swing.JLabel("Street");
        lblCity = new javax.swing.JLabel("City");
        lblState = new javax.swing.JLabel("State");
        lblCountry = new javax.swing.JLabel("Country");
        lblContinent = new javax.swing.JLabel("Continent");
        tfStreet = new javax.swing.JTextField();
        tfCity = new javax.swing.JTextField();
        tfState = new javax.swing.JTextField();
        cmbCountry = new javax.swing.JComboBox();
        cmbContinent = new javax.swing.JComboBox();
        lblNumber = new javax.swing.JLabel("Number");
        lblZip = new javax.swing.JLabel("ZIP");
        tfNumber = new javax.swing.JTextField();
        tfZip = new javax.swing.JTextField();

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

        c.gridx = 0;
        c.gridy = 3;
        add(lblCountry, c);
        c.gridx = 1;
        c.gridy = 3;
        c.gridwidth = 3;
        c.weightx = 0;
        add(cmbCountry, c);
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 4;
        add(lblContinent, c);
        c.gridx = 1;
        c.gridy = 4;
        c.gridwidth = 3;
        add(cmbContinent, c);

        // codes = CountryCode.values();     
        ArrayList<String> countryList = new ArrayList();
        countryList.add("None");
        for(Country cou : Country.getAll()){
            countryList.add(cou.getName());
        }
     //   countryList.addAll(CountryCode.getSortedNames());
        cmbCountry.setModel(new javax.swing.DefaultComboBoxModel(countryList.toArray()));

        ArrayList<String> continentList = new ArrayList();
        continentList.add("None");
        for(Continent cont: Continent.values()){
            continentList.add(cont.getName());
        }
  //      for (int i = 0; i < Continents.values().length; ++i) {
 //           continentList.add(Continents.values()[i].toString());
//       }
        cmbContinent.setModel(new javax.swing.DefaultComboBoxModel(continentList.toArray()));
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
            location = new Location(Continent.values()[cmbContinent.getSelectedIndex()-1]);

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
                location.setCountry(Country.getAll().get(cmbCountry.getSelectedIndex()-1));
            }
        }
        return location;
    }

    private javax.swing.JComboBox cmbContinent;
    private javax.swing.JComboBox cmbCountry;
    private javax.swing.JLabel lblCity;
    private javax.swing.JLabel lblContinent;
    private javax.swing.JLabel lblCountry;
    private javax.swing.JLabel lblNumber;
    private javax.swing.JLabel lblState;
    private javax.swing.JLabel lblStreet;
    private javax.swing.JLabel lblZip;
    private javax.swing.JTextField tfCity;
    private javax.swing.JTextField tfNumber;
    private javax.swing.JTextField tfState;
    private javax.swing.JTextField tfStreet;
    private javax.swing.JTextField tfZip;
    private Location location;
  //  private CountryCode[] codes;
}