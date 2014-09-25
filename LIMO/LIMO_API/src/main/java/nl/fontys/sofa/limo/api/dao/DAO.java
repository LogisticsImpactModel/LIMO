package nl.fontys.sofa.limo.api.dao;

import java.util.List;
import nl.fontys.sofa.limo.domain.BaseEntity;

public interface DAO<T extends BaseEntity> {

    public List<T> findAll();

    public T findById();

    public void insert(T domainModel);

    public boolean update(T domainModel);

    public boolean delete(int domainModelId);

    public String getTableName();

}
