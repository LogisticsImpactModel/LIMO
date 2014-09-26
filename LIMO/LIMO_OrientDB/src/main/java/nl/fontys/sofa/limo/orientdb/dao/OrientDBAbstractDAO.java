package nl.fontys.sofa.limo.orientdb.dao;

import com.orientechnologies.orient.core.iterator.ORecordIteratorClass;
import com.orientechnologies.orient.core.metadata.schema.OSchema;
import com.orientechnologies.orient.core.record.impl.ODocument;
import java.util.ArrayList;
import nl.fontys.sofa.limo.domain.BaseEntity;
import nl.fontys.sofa.limo.domain.category.CostCategory;
import nl.fontys.sofa.limo.orientdb.database.OrientDBAccess;

/**
 *
 * @author Dominik Kaisers <d.kaisers@student.fontys.nl>
 */
public abstract class OrientDBAbstractDAO{
    
    protected final OrientDBAccess orientDBAccess;

    public OrientDBAbstractDAO(OrientDBAccess orientDBAccess, String tableName) {
        this.orientDBAccess = orientDBAccess;
        
        // Create class in DB if it not yet exists
        OSchema schema = orientDBAccess.getConnection().getMetadata().getSchema();
        if (!schema.existsClass(tableName)) {
            schema.createClass(tableName);
        }
    }
    
}
