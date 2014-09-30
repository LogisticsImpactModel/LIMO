package nl.fontys.sofa.limo.domain.category;

import nl.fontys.sofa.limo.domain.BaseEntity;

public abstract class Category extends BaseEntity {

    protected String identifier;

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

}
