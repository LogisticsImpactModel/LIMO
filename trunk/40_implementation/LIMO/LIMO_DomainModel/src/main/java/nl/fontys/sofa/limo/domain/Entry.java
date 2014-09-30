package nl.fontys.sofa.limo.domain;

import java.io.Serializable;
import nl.fontys.sofa.limo.domain.value.Value;

public class Entry implements Serializable {

    protected String name;
    protected String category;
    protected Value value;

    public Entry(String name, String category) {
        this(name, category, null);
    }

    public Entry(String name, String category, Value value) {
        this.name = name;
        this.category = category;
        this.value = value;
    }

    // <editor-fold defaultstate="collapsed" desc=" ${GETTERS AND SETTERS} ">
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

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }
    // </editor-fold>
}
