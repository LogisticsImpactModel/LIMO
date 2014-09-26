package nl.fontys.sofa.limo.orientdb.mock;

import nl.fontys.sofa.limo.api.dao.CostCategoryDAO;
import nl.fontys.sofa.limo.api.dao.DAOFactory;
import nl.fontys.sofa.limo.api.dao.EventDAO;
import nl.fontys.sofa.limo.api.dao.HubDAO;
import nl.fontys.sofa.limo.api.dao.HubTypeDAO;
import nl.fontys.sofa.limo.api.dao.LegDAO;
import nl.fontys.sofa.limo.api.dao.LegTypeDAO;
import nl.fontys.sofa.limo.api.dao.TimeCategoryDAO;
import nl.fontys.sofa.limo.orientdb.dao.OrientDBCostCategoryDAO;
import nl.fontys.sofa.limo.orientdb.dao.OrientDBEventDAO;
import nl.fontys.sofa.limo.orientdb.dao.OrientDBHubDAO;
import nl.fontys.sofa.limo.orientdb.dao.OrientDBHubTypeDAO;
import nl.fontys.sofa.limo.orientdb.dao.OrientDBLegDAO;
import nl.fontys.sofa.limo.orientdb.dao.OrientDBLegTypeDAO;
import nl.fontys.sofa.limo.orientdb.dao.OrientDBTimeCategoryDAO;

public class OrientDBDAOFactoryMock implements DAOFactory {

    @Override
    public CostCategoryDAO getCostCategoryDAO() {
        return new OrientDBCostCategoryDAO(MockOrientDBAccess.getInstance());
    }

    @Override
    public TimeCategoryDAO getTimeCategoryDAO() {
        return new OrientDBTimeCategoryDAO(MockOrientDBAccess.getInstance());
    }

    @Override
    public EventDAO getEventDAO() {
        return new OrientDBEventDAO(MockOrientDBAccess.getInstance());
    }

    @Override
    public HubDAO getHubDAO() {
        return new OrientDBHubDAO(MockOrientDBAccess.getInstance());
    }

    @Override
    public HubTypeDAO getHubTypeDAO() {
        return new OrientDBHubTypeDAO(MockOrientDBAccess.getInstance());
    }

    @Override
    public LegDAO getLegDAO() {
        return new OrientDBLegDAO(MockOrientDBAccess.getInstance());
    }

    @Override
    public LegTypeDAO getLegTypeDAO() {
        return new OrientDBLegTypeDAO(MockOrientDBAccess.getInstance());
    }

}
