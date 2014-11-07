package nl.fontys.sofa.limo.test.functional.masterdata;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import junit.framework.Test;
import nl.fontys.sofa.limo.api.service.distribution.DistributionFactory;
import nl.fontys.sofa.limo.api.service.provider.EventService;
import nl.fontys.sofa.limo.domain.component.event.Event;
import nl.fontys.sofa.limo.domain.component.event.distribution.Distribution;
import nl.fontys.sofa.limo.domain.component.event.distribution.input.InputValue;
import org.netbeans.jellytools.JellyTestCase;
import org.netbeans.jellytools.WizardOperator;
import org.netbeans.jellytools.actions.ActionNoBlock;
import org.netbeans.jemmy.operators.JButtonOperator;
import org.netbeans.jemmy.operators.JComboBoxOperator;
import org.netbeans.jemmy.operators.JRadioButtonOperator;
import org.netbeans.jemmy.operators.JTableOperator;
import org.netbeans.jemmy.operators.JTextAreaOperator;
import org.netbeans.jemmy.operators.JTextFieldOperator;
import org.netbeans.junit.NbModuleSuite;
import org.netbeans.junit.NbModuleSuite.Configuration;
import org.openide.util.Lookup;

public class AddNewEventTest extends JellyTestCase {

    private static final String NEW_EVENT_NAME = "Pirates";

    private static EventService eventService;
    private WizardOperator wo;
    private static Event event;

    public AddNewEventTest(String name) {
        super(name);
    }

    public static Test suite() {
        Configuration testConfig = NbModuleSuite.createConfiguration(AddNewEventTest.class);
        testConfig = testConfig.addTest("addEventFromScratch_success", "addEventFromScratch_fail");
        testConfig = testConfig.clusters(".*").enableModules(".*");
        return testConfig.suite();
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        event = new Event();
        event.setName("Sub Events");
        eventService = Lookup.getDefault().lookup(EventService.class);
        event = eventService.insert(event);
        new ActionNoBlock("Data|Event|Add", null).perform();
        wo = new WizardOperator("Add Event");
        new JRadioButtonOperator(wo, 0).setSelected(true);
        wo.btNext().push();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        eventService.delete(event);
    }

    public void addEventFromScratch_success() throws InterruptedException {
        int size = eventService.findAll().size();
        nameDescritpionPropertyPanel();
        subEventsPanel();
        // Procedures
        wo.btFinish().push();
        // Is stored?
        List<Event> findAll = eventService.findAll();

        assertEquals(size + 1, findAll.size());
        for (Event e : findAll) {
            if (e.getName().equals(NEW_EVENT_NAME)) {
                eventService.delete(e);
            }
        }
    }

    /**
     * Name, Description, Property
     *
     * @param wo is the WizardOperator
     */
    private void nameDescritpionPropertyPanel() {
        new JTextFieldOperator(wo, 0).setText(NEW_EVENT_NAME);
        new JTextAreaOperator(wo, 0).setText("Robbery by pirates");
        DistributionFactory distributionFactory = Lookup.getDefault().lookup(DistributionFactory.class);
        List<String> distTypes = Arrays.asList(distributionFactory.getDistributionTypes());
        String distributionTypeName = distTypes.get(new Random().nextInt(distTypes.size()));
        new JComboBoxOperator(wo, 0).selectItem(distributionTypeName);
        Distribution distribution = distributionFactory.getDistributionTypeByName(distributionTypeName);
        Map<String, InputValue> inputValues = distribution.getInputValues();
        JTableOperator table = new JTableOperator(wo, 0);
        for (int i = 0; i < inputValues.size(); i++) {
            table.setValueAt("0.5", i, 1);
        }
        wo.btNext().push();
    }

    public void addEventFromScratch_fail() throws InterruptedException {
        int size = eventService.findAll().size();
        wo.btNext().push();
        // Name, Description, Property
        wo.btNext().push();
        assertFalse(wo.isValid());
        wo.btCancel().push();
        assertEquals(size, eventService.findAll().size());
    }

    private void subEventsPanel() {
        JComboBoxOperator jComboBoxOperator = new JComboBoxOperator(wo, 0);
        jComboBoxOperator.selectItem(event.getName());
        new JButtonOperator(wo, 0).push();
        Object valueAt = new JTableOperator(wo, 0).getValueAt(0, 0);
        assertEquals(event.getName(), (String) valueAt);
        wo.btNext().push();
    }

}
