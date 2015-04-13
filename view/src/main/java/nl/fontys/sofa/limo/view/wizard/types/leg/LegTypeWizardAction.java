package nl.fontys.sofa.limo.view.wizard.types.leg;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;
import nl.fontys.sofa.limo.api.service.provider.EventService;
import nl.fontys.sofa.limo.api.service.provider.LegTypeService;
import nl.fontys.sofa.limo.domain.component.Icon;
import nl.fontys.sofa.limo.domain.component.event.Event;
import nl.fontys.sofa.limo.domain.component.procedure.Procedure;
import nl.fontys.sofa.limo.domain.component.type.LegType;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;
import nl.fontys.sofa.limo.view.wizard.types.TypeWizardAction;
import org.openide.DialogDisplayer;
import org.openide.WizardDescriptor;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.ImageUtilities;
import org.openide.util.Lookup;

/**
 * LegType Wizard Action.
 *
 * @author Pascal Lindner
 */
@ActionID(category = "LegType", id = "nl.fontys.sofa.limo.view.wizard.legtype.LegTypeWizardAction")
@ActionRegistration(displayName = "New Leg Type..", iconBase = "icons/gui/add.gif")
@ActionReferences({
    @ActionReference(path = "Menu/Master Data/Leg Type", position = 20),
    @ActionReference(path = "Shortcuts", name = "DOS-L")
})
public final class LegTypeWizardAction extends TypeWizardAction {

    private LegType legType;

    @Override
    public void actionPerformed(ActionEvent e) {
        List<WizardDescriptor.Panel<WizardDescriptor>> panels = new ArrayList<>();
        if (!isUpdate) {
            panels.add(new NewOrDuplicatedLegTypeWizard());
            legType = new LegType();
        }
        panels.add(new NameDescriptionIconLegTypeWizard());
        panels.add(new ProceduresLegTypeWizard());
        EventService eventService = Lookup.getDefault().lookup(EventService.class);
        if (!eventService.findAll().isEmpty()) {
            panels.add(new EventLegTypeWizard());
        }
        String[] steps = new String[panels.size()];
        for (int i = 0; i < panels.size(); i++) {
            Component c = panels.get(i).getComponent();
            steps[i] = c.getName();
            if (c instanceof JComponent) {
                JComponent jc = (JComponent) c;
                jc.putClientProperty(WizardDescriptor.PROP_CONTENT_SELECTED_INDEX, i);
                jc.putClientProperty(WizardDescriptor.PROP_CONTENT_DATA, steps);
                jc.putClientProperty(WizardDescriptor.PROP_AUTO_WIZARD_STYLE, true);
                jc.putClientProperty(WizardDescriptor.PROP_CONTENT_DISPLAYED, true);
                jc.putClientProperty(WizardDescriptor.PROP_CONTENT_NUMBERED, true);
            }
        }
        WizardDescriptor wiz = new WizardDescriptor(new WizardDescriptor.ArrayIterator<>(panels));
        wiz.setTitleFormat(new MessageFormat("{0}"));
        wiz.putProperty(WizardDescriptor.PROP_IMAGE, ImageUtilities.loadImage("icons/limo_wizard.png", true));
        wiz.setTitle(LIMOResourceBundle.getString("ADD_LEG_TYPE"));
        if (isUpdate) {
//            wiz.putProperty(TYPE_NAME, legType.getName());
//            wiz.putProperty(TYPE_DESCRIPTION, legType.getDescription());
//            wiz.putProperty(TYPE_ICON, legType.getIcon());
//            wiz.putProperty(TYPE_EVENT, legType.getEvents());
//            wiz.putProperty(TYPE_PROCEDURES, legType.getProcedures());
            wiz.putProperty(TYPE_OLDTYPE, legType);
        }
        if (DialogDisplayer.getDefault().notify(wiz) == WizardDescriptor.FINISH_OPTION) {
            finishWizard(wiz);
        }
    }

    //For Update LegType
    public void isUpdate(LegType legType) {
        this.legType = legType;
        this.isUpdate = true;
    }

    private void finishWizard(WizardDescriptor wiz) {
        LegTypeService service = Lookup.getDefault().lookup(LegTypeService.class);
//        legType.setDescription((String) wiz.getProperty(TYPE_DESCRIPTION));
//        legType.setIcon((Icon) wiz.getProperty(TYPE_ICON));
//        legType.setName((String) wiz.getProperty(TYPE_NAME));
//        legType.setEvents((List<Event>) wiz.getProperty(TYPE_EVENT));
//        legType.setProcedures((List<Procedure>) wiz.getProperty(TYPE_PROCEDURES));
        if (isUpdate) {
            service.update(legType);
        } else {
            legType.setId(null);
            legType = service.insert(legType);
        }
    }

}
