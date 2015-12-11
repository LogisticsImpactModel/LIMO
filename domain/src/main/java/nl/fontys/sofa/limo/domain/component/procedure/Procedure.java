package nl.fontys.sofa.limo.domain.component.procedure;

import com.google.gson.annotations.Expose;
import javax.persistence.Embedded;
import nl.fontys.sofa.limo.domain.BaseEntity;
import nl.fontys.sofa.limo.domain.component.procedure.value.Value;

/**
 * A procedure always happens at a component and implies certain additional
 * costs in terms of monetary costs and time. For reporting purposes each
 * procedure belongs to a procedure category.
 *
 * @author Dominik Kaisers {@literal <d.kaisers@student.fontys.nl>}
 */
public class Procedure extends BaseEntity {

    /**
     * category was chosen to be a String rather than a ProcedureCategory, so no
     * actual relations exist. This is easier w/ exporting and importing data.
     */
    @Expose
    protected String category;
    @Embedded
    @Expose
    protected Value cost;
    @Embedded
    @Expose
    protected Value time;
    @Expose
    protected TimeType timeType;
    @Expose
    protected Value cotwo;

    public Procedure() {
        super();
    }

    public Procedure(Procedure procedure) {
        super();
        deepOverwrite(procedure);
    }

    public Procedure(String name, String category, Value cost, Value time, TimeType timeType, Value cotwo) {
        super();
        this.name = name;
        this.category = category;
        this.cost = cost;
        this.time = time;
        this.cotwo = cotwo;
        this.timeType = timeType;
    }

    public void deepOverwrite(Procedure procedure) {
        setName(procedure.getName());
        setCategory(procedure.getCategory());
        setCost(procedure.getCost());
        setTime(procedure.getTime());
        setCotwo(procedure.getCotwo());
        setTimeType(procedure.getTimeType());
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
        firePropertyChange();
    }

    public Value getCost() {
        return cost;
    }

    public void setCost(Value cost) {
        this.cost = cost;
        firePropertyChange();
    }

    public Value getTime() {
        return time;
    }

    public void setTime(Value time) {
        this.time = time;
        firePropertyChange();
    }

    public TimeType getTimeType() {
        return timeType;
    }

    public void setTimeType(TimeType timeType) {
        this.timeType = timeType;
        firePropertyChange();
    }

    public Value getCotwo() {
        return cotwo;
    }

    public void setCotwo(Value cotwo) {
        this.cotwo = cotwo;
        firePropertyChange();
    }

    @Override
    public String toString() {
        return name;
    }

    public void setCotwo(Object aValue) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setCategory(Object aValue) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
