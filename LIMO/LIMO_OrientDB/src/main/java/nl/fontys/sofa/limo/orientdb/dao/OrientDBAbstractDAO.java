package nl.fontys.sofa.limo.orientdb.dao;

import com.orientechnologies.orient.core.db.ODatabaseRecordThreadLocal;
import com.orientechnologies.orient.core.db.record.ODatabaseRecord;
import com.orientechnologies.orient.core.id.ORecordId;
import com.orientechnologies.orient.core.sql.query.OSQLSynchQuery;
import com.orientechnologies.orient.object.db.OObjectDatabaseTx;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import nl.fontys.sofa.limo.api.dao.DAO;
import nl.fontys.sofa.limo.domain.BaseEntity;
import nl.fontys.sofa.limo.orientdb.database.OrientDBAccess;

public abstract class OrientDBAbstractDAO<T extends BaseEntity> implements DAO<T> {

    protected final OrientDBAccess orientDBAccess;
    protected final Class entityClass;


    public OrientDBAbstractDAO(OrientDBAccess orientDBAccess, Class entityClass) {
        this.orientDBAccess = orientDBAccess;
        this.entityClass = entityClass;
    }

    @Override
    public List<T> findAll() {
        ArrayList<T> resultList = new ArrayList<>();
        for (Object entity : orientDBAccess.getConnection().browseClass(entityClass)) {
            resultList.add((T) entity);
        }
        return resultList;
    }

    @Override
    public T findById(String id) {
        if (!stringIsValidId(id)) {
            return null;
        }

        OSQLSynchQuery<T> query = new OSQLSynchQuery<>("select * from " + id);
        List<T> results = orientDBAccess.getConnection().query(query);
        return results.get(0);
    }

    @Override
    public T insert(T entity) {
        OObjectDatabaseTx con = orientDBAccess.getConnection();
        ODatabaseRecordThreadLocal.INSTANCE.set((ODatabaseRecord) con.getUnderlying().getUnderlying());
        
        entity.setLastUpdate(new Date().getTime());
        T t = con.save(entity);
		return t;
    }

    @Override
    public boolean update(T entity) {
        if (!stringIsValidId(entity.getId())) {
            return false;
        }

        entity.setLastUpdate(new Date().getTime());
        orientDBAccess.getConnection().save(entity);
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

    /**
     * Checks if the given ID string is a valid OrientDB RecordID.
     *
     * @param id ID string to check.
     * @return True/false dependent on validation result.
     */
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
