package nl.fontys.sofa.limo.view.custom.panel;

import java.awt.BorderLayout;
import java.util.List;
import javax.swing.JPanel;
import nl.fontys.sofa.limo.domain.component.procedure.Procedure;
import nl.fontys.sofa.limo.view.custom.procedure.StandartProcedureComponent;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;

/**
 * This panel can be used by the wizards which uses procedures.
 *
 * @author Sven Mäurer
 */
public final class StandartProceduresPanel extends JPanel {

    private StandartProcedureComponent procedureComponent;

    public StandartProceduresPanel() {
        initComponents();
    }

    @Override
    public String getName() {
        return LIMOResourceBundle.getString("PROCEDURES");
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        procedureComponent = new StandartProcedureComponent();
        add(procedureComponent, BorderLayout.CENTER);
    }

    public void update(List<Procedure> list) {
        procedureComponent.setProcedureTable(list);
    }

    public List<Procedure> getProcedures() {
        return procedureComponent.getActiveTableState();
    }

}
