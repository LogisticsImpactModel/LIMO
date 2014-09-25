/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.orientdb.database;

import java.util.List;
import nl.fontys.sofa.limo.api.dao.HubDAO;
import nl.fontys.sofa.limo.domain.Hub;

public class OrientDBHubDAO implements HubDAO {

    @Override
    public List<Hub> findAll() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Hub findById() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void insert(Hub domainModel) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean update(Hub domainModel) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean delete(int domainModelId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getTableName() {
        return "hubs";
    }

}
