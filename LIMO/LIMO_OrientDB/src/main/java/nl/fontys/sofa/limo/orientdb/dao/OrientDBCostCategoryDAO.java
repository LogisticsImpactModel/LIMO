/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.orientdb.dao;

import nl.fontys.sofa.limo.api.dao.CostCategoryDAO;
import nl.fontys.sofa.limo.domain.category.CostCategory;
import nl.fontys.sofa.limo.orientdb.database.OrientDBAccess;

public class OrientDBCostCategoryDAO extends OrientDBAbstractDAO<CostCategory> implements CostCategoryDAO {

    public OrientDBCostCategoryDAO(OrientDBAccess orientDBAccess) {
        super(orientDBAccess, CostCategory.class);
    }

}
