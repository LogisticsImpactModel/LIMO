package nl.fontys.sofa.limo.orientdb.dao;

import nl.fontys.sofa.limo.api.dao.ProcessCategoryDAO;
import nl.fontys.sofa.limo.domain.component.process.ProcessCategory;

/**
 *
 * @author Dominik Kaisers <d.kaisers@student.fontys.nl>
 */
public class OrientDBProcessCategoryDAO extends OrientDBAbstractDAO<ProcessCategory> implements ProcessCategoryDAO{

    public OrientDBProcessCategoryDAO() {
        super(ProcessCategory.class);
    }
    
}
