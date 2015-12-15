package nl.fontys.sofa.limo.view.node;

/**
 * Interface to define beans deletables.
 * If a bean is a deletable, it is removed with calling the ClearDatabaseAction or
 * the DeleteAction when it is selected (in the catalog or in the chain)
 * @author Ennyo
 */
public interface Deletable {
    
    void delete();
    
}
