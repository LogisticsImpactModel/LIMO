package nl.fontys.sofa.limo.orientdb.dao;

import com.orientechnologies.orient.core.id.ORecordId;
import com.orientechnologies.orient.core.record.impl.ODocument;
import nl.fontys.sofa.limo.api.dao.TimeCategoryDAO;
import nl.fontys.sofa.limo.domain.category.TimeCategory;
import nl.fontys.sofa.limo.orientdb.database.OrientDBAccess;

public class OrientDBTimeCategoryDAO extends OrientDBAbstractDAO<TimeCategory> implements TimeCategoryDAO {

    public OrientDBTimeCategoryDAO(OrientDBAccess orientDBAccess) {
        super(orientDBAccess, "TimeCategories");
    }

    @Override
    public TimeCategory map(ODocument doc) {
        if (doc == null) {
            return null;
        }

        TimeCategory tc = new TimeCategory();
        tc.setId(doc.getIdentity().toString());
        tc.setLastUpdate((long) doc.field("lastUpdate"));
        tc.setIdentifier((String) doc.field("identifier"));
        return tc;
    }

    @Override
    public ODocument map(TimeCategory entity) {
        if (entity == null) {
            return null;
        }

        ODocument doc;
        if (entity.getId() != null) {
            doc = new ODocument(tableName, new ORecordId(entity.getId()));
        } else {
            doc = new ODocument(tableName);
        }

        doc.field("lastUpdate", entity.getLastUpdate());
        doc.field("identifier", entity.getIdentifier());
        return doc;
    }

}
