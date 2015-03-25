package nl.fontys.sofa.limo.view.wizard.export;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JComponent;
import nl.fontys.sofa.limo.domain.BaseEntity;
import nl.fontys.sofa.limo.externaltrader.JSONExporter;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;
import nl.fontys.sofa.limo.view.wizard.export.data.panel.EventSelectionPanel;
import nl.fontys.sofa.limo.view.wizard.export.data.panel.FileChooserPanel;
import nl.fontys.sofa.limo.view.wizard.export.data.panel.HubSelectionPanel;
import nl.fontys.sofa.limo.view.wizard.export.data.panel.HubTypeSelectionPanel;
import nl.fontys.sofa.limo.view.wizard.export.data.panel.LegTypeSelectionPanel;
import nl.fontys.sofa.limo.view.wizard.export.data.panel.ProcedureCategorySelectionPanel;
import org.openide.DialogDisplayer;
import org.openide.WizardDescriptor;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;

/**
 * This class handles the Action of the Export Wizard.
 *
 * @author Matthias Brück
 */
@ActionID(category
        = "Master Data",
        id = "nl.fontys.sofa.limo.view.wizard.export.ExportWizardAction"
)
@ActionRegistration(
        displayName = "Export Master Data...",
        iconBase = "icons/gui/database.gif"
)
@ActionReferences({
    @ActionReference(path = "Menu/Master Data", position = 60, separatorBefore = 55),
    @ActionReference(path = "Shortcuts", name = "DOS-E")
})
public final class ExportWizardAction implements ActionListener {

    public static final String CATEGORIES = "categories";
    public static final String LEG_TYPES = "legtypes";
    public static final String HUB_TYPES = "hubtypes";
    public static final String HUBS = "hubs";
    public static final String EVENTS = "events";
    public static final String PATH = "path";

    private Map<String, List<BaseEntity>> objectsToExport;

    @Override
    public void actionPerformed(ActionEvent e) {
        List<WizardDescriptor.Panel<WizardDescriptor>> panels = new ArrayList<>();
        panels.add(new ProcedureCategorySelectionPanel());
        panels.add(new LegTypeSelectionPanel());
        panels.add(new HubTypeSelectionPanel());
        panels.add(new HubSelectionPanel());
        panels.add(new EventSelectionPanel());
        panels.add(new FileChooserPanel());

        String[] steps = new String[panels.size()];
        for (int i = 0; i < panels.size(); i++) {
            Component c = panels.get(i).getComponent();
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
        WizardDescriptor wizardDescriptor = new WizardDescriptor(new WizardDescriptor.ArrayIterator<>(panels));
        wizardDescriptor.setTitleFormat(new MessageFormat("{0}"));
        wizardDescriptor.setTitle(LIMOResourceBundle.getString("EXPORT_DATA"));
        if (DialogDisplayer.getDefault().notify(wizardDescriptor) == WizardDescriptor.FINISH_OPTION) {
            handleWizardFinishClick(wizardDescriptor);
        }
    }

    /**
     * Save the selected data with the JSONExporter.
     *
     * @param wiz - the WizardDescriptor which contains the inputs.
     */
    private void handleWizardFinishClick(final WizardDescriptor wizardDescriptor) {
        objectsToExport = new HashMap<>();
        objectsToExport.put(CATEGORIES, (List<BaseEntity>) wizardDescriptor.getProperty(CATEGORIES));
        objectsToExport.put(LEG_TYPES, (List<BaseEntity>) wizardDescriptor.getProperty(LEG_TYPES));
        objectsToExport.put(HUB_TYPES, (List<BaseEntity>) wizardDescriptor.getProperty(HUB_TYPES));
        objectsToExport.put(HUBS, (List<BaseEntity>) wizardDescriptor.getProperty(HUBS));
        objectsToExport.put(EVENTS, (List<BaseEntity>) wizardDescriptor.getProperty(EVENTS));
        String filepath = (String) wizardDescriptor.getProperty(PATH);
        JSONExporter.export(objectsToExport, filepath);
    }
}
