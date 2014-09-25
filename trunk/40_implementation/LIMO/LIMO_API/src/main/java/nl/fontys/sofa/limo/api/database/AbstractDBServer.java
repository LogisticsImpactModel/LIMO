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
        return connection;
    }

    @Override
    public abstract void closeConnection();

}
