package nl.fontys.sofa.limo.view.custom.panel;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
import nl.fontys.sofa.limo.view.wizard.event.EventWizardAction;
import org.openide.util.Lookup;

/**
 * Base class for wizards or property sheets that have to add events to a domain
 * object. The table model and the add action has to be implemented by the
 * implementing class.
 *
 * @author Sven Mäurer
 */
public abstract class EventsPanel extends JPanel {

    protected JTable eventsTable;
    protected JButton addButton;
    protected JButton deleteButton;
    protected JButton editButton;
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
        setEditButtonListener();
        setDeleteButtonListener();
        checkButtonsState();
    }

    /**
     * Initialize the view components.
     */
    protected void assignComponents() {
        eventsComboBox = new JComboBox();
        eventsTableModel = new EventTableModel();
        eventsTable = new JTable(eventsTableModel);
        executionStateComboBox = new JComboBox<>(ExecutionState.values());
        addButton = new JButton(new ImageIcon(IconUtil.getIcon(IconUtil.UI_ICON.ADD)));
        editButton = new JButton(new ImageIcon(IconUtil.getIcon(IconUtil.UI_ICON.EDIT)));
        deleteButton = new JButton(new ImageIcon(IconUtil.getIcon(IconUtil.UI_ICON.TRASH)));
    }

    private void initEventService() {
        service = Lookup.getDefault().lookup(EventService.class);
        allEvents = service.findAll();
    }

    protected void checkButtonsState() {
        deleteButton.setEnabled(eventsTableModel.getRowCount() > 0 && eventsTable.getSelectedRow() != -1);
        editButton.setEnabled(eventsTableModel.getRowCount() > 0 && eventsTable.getSelectedRow() != -1);
        addButton.setEnabled(eventsComboBox.getModel().getSize() > 0);
    }

    public List<Event> getEvents() {
        return eventsTableModel.getEvents();
    }

    /**
     * The add action where you have to add the event to the table model and
     * adapt the view status.
     */
    protected abstract void setAddButtonListener();

    /**
     * The edit action where you have to edit the event to the table model and
     * adapt the view status.
     */
    protected void setEditButtonListener() {
        editButton.addActionListener((ActionEvent e) -> {
            if (eventsTable.getSelectedRow() >= 0) {
                
                Event editEvent = eventsTableModel.getEvents().get(eventsTable.getSelectedRow());
                
                EventWizardAction wiz = new EventWizardAction(true);
                wiz.setEvent(editEvent);
                wiz.actionPerformed(null);
                
                editEvent = wiz.getEvent();
                eventsTableModel.fireTableDataChanged();
            }
        });
    }

    private void setTable() {
        TableColumn dependencyCol = eventsTable.getColumnModel().getColumn(1);
        dependencyCol.setCellEditor(new DefaultCellEditor(executionStateComboBox));
        setTableModel();
    }

    /**
     * Set the table model based on the parts of the event you need.
     */
    protected abstract void setTableModel();

    /**
     * Delete an event from the table model and adapt the view status.
     */
    private void setDeleteButtonListener() {
        deleteButton.addActionListener((ActionEvent e) -> {
            if (eventsTable.getSelectedRow() >= 0) {
                eventsTableModel.getEvents().remove(eventsTable.getSelectedRow());
                eventsTableModel.fireTableDataChanged();
                checkButtonsState();
            }
        });
    }

    /**
     * Set the events as the events of the table model.
     *
     * @param events to be used.
     */
    public void update(List<Event> events) {
        eventsTableModel.getEvents().clear();
        eventsTableModel.setEvents(events);
        eventsTableModel.fireTableDataChanged();
        checkButtonsState();
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

        eventsTable.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getSource().equals(eventsTable)) {
                    checkButtonsState();
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                //Not requierd
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                //Not requierd
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                //Not requierd
            }

            @Override
            public void mouseExited(MouseEvent e) {
                //Not requierd
            }
        });

        JPanel panelLeft = new JPanel();
        panelLeft.setLayout(new BoxLayout(panelLeft, BoxLayout.Y_AXIS));
        panelLeft.add(addButton);
        panelLeft.add(editButton);
        panelLeft.add(deleteButton);
        panel.add(panelLeft, BorderLayout.EAST);

        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 5;
        add(panel, c);
    }

}
