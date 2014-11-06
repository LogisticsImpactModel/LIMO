/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.view.wizard.hubtype;

import java.awt.BorderLayout;
import java.util.List;
import javax.swing.JPanel;
import nl.fontys.sofa.limo.domain.component.procedure.Procedure;
import nl.fontys.sofa.limo.view.custom.ProcedureComponent;

public final class HubTypeVisualPanel3 extends JPanel {

    /**
     * Creates new form HubTypeVisualPanel3
     */
    public HubTypeVisualPanel3() {
        initComponents();
    }

    @Override
    public String getName() {
        return "Procedures";
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    private void initComponents() {
        setLayout(new BorderLayout());
        cmp = new ProcedureComponent();
        add(cmp, BorderLayout.CENTER);
    }
    
    public void update(List<Procedure> list){
        cmp.setProcedureTable(list);
    }
    
    public List<Procedure> getProcedures(){
        return cmp.getActiveTableState();
    }
    ProcedureComponent cmp;
    // Variables declaration - do not modify                     
    // End of variables declaration                   
}