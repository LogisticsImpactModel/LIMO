package nl.fontys.sofa.limo.view.action;

import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import nl.fontys.limo.simulation.Simulator;
import nl.fontys.sofa.limo.domain.component.SupplyChain;
import nl.fontys.sofa.limo.view.chain.ChainBuilder;
import nl.fontys.sofa.limo.view.chain.ChainGraphScene;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.Lookup;
import org.openide.util.NbBundle.Messages;
import org.openide.util.Utilities;
import org.openide.util.actions.Presenter;
import org.openide.windows.TopComponent;

@ActionID(
        category = "Window",
        id = "nl.fontys.sofa.limo.view.action.SimulateAction"
)
@ActionRegistration(
        lazy = false,
        //        iconBase = "icons/gui/simulate.png",
        displayName = "#CTL_SimulateAction"
)
@ActionReferences({
    @ActionReference(path = "Menu/File", position = 20),
//    @ActionReference(path = "Toolbars/File", position = 25),
    @ActionReference(path = "Shortcuts", name = "D-R")
})

@Messages("CTL_SimulateAction=Simulate")

public final class SimulateAction extends AbstractAction implements Presenter.Toolbar {

    private Image image;
    private SupplyChain supplyChain;

    @Override
    public void actionPerformed(ActionEvent e) {
        Lookup global = Utilities.actionsGlobalContext();
        ChainGraphScene scene = global.lookup(ChainGraphScene.class);

//        ChainBuilder chainBuilder = global.lookup(ChainBuilder.class);
        if (scene != null) {
            TopComponent tc = scene.getParent();
            ChainBuilder chainBuilder = scene.getChainBuilder();
            if (chainBuilder != null && chainBuilder.validate()) {
                supplyChain = chainBuilder.getSupplyChain();
                tc.makeBusy(true);
                scene.setEnabled(false);
                scene.setBackground(Color.LIGHT_GRAY);
                Simulator.simulate(256, supplyChain);
            } else {
                DialogDisplayer.getDefault().notify(
                        new NotifyDescriptor.Message(
                                "Validation of chain failed.\n\nPlease make sure you"
                                + " have built your chain correctly.",
                                NotifyDescriptor.WARNING_MESSAGE));
            }
        }
    }

    public void setSupplyChain(SupplyChain supplyChain) {
        this.supplyChain = supplyChain;
    }

    @Override
    public Component getToolbarPresenter() {
        JButton button = new JButton(this);
        button.setIcon(new ImageIcon(getClass().getClassLoader().getResource("icons/gui/simulate.png")));
        button.setText("Simulate");
        button.setOpaque(false);
        button.setBorder(null);
        return button;
    }
}
