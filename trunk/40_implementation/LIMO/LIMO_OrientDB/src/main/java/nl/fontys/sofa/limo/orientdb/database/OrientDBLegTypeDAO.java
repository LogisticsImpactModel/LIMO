/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.orientdb.database;

import java.util.List;
import nl.fontys.sofa.limo.api.dao.LegTypeDAO;
import nl.fontys.sofa.limo.domain.types.LegType;

public class OrientDBLegTypeDAO implements LegTypeDAO {

    @Override
    public List<LegType> findAll() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public LegType findById() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void insert(LegType domainModel) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean update(LegType domainModel) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean delete(int domainModelId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getTableName() {
        return "legtypes";
    }

}
