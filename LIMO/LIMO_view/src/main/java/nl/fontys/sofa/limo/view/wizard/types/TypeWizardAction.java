package nl.fontys.sofa.limo.view.wizard.types;

import java.awt.event.ActionListener;

public abstract class TypeWizardAction implements ActionListener {

    public static final String TYPE_NAME = "hubTypeName";
    public static final String TYPE_DESCRIPTION = "hubTypeDescription";
    public static final String TYPE_ICON = "hubTypeIcon";
    public static final String TYPE_PROCEDURES = "hubTypeProcedures";

    protected boolean isUpdate = false;
    protected boolean saveToDatabase = true;

    public void setSaveToDatabase(boolean saveToDatabase) {
        this.saveToDatabase = saveToDatabase;
    }

}
