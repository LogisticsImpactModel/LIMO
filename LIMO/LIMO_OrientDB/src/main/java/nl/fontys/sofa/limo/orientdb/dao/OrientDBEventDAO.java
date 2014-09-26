/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.orientdb.dao;

import com.orientechnologies.orient.core.id.ORecordId;
import com.orientechnologies.orient.core.metadata.schema.OType;
import com.orientechnologies.orient.core.record.impl.ODocument;
import java.util.ArrayList;
import nl.fontys.sofa.limo.api.dao.EventDAO;
import nl.fontys.sofa.limo.domain.Actor;
import nl.fontys.sofa.limo.domain.Component;
import nl.fontys.sofa.limo.domain.Entry;
import nl.fontys.sofa.limo.domain.Icon;
import nl.fontys.sofa.limo.domain.component.Event;
import nl.fontys.sofa.limo.domain.component.EventExecutionStateDependency;
import nl.fontys.sofa.limo.domain.distribution.DistributionType;
import nl.fontys.sofa.limo.orientdb.database.OrientDBAccess;

public class OrientDBEventDAO extends OrientDBAbstractDAO<Event> implements EventDAO {

    public OrientDBEventDAO(OrientDBAccess orientDBAccess) {
        super(orientDBAccess, "Events");
    }

    @Override
    public Event map(ODocument doc) {
        Event event = new Event();
        event.setId(doc.getIdentity().toString());
        event.setLastUpdate((long) doc.field("lastUpdate"));
        event.setIdentifier((String) doc.field("identifier"));
        event.setActor((Actor) doc.field("actor", OType.EMBEDDED));
        //event.setCosts(doc.fields("costs", OType.EMBEDDEDLIST));
        event.setDelays((ArrayList<Entry>) doc.field("delays", OType.EMBEDDEDLIST));
        event.setDependency((EventExecutionStateDependency) doc.field("depency"));
        event.setExecutionState((boolean) doc.field("executionState"));
        event.setIcon((Icon) doc.field("icon"));
        event.setLeadTimes((ArrayList<Entry>) doc.field("leadTimes"));
        event.setParent((Component) doc.field("parent"));
        event.setProbability((DistributionType) doc.field("propability"));
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
        doc.field("costs", entity.getCosts(), OType.EMBEDDEDLIST);
        doc.field("delays", entity.getDelays(), OType.EMBEDDEDLIST);
        doc.field("dependency", entity.getDependency());
        doc.field("executionState", entity.isExecutionState());
        doc.field("icon", entity.getIcon());
        doc.field("leadTimes", entity.getLeadTimes(), OType.EMBEDDEDLIST);
        doc.field("parent", entity.getParent());
        doc.field("probability", entity.getProbability());
        return doc;
    }

}
