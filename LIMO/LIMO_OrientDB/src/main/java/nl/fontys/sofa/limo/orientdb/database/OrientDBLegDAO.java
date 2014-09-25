/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.orientdb.database;

import java.util.List;
import nl.fontys.sofa.limo.api.dao.LegDAO;
import nl.fontys.sofa.limo.domain.component.Leg;

public class OrientDBLegDAO implements LegDAO {

    @Override
    public List<Leg> findAll() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Leg findById() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void insert(Leg domainModel) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean update(Leg domainModel) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean delete(int domainModelId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getTableName() {
        return "legs";
    }

}
