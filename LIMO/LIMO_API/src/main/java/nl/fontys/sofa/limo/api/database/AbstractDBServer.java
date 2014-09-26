/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.api.database;

public abstract class AbstractDBServer<S> implements DBServer<S> {

    protected S connection;

    @Override
    public S getConnection() {
        checkConnection();
        
        return connection;
    }

    @Override
    public abstract void closeConnection();

    /**
     * Checks if the connection object exists, and whether the connection is open. Creates and opens
     * connection if needed.
     */
    protected abstract void checkConnection();

}
