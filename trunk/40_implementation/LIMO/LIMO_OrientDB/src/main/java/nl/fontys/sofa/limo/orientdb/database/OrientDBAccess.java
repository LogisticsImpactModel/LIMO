/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.orientdb.database;

import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import java.io.File;
import nl.fontys.sofa.limo.api.database.AbstractDBServer;

public class OrientDBAccess extends AbstractDBServer<ODatabaseDocumentTx> {

    private static OrientDBAccess instance;

    private OrientDBAccess() {
        String path = System.getProperty("user.home") + File.separator + "LIMO";
        connection = new ODatabaseDocumentTx("plocal:" + path);
        
        if (!connection.exists()) {
            connection.create();
        } else {
            connection.open("admin", "admin");
        }
    }

    public synchronized static OrientDBAccess getInstance() {
        if (instance == null) {
            instance = new OrientDBAccess();
        }
        return instance;
    }

    @Override
    public void closeConnection() {
        connection.close();
    }

}
