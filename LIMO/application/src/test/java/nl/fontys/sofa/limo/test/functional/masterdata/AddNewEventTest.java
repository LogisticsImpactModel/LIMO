package nl.fontys.sofa.limo.test.functional.masterdata;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import junit.framework.Test;
import nl.fontys.sofa.limo.api.dao.EventDAO;
import nl.fontys.sofa.limo.api.service.distribution.DistributionFactory;
import nl.fontys.sofa.limo.api.service.provider.EventService;
import nl.fontys.sofa.limo.domain.component.event.Event;
import nl.fontys.sofa.limo.domain.component.event.distribution.Distribution;
import nl.fontys.sofa.limo.domain.component.event.distribution.input.InputValue;
import nl.fontys.sofa.limo.orientdb.OrientDBConnector;
import nl.fontys.sofa.limo.orientdb.dao.OrientDBEventDAO;
import nl.fontys.sofa.limo.test.mock.service.EventMockService;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.netbeans.jellytools.JellyTestCase;
import org.netbeans.jellytools.WizardOperator;
import org.netbeans.jellytools.actions.ActionNoBlock;
import org.netbeans.jemmy.operators.JComboBoxOperator;
import org.netbeans.jemmy.operators.JRadioButtonOperator;
import org.netbeans.jemmy.operators.JTableOperator;
import org.netbeans.jemmy.operators.JTextAreaOperator;
import org.netbeans.jemmy.operators.JTextFieldOperator;
import org.netbeans.junit.MockServices;
import org.netbeans.junit.NbModuleSuite;
import org.netbeans.junit.NbModuleSuite.Configuration;
import org.openide.util.Lookup;

public class AddNewEventTest extends JellyTestCase {

    private static final String NEW_EVENT_NAME = "Pirates";

    private EventService eventService;
    private WizardOperator wo;
    private static Event event;

    public AddNewEventTest(String name) {
        super(name);
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        MockServices.setServices(EventMockService.class);
    }

    public static Test suite() {
        Configuration testConfig = NbModuleSuite.createConfiguration(AddNewEventTest.class);
        testConfig = testConfig.addTest("addEventFromScratch_success", "addEventFromScratch_fail");
        testConfig = testConfig.clusters(".*").enableModules(".*");
        return testConfig.suite();
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        eventService = Lookup.getDefault().lookup(EventService.class);
        event = new Event();
        event.setName("Sub Events");
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
        boolean wasAdded = false;
        nameDescritpionPropertyPanel();
        subEventsPanel();
        // Procedures
        wo.btFinish().push();
        // Is stored?
        for (Event e : eventService.findAll()) {
            if (e.getName().equals(NEW_EVENT_NAME)) {
                wasAdded = true;
                eventService.delete(e);
            }
        }
        assertTrue(wasAdded);
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
        int i = 0;
        for (Map.Entry<String, InputValue> entrySet : inputValues.entrySet()) {
            Class clazz = entrySet.getValue().getType();
            if (clazz.equals(Integer.class)) {
                table.setValueAt("1", i, 1);
            } else if (clazz.equals(Float.class) || clazz.equals(Double.class)) {
                table.setValueAt("0.5", i, 1);
            }
        }
        wo.btNext().push();
    }

    public void addEventFromScratch_fail() throws InterruptedException {
        int size = eventService.findAll().size();
        wo.btNext().pushNoBlock();
        // Name, Description, Property
        wo.btNext().pushNoBlock();
        assertFalse(wo.isValid());
        wo.btCancel().pushNoBlock();
        assertEquals(size, eventService.findAll().size());
    }

    private void subEventsPanel() {
        //Todo: Strange behavior that the item is not selected
        /*JComboBoxOperator cb = new JComboBoxOperator(wo, 0);
         String itemAt = (String) cb.getItemAt(0);
         cb.selectItem(itemAt);
         new JButtonOperator(wo, 0).push();
         new Timeout("wait for adding to table", 5000).sleep();
         JTableOperator table = new JTableOperator(wo, 0);
         assertEquals(event.getName(), (String) table.getValueAt(0, 0));*/
        wo.btNext().push();
    }

}
