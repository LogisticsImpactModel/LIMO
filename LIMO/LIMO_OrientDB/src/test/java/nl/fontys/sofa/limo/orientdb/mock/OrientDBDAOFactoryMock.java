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

public class OrientDBDAOFactoryMock implements DAOFactory {

    @Override
    public CostCategoryDAO getCostCategoryDAO() {
        return new OrientDBCostCategoryDAO(MockOrientDBAccess.getInstance());
    }

    @Override
    public TimeCategoryDAO getTimeCategoryDAO() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public EventDAO getEventDAO() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public HubDAO getHubDAO() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public HubTypeDAO getHubTypeDAO() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public LegDAO getLegDAO() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public LegTypeDAO getLegTypeDAO() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
