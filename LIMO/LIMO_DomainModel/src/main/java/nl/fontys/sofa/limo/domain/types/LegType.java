package nl.fontys.sofa.limo.domain.types;

import java.util.List;
import nl.fontys.sofa.limo.domain.Entry;

/**
 *
 * @author Dominik Kaisers <d.kaisers@student.fontys.nl>
 */
public class LegType extends Type{

    public LegType(String identifier, List<Entry> costs, List<Entry> leadTimes, List<Entry> delays, String iconID) {
        super(identifier, costs, leadTimes, delays, iconID);
    }
    
}
