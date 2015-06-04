/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IT.nl.fontys.sofa.limo;

import junit.framework.Test;
import org.netbeans.jellytools.JellyTestCase;
import org.netbeans.jellytools.TopComponentOperator;
import org.netbeans.jellytools.actions.Action;
import org.netbeans.junit.NbModuleSuite;
import org.netbeans.junit.NbModuleSuite.Configuration;

/**
 *
 * @author Christina Zenzes
 */
public class FirstIT extends JellyTestCase {

    public FirstIT(String testName) {
        super(testName);
    }

    public static Test suite() {
        Configuration testConfig = NbModuleSuite.createConfiguration(FirstIT.class);
        testConfig = testConfig.addTest("testCreateNewSupplyChained");
        testConfig = testConfig.clusters(".*").enableModules(".*");
        return NbModuleSuite.create(testConfig);
    }

    public void setUp(){
        System.out.println("#########"+getName()+"########################");
    }
    
    public void testCreateNewSupplyChained() {
      
        
       //NbDialogOperator dialog = new NbDialogOperator("Set Supply Chain name");
       // JemmyProperties p =  JemmyProperties.getProperties();
       
        
         Action a = new Action("File|New Supply Chain",null);
         a.performMenu();
        String nkjsdjka ="";
      //  a.perform(dialog);
        //p.setProperty("name", "testSup");
        //dialog.setProperties(p);
        //dialog.ok();
        
        TopComponentOperator top = new TopComponentOperator("testSup");
        
        
    }

}
