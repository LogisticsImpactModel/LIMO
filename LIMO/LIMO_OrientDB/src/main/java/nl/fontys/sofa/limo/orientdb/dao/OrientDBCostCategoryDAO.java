/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.orientdb.dao;

import com.orientechnologies.orient.core.id.ORecordId;
import com.orientechnologies.orient.core.record.impl.ODocument;
import java.util.List;
import nl.fontys.sofa.limo.api.dao.CostCategoryDAO;
import nl.fontys.sofa.limo.domain.category.CostCategory;
import nl.fontys.sofa.limo.orientdb.database.OrientDBAccess;

public class OrientDBCostCategoryDAO extends OrientDBAbstractDAO<CostCategory> implements CostCategoryDAO {

    public OrientDBCostCategoryDAO(OrientDBAccess orientDBAccess) {
        super(orientDBAccess, "CostCategories");
    }

    @Override
    public CostCategory map(ODocument doc) {
        if (doc == null) {
            return null;
        }

        CostCategory cc = new CostCategory();
        cc.setId(doc.getIdentity().toString());
        cc.setLastUpdate((long) doc.field("lastUpdate"));
        cc.setIdentifier((String) doc.field("identifier"));
        return cc;
    }

    @Override
    public ODocument map(CostCategory entity) {
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
