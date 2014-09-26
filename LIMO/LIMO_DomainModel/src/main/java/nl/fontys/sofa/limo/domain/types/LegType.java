package nl.fontys.sofa.limo.domain.types;

import java.util.List;
import nl.fontys.sofa.limo.domain.Entry;
import nl.fontys.sofa.limo.domain.Icon;

/**
 *
 * @author Dominik Kaisers <d.kaisers@student.fontys.nl>
 */
public class LegType extends Type {

    public LegType(String identifier, List<Entry> costs, List<Entry> leadTimes, List<Entry> delays) {
        super(identifier, costs, leadTimes, delays);
    }

    public LegType(String identifier, List<Entry> costs, List<Entry> leadTimes, List<Entry> delays, Icon icon) {
        super(identifier, costs, leadTimes, delays, icon);
    }
}
