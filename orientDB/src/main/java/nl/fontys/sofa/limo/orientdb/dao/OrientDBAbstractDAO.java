package nl.fontys.sofa.limo.orientdb.dao;

import com.orientechnologies.orient.core.exception.ODatabaseException;
import com.orientechnologies.orient.core.id.ORecordId;
import com.orientechnologies.orient.core.query.OQueryAbstract;
import com.orientechnologies.orient.core.record.impl.ODocument;
import com.orientechnologies.orient.core.sql.query.OSQLSynchQuery;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import nl.fontys.sofa.limo.api.dao.DAO;
import nl.fontys.sofa.limo.domain.BaseEntity;
import nl.fontys.sofa.limo.orientdb.OrientDBConnector;

public abstract class OrientDBAbstractDAO<T extends BaseEntity> implements DAO<T> {

    protected final Class entityClass;

    public OrientDBAbstractDAO(Class entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public List<T> findAll() {
        ArrayList<T> results = new ArrayList<>();
        for (Object entity : OrientDBConnector.connection().browseClass(entityClass).setFetchPlan("*:-1")) {
            results.add((T) OrientDBConnector.connection().detachAll(entity, true));
        }
        return results;
    }

    @Override
    public T findById(String id) {
        if (!stringIsValidId(id)) {
            return null;
        }

        OQueryAbstract query = new OSQLSynchQuery<>("select * from " + id).setFetchPlan("*:-1");
        List<T> results;

        try {
            results = OrientDBConnector.connection().query(query);
        } catch (ODatabaseException ex) {
            return null;
        }

        return (T) (results.isEmpty() ? null : OrientDBConnector.connection().detachAll(results.get(0), true));
    }

    @Override
    public T findByUniqueIdentifier(String uniqueIdentifier) {
        OSQLSynchQuery<ODocument> query = new OSQLSynchQuery<>("select from index:uuid where key = '" + uniqueIdentifier + "'");
        List<ODocument> results = OrientDBConnector.connection().query(query);
        if (results.isEmpty()) {
            return null;
        }

        ODocument result = results.get(0).field("rid");
        return (T) OrientDBConnector.connection().detachAll(OrientDBConnector.connection().getUserObjectByRecord(result, "*:-1"), true);
    }

    @Override
    public T insert(T entity) {
        return insert(entity, true);
    }

    @Override
    public T insert(T entity, boolean updateTimestamp) {
        if (entity == null || entity.getId() != null) {
            return null;
        }

        if (updateTimestamp) {
            entity.setLastUpdate(new Date().getTime());
        }

        return OrientDBConnector.connection().detachAll(OrientDBConnector.connection().save(entity), true);
    }

    @Override
    public boolean update(T entity) {
        if (entity == null || !stringIsValidId(entity.getId())) {
            return false;
        }

        entity.setLastUpdate(new Date().getTime());
        OrientDBConnector.connection().save(entity);
        return true;
    }

    @Override
    public boolean delete(T entity) {
        if (entity == null || !stringIsValidId(entity.getId())) {
            return false;
        }

        OrientDBConnector.connection().delete(new ORecordId(entity.getId()));
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
