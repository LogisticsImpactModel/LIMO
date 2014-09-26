package nl.fontys.sofa.limo.orientdb.dao;

import com.orientechnologies.orient.core.id.ORecordId;
import com.orientechnologies.orient.core.record.impl.ODocument;
import nl.fontys.sofa.limo.api.dao.IconDAO;
import nl.fontys.sofa.limo.domain.Icon;
import nl.fontys.sofa.limo.orientdb.database.OrientDBAccess;

/**
 *
 * @author Dominik Kaisers <d.kaisers@student.fontys.nl>
 */
public class OrientDBIconDAO extends OrientDBAbstractDAO<Icon> implements IconDAO{

    public OrientDBIconDAO(OrientDBAccess orientDBAccess) {
        super(orientDBAccess, "Icons");
    }

    @Override
    public Icon map(ODocument doc) {
        if (doc == null) {
            return null;
        }
        
        Icon icon = new Icon();
        icon.setId(doc.getIdentity().toString());
        icon.setLastUpdate((long) doc.field("lastUpdate"));
        icon.setIcon((byte[]) doc.field("iconByteArray"));
        return icon;
    }

    @Override
    public ODocument map(Icon entity) {
        if (entity == null) {
            return null;
        }
        
        ODocument doc;
        if (entity.getId() != null) {
            doc = new ODocument(tableName, new ORecordId(entity.getId()));
        } else {
            doc = new ODocument(tableName);
        }
        doc.field("lastUpdate", entity.getLastUpdate());
        doc.field("iconByteArray", entity.getIconBytes());
        return doc;
    }
    
}
