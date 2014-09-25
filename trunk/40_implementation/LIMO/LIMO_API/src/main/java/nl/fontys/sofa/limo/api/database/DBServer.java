/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.api.database;

public interface DBServer<T> {

    public T getConnection();

    public void closeConnection();

}
