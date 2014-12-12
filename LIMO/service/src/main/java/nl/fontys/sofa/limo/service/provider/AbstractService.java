package nl.fontys.sofa.limo.service.provider;

import java.util.List;
import nl.fontys.sofa.limo.api.dao.DAO;
import nl.fontys.sofa.limo.api.exception.DAONotFoundException;
import nl.fontys.sofa.limo.api.service.status.StatusBarService;
import nl.fontys.sofa.limo.domain.BaseEntity;
import org.openide.util.Lookup;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;

/**
 * Decorator for DAOs, i.e. DAOS handle database communication, but this Service
 * handles updating data in the application when data in the DB has changed in
 * the meanwhile (e.g. when a tuple is removed in DB, this service makes sure
 * that this data is not available anymore in the application itself
 *
 * @author Dominik Kaisers <d.kaisers@student.fontys.nl>
 * @param <T> - baseEntities
 */
public class AbstractService<T extends BaseEntity> implements DAO<T>, Lookup.Provider {

    protected final DAO dao;
    protected final InstanceContent instanceContent;
    protected final Lookup lookup;
    private final StatusBarService status;

    public AbstractService(Class<? extends DAO> daoClass) throws DAONotFoundException {
        this.dao = Lookup.getDefault().lookup(daoClass);
        this.instanceContent = new InstanceContent();
        this.lookup = new AbstractLookup(instanceContent);
        status = Lookup.getDefault().lookup(StatusBarService.class);

        if (dao == null) {
            throw new DAONotFoundException("DAO of type " + daoClass.getSimpleName() + " not found...");
        } else {
            List<T> instances = dao.findAll();
            instanceContent.set(instances, null);
        }
    }

    @Override
    public List<T> findAll() {
        return dao.findAll();
    }

    @Override
    public T findById(String id) {
        try {
            return (T) dao.findById(id);
        } catch (Exception e) {
            status.setMessage(id, StatusBarService.ACTION_FOUND, StatusBarService.STATE_FAIL, e);
            return null;
        }
    }

    @Override
    public T findByUniqueIdentifier(String uniqueIdentifier) {
        return (T) dao.findByUniqueIdentifier(uniqueIdentifier);
    }

    @Override
    public T insert(T entity) {
        return insert(entity, true);
    }

    @Override
    public T insert(T entity, boolean updateTimestamp) {
        try {
            T result = (T) dao.insert(entity, updateTimestamp);
            instanceContent.add(result);
            status.setMessage(entity.getName(), StatusBarService.ACTION_CREATE, StatusBarService.STATE_SUCCESS, null);
            return result;
        } catch (Exception e) {
            status.setMessage(entity.getName(), StatusBarService.ACTION_CREATE, StatusBarService.STATE_FAIL, e);
            return null;
        }
    }

    @Override
    public boolean update(T entity) {
        try {
            boolean result = dao.update(entity);
            status.setMessage(entity.getName(), StatusBarService.ACTION_UPDATE, StatusBarService.STATE_SUCCESS, null);
            return result;
        } catch (Exception e) {
            status.setMessage("", StatusBarService.ACTION_UPDATE, StatusBarService.STATE_FAIL, e);
            return false;
        }
    }

    @Override
    public boolean delete(T entity) {
        try {
            boolean result = dao.delete(entity);
            instanceContent.set(dao.findAll(), null);
            status.setMessage(entity.getName(), StatusBarService.ACTION_DELETE, StatusBarService.STATE_SUCCESS, null);
            return result;
        } catch (Exception e) {
            status.setMessage(entity.getName(), StatusBarService.ACTION_DELETE, StatusBarService.STATE_FAIL, null);
            return false;
        }
    }

    @Override
    public Lookup getLookup() {
        return this.lookup;
    }

}
