package nl.fontys.sofa.limo.view.wizard.export;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;
import nl.fontys.sofa.limo.api.service.provider.EventService;
import nl.fontys.sofa.limo.domain.BaseEntity;
import nl.fontys.sofa.limo.view.wizard.export.data.panel.EventSelectionPanel;
import nl.fontys.sofa.limo.view.wizard.export.data.panel.HubSelectionPanel;
import nl.fontys.sofa.limo.view.wizard.export.data.panel.HubTypeSelectionPanel;
import nl.fontys.sofa.limo.view.wizard.export.data.panel.LegTypeSelectionPanel;
import nl.fontys.sofa.limo.view.wizard.export.data.panel.ProcedureCategorySelectionPanel;
import org.openide.DialogDisplayer;
import org.openide.WizardDescriptor;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.Lookup;

/**
 *
 * @author Matthias Brück
 */
@ActionID(category = "Export", id = "nl.fontys.limo.view.wizard.export.ExportWizardAction")
@ActionRegistration(displayName = "Export data")
@ActionReference(path = "Menu/Master Data/Export", position = 20)
public final class ExportWizardAction implements ActionListener {

    private List<List<BaseEntity>> objectsToExport;

    public static final String CATEGORIES = "categories";
    public static final String LEG_TYPES = "legtypes";
    public static final String HUB_TYPES = "hubtypes";
    public static final String HUBS = "hubs";
    public static final String EVENTS = "events";

    @Override
    public void actionPerformed(ActionEvent e) {
        List<WizardDescriptor.Panel<WizardDescriptor>> wizardDescritorPanels = new ArrayList<>();

        wizardDescritorPanels.add(new ProcedureCategorySelectionPanel());
        wizardDescritorPanels.add(new LegTypeSelectionPanel());
        wizardDescritorPanels.add(new HubTypeSelectionPanel());
        wizardDescritorPanels.add(new HubSelectionPanel());
        wizardDescritorPanels.add(new EventSelectionPanel());

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
        wizardDescriptor.setTitle("Export data");
        if (DialogDisplayer.getDefault().notify(wizardDescriptor) == WizardDescriptor.FINISH_OPTION) {
            objectsToExport = new ArrayList<>();
            //
            //JUST FOR FAST COPY PASTE LATER ON CREATED HERE
            //
            EventService eventService = Lookup.getDefault().lookup(EventService.class);
            //
            //EXAMPLE
            //
            /*
             leg.setName((String) wiz.getProperty("name"));
             leg.setDescription((String) wiz.getProperty("description"));
             leg.setIcon((Icon) wiz.getProperty("icon"));
             leg.setEvents((List<Event>) wiz.getProperty("events"));
             leg.setProcedures((List<Procedure>) wiz.getProperty("procedures"));
             */
            //
            //EXPORTER EXPORT THE LIST
            //
        }
    }
}
