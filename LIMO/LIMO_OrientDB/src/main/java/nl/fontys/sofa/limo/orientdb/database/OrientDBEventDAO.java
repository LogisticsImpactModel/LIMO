/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.orientdb.database;

import java.util.List;
import nl.fontys.sofa.limo.api.dao.EventDAO;
import nl.fontys.sofa.limo.domain.events.Event;

public class OrientDBEventDAO implements EventDAO {

    @Override
    public List<Event> findAll() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Event findById() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void insert(Event domainModel) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean update(Event domainModel) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean delete(int domainModelId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getTableName() {
        return "events";
    }

}
