/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.view;

import java.util.logging.Logger;
import nl.fontys.sofa.limo.view.status.NewFunctionExceptionHandler;
import org.openide.modules.ModuleInstall;

public class Installer extends ModuleInstall {

    @Override
    public void restored() {
        System.setProperty("netbeans.exception.report.min.level", "999999");
        System.setProperty("netbeans.exception.alert.min.level", "999999");
        Logger.getLogger("").addHandler(new NewFunctionExceptionHandler());
        super.restored();
    }

}
