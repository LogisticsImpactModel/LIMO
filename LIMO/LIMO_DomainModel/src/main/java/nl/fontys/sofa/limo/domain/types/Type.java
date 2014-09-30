package nl.fontys.sofa.limo.domain.types;

import java.util.List;
import nl.fontys.sofa.limo.domain.BaseEntity;
import nl.fontys.sofa.limo.domain.Entry;
import nl.fontys.sofa.limo.domain.Icon;

public abstract class Type extends BaseEntity {

    protected String identifier;
    protected List<Entry> costs;
    protected List<Entry> leadTimes;
    protected List<Entry> delays;
    protected Icon icon;

    public Type(String identifier, List<Entry> costs, List<Entry> leadTimes, List<Entry> delays) {
        this.identifier = identifier;
        this.costs = costs;
        this.leadTimes = leadTimes;
        this.delays = delays;
        icon = null;
    }

    public Type(String identifier, List<Entry> costs, List<Entry> leadTimes, List<Entry> delays, Icon icon) {
        this.identifier = identifier;
        this.costs = costs;
        this.leadTimes = leadTimes;
        this.delays = delays;
        this.icon = icon;
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

    public Icon getIcon() {
        return icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }
}
