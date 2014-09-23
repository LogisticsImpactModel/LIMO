package nl.fontys.maven.mavenproject1;

import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.record.impl.ODocument;

public class NewClass {

    public static void main(String[] args) {
        // plocal for embedded
        ODatabaseDocumentTx db = new ODatabaseDocumentTx("plocal:/tmp/bugsac");
        if (!db.exists()) {
            db.create();
        } else {
            // This is the default user
            db.open("admin", "admin");
        }
        try {
            ODocument doc = new ODocument("Person");
            doc.field("name", "Luke");
            doc.field("surname", "Skywalker");
            doc.field("city", new ODocument("City").field("name", "Rome").field("country", "Italy"));
            doc.save();
        } finally {
            db.close();
        }
    }

}
