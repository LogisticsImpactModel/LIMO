package nl.fontys.sofa.limo.view.wizard.importer;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;
import nl.fontys.sofa.limo.api.dao.DAO;
import nl.fontys.sofa.limo.api.dao.EventDAO;
import nl.fontys.sofa.limo.api.dao.HubDAO;
import nl.fontys.sofa.limo.api.dao.HubTypeDAO;
import nl.fontys.sofa.limo.api.dao.LegTypeDAO;
import nl.fontys.sofa.limo.api.dao.ProcedureCategoryDAO;
import nl.fontys.sofa.limo.domain.BaseEntity;
import nl.fontys.sofa.limo.domain.component.event.Event;
import nl.fontys.sofa.limo.domain.component.hub.Hub;
import nl.fontys.sofa.limo.domain.component.procedure.ProcedureCategory;
import nl.fontys.sofa.limo.domain.component.type.HubType;
import nl.fontys.sofa.limo.domain.component.type.LegType;
import org.openide.DialogDisplayer;
import org.openide.WizardDescriptor;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.Lookup;

/**
 * @author Matthias Brück
 */
@ActionID(category = "Master Data", id = "nl.fontys.sofa.limo.view.wizard.importer.ImportWizardAction")
@ActionRegistration(displayName = "Import Master Data...", iconBase = "icons/gui/database.gif")
@ActionReferences({
    @ActionReference(path = "Menu/Master Data", position = 70),
    @ActionReference(path = "Shortcuts", name = "DOS-I")
})
public final class ImportWizardAction implements ActionListener {

    private List<BaseEntity> objectsToOvewrite;
    public static final String LIST = "list";
    public static final String PATH = "path";

    @Override
    public void actionPerformed(ActionEvent e) {
        List<WizardDescriptor.Panel<WizardDescriptor>> wizardDescritorPanels = new ArrayList<>();

        wizardDescritorPanels.add(new ImportFileChooser());
        wizardDescritorPanels.add(new ImportPanel());

        String[] steps = new String[wizardDescritorPanels.size()];
        for (int i = 0; i < wizardDescritorPanels.size(); i++) {
            Component c = wizardDescritorPanels.get(i).getComponent();
            // Default step name to component name of panel.
            steps[i] = c.getName();
            if (c instanceof JComponent) { // assume Swing components
                JComponent component = (JComponent) c;
                component.putClientProperty(WizardDescriptor.PROP_CONTENT_SELECTED_INDEX, i);
                component.putClientProperty(WizardDescriptor.PROP_CONTENT_DATA, steps);
                component.putClientProperty(WizardDescriptor.PROP_AUTO_WIZARD_STYLE, true);
                component.putClientProperty(WizardDescriptor.PROP_CONTENT_DISPLAYED, true);
                component.putClientProperty(WizardDescriptor.PROP_CONTENT_NUMBERED, true);
            }
        }
        WizardDescriptor wizardDescriptor = new WizardDescriptor(new WizardDescriptor.ArrayIterator<>(wizardDescritorPanels));
        wizardDescriptor.setTitleFormat(new MessageFormat("{0}"));
        wizardDescriptor.setTitle("Import Masterdata");
        if (DialogDisplayer.getDefault().notify(wizardDescriptor) == WizardDescriptor.FINISH_OPTION) {
            handleWizardFinishClick(wizardDescriptor);
        }
    }

    /**
     * Save or update the event based on the inputs.
     *
     * @param wiz - the WizardDescriptor which contains the inputs.
     */
    private void handleWizardFinishClick(final WizardDescriptor wizardDescriptor) {
        objectsToOvewrite = (List<BaseEntity>) wizardDescriptor.getProperty(LIST);
        for (BaseEntity entity : objectsToOvewrite) {
            if (entity instanceof ProcedureCategory) {
                ImportWizardAction.<ProcedureCategory>updateItem((ProcedureCategory) entity, ProcedureCategoryDAO.class);
            }
            if (entity instanceof LegType) {
                ImportWizardAction.<LegType>updateItem((LegType) entity, LegTypeDAO.class);
            }
            if (entity instanceof HubType) {
                ImportWizardAction.<HubType>updateItem((HubType) entity, HubTypeDAO.class);
            }
            if (entity instanceof Hub) {
                ImportWizardAction.<Hub>updateItem((Hub) entity, HubDAO.class);
            }
            if (entity instanceof Event) {
                ImportWizardAction.<Event>updateItem((Event) entity, EventDAO.class);
            }
        }
    }

    private static <T extends BaseEntity> void updateItem(T item, Class daoclass) {
        DAO pcDAO = (DAO) Lookup.getDefault().lookup(daoclass);
        T old = (T) pcDAO.findByUniqueIdentifier(item.getUniqueIdentifier());
        System.out.println(item);
        pcDAO.delete(old);
        pcDAO.insert(item, false);
    }
}
