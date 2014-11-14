package nl.fontys.sofa.limo.view.custom.pane;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ResourceBundle;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;
import nl.fontys.sofa.limo.api.service.provider.EventService;
import nl.fontys.sofa.limo.domain.component.event.Event;
import nl.fontys.sofa.limo.domain.component.event.ExecutionState;
import nl.fontys.sofa.limo.view.custom.table.EventTableModel;
import nl.fontys.sofa.limo.view.util.IconUtil;
import org.openide.util.Lookup;

public abstract class EventsPanel extends JPanel {

    private JLabel lblEvent;
    protected JComboBox cbEvents;
    protected JTable eventsTable;
    protected JButton btnAdd;
    protected JButton btnDelete;

    protected EventService service;
    protected List<Event> eventList;
    protected EventTableModel tableModel;
    protected static ResourceBundle bundle;

    public EventsPanel() {
        bundle = ResourceBundle.getBundle("nl/fontys/sofa/limo/view/wizard/event/Bundle");
        initComponents();
    }

    private void initComponents() {
        lblEvent = new JLabel(bundle.getString("EVENT"));
        cbEvents = new JComboBox();

        tableModel = new EventTableModel();
        eventsTable = new JTable(tableModel);
        TableColumn dependencyCol = eventsTable.getColumnModel().getColumn(1);
        dependencyCol.setCellEditor(new DefaultCellEditor(new JComboBox(ExecutionState.values())));

        btnAdd = new JButton(new ImageIcon(IconUtil.getIcon(IconUtil.UI_ICON.ADD)));
        btnAdd.setEnabled(false);
        btnDelete = new JButton(new ImageIcon(IconUtil.getIcon(IconUtil.UI_ICON.TRASH)));
        btnDelete.setEnabled(false);
        JPanel panelLeft = new JPanel();

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        c.weightx = 0.2;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        add(lblEvent, c);

        c.weightx = 0.7;
        c.gridx = 1;
        c.gridy = 0;
        add(cbEvents, c);

        c.weightx = 0.1;
        c.gridx = 2;
        c.gridy = 0;
        add(btnAdd, c);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JScrollPane(eventsTable), BorderLayout.CENTER);

        panelLeft.setLayout(new BoxLayout(panelLeft, BoxLayout.Y_AXIS));
        panelLeft.add(btnDelete);
        panel.add(panelLeft, BorderLayout.EAST);

        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 5;
        add(panel, c);

        setAddButtonListener();

        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (eventsTable.getSelectedRow() >= 0) {
                    tableModel.getEvents().remove(eventsTable.getSelectedRow());
                    tableModel.fireTableDataChanged();
                }
            }
        });

        service = Lookup.getDefault().lookup(EventService.class);
        eventList = service.findAll();
        setTableModel();
    }

    public void update(List<Event> events) {
        tableModel.getEvents().clear();
        tableModel.getEvents().addAll(events);
        tableModel.fireTableDataChanged();
        btnDelete.setEnabled(tableModel.getRowCount() > 0);
    }

    public List<Event> getEvents() {
        return tableModel.getEvents();
    }

    protected abstract void setAddButtonListener();

    protected abstract void setTableModel();

}
