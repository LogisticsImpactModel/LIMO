package nl.fontys.sofa.limo.service.provider;

import java.util.List;
import nl.fontys.sofa.limo.api.dao.DAO;
import nl.fontys.sofa.limo.api.exception.DAONotFoundException;
import nl.fontys.sofa.limo.domain.BaseEntity;
import org.openide.util.Lookup;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;

/**
 *
 * @author Dominik Kaisers <d.kaisers@student.fontys.nl>
 */
public class AbstractService<T extends BaseEntity> implements DAO<T>, Lookup.Provider{
    
    protected final DAO dao;
    protected final InstanceContent instanceContent;
    protected final Lookup lookup;

    public AbstractService(Class<? extends DAO> daoClass) throws DAONotFoundException {
        this.dao = Lookup.getDefault().lookup(daoClass);
        this.instanceContent = new InstanceContent();
        this.lookup = new AbstractLookup(instanceContent);
        
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
        return (T) dao.findById(id);
    }

    @Override
    public T findByUniqueIdentifier(String uniqueIdentifier) {
        return (T) dao.findByUniqueIdentifier(uniqueIdentifier);
    }

    @Override
    public T insert(T entity) {
        T result = (T) dao.insert(entity);
        instanceContent.add(entity);
        return result;
    }

    @Override
    public boolean update(T entity) {
        boolean result = dao.update(entity);
        return result;
    }

    @Override
    public boolean delete(T entity) {
        boolean result = dao.delete(entity);
        instanceContent.set(dao.findAll(), null);
        return result;
    }

    @Override
    public Lookup getLookup() {
        return this.lookup;
    }
    
}
