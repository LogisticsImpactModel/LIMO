/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.orientdb.dao;

import com.orientechnologies.orient.core.id.ORecordId;
import com.orientechnologies.orient.core.iterator.ORecordIteratorClass;
import com.orientechnologies.orient.core.metadata.schema.OSchema;
import com.orientechnologies.orient.core.record.impl.ODocument;
import com.orientechnologies.orient.core.sql.query.OSQLSynchQuery;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import nl.fontys.sofa.limo.api.dao.CostCategoryDAO;
import nl.fontys.sofa.limo.domain.category.CostCategory;
import nl.fontys.sofa.limo.orientdb.OrientDBMapper;
import nl.fontys.sofa.limo.orientdb.database.OrientDBAccess;

public class OrientDBCostCategoryDAO extends OrientDBAbstractDAO implements CostCategoryDAO, OrientDBMapper<CostCategory> {

    private static final String TABLE_NAME = "CostCategories";
    
    public OrientDBCostCategoryDAO(OrientDBAccess orientDBAccess) {
        super(orientDBAccess, TABLE_NAME);
    }

    @Override
    public List<CostCategory> findAll() {
        ORecordIteratorClass<ODocument> results = orientDBAccess.getConnection().browseClass(getTableName());
        ArrayList<CostCategory> resultList = new ArrayList<>();

        for (ODocument doc : results) {
            resultList.add(map(doc));
        }

        return resultList;
    }

    @Override
    public CostCategory findById(String id) {
        OSQLSynchQuery<ODocument> query = new OSQLSynchQuery<>("select * from " + id);
        List<ODocument> results = orientDBAccess.getConnection().query(query);
        return map(results.get(0));
    }

    @Override
    public void insert(CostCategory entity) {
        entity.setLastUpdate(new Date().getTime());
        ODocument doc = map(entity).save();
        entity.setId(doc.getIdentity().toString());
    }

    @Override
    public boolean update(CostCategory entity) {
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
    public final String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public CostCategory map(ODocument doc) {
        CostCategory cc = new CostCategory();
        cc.setId(doc.getIdentity().toString());
        cc.setLastUpdate((long) doc.field("lastUpdate"));
        cc.setIdentifier((String) doc.field("identifier"));
        return cc;
    }

    @Override
    public ODocument map(CostCategory entity) {
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
