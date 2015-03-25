package nl.fontys.sofa.limo.domain.component.procedure;

import java.io.Serializable;
import javax.persistence.Embedded;
import nl.fontys.sofa.limo.domain.component.procedure.value.Value;

/**
 * A procedure always happens at a component and implies certain additional
 * costs in terms of monetary costs and time. For reporting purposes each
 * procedure belongs to a procedure category.
 *
 * @author Dominik Kaisers <d.kaisers@student.fontys.nl>
 */
public class Procedure implements Serializable {

    private String name;
    /**
     * category was chosen to be a String rather than a ProcedureCategory, so no
     * actual relations exist. This is easier w/ exporting and importing data.
     */
    private String category;
    @Embedded
    private Value cost;
    @Embedded
    private Value time;
    private TimeType timeType;

    public Procedure() {
    }

    public Procedure(String name, String category, Value cost, Value time, TimeType timeType) {
        this.name = name;
        this.category = category;
        this.cost = cost;
        this.time = time;
        this.timeType = timeType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Value getCost() {
        return cost;
    }

    public void setCost(Value cost) {
        this.cost = cost;
    }

    public Value getTime() {
        return time;
    }

    public void setTime(Value time) {
        this.time = time;
    }

    public TimeType getTimeType() {
        return timeType;
    }

    public void setTimeType(TimeType timeType) {
        this.timeType = timeType;
    }
}
