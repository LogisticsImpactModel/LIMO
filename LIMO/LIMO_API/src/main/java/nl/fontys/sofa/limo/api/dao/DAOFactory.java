package nl.fontys.sofa.limo.api.dao;

public interface DAOFactory {

    public CostCategoryDAO getCostCategoryDAO();

    public TimeCategoryDAO getTimeCategoryDAO();

    public EventDAO getEventDAO();

    public HubDAO getHubDAO();

    public HubTypeDAO getHubTypeDAO();

    public LegDAO getLegDAO();

    public LegTypeDAO getLegTypeDAO();

}
