/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.orientdb.dao;

import com.orientechnologies.orient.core.id.ORecordId;
import com.orientechnologies.orient.core.metadata.schema.OType;
import com.orientechnologies.orient.core.record.impl.ODocument;
import java.util.List;
import nl.fontys.sofa.limo.api.dao.EventDAO;
import nl.fontys.sofa.limo.domain.Actor;
import nl.fontys.sofa.limo.domain.component.Event;
import nl.fontys.sofa.limo.orientdb.database.OrientDBAccess;

public class OrientDBEventDAO extends OrientDBAbstractDAO<Event> implements EventDAO {

    public OrientDBEventDAO(OrientDBAccess orientDBAccess) {
        super(orientDBAccess, "Events");
    }

    @Override
    public List<Event> findAll() {
        return super.findAll();
    }

    @Override
    public Event findById(String id) {
        return super.findById(id);
    }

    @Override
    public void insert(Event entity) {
        super.insert(entity);
    }

    @Override
    public boolean update(Event entity) {
        return super.update(entity);
    }

    @Override
    public boolean delete(String id) {
        return super.delete(id);
    }

    @Override
    public Event map(ODocument doc) {
        Event event = new Event();
        event.setId(doc.getIdentity().toString());
        event.setLastUpdate((long) doc.field("lastUpdate"));
        event.setIdentifier((String) doc.field("identifier"));
        event.setActor((Actor) doc.field("actor", OType.EMBEDDED));
        /*event.setCosts(null);
         event.setDelays(null);
         event.setDependency(null);
         event.setExecutionState(true);
         event.setIcon(null);
         event.setLeadTimes(null);
         event.setParent(event);
         event.setProbability(null);*/
        return event;
    }

    @Override
    public ODocument map(Event entity) {
        ODocument doc;
        if (entity.getId() != null) {
            doc = new ODocument(tableName, new ORecordId(entity.getId()));
        } else {
            doc = new ODocument(tableName);
        }
        doc.field("lastUpdate", entity.getLastUpdate());
        doc.field("identifier", entity.getIdentifier());
        doc.field("actor", entity.getActor(), OType.EMBEDDED);
        return doc;
    }

}
