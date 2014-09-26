package nl.fontys.sofa.limo.orientdb.dao;

import com.orientechnologies.orient.core.id.ORecordId;
import com.orientechnologies.orient.core.iterator.ORecordIteratorClass;
import com.orientechnologies.orient.core.metadata.schema.OSchema;
import com.orientechnologies.orient.core.record.impl.ODocument;
import com.orientechnologies.orient.core.sql.query.OSQLSynchQuery;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import nl.fontys.sofa.limo.api.dao.DAO;
import nl.fontys.sofa.limo.domain.BaseEntity;
import nl.fontys.sofa.limo.orientdb.OrientDBMapper;
import nl.fontys.sofa.limo.orientdb.database.OrientDBAccess;

/**
 *
 * @author Dominik Kaisers <d.kaisers@student.fontys.nl>
 */
public abstract class OrientDBAbstractDAO<T extends BaseEntity> implements DAO<T>, OrientDBMapper<T> {

    protected final OrientDBAccess orientDBAccess;
    protected final String tableName;

    public OrientDBAbstractDAO(OrientDBAccess orientDBAccess, String tableName) {
        this.orientDBAccess = orientDBAccess;
        this.tableName = tableName;

        // Create class in DB if it not yet exists
        OSchema schema = orientDBAccess.getConnection().getMetadata().getSchema();
        if (!schema.existsClass(tableName)) {
            schema.createClass(tableName);
        }
    }

    @Override
    public List<T> findAll() {
        ORecordIteratorClass<ODocument> results = orientDBAccess.getConnection().browseClass(tableName);
        ArrayList<T> resultList = new ArrayList<>();

        for (ODocument doc : results) {
            resultList.add(map(doc));
        }

        return resultList;
    }

    @Override
    public T findById(String id) {
        if (!stringIsValidId(id)) {
            return null;
        }

        OSQLSynchQuery<ODocument> query = new OSQLSynchQuery<>("select * from " + id);
        List<ODocument> results = orientDBAccess.getConnection().query(query);
        return map(results.get(0));
    }

    @Override
    public void insert(T entity) {
        entity.setLastUpdate(new Date().getTime());
        ODocument doc = map(entity).save();
        entity.setId(doc.getIdentity().toString());
    }

    @Override
    public boolean update(T entity) {
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

    protected static boolean stringIsValidId(String id) {
        if (id == null || id.length() == 0) {
            return false;
        }

        try {
            ORecordId orid = new ORecordId(id);
        } catch (IllegalArgumentException ex) {
            return false;
        }

        return true;
    }

}
