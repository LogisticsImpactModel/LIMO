/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nl.fontys.limo.test.functional.masterdata;

import junit.framework.Test;
import org.netbeans.jellytools.JellyTestCase;
import org.netbeans.jellytools.MainWindowOperator;
import org.netbeans.jellytools.TopComponentOperator;
import org.netbeans.jellytools.actions.Action;
import org.netbeans.jemmy.operators.JButtonOperator;
import org.netbeans.jemmy.operators.JSliderOperator;
import org.netbeans.junit.NbModuleSuite;
import org.netbeans.junit.NbModuleSuite.Configuration;

/**
 *
 * @author Sebastiaan Heijmann
 */

public class AddMasterDataTest extends JellyTestCase {

    /** Constructor required by JUnit */
    public AddMasterDataTest(String name) {
        super(name);
    }

    /** Creates suite from particular test cases. You can define order of testcases here. */
    public static Test suite() {
        Configuration testConfig = NbModuleSuite.createConfiguration(AddMasterDataTest.class);
        testConfig = testConfig.addTest("openWizard");
        testConfig = testConfig.clusters(".*").enableModules(".*");
        return NbModuleSuite.create(testConfig);
    }

    /** Called before every test case. */
    public void setUp() throws InterruptedException {
        System.out.println("########  "+getName()+"  #######");
    }

    // Add test methods here, they have to start with 'test' name:

    /** Test opening a wizard. */
    public void openWizard() {
			System.out.println("Opening a wizard");
			new Action("Data|New", null).perform();
    }

}