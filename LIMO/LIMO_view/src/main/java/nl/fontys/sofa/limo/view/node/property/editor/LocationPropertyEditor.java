package nl.fontys.sofa.limo.view.node.property.editor;

import com.sksamuel.gaia.Continent;
import com.sksamuel.gaia.Country;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyEditorSupport;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import nl.fontys.sofa.limo.domain.component.hub.Location;
import org.openide.nodes.PropertyEditorRegistration;

/**
 * Property editor for the location object of hubs.
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
            setModal(true);
            setLocationRelativeTo(null);
            setPreferredSize(new Dimension(480, 240));
            setTitle("Edit Location");

            initComponents();
        }

        public Location getHubLocation() {
            return hubLocation;
        }

        public boolean isShouldSafe() {
            return shouldSafe;
        }

        public final void initComponents() {
            JPanel container = new JPanel();
            container.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
            setLayout(new BorderLayout());
            add(container, BorderLayout.CENTER);

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
            cmbContinent.addItemListener(new ItemListener() {

                private Continent oldContinent = null;

                @Override
                public void itemStateChanged(ItemEvent e) {
                    if (e.getStateChange() == ItemEvent.DESELECTED) {
                        oldContinent = (Continent) e.getItem();
                    } else {
                        if (!oldContinent.equals(e.getItem())) {
                            cmbCountry.setModel(new DefaultComboBoxModel(getCountries((Continent) e.getItem())));
                            cmbCountry.setSelectedIndex(0);
                        }
                        oldContinent = null;
                    }
                }
            });

            cmbCountry = new JComboBox(getCountries(hubLocation.getContinent()));
            cmbCountry.setSelectedItem(hubLocation.getCountry().getName());
            cmbCountry.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    boolean enable = cmbCountry.getSelectedIndex() > 0;
                    tfStreet.setEnabled(enable);
                    tfNumber.setEnabled(enable);
                    tfCity.setEnabled(enable);
                    tfZip.setEnabled(enable);
                    tfState.setEnabled(enable);
                }
            });

            tfStreet = new JTextField(hubLocation.getStreet());
            tfNumber = new JTextField(hubLocation.getHousenumber());
            tfCity = new JTextField(hubLocation.getTown());
            tfZip = new JTextField(hubLocation.getPostcode());
            tfState = new JTextField(hubLocation.getState());

            boolean enable = cmbCountry.getSelectedIndex() > 0;
            tfStreet.setEnabled(enable);
            tfNumber.setEnabled(enable);
            tfCity.setEnabled(enable);
            tfZip.setEnabled(enable);
            tfState.setEnabled(enable);

            //<editor-fold defaultstate="collapsed" desc="Layout">
            container.setLayout(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();
            c.fill = GridBagConstraints.HORIZONTAL;

            c.gridx = 0;
            c.gridy = 0;
            c.gridwidth = 1;
            c.weightx = 0.0;
            container.add(new JLabel("Continent"), c);
            c.gridx = 1;
            c.gridy = 0;
            c.gridwidth = 4;
            c.weightx = 0.8;
            container.add(cmbContinent, c);

            c.gridx = 0;
            c.gridy = 1;
            c.gridwidth = 1;
            c.weightx = 0.0;
            container.add(new JLabel("Country"), c);
            c.gridx = 1;
            c.gridy = 1;
            c.gridwidth = 4;
            c.weightx = 0.8;
            container.add(cmbCountry, c);

            c.gridx = 0;
            c.gridy = 2;
            c.gridwidth = 1;
            c.weightx = 0.0;
            container.add(new JLabel("Street"), c);
            c.gridx = 1;
            c.gridy = 2;
            c.gridwidth = 2;
            c.weightx = 0.4;
            container.add(tfStreet, c);
            c.gridx = 3;
            c.gridy = 2;
            c.gridwidth = 1;
            c.weightx = 0.0;
            container.add(new JLabel("Number"), c);
            c.gridx = 4;
            c.gridy = 2;
            c.gridwidth = 1;
            c.weightx = 0.2;
            container.add(tfNumber, c);

            c.gridx = 0;
            c.gridy = 3;
            c.gridwidth = 1;
            c.weightx = 0.0;
            container.add(new JLabel("City"), c);
            c.gridx = 1;
            c.gridy = 3;
            c.gridwidth = 2;
            c.weightx = 0.4;
            container.add(tfCity, c);
            c.gridx = 3;
            c.gridy = 3;
            c.gridwidth = 1;
            c.weightx = 0.0;
            container.add(new JLabel("Postcode"), c);
            c.gridx = 4;
            c.gridy = 3;
            c.gridwidth = 1;
            c.weightx = 0.2;
            container.add(tfZip, c);

            c.gridx = 0;
            c.gridy = 4;
            c.gridwidth = 1;
            c.weightx = 0.0;
            container.add(new JLabel("State"), c);
            c.gridx = 1;
            c.gridy = 4;
            c.gridwidth = 2;
            c.weightx = 0.4;
            container.add(tfState, c);

            add(buttonBar, BorderLayout.SOUTH);
//</editor-fold>
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(btnOk)) {
                shouldSafe = true;

                Location loc = new Location();
                loc.setContinent((Continent) cmbContinent.getSelectedItem());
                loc.setCountry(countriesByName.get((String) cmbCountry.getSelectedItem()));
                if (cmbCountry.getSelectedIndex() > 0) {
                    loc.setStreet(tfStreet.getText());
                    loc.setHousenumber(tfNumber.getText());
                    loc.setTown(tfCity.getText());
                    loc.setPostcode(tfZip.getText());
                    loc.setState(tfState.getText());
                }
                hubLocation = loc;
            }

            for (WindowListener wl : getWindowListeners()) {
                wl.windowClosing(null);
            }
            setVisible(false);
            dispose();
        }

        private Map<String, Country> countriesByName;

        private String[] getCountries(Continent continent) {
            List<Country> countryList = continent == null ? Country.getAll() : continent.getCountries();
            String[] countries = new String[countryList.size() + 1];
            countries[0] = "";
            countriesByName = new HashMap<>(countryList.size());

            for (int i = 0; i < countryList.size(); i++) {
                Country country = countryList.get(i);

                countries[i + 1] = country.getName();
                countriesByName.put(country.getName(), country);
            }

            return countries;
        }

    }

}
