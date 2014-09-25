/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.api.dao;

public interface DAOFactory {

    public CostCategoryDAO getCostCategoryDAO();

    public DelayCategoryDAO getDelayCategoryDAO();

    public EventDAO getEventDAO();

    public HubDAO getHubDAO();

    public HubTypeDAO getHubTypeDAO();

    public LegDAO getLegDAO();

    public LegTypeDAO getLegTypeDAO();

}
