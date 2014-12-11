package nl.fontys.sofa.limo.view.custom.panel;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import nl.fontys.sofa.limo.api.service.provider.HubService;
import nl.fontys.sofa.limo.domain.component.Icon;
import nl.fontys.sofa.limo.domain.component.hub.Hub;
import nl.fontys.sofa.limo.view.custom.procedure.ProcedureComponent;
import nl.fontys.sofa.limo.view.util.IconFileChooser;
import nl.fontys.sofa.limo.view.util.IconUtil;
import nl.fontys.sofa.limo.view.wizard.hub.HubWizardAction;
import nl.fontys.sofa.limo.view.wizard.hub.LocationHubPanel;
import org.openide.util.Lookup;

public final class ManageHubsPanel extends JPanel {

    public ManageHubsPanel() {
        initComponents();
    }

    public void update() {
        HubService hubService = Lookup.getDefault().lookup(HubService.class);
        model.setList(hubService.findAll());
    }

    public void initComponents() {
        setLayout(new BorderLayout());
        HubService hubService = Lookup.getDefault().lookup(HubService.class);
        model = new HubTableModel(hubService.findAll());
        table = new JTable(model);
        add(table, BorderLayout.CENTER);
        panelRight = new JPanel();
        btnAdd = new JButton(new ImageIcon(IconUtil.getIcon(IconUtil.UI_ICON.ADD)));
        btnEdit = new JButton(new ImageIcon(IconUtil.getIcon(IconUtil.UI_ICON.EDIT)));
        btnDelete = new JButton(new ImageIcon(IconUtil.getIcon(IconUtil.UI_ICON.TRASH)));
        panelRight.setLayout(new BoxLayout(panelRight, BoxLayout.Y_AXIS));
        panelRight.add(btnAdd);
        panelRight.add(btnEdit);
        panelRight.add(btnDelete);
        add(panelRight, BorderLayout.EAST);

        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HubWizardAction hubWizard = new HubWizardAction();
                hubWizard.actionPerformed(e);
                update();
            }
        });

        btnEdit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                updateHub(table.getSelectedRow());
            }
        });

        btnDelete.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (table.getSelectedRow() > 0) {
                    model.remove(table.getSelectedRow());
                }
            }
        });

        fc = new IconFileChooser();
    }

    public void updateHub(int rowIndex) {
        final Hub hub = model.getHub(rowIndex);
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 0;
        JLabel lblName = new JLabel("Name:");
        panel.add(lblName, c);
        c.weightx = 1;
        c.gridwidth = 2;
        c.gridx = 1;
        c.gridy = 0;
        TextField tfName = new TextField(hub.getName());
        panel.add(tfName, c);
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        JLabel lblDesc = new JLabel("Description:");
        panel.add(lblDesc, c);
        c.weightx = 1;
        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 2;
        TextField tfDesc = new TextField(hub.getDescription());
        panel.add(tfDesc, c);
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 1;
        JLabel lblPfad = new JLabel("Image:");
        panel.add(lblPfad, c);
        c.weightx = 1;
        c.gridx = 1;
        c.gridy = 2;
        c.gridwidth = 2;
        JButton btnImage = new JButton("Choose");
        btnImage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int returnVal = fc.showOpenDialog(ManageHubsPanel.this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File icon = fc.getSelectedFile();
                    Icon newIcon = new Icon(new ImageIcon(icon.getAbsolutePath()).getImage(), icon.getPath().split("\\.")[icon.getPath().split("\\.").length]);
                    hub.setIcon(newIcon);
                }

            }
        });
        panel.add(btnImage, c);
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 1;
        JButton btnLocation = new JButton("Location");
        panel.add(btnLocation, c);
        c.weightx = 1;
        c.gridx = 1;
        c.gridy = 3;
        c.gridwidth = 1;
        btnLocation.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                LocationHubPanel panelLocation = new LocationHubPanel();
                if (hub.getLocation() != null) {
                    panelLocation.updateLabel(hub.getLocation());
                }
                int result = JOptionPane.showConfirmDialog(null, panelLocation, "Add/Change Location",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (result == JOptionPane.OK_OPTION) {
                    hub.setLocation(panelLocation.getHubLocation());
                }
            }
        });
        JButton btnC = new JButton("Costs/Delays/Times");
        panel.add(btnC, c);
        c.weightx = 1;
        c.gridx = 2;
        c.gridy = 3;
        c.gridwidth = 1;
        btnC.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ProcedureComponent cmp = new ProcedureComponent(hub.getProcedures());
                if (hub.getProcedures().size() > 0) {
                }
                int result = JOptionPane.showConfirmDialog(null, cmp, "Cost/Delays/Times",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (result == JOptionPane.OK_OPTION) {
                    hub.setProcedures(cmp.getActiveTableState());
                }
            }
        });
        JButton btnEvent = new JButton("Events");
        btnEvent.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel p = new JPanel();
                if (hub.getEvents().size() > 0) {

                }
                int result = JOptionPane.showConfirmDialog(null, p, "Events",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (result == JOptionPane.OK_OPTION) {

                }
            }
        });
        panel.add(btnEvent, c);
        int result = JOptionPane.showConfirmDialog(null, panel, "Edit Hub",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            hub.setName(tfName.getText());
            hub.setDescription(tfDesc.getText());
            model.updateHub(rowIndex, hub);
        }
    }

    private JTable table;
    private HubTableModel model;
    private JButton btnAdd;
    private JButton btnEdit;
    private JButton btnDelete;
    private JPanel panelRight;
    private JFileChooser fc;

    private class HubTableModel extends AbstractTableModel {

        private List<Hub> hubs;

        public HubTableModel(List<Hub> hubs) {
            this.hubs = hubs;
        }

        @Override
        public String getColumnName(int column) {
            switch (column) {
                case 0:
                    return "Name";
                case 1:
                    return "Description";
            }
            return null;
        }

        @Override
        public int getColumnCount() {
            return 2;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Hub hub = hubs.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return hub.getName();
                case 1:
                    return hub.getDescription();
            }
            return null;
        }

        @Override
        public int getRowCount() {
            return hubs.size();
        }

        public void addHub(Hub entrie) {
            hubs.add(entrie);
            fireTableDataChanged();
        }

        public List<Hub> getList() {
            return hubs;
        }

        public void setList(List<Hub> hubs) {
            this.hubs = hubs;
            fireTableDataChanged();
        }

        public Hub getHub(int rowIndex) {
            return hubs.get(rowIndex);
        }

        public void remove(int rowIndex) {
            HubService service = Lookup.getDefault().lookup(HubService.class);
            service.delete(hubs.get(rowIndex));
            hubs.remove(rowIndex);
            fireTableDataChanged();
        }

        public void updateHub(int rowIndex, Hub hub) {
            HubService service = Lookup.getDefault().lookup(HubService.class);
            service.update(hub);
            Hub uHub = hubs.get(rowIndex);
            uHub.setName(hub.getName());
            uHub.setDescription(hub.getDescription());
        }
    }
}
