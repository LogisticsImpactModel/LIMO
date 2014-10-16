package nl.fontys.sofa.limo.domain.types;

import java.util.List;
import nl.fontys.sofa.limo.domain.Entry;
import nl.fontys.sofa.limo.domain.Icon;

public class HubType extends Type {

    public HubType() {
    }

    public HubType(String identifier, List<Entry> costs, List<Entry> leadTimes, List<Entry> delays) {
        super(identifier, costs, leadTimes, delays);
    }

    public HubType(String identifier, List<Entry> costs, List<Entry> leadTimes, List<Entry> delays, Icon icon) {
        super(identifier, costs, leadTimes, delays, icon);
    }
}