/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.orientdb.dao;

import com.orientechnologies.orient.core.id.ORecordId;
import com.orientechnologies.orient.core.metadata.schema.OType;
import com.orientechnologies.orient.core.record.impl.ODocument;
import java.util.ArrayList;
import nl.fontys.sofa.limo.api.dao.LegTypeDAO;
import nl.fontys.sofa.limo.domain.Entry;
import nl.fontys.sofa.limo.domain.Icon;
import nl.fontys.sofa.limo.domain.types.LegType;
import nl.fontys.sofa.limo.orientdb.database.OrientDBAccess;

public class OrientDBLegTypeDAO extends OrientDBAbstractDAO<LegType> implements LegTypeDAO {

    public OrientDBLegTypeDAO(OrientDBAccess orientDBAccess) {
        super(orientDBAccess, "LegType");
    }

    @Override
    public LegType map(ODocument doc) {
        LegType lt = new LegType(null, null, null, null);
        lt.setId(doc.getIdentity().toString());
        lt.setCosts((ArrayList<Entry>) doc.field("costs", OType.EMBEDDEDLIST));
        lt.setLeadTimes((ArrayList<Entry>) doc.field("leadTimes", OType.EMBEDDEDLIST));
        lt.setDelays((ArrayList<Entry>) doc.field("delays", OType.EMBEDDEDLIST));
        lt.setIcon((Icon) doc.field("icon"));
        lt.setLastUpdate((long) doc.field("lastUpdate"));
        lt.setIdentifier((String) doc.field("identifier"));
        return lt;
    }

    @Override
    public ODocument map(LegType entity) {
        ODocument doc;
        if (entity.getId() != null) {
            doc = new ODocument(tableName, new ORecordId(entity.getId()));
        } else {
            doc = new ODocument(tableName);
        }
        doc.field("costs", entity.getCosts(), OType.EMBEDDEDLIST);
        doc.field("leadTimes", entity.getLeadTimes(), OType.EMBEDDEDLIST);
        doc.field("delays", entity.getDelays(), OType.EMBEDDEDLIST);
        doc.field("icon", entity.getIcon());
        doc.field("lastUpdate", entity.getLastUpdate());
        doc.field("identifier", entity.getIdentifier());
        
        return doc;
    }
}
