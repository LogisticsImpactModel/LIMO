/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.orientdb.dao;

import com.orientechnologies.orient.core.id.ORecordId;
import com.orientechnologies.orient.core.iterator.ORecordIteratorClass;
import com.orientechnologies.orient.core.record.impl.ODocument;
import com.orientechnologies.orient.core.sql.query.OSQLSynchQuery;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import nl.fontys.sofa.limo.api.dao.TimeCategoryDAO;
import nl.fontys.sofa.limo.domain.category.TimeCategory;
import nl.fontys.sofa.limo.orientdb.database.OrientDBAccess;

public class OrientDBTimeCategoryDAO extends OrientDBAbstractDAO<TimeCategory> implements TimeCategoryDAO {

    public OrientDBTimeCategoryDAO(OrientDBAccess orientDBAccess) {
        super(orientDBAccess, "TimeCategories");
    }

    @Override
    public List<TimeCategory> findAll() {
        ORecordIteratorClass<ODocument> results = orientDBAccess.getConnection().browseClass(tableName);
        ArrayList<TimeCategory> resultList = new ArrayList<>();

        for (ODocument doc : results) {
            resultList.add(map(doc));
        }

        return resultList;
    }

    @Override
    public TimeCategory findById(String id) {
        if (!stringIsValidId(id)) {
            return null;
        }

        OSQLSynchQuery<ODocument> query = new OSQLSynchQuery<>("select * from " + id);
        List<ODocument> results = orientDBAccess.getConnection().query(query);
        return map(results.get(0));
    }

    @Override
    public void insert(TimeCategory entity) {
        entity.setLastUpdate(new Date().getTime());
        ODocument doc = map(entity).save();
        entity.setId(doc.getIdentity().toString());
    }

    @Override
    public boolean update(TimeCategory entity) {
        if (!stringIsValidId(entity.getId())) {
            return false;
        }

        entity.setLastUpdate(new Date().getTime());
        map(entity).save();
        return true;
    }

    @Override
    public boolean delete(String id) {
        if (!stringIsValidId(id)) {
            return false;
        }

        orientDBAccess.getConnection().delete(new ORecordId(id));
        return true;
    }

    @Override
    public TimeCategory map(ODocument doc) {
        TimeCategory tc = new TimeCategory();
        tc.setId(doc.getIdentity().toString());
        tc.setLastUpdate((long) doc.field("lastUpdate"));
        tc.setIdentifier((String) doc.field("identifier"));
        return tc;
    }

    @Override
    public ODocument map(TimeCategory entity) {
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
