package nl.fontys.sofa.limo.domain.component.process;

import java.io.Serializable;
import javax.persistence.Embedded;
import nl.fontys.sofa.limo.domain.component.process.value.Value;

/**
 * A process is something happening inside a component with a cost and time
 * specified. For reporting purposes each process belongs to a process category.
 *
 * @author Dominik Kaisers <d.kaisers@student.fontys.nl>
 */
public class Process implements Serializable {

    private String name;
    private String category;
    @Embedded
    private Value cost;
    @Embedded
    private Value time;
    private TimeType timeType;
    private ProcessResponsibilityDirection direction;

    public Process() {
    }

    public Process(String name, String category, Value cost, Value time, TimeType timeType, ProcessResponsibilityDirection direction) {
        this.name = name;
        this.category = category;
        this.cost = cost;
        this.time = time;
        this.direction = direction;
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

    public ProcessResponsibilityDirection getDirection() {
        return direction;
    }

    public void setDirection(ProcessResponsibilityDirection direction) {
        this.direction = direction;
    }
}
