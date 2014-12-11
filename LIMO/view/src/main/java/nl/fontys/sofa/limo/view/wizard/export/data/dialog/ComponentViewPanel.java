package nl.fontys.sofa.limo.view.wizard.export.data.dialog;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import nl.fontys.sofa.limo.domain.component.Component;
import nl.fontys.sofa.limo.domain.component.event.Event;
import nl.fontys.sofa.limo.domain.component.event.ExecutionState;
import nl.fontys.sofa.limo.domain.component.procedure.Procedure;

/**
 * @author Matthias Brück
 */
public class ComponentViewPanel extends JPanel {

    private final JLabel lblEvent, lblProcedure;
    private final JTable tblEvents, tblProcedures;
    private final DefaultTableModel tblmdlEvents, tblmdlProcedures;
    private final Event[] events;
    private final CellConstraints cc;
    private final FormLayout layout;

    public ComponentViewPanel(Component entity) {
        cc = new CellConstraints();
        layout = new FormLayout("5px, pref, 5px, pref:grow, 5px", "5px, pref, 5px, pref, 5px, 125, 5px, pref, 5px, 125, 5px");
        this.setLayout(layout);

        this.add(new BaseEntityViewPanel(entity), cc.xyw(2, 2, 3));

        lblEvent = new JLabel("Events");
        this.add(lblEvent, cc.xyw(2, 4, 3));
        events = entity.getEvents().toArray(new Event[]{});
        Object[][] eventsModel = new Object[events.length][2];
        for (int i = 0; i < events.length; i++) {
            eventsModel[i][0] = events[i].getName();
            eventsModel[i][1] = events[i].getDependency();
        }
        tblmdlEvents = new DefaultTableModel(eventsModel, new String[]{"Name", "Dependency"}, null, new Class[]{String.class, ExecutionState.class});
        tblEvents = new JTable(tblmdlEvents);
        JScrollPane tblEventsPane = new JScrollPane(tblEvents);
        this.add(tblEventsPane, cc.xyw(2, 6, 3));
        lblProcedure = new JLabel("Procedures");
        this.add(lblProcedure, cc.xyw(2, 8, 3));
        Procedure[] procedures = entity.getProcedures().toArray(new Procedure[]{});
        Object[][] procedureModel = new Object[procedures.length][3];
        for (int i = 0; i < procedures.length; i++) {
            procedureModel[i][0] = procedures[i].getName();
            procedureModel[i][1] = procedures[i].getCategory();
        }
        tblmdlProcedures = new DefaultTableModel(procedureModel, new String[]{"Name", "Category"}, null, new Class[]{String.class, String.class});
        tblProcedures = new JTable(tblmdlProcedures);
        JScrollPane tblProcedurePane = new JScrollPane(tblProcedures);
        this.add(tblProcedurePane, cc.xyw(2, 10, 3));
        tblEvents.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getSource().equals(tblEvents)) {
                    if (tblEvents.getSelectedRow() >= 0 && tblEvents.getSelectedRow() < tblmdlEvents.getRowCount()) {
                        new EventDataDialog(events[tblEvents.getSelectedRow()]);
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
        this.setVisible(true);
    }
}
