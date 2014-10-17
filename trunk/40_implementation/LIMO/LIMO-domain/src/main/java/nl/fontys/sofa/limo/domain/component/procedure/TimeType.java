package nl.fontys.sofa.limo.domain.component.procedure;

/**
 * Time type.
 *
 * @author Dominik Kaisers <d.kaisers@student.fontys.nl>
 */
public enum TimeType {
    
    MINUTES("Minutes", 1),
    HOURS("Hours", 60),
    DAYS("Days", 1440),
    WEEKS("Weeks", 10080);
    
    private final String name;
    private final int minutes;

    private TimeType(String name, int minutes) {
        this.name = name;
        this.minutes = minutes;
    }

    /**
     * Name of time type.
     * @return Name of time type.
     */
    public String getName() {
        return name;
    }
    
    /**
     * Minutes the time type has for a value of 1.
     * @return Minutes for time type value of 1.
     */
    public int getMinutes() {
        return minutes;
    }
    
    /**
     * Calculates the minutes for a time type, given an input for this time type.
     * @param input Value for this time type.
     * @return Value of this time type in minutes.
     */
    public double getMinutes(double input) {
        return input * minutes;
    }

    @Override
    public String toString() {
        return this.name;
    }
    
}
