/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.view.wizard.event;

import java.awt.BorderLayout;
import java.util.List;
import java.util.ResourceBundle;
import javax.swing.JPanel;
import nl.fontys.sofa.limo.domain.component.procedure.Procedure;
import nl.fontys.sofa.limo.view.custom.ProcedureComponent;

public final class ProceduresPanel extends JPanel {

    private final ResourceBundle bundle;

    public ProceduresPanel() {
        bundle = ResourceBundle.getBundle("nl/fontys/sofa/limo/view/wizard/event/Bundle");
        initComponents();
    }

    @Override
    public String getName() {
        return bundle.getString("PROCEDURES");
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        cmp = new ProcedureComponent();
        add(cmp, BorderLayout.CENTER);
    }

    public void update(List<Procedure> procedures) {
        cmp.setProcedureTable(procedures);
    }

    public List<Procedure> getProcedures() {
        return cmp.getActiveTableState();
    }

    private ProcedureComponent cmp;
}
