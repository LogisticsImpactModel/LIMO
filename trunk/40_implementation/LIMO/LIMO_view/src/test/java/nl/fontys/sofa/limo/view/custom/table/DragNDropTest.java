/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.view.custom.table;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
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
        List<List<Object>> values = new ArrayList<>();
        ArrayList<Object> value = new ArrayList<>();
        value.add("A");
        value.add("B");
        values.add(value);
        value = new ArrayList<>();
        value.add("C");
        value.add("D");
        values.add(value);
        value = new ArrayList<>();
        value.add("E");
        value.add("F");
        values.add(value);
        value = new ArrayList<>();
        value.add("G");
        value.add("H");
        values.add(value);
        value = new ArrayList<>();
        value.add("I");
        value.add("J");
        values.add(value);
        DragNDropTableModel model = new DragNDropTableModel(new String[]{"C1", "C2"},
                values,
                new Class[]{String.class, String.class});
        table = new DragNDropTable(model);
        table.getColumnModel().getColumn(0).setPreferredWidth(300);
        table.getColumnModel().getColumn(1).setPreferredWidth(300);
        frame.add(table);
        frame.setVisible(true);
    }
}
