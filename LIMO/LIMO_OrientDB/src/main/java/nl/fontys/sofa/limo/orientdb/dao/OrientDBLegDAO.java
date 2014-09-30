/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.orientdb.dao;

import com.orientechnologies.orient.core.record.impl.ODocument;
import java.util.List;
import nl.fontys.sofa.limo.api.dao.EventDAO;
import nl.fontys.sofa.limo.api.dao.LegDAO;
import nl.fontys.sofa.limo.domain.component.Event;
import nl.fontys.sofa.limo.domain.component.Leg;
import nl.fontys.sofa.limo.orientdb.database.OrientDBAccess;

public class OrientDBLegDAO extends OrientDBAbstractDAO<Leg> implements LegDAO {

    public OrientDBLegDAO(OrientDBAccess orientDBAccess) {
        super(orientDBAccess, "Legs");
    }
    @Override
    public Leg map(ODocument doc) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ODocument map(Leg entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
