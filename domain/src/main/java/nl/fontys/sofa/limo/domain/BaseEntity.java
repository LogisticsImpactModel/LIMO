package nl.fontys.sofa.limo.domain;

import com.google.gson.annotations.Expose;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
    @Expose
    protected String id;
    @Size(min = 1)
    @Expose
    protected String name;
    @Expose
    protected long lastUpdate;
    @Expose
    protected String uniqueIdentifier;
    @Expose
    protected String description;

    protected final transient List<PropertyChangeListener> listeners = new ArrayList<>();

    public BaseEntity(String name, String description) {
        this.uniqueIdentifier = UUID.randomUUID().toString();
        this.name = name;
        this.description = description;
        // this.listeners = new ArrayList<>();
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        listeners.add(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        listeners.remove(listener);
    }

    protected void firePropertyChange() {
        PropertyChangeEvent event = new PropertyChangeEvent(this, "", this, this);
        if (listeners != null) {
            listeners.parallelStream().forEach((listern) -> {
                listern.propertyChange(event);
            });
        }
    }

    public BaseEntity() {
        this.uniqueIdentifier = UUID.randomUUID().toString();
        //listeners = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
        firePropertyChange();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        firePropertyChange();
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
        firePropertyChange();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        firePropertyChange();
    }

}
