/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.view;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Action;
import nl.fontys.sofa.limo.view.wizard.importer.ImportWizardAction;
import org.netbeans.api.sendopts.CommandException;
import org.netbeans.spi.sendopts.Env;
import org.netbeans.spi.sendopts.Option;
import org.netbeans.spi.sendopts.OptionProcessor;
import org.openide.awt.Actions;
import org.openide.util.actions.SystemAction;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author nilsh
 */
@ServiceProvider(service = OptionProcessor.class)
public class StartupOptionProcessor extends OptionProcessor {

    private static final Logger logger = Logger.getLogger(StartupOptionProcessor.class.getName());
    private Option openOption = Option.defaultArguments();
    private Option openOption2 = Option.additionalArguments('o', "open");

    @Override
    protected Set<Option> getOptions() {
        HashSet set = new HashSet();
        set.add(openOption);
        set.add(openOption2);
        return set;
    }

    @Override
    protected void process(Env env, Map<Option, String[]> values) throws CommandException {

        List<String> filenameList = new ArrayList<>();
        Object obj = values.get(openOption);
        if (obj != null) {
            filenameList.addAll(Arrays.asList((String[]) obj));
        }
        obj = values.get(openOption2);
        if (obj != null) {
            filenameList.addAll(Arrays.asList((String[]) obj));
        }

        for (int i = 0; i < filenameList.size(); i++) {
            File file = new File(filenameList.get(i));
            if (!file.isAbsolute()) {
                file = new File(env.getCurrentDirectory(),
                        filenameList.get(i));
            }
            logger.log(Level.INFO, "Open file: {0}", file.getPath());

            String path = file.getAbsolutePath();

            if (path.endsWith(".lef")) {
                ActionEvent e = new ActionEvent(this, ActionEvent.ACTION_FIRST, path);
                Action action = Actions.forID("Master Data", "nl.fontys.sofa.limo.view.wizard.importer.ImportWizardAction");
                action.actionPerformed(e);
                continue;
            }
            
            if(path.endsWith(".lsc")){
                ActionEvent e = new ActionEvent(this, ActionEvent.ACTION_FIRST, path);
                Action action =  Actions.forID("Window", "nl.fontys.sofa.limo.view.action.OpenChainAction");
               
                action.actionPerformed(e);
                continue;
            }
            

        }
    }

}
