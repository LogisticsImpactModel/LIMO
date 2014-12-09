package nl.fontys.sofa.limo.externaltrader;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import nl.fontys.limo.simulation.result.DataEntry;
import nl.fontys.limo.simulation.result.SimulationResult;
import nl.fontys.sofa.limo.domain.component.Node;
import nl.fontys.sofa.limo.domain.component.SupplyChain;

/**
 * @author Matthias Brück
 */
public class CSVExporter {

    public static boolean exportTestCaseResult(SimulationResult result, String file) {
        FileWriter writer = null;
        try {
            File f = new File(file);
            if (f.exists()) {
                return false;
            }
            writer = new FileWriter(file + ".csv");
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
            addMapToList(writer, result.getCostsByCategory(), "Costs");
            writer.append("Total Extra Costs:;Max;Min;Avg\n");
            writer.append(";" + result.getTotalExtraCosts().getMax() + ";" + result.getTotalExtraCosts().getMin() + ";" + result.getTotalExtraCosts().getAvg() + "\n\n\n");
            addMapToList(writer, result.getExtraCostsByCategory(), "Extra Costs");
            writer.append("Total Delays:;Max;Min;Avg\n");
            writer.append(";" + result.getTotalDelays().getMax() + ";" + result.getTotalDelays().getMin() + ";" + result.getTotalDelays().getAvg() + "\n\n\n");
            addMapToList(writer, result.getDelaysByCategory(), "Delays");
            writer.append("Total Lead Times:;Max;Min;Avg\n");
            writer.append(";" + result.getTotalLeadTimes().getMax() + ";" + result.getTotalLeadTimes().getMin() + ";" + result.getTotalLeadTimes().getAvg() + "\n\n\n");
            addMapToList(writer, result.getLeadTimesByCategory(), "Lead Times");
            writer.append("\n\n");
            addEvents(writer, result);
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

    private static void addMapToList(FileWriter writer, Map<String, DataEntry> map, String name) throws IOException {
        writer.append("Number of different " + name + ";" + map.values().size() + "\n\n");
        writer.append("Name;Max;Min;Avg\n");
        Set<Map.Entry<String, DataEntry>> set = map.entrySet();
        for (Map.Entry<String, DataEntry> entry : set) {
            writer.append(entry.getKey() + ";" + entry.getValue().getMax() + ";" + entry.getValue().getMin() + ";" + entry.getValue().getAvg() + "\n");
        }
        writer.append("\n");
    }

    private static void addEvents(FileWriter writer, SimulationResult result) throws IOException {
        Map<String, Double> map = result.getEventExecutionRate();
        writer.append("Number of Events;" + map.keySet().size() + "\n\n");
        writer.append("Name;Execution Rate\n");
        Set<Map.Entry<String, Double>> set = map.entrySet();
        for (Map.Entry<String, Double> entry : set) {
            writer.append(entry.getKey() + ";" + entry.getValue() + "\n");
        }
    }
}
