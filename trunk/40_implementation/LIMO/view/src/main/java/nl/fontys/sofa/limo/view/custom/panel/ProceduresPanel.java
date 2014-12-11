package nl.fontys.sofa.limo.view.custom.panel;

import java.awt.BorderLayout;
import java.util.List;
import java.util.ResourceBundle;
import javax.swing.JPanel;
import nl.fontys.sofa.limo.domain.component.procedure.Procedure;
import nl.fontys.sofa.limo.view.custom.procedure.ProcedureComponent;

/**
 * This panel can be used by the wizards which uses procedures.
 *
 * @author Sven Mäurer
 */
public final class ProceduresPanel extends JPanel {

    private ProcedureComponent procedureComponent;

    public ProceduresPanel() {
        initComponents();
    }

    @Override
    public String getName() {
        return ResourceBundle.getBundle("nl/fontys/sofa/limo/view/Bundle").getString("PROCEDURES");
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        procedureComponent = new ProcedureComponent();
        add(procedureComponent, BorderLayout.CENTER);
    }

    public void update(List<Procedure> list) {
        procedureComponent.setProcedureTable(list);
    }

    public List<Procedure> getProcedures() {
        return procedureComponent.getActiveTableState();
    }

}
