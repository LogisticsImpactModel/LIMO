/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.orientdb.database;

import java.util.List;
import nl.fontys.sofa.limo.api.dao.HubTypeDAO;
import nl.fontys.sofa.limo.domain.types.HubType;

public class OrientDBHubTypeDAO implements HubTypeDAO {

    @Override
    public List<HubType> findAll() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public HubType findById() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void insert(HubType domainModel) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean update(HubType domainModel) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean delete(int domainModelId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getTableName() {
        return "hubtypes";
    }

}
