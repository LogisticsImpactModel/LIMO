package nl.fontys.sofa.limo.test.functional.masterdata;

import java.util.List;
import javax.swing.JTextField;
import junit.framework.Test;
import nl.fontys.sofa.limo.api.dao.EventDAO;
import nl.fontys.sofa.limo.domain.component.event.Event;
import nl.fontys.sofa.limo.view.wizard.event.EventWizardAction;
import org.netbeans.jellytools.JellyTestCase;
import org.netbeans.jellytools.WizardOperator;
import org.netbeans.jellytools.actions.ActionNoBlock;
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
        testConfig = testConfig.addTest("success", "fails");
        testConfig = testConfig.clusters(".*").enableModules(".*");
        return testConfig.suite();
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        new ActionNoBlock("Data|Event|Add", null).perform();
        eventDAO = Lookup.getDefault().lookup(EventDAO.class);
    }

    public void success() throws InterruptedException {
        int size = eventDAO.findAll().size();
        // Create wizard
        WizardOperator wo = new WizardOperator("Add Event");
        assertEquals("Title should be Add Event", "Add Event", wo.getTitle());
        wo.btNext().push();
        // Name, Description, Property
        new JTextFieldOperator(wo, 0).setText("Pirates");
        new JTextAreaOperator(wo, 0).setText("Robbery by pirates");
        wo.btNext().push();
        // Procedures
        wo.btNext().push();
        // Subevents
        wo.btFinish().push();
        // Is stored?
        List<Event> findAll = eventDAO.findAll();
        assertEquals(size, findAll.size());
        for (Event e : findAll) {
            if (e.getName().equals("Pirates")) {
                eventDAO.delete(e);
            }
        }
    }

    public void fails() throws InterruptedException {
        int size = eventDAO.findAll().size();
        // Create wizard
        WizardOperator wo = new WizardOperator("Add Event");
        assertEquals("Title should be Add Event", "Add Event", wo.getTitle());
        wo.btNext().push();
        // Name, Description, Property
        wo.btNext().push();
        assertFalse(wo.isValid());
        new JTextFieldOperator(wo, 0).setText("Pirates");
        /*wo.btNext().push();
         assertTrue(wo.isValid());*/
        wo.btCancel().push();
        assertEquals(size, eventDAO.findAll().size());
    }

}
