/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.orientdb.dao;

import nl.fontys.sofa.limo.api.dao.LegDAO;
import nl.fontys.sofa.limo.domain.component.Leg;
import nl.fontys.sofa.limo.orientdb.database.OrientDBAccess;

public class OrientDBLegDAO extends OrientDBAbstractDAO<Leg> implements LegDAO {

    public OrientDBLegDAO(OrientDBAccess orientDBAccess) {
        super(orientDBAccess, Leg.class);
    }
}
