package nl.fontys.sofa.limo.view.wizard.types;

import java.awt.event.ActionListener;

public abstract class TypeWizardAction implements ActionListener {

    public static final String TYPE_NAME = "typeName";
    public static final String TYPE_DESCRIPTION = "typeDescription";
    public static final String TYPE_ICON = "typeIcon";
    public static final String TYPE_PROCEDURES = "typeProcedures";
    public static final String TYPE_EVENT = "typEvent";
    public static final String TYPE_OLDTYPE = "oldEvent";

    protected boolean isUpdate = false;
    protected boolean saveToDatabase = true;

    public void setSaveToDatabase(boolean saveToDatabase) {
        this.saveToDatabase = saveToDatabase;
    }

}
