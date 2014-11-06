package nl.fontys.sofa.limo.test.functional.masterdata;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import junit.framework.Test;
import nl.fontys.sofa.limo.api.dao.EventDAO;
import nl.fontys.sofa.limo.api.service.distribution.DistributionFactory;
import nl.fontys.sofa.limo.domain.component.event.Event;
import nl.fontys.sofa.limo.domain.component.event.distribution.Distribution;
import nl.fontys.sofa.limo.domain.component.event.distribution.input.InputValue;
import org.netbeans.jellytools.JellyTestCase;
import org.netbeans.jellytools.WizardOperator;
import org.netbeans.jellytools.actions.ActionNoBlock;
import org.netbeans.jemmy.Timeout;
import org.netbeans.jemmy.operators.JComboBoxOperator;
import org.netbeans.jemmy.operators.JTableOperator;
import org.netbeans.jemmy.operators.JTextAreaOperator;
import org.netbeans.jemmy.operators.JTextFieldOperator;
import org.netbeans.junit.NbModuleSuite;
import org.netbeans.junit.NbModuleSuite.Configuration;
import org.openide.util.Lookup;

public class AddEventTest extends JellyTestCase {

    private EventDAO eventDAO;

    public AddEventTest(String name) {
        super(name);
    }

    public static Test suite() {
        Configuration testConfig = NbModuleSuite.createConfiguration(AddEventTest.class);
        testConfig = testConfig.addTest("addEventFromScratch_success", "addEventFromScratch_fail");
        testConfig = testConfig.clusters(".*").enableModules(".*");
        return testConfig.suite();
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        new ActionNoBlock("Data|Event|Add", null).perform();
        eventDAO = Lookup.getDefault().lookup(EventDAO.class);
    }

    public void addEventFromScratch_success() throws InterruptedException {
        int size = eventDAO.findAll().size();
        // Create wizard
        WizardOperator wo = new WizardOperator("Add Event");
        assertEquals("Title should be Add Event", "Add Event", wo.getTitle());
        wo.btNext().push();
        firstPanel(wo);

        wo.btNext().push();
        // Procedures
        wo.btFinish().push();
        // Is stored?
        List<Event> findAll = eventDAO.findAll();

        assertEquals(size + 1, findAll.size());
        for (Event e : findAll) {
            if (e.getName().equals("Pirates")) {
                eventDAO.delete(e);
            }
        }
    }

    /**
     * Name, Description, Property
     *
     * @param wo is the WizardOperator
     */
    private void firstPanel(WizardOperator wo) {
        new JTextFieldOperator(wo, 0).setText("Pirates");
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
            new Timeout("pause", 5000).sleep();
        }
        wo.btNext().push();
    }

    public void addEventFromScratch_fail() throws InterruptedException {
        int size = eventDAO.findAll().size();
        // Create wizard
        WizardOperator wo = new WizardOperator("Add Event");
        assertEquals("Title should be Add Event", "Add Event", wo.getTitle());
        wo.btNext().push();
        // Name, Description, Property
        wo.btNext().push();
        assertFalse(wo.isValid());
        wo.btCancel().push();
        assertEquals(size, eventDAO.findAll().size());
    }

}
