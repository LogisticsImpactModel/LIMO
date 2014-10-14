package nl.fontys.sofa.limo.domain.component;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import nl.fontys.sofa.limo.domain.Component;
import nl.fontys.sofa.limo.domain.Entry;

public class ScheduledLeg extends Leg {

    private Date expectationTime;
    private List<Date> acceptanceTimes;
    private Leg alternativeLeg;
    private Date waitingTimeLimit;

    public ScheduledLeg() {
    }

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

    // <editor-fold defaultstate="collapsed" desc=" ${GETTERS AND SETTERS} ">
    public Date getExpectationTime() {
        return expectationTime;
    }

    public void setExpectationTime(Date expectationTime) {
        this.expectationTime = expectationTime;
    }

    public List<Date> getAcceptanceTimes() {
        return acceptanceTimes;
    }

    public void setAcceptanceTimes(List<Date> acceptanceTimes) {
        this.acceptanceTimes = acceptanceTimes;
    }

    public Leg getAlternativeLeg() {
        return alternativeLeg;
    }

    public void setAlternativeLeg(Leg alternativeLeg) {
        this.alternativeLeg = alternativeLeg;
    }

    public Date getWaitingTimeLimit() {
        return waitingTimeLimit;
    }

    public void setWaitingTimeLimit(Date waitingTimeLimit) {
        this.waitingTimeLimit = waitingTimeLimit;
    }
    // </editor-fold>

    @Override
    public Component copy() {
        ScheduledLeg copied = new ScheduledLeg();
        ArrayList<Date> acceptanceTimesList = new ArrayList<>();
        for (Date acceptanceTime : acceptanceTimes) {
            Date d = new Date(acceptanceTime.getTime());
            acceptanceTimesList.add(d);
        }
        copied.setAcceptanceTimes(acceptanceTimesList);
        ArrayList<Entry> costsList = new ArrayList<>();
        for (Entry cost : costs) {
            costsList.add(cost.copy());
        }
        copied.setCosts(costsList);
        ArrayList<Entry> delaysList = new ArrayList<>();
        for (Entry delay : delays) {
            delaysList.add(delay.copy());
        }
        copied.setDelays(delaysList);
        copied.setDescription(description);
        copied.setExpectationTime(new Date(expectationTime.getTime()));
        copied.setIcon(icon.copy());
        copied.setIdentifier(identifier);
        copied.setLastUpdate(lastUpdate);
        copied.setWaitingTimeLimit(new Date(waitingTimeLimit.getTime()));
        return copied;
    }
}
