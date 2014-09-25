package nl.fontys.sofa.limo.api.dao;

import java.util.List;
import nl.fontys.sofa.limo.domain.BaseEntity;

public interface DAO<T extends BaseEntity> {

    public List<T> findAll();

    public T findById(String id);

    public void insert(T entity);

    public boolean update(T entity);

    public boolean delete(String id);

    public String getTableName();

}
