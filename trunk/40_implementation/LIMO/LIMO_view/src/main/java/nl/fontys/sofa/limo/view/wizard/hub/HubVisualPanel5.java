/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.view.wizard.hub;

import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;

public final class HubVisualPanel5 extends JPanel {

    /**
     * Creates new form HubVisualPanel5
     */
    public HubVisualPanel5() {
        initComponents();
    }

    @Override
    public String getName() {
        return "Events";
    }

    private void initComponents() {
        listbox = new JList();
        btnAdd = new JButton("Add");
        btnEdit = new JButton("Edit");
        btnDelete = new JButton("Delete");
        panelLeft = new JPanel();
        setLayout(new BorderLayout());
        panelLeft.setLayout(new BoxLayout(panelLeft, BoxLayout.Y_AXIS));
        add(listbox, BorderLayout.CENTER);
        panelLeft.add(btnAdd);
        panelLeft.add(btnEdit);
        panelLeft.add(btnDelete);
        add(panelLeft, BorderLayout.EAST);
    }

    JList listbox;
    JButton btnAdd;
    JButton btnEdit;
    JButton btnDelete;
    JPanel panelLeft;
}
