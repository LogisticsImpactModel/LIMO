package nl.fontys.sofa.limo.domain;

import java.io.Serializable;
import javax.persistence.Id;

public abstract class BaseEntity implements Serializable {

    @Id
    protected String id;
    protected long lastUpdate;

    // <editor-fold defaultstate="collapsed" desc=" ${GETTERS AND SETTERS} ">
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
    // </editor-fold>
}
