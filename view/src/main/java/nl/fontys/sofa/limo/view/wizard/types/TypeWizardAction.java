package nl.fontys.sofa.limo.view.wizard.types;

import java.awt.event.ActionListener;

/**
 * Abstract class for the hub and leg type wizards which all have a name,
 * description, icon, procedures and events.
 *
 * @author Sven Mäurer
 */
public abstract class TypeWizardAction implements ActionListener {

    public static final String TYPE_NAME = "typeName";
    public static final String TYPE_DESCRIPTION = "typeDescription";
    public static final String TYPE_ICON = "typeIcon";
    public static final String TYPE_PROCEDURES = "typeProcedures";
    public static final String TYPE_EVENT = "typEvent";
    public static final String TYPE_OLDTYPE = "oldEvent";

    protected boolean isUpdate = false;

}
