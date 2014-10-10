package nl.fontys.sofa.limo.api.dao;

import java.util.List;
import nl.fontys.sofa.limo.domain.BaseEntity;
import org.openide.util.Lookup;

public interface DAO<T extends BaseEntity> extends Lookup.Provider{

    /**
     * Find a list of all DAOs. If there is no one, the list is empty.
     *
     * @return a list with all the DAOs.
     */
    public List<T> findAll();

    /**
     * Find one DAO by its id. If there is no one, the result is null.
     *
     * @param id of the DAO.
     * @return one DAO or null.
     */
    public T findById(String id);

    /**
     * Insert a DAO into storage.
     *
     * @param entity to be inserted.
     * @return Managed object.
     */
    public T insert(T entity);

    /**
     * Update a DAO from storage.
     *
     * @param entity with new information.
     * @return true if the update was successful, otherwise false.
     */
    public boolean update(T entity);

    /**
     * Delete a DAO from storage.
     *
     * @param id of the DAO which should be deleted.
     * @return true if the deletion was successful, otherwise false.
     */
    public boolean delete(String id);

    /**
     * Get the name of the table for the storage.
     *
     * @return the name as String.
     */
    /* public String getTableName();*/
}
