package nl.fontys.sofa.limo.externaltrader;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import nl.fontys.sofa.limo.domain.component.Node;
import nl.fontys.sofa.limo.domain.component.SupplyChain;
import nl.fontys.sofa.limo.simulation.result.DataEntry;
import nl.fontys.sofa.limo.simulation.result.SimulationResult;

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
     * Exports a simulation result to a csv file. The given String is the file
     * that should get used. Returns false when the file could not get saved or
     * is already existing. NO FILE OVERRIDE!
     *
     * @param result The SimulationResult that has to be exported.
     * @param filePath The path to the file in that the result has to be
     * written.
     * @return true if everything went as expected and the file got saved. False
     * if the file could not get written or is already existing.
     */
    public static boolean exportTestCaseResult(SimulationResult result, String filePath) {
        FileWriter writer = null;
        try {
            File f = new File(filePath);
            if (f.exists()) {
                return false;
            }
            writer = new FileWriter(filePath + ".csv");
            SupplyChain supplyChain = result.getSupplyChain();
            Node node = supplyChain.getStart();
            writer.append("Supply Chain: From" + node.getName() + " To ");
            while (node.getNext() != null) {
                node = node.getNext();
            }
            writer.append(node.getName() + "\n");
            writer.append("Number of test cases;" + result.getTestCaseCount() + "\n\n\n");
            writer.append("Total Costs:;Max;Min;Avg\n");
            writer.append(";" + result.getTotalCosts().getMax() + ";" + result.getTotalCosts().getMin() + ";" + result.getTotalCosts().getAvg() + "\n\n\n");
            addMapToFileWriter(writer, result.getCostsByCategory(), "Costs");
            writer.append("Total Extra Costs:;Max;Min;Avg\n");
            writer.append(";" + result.getTotalExtraCosts().getMax() + ";" + result.getTotalExtraCosts().getMin() + ";" + result.getTotalExtraCosts().getAvg() + "\n\n\n");
            addMapToFileWriter(writer, result.getExtraCostsByCategory(), "Extra Costs");
            writer.append("Total Delays:;Max;Min;Avg\n");
            writer.append(";" + result.getTotalDelays().getMax() + ";" + result.getTotalDelays().getMin() + ";" + result.getTotalDelays().getAvg() + "\n\n\n");
            addMapToFileWriter(writer, result.getDelaysByCategory(), "Delays");
            writer.append("Total Lead Times:;Max;Min;Avg\n");
            writer.append(";" + result.getTotalLeadTimes().getMax() + ";" + result.getTotalLeadTimes().getMin() + ";" + result.getTotalLeadTimes().getAvg() + "\n\n\n");
            addMapToFileWriter(writer, result.getLeadTimesByCategory(), "Lead Times");
            writer.append("\n\n");
            addEventsToFileWriter(writer, result);
            writer.flush();
            writer.close();
            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
            try {
                writer.close();
            } catch (IOException ex1) {
                ex1.printStackTrace();
            }
            return false;
        }
    }

    /**
     * Adds a Map to the FileWriter.
     *
     * @param writer The FileWriter thats currently writing the file.
     * @param map The map that has to be added.
     * @param name The name of the map, or better, name of the thing stored in
     * the map.
     * @throws IOException When the data could not be written to the FileWriter
     * an IOException gets thrown.
     */
    private static void addMapToFileWriter(FileWriter writer, Map<String, DataEntry> map, String name) throws IOException {
        writer.append("Number of different " + name + ";" + map.values().size() + "\n\n");
        writer.append("Name;Max;Min;Avg\n");
        Set<Map.Entry<String, DataEntry>> set = map.entrySet();
        for (Map.Entry<String, DataEntry> entry : set) {
            writer.append(entry.getKey() + ";" + entry.getValue().getMax() + ";" + entry.getValue().getMin() + ";" + entry.getValue().getAvg() + "\n");
        }
        writer.append("\n");
    }

    /**
     * Adds the events to the FileWriter.
     *
     * @param writer The FileWriter thats currently writing the file.
     * @param simulationResult The SimulationResult that stores the events.
     * @throws IOException When the data could not be written to the FileWriter
     * an IOException gets thrown.
     */
    private static void addEventsToFileWriter(FileWriter writer, SimulationResult simulationResult) throws IOException {
        Map<String, Double> map = simulationResult.getEventExecutionRate();
        writer.append("Number of Events;" + map.keySet().size() + "\n\n");
        writer.append("Name;Execution Rate\n");
        Set<Map.Entry<String, Double>> set = map.entrySet();
        for (Map.Entry<String, Double> entry : set) {
            writer.append(entry.getKey() + ";" + entry.getValue() + "\n");
        }
    }
}
