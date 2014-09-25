/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.orientdb.database;

import nl.fontys.sofa.limo.api.dao.CostCategoryDAO;
import nl.fontys.sofa.limo.api.dao.DAOFactory;
import nl.fontys.sofa.limo.api.dao.DelayCategoryDAO;
import nl.fontys.sofa.limo.api.dao.EventDAO;
import nl.fontys.sofa.limo.api.dao.HubDAO;
import nl.fontys.sofa.limo.api.dao.HubTypeDAO;
import nl.fontys.sofa.limo.api.dao.LegDAO;
import nl.fontys.sofa.limo.api.dao.LegTypeDAO;
import nl.fontys.sofa.limo.orientdb.dao.OrientDBCostCategoryDAO;
import nl.fontys.sofa.limo.orientdb.dao.OrientDBDelayCategoryDAO;

public class OrientDBDAOFactory implements DAOFactory {

    @Override
    public CostCategoryDAO getCostCategoryDAO() {
        return new OrientDBCostCategoryDAO();
    }

    @Override
    public DelayCategoryDAO getDelayCategoryDAO() {
        return new OrientDBDelayCategoryDAO();
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
