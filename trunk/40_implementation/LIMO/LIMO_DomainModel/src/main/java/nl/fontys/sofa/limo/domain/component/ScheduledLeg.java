package nl.fontys.sofa.limo.domain.component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ScheduledLeg extends Leg {

    private Date expectationTime;
    private final List<Date> acceptanceTimes;
    private Leg alternativeLeg;
    private Date waitingTimeLimit;

    public ScheduledLeg(String identifier) {
        super(identifier);
        acceptanceTimes = new ArrayList<>();
    }

    public ScheduledLeg(String identifier, Date expectationTime, Leg alternativeLeg, Date waitingTimeLimit) {
        super(identifier);
        this.expectationTime = expectationTime;
        this.alternativeLeg = alternativeLeg;
        this.waitingTimeLimit = waitingTimeLimit;
        acceptanceTimes = new ArrayList<>();
    }

    public Date getExpectationTime() {
        return expectationTime;
    }

    public List<Date> getAcceptanceTimes() {
        return acceptanceTimes;
    }

    public Leg getAlternativeLeg() {
        return alternativeLeg;
    }

    public Date getWaitingTimeLimit() {
        return waitingTimeLimit;
    }

    public void setExpectationTime(Date expectationTime) {
        this.expectationTime = expectationTime;
    }

    public void setAlternativeLeg(Leg alternativeLeg) {
        this.alternativeLeg = alternativeLeg;
    }

    public void setWaitingTimeLimit(Date waitingTimeLimit) {
        this.waitingTimeLimit = waitingTimeLimit;
    }
}
