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
import org.openide.util.lookup.ServiceProvider;

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
            costCategoryDAO = new OrientDBCostCategoryDAO(OrientDBAccess.getInstance());
            daosCache.put(CostCategoryDAO.class, costCategoryDAO);
        }
        return costCategoryDAO;
    }

    @Override
    public TimeCategoryDAO getTimeCategoryDAO() {
        TimeCategoryDAO timeCategoryDAO = (TimeCategoryDAO) daosCache.get(TimeCategoryDAO.class);
        if (timeCategoryDAO == null) {
            timeCategoryDAO = new OrientDBTimeCategoryDAO(OrientDBAccess.getInstance());
            daosCache.put(TimeCategoryDAO.class, timeCategoryDAO);
        }
        return timeCategoryDAO;
    }

    @Override
    public EventDAO getEventDAO() {
        EventDAO eventDAO = (EventDAO) daosCache.get(EventDAO.class);
        if (eventDAO == null) {
            eventDAO = new OrientDBEventDAO(OrientDBAccess.getInstance());
            daosCache.put(EventDAO.class, eventDAO);
        }
        return eventDAO;
    }

    @Override
    public HubDAO getHubDAO() {
        HubDAO hubDAO = (HubDAO) daosCache.get(HubDAO.class);
        if (hubDAO == null) {
            hubDAO = new OrientDBHubDAO(OrientDBAccess.getInstance());
            daosCache.put(HubDAO.class, hubDAO);
        }
        return hubDAO;
    }

    @Override
    public HubTypeDAO getHubTypeDAO() {
        HubTypeDAO hubTypeDAO = (HubTypeDAO) daosCache.get(HubTypeDAO.class);
        if (hubTypeDAO == null) {
            hubTypeDAO = new OrientDBHubTypeDAO(OrientDBAccess.getInstance());
            daosCache.put(HubTypeDAO.class, hubTypeDAO);
        }
        return hubTypeDAO;
    }

    @Override
    public LegDAO getLegDAO() {
        LegDAO legDAO = (LegDAO) daosCache.get(LegDAO.class);
        if (legDAO == null) {
            legDAO = new OrientDBLegDAO(OrientDBAccess.getInstance());
            daosCache.put(LegDAO.class, legDAO);
        }
        return legDAO;
    }

    @Override
    public LegTypeDAO getLegTypeDAO() {
        LegTypeDAO legTypeDAO = (LegTypeDAO) daosCache.get(LegTypeDAO.class);
        if (legTypeDAO == null) {
            legTypeDAO = new OrientDBLegTypeDAO(OrientDBAccess.getInstance());
            daosCache.put(LegTypeDAO.class, legTypeDAO);
        }
        return legTypeDAO;
    }

}
