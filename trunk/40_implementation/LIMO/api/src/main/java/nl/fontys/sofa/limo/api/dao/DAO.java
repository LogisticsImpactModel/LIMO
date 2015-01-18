package nl.fontys.sofa.limo.api.dao;

import java.util.List;
import nl.fontys.sofa.limo.domain.BaseEntity;

/**
 * Interface for the Data Access Object Pattern. Every class which has to be
 * persisted, which indicates that it inherits from BaseEntity, has to
 * implemented a DAO of this interface.
 *
 * @author Sven Mäurer
 * @param <T> the class to be persisted.
 */
public interface DAO<T extends BaseEntity> {

    /**
     * Find a list of all DAOs. If there is no one, the list is empty.
     *
     * @return a list with all the DAOs.
     */
    List<T> findAll();

    /**
     * Find one DAO by its id. If there is no one, the result is null.
     *
     * @param id of the DAO.
     * @return one DAO or null.
     */
    T findById(String id);

    /**
     * Find one entity by its unique identifier.
     *
     * @param uniqueIdentifier Unique identifier of entity to be found.
     * @return Entity.
     */
    T findByUniqueIdentifier(String uniqueIdentifier);

    /**
     * Insert a DAO into storage.
     *
     * @param entity to be inserted.
     * @return Managed object.
     */
    T insert(T entity);

    /**
     * Insert a DAO into storage. Used mainly for importer.
     *
     * @param entity to be inserted.
     * @param updateTimestamp True if lastUpdate should be updated.
     * @return Managed object.
     */
    T insert(T entity, boolean updateTimestamp);

    /**
     * Update a DAO from storage.
     *
     * @param entity with new information.
     * @return true if the update was successful, otherwise false.
     */
    boolean update(T entity);

    /**
     * Delete a DAO from storage.
     *
     * @param entity of the DAO which should be deleted.
     * @return true if the deletion was successful, otherwise false.
     */
    boolean delete(T entity);
}
