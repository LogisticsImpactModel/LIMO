package nl.fontys.sofa.limo.domain.component;

import nl.fontys.sofa.limo.domain.Actor;
import nl.fontys.sofa.limo.domain.Component;
import nl.fontys.sofa.limo.domain.types.LegType;

/**
 * @author Matthias Brück
 */
public class Leg extends Component {

    private Hub startHub;
    private Hub endHub;
    private LegType type;
    private Actor actor;
    
    public Leg(String identifier){
        super(identifier);
    }
    
    public Leg(String identifier, LegType type){
        super(identifier);
        this.type = type;
    }

    public Hub getStartHub() {
        return this.startHub;
    }

    public Hub getEndHub() {
        return this.endHub;
    }

    public LegType getType() {
        return this.type;
    }

    public Actor getActor() {
        return this.actor;
    }

    public void setStartHub(Hub startHub) {
        this.startHub = startHub;
    }

    public void setEndHub(Hub endHub) {
        this.endHub = endHub;
    }

    public void setType(LegType type) {
        this.type = type;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }
}