/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.orientdb.dao;

import java.util.List;
import nl.fontys.sofa.limo.api.dao.HubDAO;
import nl.fontys.sofa.limo.domain.component.Hub;
import nl.fontys.sofa.limo.orientdb.database.OrientDBAccess;

public class OrientDBHubDAO implements HubDAO {

    private final OrientDBAccess orientDBAccess;

    public OrientDBHubDAO(OrientDBAccess orientDBAccess) {
        this.orientDBAccess = orientDBAccess;
    }

    @Override
    public List<Hub> findAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Hub findById(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void insert(Hub entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean update(Hub entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getTableName() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
