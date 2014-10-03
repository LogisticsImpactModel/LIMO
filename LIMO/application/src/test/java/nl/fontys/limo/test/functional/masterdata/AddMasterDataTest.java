//package nl.fontys.limo.test.functional.masterdata;
//
//import junit.framework.Test;
//import org.netbeans.jellytools.JellyTestCase;
//import org.netbeans.jellytools.WizardOperator;
//import org.netbeans.jellytools.actions.ActionNoBlock;
//import org.netbeans.junit.NbModuleSuite;
//import org.netbeans.junit.NbModuleSuite.Configuration;
//
///**
// * Functional Test for adding masterdata to the application.
// * User scenarios can be found in the AddMasterDataTestScenario file.
// * 
// * @author Sebastiaan Heijmann
// */
//
//public class AddMasterDataTest extends JellyTestCase {
//
//    /** Constructor required by JUnit */
//    public AddMasterDataTest(String name) {
//        super(name);
//    }
//
//    /** Creates suite from particular test cases. You can define order of testcases here. */
//    public static Test suite() {
//        Configuration testConfig = NbModuleSuite.createConfiguration(AddMasterDataTest.class);
//        testConfig = testConfig.addTest("openWizard");
//        testConfig = testConfig.clusters(".*").enableModules(".*");
//        return NbModuleSuite.create(testConfig);
//    }
//
//    /** Called before every test case. */
//    public void setUp() throws InterruptedException {
//        System.out.println("########  "+getName()+"  #######");
//    }
//
//    // Add test methods here, they have to start with 'test' name:
//
//    /** Test opening a wizard. */
//    public void openWizard() throws InterruptedException {
//			/*System.out.println("Opening a wizard");
//			new ActionNoBlock("Data|Add Master Data", null).perform();
//			WizardOperator wo = new WizardOperator("Add Master Data");
//			assertEquals("Title should be Add Hub", "Add Hub", wo.getTitle());*/
//    }
//
//}