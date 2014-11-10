//package nl.fontys.sofa.limo.test.functional.startpage;
//
///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//
//import junit.framework.Test;
//import org.netbeans.jellytools.EditorOperator;
//import org.netbeans.jellytools.EditorWindowOperator;
//import org.netbeans.jellytools.JellyTestCase;
//import org.netbeans.jellytools.TopComponentOperator;
//import org.netbeans.jemmy.operators.ComponentOperator;
//import org.netbeans.junit.NbModuleSuite;
//import org.netbeans.junit.NbModuleSuite.Configuration;
//
///**
// * Functional Test for displaying the startpage of the application.
// * User scenarios can be found in the DisplayStartpageTestScenarios file.
// * 
// * @author Sebastiaan Heijmann
// */
//
//public class DisplayStartpageTest extends JellyTestCase {
//	
//    /** Constructor required by JUnit */
//    public DisplayStartpageTest(String name) {
//        super(name);
//    }
//
//    /** Creates suite from particular test cases. You can define order of testcases here. */
//    public static Test suite() {
//        Configuration testConfig = NbModuleSuite.createConfiguration(DisplayStartpageTest.class);
//        testConfig = testConfig.addTest("displayStartpage");
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
//    /** 
//     * Test display startpage
//     */
//    public void displayStartpage() {
//			TopComponentOperator tco = new TopComponentOperator("Startpage");
//			assertEquals("Title Startpage should be StartPage Window", "Startpage Window",tco.getName());
//                        
//    }
//
//}
