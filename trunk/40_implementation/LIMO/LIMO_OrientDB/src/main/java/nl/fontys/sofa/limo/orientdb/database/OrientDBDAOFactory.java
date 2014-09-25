/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.orientdb.database;

import nl.fontys.sofa.limo.orientdb.dao.OrientDBLegTypeDAO;
import nl.fontys.sofa.limo.orientdb.dao.OrientDBEventDAO;
import nl.fontys.sofa.limo.orientdb.dao.OrientDBHubTypeDAO;
import nl.fontys.sofa.limo.orientdb.dao.OrientDBLegDAO;
import nl.fontys.sofa.limo.orientdb.dao.OrientDBHubDAO;
import nl.fontys.sofa.limo.api.dao.CostCategoryDAO;
import nl.fontys.sofa.limo.api.dao.DAOFactory;
import nl.fontys.sofa.limo.api.dao.TimeCategoryDAO;
import nl.fontys.sofa.limo.api.dao.EventDAO;
import nl.fontys.sofa.limo.api.dao.HubDAO;
import nl.fontys.sofa.limo.api.dao.HubTypeDAO;
import nl.fontys.sofa.limo.api.dao.LegDAO;
import nl.fontys.sofa.limo.api.dao.LegTypeDAO;
import nl.fontys.sofa.limo.orientdb.dao.OrientDBCostCategoryDAO;
import nl.fontys.sofa.limo.orientdb.dao.OrientDBTimeCategoryDAO;

public class OrientDBDAOFactory implements DAOFactory {

    @Override
    public CostCategoryDAO getCostCategoryDAO() {
        return new OrientDBCostCategoryDAO();
    }

    @Override
    public TimeCategoryDAO getDelayCategoryDAO() {
        return new OrientDBTimeCategoryDAO();
    }

    @Override
    public EventDAO getEventDAO() {
        return new OrientDBEventDAO();
    }

    @Override
    public HubDAO getHubDAO() {
        return new OrientDBHubDAO();
    }

    @Override
    public HubTypeDAO getHubTypeDAO() {
        return new OrientDBHubTypeDAO();
    }

    @Override
    public LegDAO getLegDAO() {
        return new OrientDBLegDAO();
    }

    @Override
    public LegTypeDAO getLegTypeDAO() {
        return new OrientDBLegTypeDAO();
    }

}
