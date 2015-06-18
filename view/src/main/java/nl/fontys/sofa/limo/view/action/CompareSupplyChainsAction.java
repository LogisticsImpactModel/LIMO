/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.view.action;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import nl.fontys.sofa.limo.view.chain.ChainGraphScene;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.TopComponent;

/**
 *
 * @author Christina Zenzes
 */
@ActionID(
        category = "Window",
        id = "nl.fontys.sofa.limo.view.action.CompareSupplyChainsAction"
)
@ActionRegistration(
        iconBase = "icons/gui/Compare_24x24.png",
        displayName = "#CTL_CompareSupplyChainsAction"
)
@ActionReferences({
    @ActionReference(path = "Toolbars/Run", position = 40),
    @ActionReference(path = "Shortcuts", name = "D-B"),})

@Messages({
    "CTL_CompareSupplyChainsAction=Compare Supply Chains"
})

public class CompareSupplyChainsAction extends AbstractAction {
    
    private List<ChainGraphScene> scenes;

    
    @Override
    public void actionPerformed(ActionEvent e) {
         scenes = new ArrayList<>();
        for (TopComponent tc : TopComponent.getRegistry().getOpened()) {
            ChainGraphScene scene = tc.getLookup().lookup(ChainGraphScene.class);
            if (scene != null) {
                scenes.add(scene);
            }
        }
        
        if (scenes.size() < 2) {
            DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message(LIMOResourceBundle.getString("NOT_ENOUGH_SUPPLY_CHAIN_OPEN"), 2));
            return;
        }
        
        CompareSupplyChainPanel panel = new CompareSupplyChainPanel(scenes);
        DialogDescriptor dd = new DialogDescriptor(panel, LIMOResourceBundle.getString("COMPARISON_SELECTION_MESSAGE"));
        panel.setDialogDesriptor(dd);
        dd.setValid(false);
        
        if (DialogDisplayer.getDefault().notify(dd) == NotifyDescriptor.OK_OPTION) {
            SimulateAction action = new SimulateAction(panel.getSelectedScenes());
            action.actionPerformed(e);
        }
        
    }
    
    private class CompareSupplyChainPanel extends JPanel implements ItemListener {
        
        private Map<JCheckBox, ChainGraphScene> scenes;
        private List<ChainGraphScene> selectedScenes;
        private DialogDescriptor descriptor;
        
        public CompareSupplyChainPanel(List<ChainGraphScene> scenes) {
            super();
            this.scenes = new HashMap<>();
            this.selectedScenes = new ArrayList<>();
            this.setLayout(new BorderLayout()); 
            
            JLabel explenationText = new JLabel(LIMOResourceBundle.getString("EXPLENATION_TEXT_COMPARISON"));            
            explenationText.setHorizontalAlignment(JLabel.CENTER);    
            explenationText.setBorder( BorderFactory.createEmptyBorder(5, 5, 10, 5));
            this.add(explenationText, BorderLayout.NORTH);
            JPanel checkboxesPanel= new JPanel();
            
            checkboxesPanel.setLayout(new GridLayout(0, 3));
            
            for (ChainGraphScene scene : scenes) {
                String name = scene.getSupplyChain().getName().replace(".lsc", "");
                JCheckBox box = new JCheckBox(name);
                box.addItemListener(this);
                checkboxesPanel.add(box);

                this.scenes.put(box, scene);
            }
           JScrollPane scrollPanel = new JScrollPane(checkboxesPanel);
           this.add(scrollPanel, BorderLayout.CENTER);
        }
        
        public void setDialogDesriptor(DialogDescriptor descriptor) {
            this.descriptor = descriptor;
        }
        
        @Override
        public void itemStateChanged(ItemEvent e) {
            ChainGraphScene scene = scenes.get(e.getItem());
            if (e.getStateChange() == ItemEvent.DESELECTED) {
                selectedScenes.remove(scene);
                if (selectedScenes.size() == 1) {
                    setEnabledForAllNotSelectedCheckBoxes(true);
                    descriptor.setValid(false);
                }
            } else {
                selectedScenes.add(scene);
                if (selectedScenes.size() == 2) {
                    setEnabledForAllNotSelectedCheckBoxes(false);
                    descriptor.setValid(true);
                }
            }
            
        }
        
        private void setEnabledForAllNotSelectedCheckBoxes(boolean enable) {
            for (JCheckBox box : scenes.keySet()) {
                if (!box.isSelected()) {
                    box.setEnabled(enable);
                }
            }
        }
        
        public List<ChainGraphScene> getSelectedScenes() {
            return selectedScenes;
        }
        
    }
    
}
