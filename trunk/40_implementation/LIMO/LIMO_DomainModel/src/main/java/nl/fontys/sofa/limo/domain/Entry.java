package nl.fontys.sofa.limo.domain;

import nl.fontys.sofa.limo.domain.value.Value;

/**
 *
 * @author Dominik Kaisers <d.kaisers@student.fontys.nl>
 */
public class Entry {
    
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
    
}
