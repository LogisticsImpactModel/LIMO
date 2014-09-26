package nl.fontys.sofa.limo.domain;

import java.io.Serializable;

/**
 *
 * @author Dominik Kaisers <d.kaisers@student.fontys.nl>
 */
public abstract class BaseEntity implements Serializable{
    
    protected String id;
    protected long lastUpdate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(long lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
    
}
