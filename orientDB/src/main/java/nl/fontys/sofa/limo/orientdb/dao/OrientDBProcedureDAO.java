package nl.fontys.sofa.limo.orientdb.dao;

import nl.fontys.sofa.limo.api.dao.ProcedureDAO;
import nl.fontys.sofa.limo.domain.component.procedure.Procedure;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author Dominik Kaisers {@literal <d.kaisers@student.fontys.nl>}
 */
@ServiceProvider(service = ProcedureDAO.class)
public class OrientDBProcedureDAO extends OrientDBAbstractDAO<Procedure> implements ProcedureDAO {

    public OrientDBProcedureDAO() {
        super(Procedure.class);
        if (this.findByUniqueIdentifier("-1") == null) {
            Procedure defaultProcedure = new Procedure();
            defaultProcedure.setName("Costs not accounted");
            defaultProcedure.setDescription("Default procedure");
            defaultProcedure.setUniqueIdentifier("-1");
            this.insert(defaultProcedure);
        }
    }

}
