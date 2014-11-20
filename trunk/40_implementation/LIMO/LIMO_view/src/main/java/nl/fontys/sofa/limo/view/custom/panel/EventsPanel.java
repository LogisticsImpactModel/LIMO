package nl.fontys.sofa.limo.view.custom.panel;

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

    private JLabel lbl_event;
    protected JComboBox<Event> cbox_addEvent;
    protected JTable tbl_usedEvents;
    protected JButton btn_add;
    protected JButton btn_delete;

    protected EventService service;
    protected List<Event> allEvents;
    protected EventTableModel tblmdl_usedEvents;
    protected static ResourceBundle bundle;
    protected JComboBox<ExecutionState> cbox_executionState;

    public EventsPanel() {
        bundle = ResourceBundle.getBundle("nl/fontys/sofa/limo/view/Bundle");
        initComponents();
    }

    private void initComponents() {
        lbl_event = new JLabel(bundle.getString("EVENT"));
        cbox_addEvent = new JComboBox();

        tblmdl_usedEvents = new EventTableModel();
        tbl_usedEvents = new JTable(tblmdl_usedEvents);
        TableColumn dependencyCol = tbl_usedEvents.getColumnModel().getColumn(1);
        cbox_executionState = new JComboBox<>(ExecutionState.values());
        dependencyCol.setCellEditor(new DefaultCellEditor(cbox_executionState));

        btn_add = new JButton(new ImageIcon(IconUtil.getIcon(IconUtil.UI_ICON.ADD)));
        btn_add.setEnabled(false);
        btn_delete = new JButton(new ImageIcon(IconUtil.getIcon(IconUtil.UI_ICON.TRASH)));
        btn_delete.setEnabled(false);
        JPanel panelLeft = new JPanel();

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        c.weightx = 0.2;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        add(lbl_event, c);

        c.weightx = 0.7;
        c.gridx = 1;
        c.gridy = 0;
        add(cbox_addEvent, c);

        c.weightx = 0.1;
        c.gridx = 2;
        c.gridy = 0;
        add(btn_add, c);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JScrollPane(tbl_usedEvents), BorderLayout.CENTER);

        panelLeft.setLayout(new BoxLayout(panelLeft, BoxLayout.Y_AXIS));
        panelLeft.add(btn_delete);
        panel.add(panelLeft, BorderLayout.EAST);

        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 5;
        add(panel, c);

        btn_delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tbl_usedEvents.getSelectedRow() >= 0) {
                    tblmdl_usedEvents.getEvents().remove(tbl_usedEvents.getSelectedRow());
                    tblmdl_usedEvents.fireTableDataChanged();
                }
            }
        });

        service = Lookup.getDefault().lookup(EventService.class);
        allEvents = service.findAll();
        setTableModel();
        setAddButtonListener();
    }

    protected void update(List<Event> events) {
        tblmdl_usedEvents.getEvents().clear();
        tblmdl_usedEvents.getEvents().addAll(events);
        tblmdl_usedEvents.fireTableDataChanged();
        checkDeleteButtonState();
    }

    protected void checkDeleteButtonState() {
        btn_delete.setEnabled(tblmdl_usedEvents.getRowCount() > 0);
    }

    protected void checkAddButtonState() {
        btn_add.setEnabled(cbox_addEvent.getModel().getSize() > 0);
    }

    public List<Event> getEvents() {
        return tblmdl_usedEvents.getEvents();
    }

    protected abstract void setAddButtonListener();

    protected abstract void setTableModel();

}
