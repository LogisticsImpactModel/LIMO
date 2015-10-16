package nl.fontys.sofa.limo.orientdb.dao;

import nl.fontys.sofa.limo.api.dao.ProcedureCategoryDAO;
import nl.fontys.sofa.limo.domain.component.procedure.ProcedureCategory;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author Dominik Kaisers {@literal <d.kaisers@student.fontys.nl>}
 */
@ServiceProvider(service = ProcedureCategoryDAO.class)
public class OrientDBProcedureCategoryDAO extends OrientDBAbstractDAO<ProcedureCategory> implements ProcedureCategoryDAO {

    public OrientDBProcedureCategoryDAO() {
        super(ProcedureCategory.class);
        if (this.findByUniqueIdentifier("-1") == null) {
            ProcedureCategory defaultProcedure = new ProcedureCategory();
            defaultProcedure.setName("Costs not accounted");
            defaultProcedure.setDescription("Default procedure");
            defaultProcedure.setUniqueIdentifier("-1");
            this.insert(defaultProcedure);
        }
    }

}
