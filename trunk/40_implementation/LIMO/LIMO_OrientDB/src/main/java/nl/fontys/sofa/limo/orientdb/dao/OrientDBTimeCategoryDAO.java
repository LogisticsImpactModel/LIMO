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
import nl.fontys.sofa.limo.orientdb.OrientDBMapper;
import nl.fontys.sofa.limo.orientdb.database.OrientDBAccess;

public class OrientDBTimeCategoryDAO extends OrientDBAbstractDAO implements TimeCategoryDAO, OrientDBMapper<TimeCategory> {

    private static final String TABLE_NAME = "TimeCategories";

    public OrientDBTimeCategoryDAO(OrientDBAccess orientDBAccess) {
        super(orientDBAccess, TABLE_NAME);
    }

    @Override
    public List<TimeCategory> findAll() {
        ORecordIteratorClass<ODocument> results = orientDBAccess.getConnection().browseClass(getTableName());
        ArrayList<TimeCategory> resultList = new ArrayList<>();

        for (ODocument doc : results) {
            resultList.add(map(doc));
        }

        return resultList;
    }

    @Override
    public TimeCategory findById(String id) {
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
        entity.setLastUpdate(new Date().getTime());
        map(entity).save();
        return true;
    }

    @Override
    public boolean delete(String id) {
        orientDBAccess.getConnection().delete(new ORecordId(id));
        return true;
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
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
            doc = new ODocument(getTableName(), new ORecordId(entity.getId()));
        } else {
            doc = new ODocument(getTableName());
        }
        doc.field("lastUpdate", entity.getLastUpdate());
        doc.field("identifier", entity.getIdentifier());
        return doc;
    }

}
