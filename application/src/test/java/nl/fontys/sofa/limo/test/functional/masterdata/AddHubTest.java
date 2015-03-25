///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package nl.fontys.sofa.limo.test.functional.masterdata;
//
//import java.lang.reflect.Field;
//import java.util.List;
//import static junit.framework.Assert.assertEquals;
//import junit.framework.Test;
//import nl.fontys.sofa.limo.api.dao.HubDAO;
//import nl.fontys.sofa.limo.api.service.provider.HubService;
//import nl.fontys.sofa.limo.domain.component.hub.Hub;
//import nl.fontys.sofa.limo.orientdb.OrientDBConnector;
//import nl.fontys.sofa.limo.test.mock.service.EventMockService;
//import org.junit.BeforeClass;
//import org.netbeans.jellytools.JellyTestCase;
//import org.netbeans.jellytools.WizardOperator;
//import org.netbeans.jellytools.actions.ActionNoBlock;
//import org.netbeans.jemmy.operators.JComboBoxOperator;
//import org.netbeans.jemmy.operators.JRadioButtonOperator;
//import org.netbeans.jemmy.operators.JTextFieldOperator;
//import org.netbeans.junit.MockServices;
//import org.netbeans.junit.NbModuleSuite;
//import org.openide.util.Lookup;
//
///**
// *
// * @author lnx
// */
//public class AddHubTest extends JellyTestCase {
//
//    private HubService hubService;
//    private WizardOperator wo;
//
//    public AddHubTest(String testName) {
//        super(testName);
//    }
//
//    @BeforeClass
//    public static void setUpClass() throws Exception {
//        //MockServices.setServices(HubMockService.class);
//    }
//
//    public static Test suite() {
//        NbModuleSuite.Configuration testConfig = NbModuleSuite.createConfiguration(AddHubTest.class);
//        testConfig = testConfig.addTest("addHubFromScratch_success", "addHubFromCopy_success");
//        testConfig = testConfig.clusters(".*").enableModules(".*");
//        return testConfig.suite();
//    }
//
//    @Override
//    public void setUp() throws Exception {
//        super.setUp();
//        new ActionNoBlock("Data|Hub|Add", null).perform();
//        hubService = Lookup.getDefault().lookup(HubService.class);
//    }
//
//    public void addHubFromScratch_success() throws InterruptedException {
//        int size = hubService.findAll().size();
//        // Create wizard
//        wo = new WizardOperator("Add Hub");
//        assertEquals("Title should be Add Hub", "Add Hub", wo.getTitle());
//        wo.btNext().push();
//        firstPanel(wo);
//        secoundPanel(wo);
//
//        wo.btNext().push();
//        // Procedures
//        wo.btFinish().push();
//        // Is stored?
//        List<Hub> findAll = hubService.findAll();
//
//        assertEquals(size + 1, findAll.size());
//    }
//
//    /**
//     * Name, Description, Property
//     *
//     * @param wo is the WizardOperator
//     */
//    private void firstPanel(WizardOperator wo) {
//        new JTextFieldOperator(wo, 0).setText("TestHub");
//        wo.btNext().push();
//    }
//
//    private void secoundPanel(WizardOperator wo) {
//        new JTextFieldOperator(wo, 0).setText("TestStraße");
//        new JTextFieldOperator(wo, 1).setText("123");
//        new JTextFieldOperator(wo, 2).setText("TestCity");
//        new JTextFieldOperator(wo, 3).setText("54321");
//        new JTextFieldOperator(wo, 4).setText("TestState");
//        new JComboBoxOperator(wo, 0).selectItem(4);
//        new JComboBoxOperator(wo, 1).selectItem(3);
//        wo.btNext().push();
//    }
//
//    public void addHubFromCopy_success() throws InterruptedException {
//
//        int size = hubService.findAll().size();
//        wo = new WizardOperator("Add Hub");
//        new JRadioButtonOperator(wo, 1).setSelected(true);
//        new JComboBoxOperator(wo, 0).setEnabled(true);
//        new JComboBoxOperator(wo, 0).selectItem(0);
//        wo.btNext().push();
//        new JTextFieldOperator(wo, 0).setText("TestHubCopy");
//        wo.btNext().push();
//        new JTextFieldOperator(wo, 0).setText("TestStraße2");
//        wo.btNext().push();
//
//        wo.btNext().push();
//        // Procedures
//        wo.btFinish().push();
//        // Is stored?
//        List<Hub> findAll = hubService.findAll();
//
//        assertEquals(size + 1, findAll.size());
//
//        assertFalse(findAll.get(findAll.size() - 1).getName().equals(findAll.get(findAll.size() - 2).getName()));
//        assertFalse(findAll.get(findAll.size() - 1).getLocation().getStreet().equals(findAll.get(findAll.size() - 2).getLocation().getStreet()));
//        assertTrue(findAll.get(findAll.size() - 1).getLocation().getHousenumber().equals(findAll.get(findAll.size() - 2).getLocation().getHousenumber()));
//        for (Hub e : hubService.findAll()) {
//            if (e.getName().equals("TestHub") || e.getName().equals("TestHubCopy")) {
//                hubService.delete(e);
//            }
//        }
//    }
//
//}
