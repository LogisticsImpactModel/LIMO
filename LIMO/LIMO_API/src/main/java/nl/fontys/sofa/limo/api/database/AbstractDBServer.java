package nl.fontys.sofa.limo.api.database;

public abstract class AbstractDBServer<S> implements DBServer<S> {

    protected S connection;

    @Override
    public S getConnection() {
        return connection;
    }

    @Override
    public abstract void closeConnection();

    /**
     * Checks if the connection object exists, and whether the connection is
     * open. Creates and opens connection if needed.
     */
    protected abstract void checkConnection();

}
