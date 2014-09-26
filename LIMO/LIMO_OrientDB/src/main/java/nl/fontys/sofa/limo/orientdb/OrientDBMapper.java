package nl.fontys.sofa.limo.orientdb;

import com.orientechnologies.orient.core.record.impl.ODocument;
import nl.fontys.sofa.limo.domain.BaseEntity;

/**
 * General mapping interface between domain model entities and OrientDB
 * documents.
 *
 * @author Dominik Kaisers <d.kaisers@student.fontys.nl>
 */
public interface OrientDBMapper<T extends BaseEntity> {

    /**
     * Maps given ODocument to specified BaseEntity subclass.
     *
     * @param doc ODocument to map.
     * @return Mapped entity. Null if doc is null.
     */
    public T map(ODocument doc);

    /**
     * Maps given Entity to OrientDB document.
     *
     * @param entity Entity to map.
     * @return Mapped ODocument. Null if entity is null.
     */
    public ODocument map(T entity);

}
