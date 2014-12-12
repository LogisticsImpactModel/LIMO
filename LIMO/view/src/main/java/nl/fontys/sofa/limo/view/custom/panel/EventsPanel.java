package nl.fontys.sofa.limo.view.custom.panel;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
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
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;
import org.openide.util.Lookup;

/**
 *
 * @author Sven Mäurer
 */
public abstract class EventsPanel extends JPanel {

    protected JTable eventsTable;
    protected JButton addButton;
    protected JButton deleteButton;
    protected JComboBox<Event> eventsComboBox;
    protected JComboBox<ExecutionState> executionStateComboBox;

    protected EventService service;
    protected List<Event> allEvents;
    protected EventTableModel eventsTableModel;
    protected DefaultComboBoxModel eventsComboBoxModel;

    public EventsPanel() {
        initComponents();
    }

    private void initComponents() {
        assignComponents();
        initEventService();
        buildView();
        setTable();
        setAddButtonListener();
        setDeleteButtonListener();
        checkAddButtonState();
        checkDeleteButtonState();
    }

    protected void assignComponents() {
        eventsComboBox = new JComboBox();
        eventsTableModel = new EventTableModel();
        eventsTable = new JTable(eventsTableModel);
        executionStateComboBox = new JComboBox<>(ExecutionState.values());
        addButton = new JButton(new ImageIcon(IconUtil.getIcon(IconUtil.UI_ICON.ADD)));
        deleteButton = new JButton(new ImageIcon(IconUtil.getIcon(IconUtil.UI_ICON.TRASH)));
    }

    private void initEventService() {
        service = Lookup.getDefault().lookup(EventService.class);
        allEvents = service.findAll();
    }

    protected void checkDeleteButtonState() {
        deleteButton.setEnabled(eventsTableModel.getRowCount() > 0);
    }

    protected void checkAddButtonState() {
        addButton.setEnabled(eventsComboBox.getModel().getSize() > 0);
    }

    public List<Event> getEvents() {
        return eventsTableModel.getEvents();
    }

    protected abstract void setAddButtonListener();

    private void setTable() {
        TableColumn dependencyCol = eventsTable.getColumnModel().getColumn(1);
        dependencyCol.setCellEditor(new DefaultCellEditor(executionStateComboBox));
        setTableModel();
    }

    protected abstract void setTableModel();

    private void setDeleteButtonListener() {
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (eventsTable.getSelectedRow() >= 0) {
                    eventsTableModel.getEvents().remove(eventsTable.getSelectedRow());
                    eventsTableModel.fireTableDataChanged();
                    checkAddButtonState();
                    checkDeleteButtonState();
                }
            }
        });
    }

    private void buildView() {
        setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        c.weightx = 0.2;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        add(new JLabel(LIMOResourceBundle.getString("EVENT")), c);

        c.weightx = 0.7;
        c.gridx = 1;
        c.gridy = 0;
        add(eventsComboBox, c);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JScrollPane(eventsTable), BorderLayout.CENTER);

        JPanel panelLeft = new JPanel();
        panelLeft.setLayout(new BoxLayout(panelLeft, BoxLayout.Y_AXIS));
        panelLeft.add(addButton);
        panelLeft.add(deleteButton);
        panel.add(panelLeft, BorderLayout.EAST);

        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 5;
        add(panel, c);
    }

    public void update(List<Event> events) {
        eventsTableModel.getEvents().clear();
        eventsTableModel.getEvents().addAll(events);
        eventsTableModel.fireTableDataChanged();
        checkDeleteButtonState();
    }

}
