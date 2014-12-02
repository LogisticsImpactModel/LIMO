package nl.fontys.sofa.limo.view.action;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.Action;
import nl.fontys.limo.simulation.task.Simulation;
import nl.fontys.sofa.limo.domain.component.SupplyChain;
import nl.fontys.sofa.limo.view.chain.ChainBuilder;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.NbBundle.Messages;
import org.openide.util.Utilities;

@ActionID(
        category = "File",
        id = "nl.fontys.sofa.limo.view.action.SimulateAction"
)
@ActionRegistration(
        iconBase = "icons/gui/simulate.png",
        displayName = "#CTL_SimulateAction"
)
@ActionReferences({
    @ActionReference(path = "Menu/File", position = 20),
    @ActionReference(path = "Toolbars/File", position = 25),
    @ActionReference(path = "Shortcuts", name = "D-R")
})

@Messages("CTL_SimulateAction=Simulate")

public final class SimulateAction implements ActionListener {

    private Image image;
    private SupplyChain supplyChain;

    public SimulateAction() {
        try {
            this.image = ImageIO.read(getClass().getResource("icons/gui/simulate.png"));
            //putValue(Action.SMALL_ICON, image);
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Lookup global = Utilities.actionsGlobalContext();
        ChainBuilder chainBuilder = global.lookup(ChainBuilder.class);

        if (chainBuilder != null && chainBuilder.validate()) {
            supplyChain = chainBuilder.getSupplyChain();
            Simulation simulation = new Simulation(supplyChain, 100);
            simulation.run();
        } else {
            DialogDisplayer.getDefault().notify(
                    new NotifyDescriptor.Message(
                            "Validation of chain failed.\n\nPlease make sure you"
                            + " have built your chain correctly.",
                            NotifyDescriptor.WARNING_MESSAGE));
        }
    }

    public void setSupplyChain(SupplyChain supplyChain) {
        this.supplyChain = supplyChain;
    }
}
