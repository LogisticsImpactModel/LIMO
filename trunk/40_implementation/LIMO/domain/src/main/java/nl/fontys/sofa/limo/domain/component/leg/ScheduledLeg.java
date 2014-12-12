package nl.fontys.sofa.limo.domain.component.leg;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.Embedded;

/**
 * Leg with a schedule and an alternative, if the schedule can't be uphold. Time
 * is always set in minutes.
 *
 * @author Dominik Kaisers <d.kaisers@student.fontys.nl>
 */
public class ScheduledLeg extends Leg {

    public static final String WAIT_CATEGORY = "Waiting Time";

    /**
     * Expected time of consignment to arrive. Any delay from the hub before is
     * added to this. Normally (and for ease of use) 0. [?]
     */
    private long expectedTime;

    /**
     * Delay from the hub before this leg.
     */
    private transient long delay;

    /**
     * Times after expected time, when the consigment can be send over this
     * route.
     */
    private List<Long> acceptanceTimes;

    /**
     * Maximum time between arrival (expectedTime + delay) and acceptance,
     * before alternative is used.
     */
    private long waitingTimeLimit;

    /**
     * Alternative leg, used when no acceptance times are possible (because of
     * delay) or the waiting time for acceptance is too long.
     */
    @Embedded
    private Leg alternative;

    public ScheduledLeg() {
        this.acceptanceTimes = new ArrayList<>();
    }

    public long getExpectedTime() {
        return expectedTime;
    }

    public void setExpectedTime(long expectedTime) {
        this.expectedTime = expectedTime;
    }

    public long getDelay() {
        return delay;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }

    /**
     * Sorts the arrayList acceptanceTimes and returns it
     *
     * @return a sorted list (asc) with times that are acceptable
     */
    public List<Long> getAcceptanceTimes() {
        Collections.sort(acceptanceTimes);
        return acceptanceTimes;
    }

    public void setAcceptanceTimes(List<Long> acceptanceTimes) {
        this.acceptanceTimes = acceptanceTimes;
    }

    public long getWaitingTimeLimit() {
        return waitingTimeLimit;
    }

    public void setWaitingTimeLimit(long waitingTimeLimit) {
        this.waitingTimeLimit = waitingTimeLimit;
    }

    public Leg getAlternative() {
        return alternative;
    }

    public void setAlternative(Leg alternative) {
        this.alternative = alternative;
    }

}
