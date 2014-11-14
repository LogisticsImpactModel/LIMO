package nl.fontys.sofa.limo.api.dao;

import java.util.List;
import nl.fontys.sofa.limo.domain.BaseEntity;

public interface DAO<T extends BaseEntity>{

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
     * Find one entity by its unique identifier.
     * 
     * @param uniqueIdentifier Unique identifier of entity to be found.
     * @return Entity.
     */
    public T findByUniqueIdentifier(String uniqueIdentifier);

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
     * @param entity of the DAO which should be deleted.
     * @return true if the deletion was successful, otherwise false.
     */
    public boolean delete(T entity);
}