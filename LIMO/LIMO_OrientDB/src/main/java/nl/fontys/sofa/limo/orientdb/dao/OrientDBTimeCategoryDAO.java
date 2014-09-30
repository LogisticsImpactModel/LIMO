package nl.fontys.sofa.limo.orientdb.dao;

import nl.fontys.sofa.limo.api.dao.TimeCategoryDAO;
import nl.fontys.sofa.limo.domain.category.TimeCategory;
import nl.fontys.sofa.limo.orientdb.database.OrientDBAccess;

public class OrientDBTimeCategoryDAO extends OrientDBAbstractDAO<TimeCategory> implements TimeCategoryDAO {

    public OrientDBTimeCategoryDAO(OrientDBAccess orientDBAccess) {
        super(orientDBAccess, TimeCategory.class);
    }

}
