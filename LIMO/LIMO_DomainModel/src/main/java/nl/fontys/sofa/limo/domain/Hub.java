package nl.fontys.sofa.limo.domain;

import java.util.HashMap;
import java.util.Map;
import nl.fontys.sofa.limo.domain.types.HubType;

/**
 * @author Matthias Brück
 */
public class Hub extends Component {

    private Leg inputLeg;
    private Leg outputLeg;
    private HubType type;
    private final Map<Entry, Actor> costResponsibilities;

    public Hub(String identifier, HubType type) {
        super(identifier);
        this.type = type;
        this.costResponsibilities = new HashMap<>();
    }

    public Leg getInputLeg() {
        return inputLeg;
    }

    public Leg getOutputLeg() {
        return outputLeg;
    }

    public HubType getType() {
        return type;
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

    public void setType(HubType type) {
        this.type = type;
    }
}