/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.view.wizard.hub;

import java.awt.BorderLayout;
import java.util.List;
import javax.swing.JPanel;
import nl.fontys.sofa.limo.domain.component.hub.Hub;
import nl.fontys.sofa.limo.domain.component.procedure.Procedure;
import nl.fontys.sofa.limo.view.custom.ProcedureComponent;

public final class HubVisualPanel4 extends JPanel {

    /**
     * Creates new form HubVisualPanel4
     */
    public HubVisualPanel4() {
        initComponents();
    }

    @Override
    public String getName() {
        return "Procedures";
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        cmp = new ProcedureComponent(null);
        add(cmp, BorderLayout.CENTER);
    }

    public void update(List<Procedure> procedures) {

    }

    public List<Procedure> getProcedures() {
        return cmp.getActiveTableState();
    }

    private ProcedureComponent cmp;
}
