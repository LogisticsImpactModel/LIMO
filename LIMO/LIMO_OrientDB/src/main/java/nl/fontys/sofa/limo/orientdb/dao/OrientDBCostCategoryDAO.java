/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.orientdb.dao;

import java.util.List;
import nl.fontys.sofa.limo.api.dao.CostCategoryDAO;
import nl.fontys.sofa.limo.domain.category.CostCategory;

public class OrientDBCostCategoryDAO implements CostCategoryDAO {

    @Override
    public List<CostCategory> findAll() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public CostCategory findById(String id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void insert(CostCategory entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean update(CostCategory entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean delete(String id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getTableName() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
