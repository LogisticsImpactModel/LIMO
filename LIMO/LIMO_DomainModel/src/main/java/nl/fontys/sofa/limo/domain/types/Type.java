package nl.fontys.sofa.limo.domain.types;

import java.util.List;
import nl.fontys.sofa.limo.domain.Entry;

/**
 *
 * @author Dominik Kaisers <d.kaisers@student.fontys.nl>
 */
public abstract class Type {
    
    protected String identifier;
    protected List<Entry> costs;
    protected List<Entry> leadTimes;
    protected List<Entry> delays;
    
    /**
     * ID of ORecordBytes entry in OrientDB.
     */
    protected String iconID;
    
    /**
     * Actual bytes the icon is composed of.
     */
    protected byte[] icon;

    public Type(String identifier, List<Entry> costs, List<Entry> leadTimes, List<Entry> delays, String iconID) {
        this.identifier = identifier;
        this.costs = costs;
        this.leadTimes = leadTimes;
        this.delays = delays;
        this.iconID = iconID;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public List<Entry> getCosts() {
        return costs;
    }

    public void setCosts(List<Entry> costs) {
        this.costs = costs;
    }

    public List<Entry> getLeadTimes() {
        return leadTimes;
    }

    public void setLeadTimes(List<Entry> leadTimes) {
        this.leadTimes = leadTimes;
    }

    public List<Entry> getDelays() {
        return delays;
    }

    public void setDelays(List<Entry> delays) {
        this.delays = delays;
    }

    public String getIconID() {
        return iconID;
    }

    public byte[] getIcon() {
        // TODO test if set, if not, get from DB with iconID
        
        return icon;
    }

    public void setIcon(byte[] icon) {
        // TODO Check references in DB, if >1 then keep, otherwise delete
        // TODO Create new ORecordBytes record in DB and link to iconID
        
        this.icon = icon;
    }
    
}
