package nl.fontys.sofa.limo.domain.component.procedure;

/**
 * Shows whether a process belongs to the input or output actor.
 *
 * @author Matthias Brück
 */
public enum ProcedureResponsibilityDirection {

    INPUT("Input"),
    OUTPUT("Output"),
    BOTH("Both");

    private final String name;

    private ProcedureResponsibilityDirection(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
