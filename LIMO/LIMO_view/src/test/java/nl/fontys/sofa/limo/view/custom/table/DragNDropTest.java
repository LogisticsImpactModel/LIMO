/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.view.custom.table;

import javax.swing.DropMode;
import javax.swing.JFrame;

/**
 *
 * @author Matthias Brück
 */
public class DragNDropTest {

    private static JFrame frame;
    private static DragNDropTable table;

    public static void main(String[] args) {
        frame = new JFrame("Drag 'n Drop Test");
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setSize(800, 450);
        DragNDropTableModel model = new DragNDropTableModel(new String[]{"C1", "C2"},
                new String[][]{{"A", "B"}, {"C", "D"}, {"E", "F"}, {"G", "H"}, {"I", "J"}},
                new Class[]{String.class, String.class});
        table = new DragNDropTable(model);
        table.getColumnModel().getColumn(0).setPreferredWidth(300);
        table.getColumnModel().getColumn(1).setPreferredWidth(300);
        frame.add(table);
        frame.setVisible(true);
    }
}
