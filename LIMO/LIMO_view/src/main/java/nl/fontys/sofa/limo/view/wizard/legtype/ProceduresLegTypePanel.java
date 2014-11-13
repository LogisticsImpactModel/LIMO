package nl.fontys.sofa.limo.view.wizard.legtype;

import java.awt.BorderLayout;
import java.util.List;
import java.util.ResourceBundle;
import javax.swing.JPanel;
import nl.fontys.sofa.limo.domain.component.procedure.Procedure;
import nl.fontys.sofa.limo.view.custom.ProcedureComponent;

public final class ProceduresLegTypePanel extends JPanel {

    public ProceduresLegTypePanel() {
        initComponents();
    }

    @Override
    public String getName() {
        return ResourceBundle.getBundle("nl/fontys/sofa/limo/view/Bundle").getString("PROCEDURES");
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        cmp = new ProcedureComponent();
        add(cmp, BorderLayout.CENTER);
    }

    public void update(List<Procedure> list) {
        cmp.setProcedureTable(list);
    }

    public List<Procedure> getProcedures() {
        return cmp.getActiveTableState();
    }

    private ProcedureComponent cmp;
}
