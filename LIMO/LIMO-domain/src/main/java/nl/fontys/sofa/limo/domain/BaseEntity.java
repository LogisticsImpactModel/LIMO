package nl.fontys.sofa.limo.domain;

import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Id;

/**
 * This class is the superclass of all entities that will be saved to their own
 * database table.
 *
 * @author Dominik Kaisers <d.kaisers@student.fontys.nl>
 */
public class BaseEntity implements Serializable {

    @Id
    protected String id;
    protected String name;
    protected long lastUpdate;
    protected String uniqueIdentifier;
    protected String description;

    public BaseEntity() {
        this.uniqueIdentifier = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(long lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getUniqueIdentifier() {
        return uniqueIdentifier;
    }

    public void setUniqueIdentifier(String uniqueIdentifier) {
        this.uniqueIdentifier = uniqueIdentifier;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
