package nl.fontys.limo.externaltraider;

import com.orientechnologies.orient.core.record.impl.ODocument;
import com.orientechnologies.orient.core.sql.query.OSQLSynchQuery;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.Set;
import nl.fontys.sofa.limo.domain.BaseEntity;
import nl.fontys.sofa.limo.orientdb.OrientDBConnector;
import org.json.JSONObject;

/**
 * @author Matthias Brück
 */
public class JSONExporter {

    public JSONExporter() {

    }

    public void exportToJson(Map<String, List<BaseEntity>> allEntities, String filepath) {
        JSONObject json = new JSONObject();
        Set<Map.Entry<String, List<BaseEntity>>> entrySet = allEntities.entrySet();
        for (Map.Entry<String, List<BaseEntity>> set : entrySet) {
            for (BaseEntity entity : set.getValue()) {
                OSQLSynchQuery<ODocument> query = new OSQLSynchQuery<>("select from index:uuid where key = '" + entity.getUniqueIdentifier() + "'");
                List<ODocument> result = OrientDBConnector.connection().query(query);
                if (!result.isEmpty()) {
                    ODocument d = result.get(0).field("rid");
                    json.append(set.getKey(), new JSONObject(d.toJSON("class,attribSameRow")));
                }
            }
        }
        writeJSON(json, filepath);
    }

    private void writeJSON(JSONObject json, String filepath) {
        try {
            
            PrintWriter out = new PrintWriter(filepath);
            out.print(json.toString());
            out.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }
}
