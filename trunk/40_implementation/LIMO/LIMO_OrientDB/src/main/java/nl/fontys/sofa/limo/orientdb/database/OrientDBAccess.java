/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.orientdb.database;

import com.orientechnologies.orient.object.db.OObjectDatabaseTx;
import java.io.File;
import nl.fontys.sofa.limo.api.database.AbstractDBServer;

public class OrientDBAccess extends AbstractDBServer<OObjectDatabaseTx> {

    protected static OrientDBAccess instance;

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

    @Override
    protected void checkConnection() {
        if (connection == null) {
            String path = System.getProperty("user.home") + File.separator + "LIMO";
            connection = new OObjectDatabaseTx("plocal:" + path);

            if (!connection.exists()) {
                connection.create();
            }
        }

        if (connection.isClosed()) {
            connection.open("admin", "admin");
        }
    }

}
