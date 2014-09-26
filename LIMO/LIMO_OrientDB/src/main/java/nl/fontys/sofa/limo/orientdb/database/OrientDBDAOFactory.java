/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.orientdb.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.fontys.sofa.limo.orientdb.dao.OrientDBLegTypeDAO;
import nl.fontys.sofa.limo.orientdb.dao.OrientDBEventDAO;
import nl.fontys.sofa.limo.orientdb.dao.OrientDBHubTypeDAO;
import nl.fontys.sofa.limo.orientdb.dao.OrientDBLegDAO;
import nl.fontys.sofa.limo.orientdb.dao.OrientDBHubDAO;
import nl.fontys.sofa.limo.api.dao.CostCategoryDAO;
import nl.fontys.sofa.limo.api.dao.DAO;
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

    private static OrientDBDAOFactory instance;
    private final Map<Class, DAO> daosCache;

    private OrientDBDAOFactory() {
        this.daosCache = new HashMap<>();
    }

    public static OrientDBDAOFactory getInstance() {
        if (instance == null) {
            instance = new OrientDBDAOFactory();
        }
        return instance;
    }

    @Override
    public CostCategoryDAO getCostCategoryDAO() {
        CostCategoryDAO costCategoryDAO = (CostCategoryDAO) daosCache.get(CostCategoryDAO.class);
        if (costCategoryDAO == null) {
            daosCache.put(CostCategoryDAO.class, new OrientDBCostCategoryDAO(OrientDBAccess.getInstance()));
        }
        return costCategoryDAO;
    }

    @Override
    public TimeCategoryDAO getTimeCategoryDAO() {
        return new OrientDBTimeCategoryDAO(OrientDBAccess.getInstance());
    }

    @Override
    public EventDAO getEventDAO() {
        return new OrientDBEventDAO(OrientDBAccess.getInstance());
    }

    @Override
    public HubDAO getHubDAO() {
        return new OrientDBHubDAO(OrientDBAccess.getInstance());
    }

    @Override
    public HubTypeDAO getHubTypeDAO() {
        return new OrientDBHubTypeDAO(OrientDBAccess.getInstance());
    }

    @Override
    public LegDAO getLegDAO() {
        return new OrientDBLegDAO(OrientDBAccess.getInstance());
    }

    @Override
    public LegTypeDAO getLegTypeDAO() {
        return new OrientDBLegTypeDAO(OrientDBAccess.getInstance());
    }

}
