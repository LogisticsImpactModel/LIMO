package nl.fontys.sofa.limo.externaltrader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import javax.swing.JTable;
import javax.swing.table.TableModel;

/**
 * This class offers the possibility to write a SimulationResult to a csv file.
 *
 * @author Matthias Brück
 */
public final class CSVExporter {

    /**
     * Private constructor to remove the possibility of creating an instance.
     */
    private CSVExporter() {
    }

    /**
     * Exports JTables to .csv. Does only save when the file does not exists
     * yet.
     *
     * @param filePath The file in which you want to write the result.
     * @param tables The tables you want to export into a .csv file.
     * @param titles The titles of the table. Does not matter if there are less
     * or more titles than tables, it takes title 1 and table 1, title 2 and
     * table 2, and so on.
     */
    public static void exportTables(String filePath, JTable[] tables, String[] titles) {
        File file = new File(filePath);
        if (file.exists()) {
            return;
        }
        try {
            file.createNewFile();
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                for (int i = 0; i < tables.length; i++) {
                    TableModel model = tables[i].getModel();
                    if (titles.length > i) {
                        bw.write(titles[i]);
                        bw.newLine();
                    }
                    for (int names = 0; names < model.getColumnCount(); names++) {
                        bw.write(model.getColumnName(names));
                        bw.write(";");
                    }
                    bw.newLine();
                    for (int clmCnt = model.getColumnCount(), rowCnt = model.getRowCount(), j = 0; j < rowCnt; j++) {
                        for (int k = 0; k < clmCnt; k++) {
                            String value = model.getValueAt(j, k).toString().replaceAll("\\.", ",");
                            bw.write(value);
                            bw.write(";");
                        }
                        bw.newLine();
                    }
                    bw.newLine();
                }
                bw.flush();
            }
        } catch (IOException e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }
}
