package nl.fontys.sofa.limo.domain;

import com.google.gson.annotations.Expose;
import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Id;
import nl.fontys.sofa.limo.validation.annotations.Size;

/**
 * This class is the superclass of all entities that will be saved to their own
 * database table.
 *
 * @author Dominik Kaisers {@literal <d.kaisers@student.fontys.nl>}
 */
public class BaseEntity implements Serializable {

    @Id
    @Expose protected String id;
    @Size(min=1)
    @Expose protected String name;
    @Expose protected long lastUpdate;
    @Expose protected String uniqueIdentifier;
    @Expose protected String description;

    public BaseEntity(String name, String description) {
        this.uniqueIdentifier = UUID.randomUUID().toString();
        this.name = name;
        this.description = description;
    }

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
