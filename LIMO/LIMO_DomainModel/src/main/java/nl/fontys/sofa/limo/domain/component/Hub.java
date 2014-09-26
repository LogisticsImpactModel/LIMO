package nl.fontys.sofa.limo.domain.component;

import java.util.HashMap;
import java.util.Map;
import nl.fontys.sofa.limo.domain.Actor;
import nl.fontys.sofa.limo.domain.Component;
import nl.fontys.sofa.limo.domain.Entry;

/**
 * @author Matthias Brück
 */
public class Hub extends Component {

    private Leg inputLeg;
    private Leg outputLeg;
    private final HashMap<Entry, Actor> costResponsibilities;

    public Hub(String identifier) {
        super(identifier);
        this.costResponsibilities = new HashMap<>();
    }

    public Leg getInputLeg() {
        return inputLeg;
    }

    public Leg getOutputLeg() {
        return outputLeg;
    }

    public Map<Entry, Actor> getCostResponsibilities() {
        return costResponsibilities;
    }

    public void setInputLeg(Leg inputLeg) {
        this.inputLeg = inputLeg;
    }

    public void setOutputLeg(Leg outputLeg) {
        this.outputLeg = outputLeg;
    }
}
