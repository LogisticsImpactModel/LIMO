package nl.fontys.sofa.limo.orientdb.dao;

import nl.fontys.sofa.limo.api.dao.ProcedureCategoryDAO;
import nl.fontys.sofa.limo.domain.component.process.ProcedureCategory;

/**
 *
 * @author Dominik Kaisers <d.kaisers@student.fontys.nl>
 */
public class OrientDBProcedureCategoryDAO extends OrientDBAbstractDAO<ProcedureCategory> implements ProcedureCategoryDAO{

    public OrientDBProcedureCategoryDAO() {
        super(ProcedureCategory.class);
    }
    
}
