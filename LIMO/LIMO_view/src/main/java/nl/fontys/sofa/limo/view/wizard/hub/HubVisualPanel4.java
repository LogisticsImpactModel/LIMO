/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.view.wizard.hub;

import java.awt.BorderLayout;
import javax.swing.JPanel;

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
    }
    
//    public void setComponent(Component comp){
//        removeAll();
//        tce = new TabbedComponentEntries(comp);
//        add(tce);
//    }
    
//    public Component getComponent(){
//    }
//    
   // private TabbedComponentEntries tce;
}
