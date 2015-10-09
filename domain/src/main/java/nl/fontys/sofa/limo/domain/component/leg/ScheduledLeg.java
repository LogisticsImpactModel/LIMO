package nl.fontys.sofa.limo.domain.component.leg;

import com.google.gson.annotations.Expose;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.Embedded;
import nl.fontys.sofa.limo.validation.annotations.Min;
import nl.fontys.sofa.limo.validation.annotations.NotNull;
import nl.fontys.sofa.limo.validation.annotations.Size;

/**
 * Leg with a schedule and an alternative, if the schedule can't be uphold. Time
 * is always set in minutes.
 *
 * @author Dominik Kaisers {@literal <d.kaisers@student.fontys.nl>}
 */
public class ScheduledLeg extends Leg {

    @Expose private static final long serialVersionUID = -836462388111897335L;

    public static final String WAIT_CATEGORY = "Waiting Time";

    /**
     * Expected time of consignment to arrive. Any delay from the hub before is
     * added to this. Normally (and for ease of use) 0. [?]
     */
    @Min(1)
    @Expose private long expectedTime;

    /**
     * Delay from the hub before this leg.
     */
    @Expose private transient long delay;

    /**
     * Times after expected time, when the consigment can be send over this
     * route.
     */
    @Size(min=1)
    @Expose private List<Long> acceptanceTimes;

    /**
     * Maximum time between arrival (expectedTime + delay) and acceptance,
     * before alternative is used.
     */
   @Min(1)
   @Expose private long waitingTimeLimit;

    /**
     * Alternative leg, used when no acceptance times are possible (because of
     * delay) or the waiting time for acceptance is too long.
     */
    @NotNull
    @Embedded
    @Expose private Leg alternative;

    public ScheduledLeg() {
        this.acceptanceTimes = new ArrayList<>();
    }

    /**
     * Overwrites all attributes of the {@link MultiModeLeg}-object with the
     * attributes of the sourceLeg. The previous and next attributes are not
     * copied.
     *
     * @param sourceLeg
     */
    public ScheduledLeg(ScheduledLeg sourceLeg) {
        super(sourceLeg);
        deepOverwrite(sourceLeg);
    }

    /**
     * Overwrites all attributes of the {@link ScheduledLeg}-object with the
     * attributes of the sourceLeg. The previous and next attributes are not
     * copied.
     *
     * @param sourceLeg
     */
    public void deepOverwrite(ScheduledLeg sourceLeg) {
        super.deepOverwrite(sourceLeg);

        setExpectedTime(sourceLeg.getExpectedTime());
        setDelay(sourceLeg.getDelay());
        setAcceptanceTimes(sourceLeg.getAcceptanceTimes());
        setWaitingTimeLimit(sourceLeg.getWaitingTimeLimit());
        setAlternative(sourceLeg.getAlternative());
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
