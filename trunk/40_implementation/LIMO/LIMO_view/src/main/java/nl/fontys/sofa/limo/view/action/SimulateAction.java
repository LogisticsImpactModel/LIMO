package nl.fontys.sofa.limo.view.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import nl.fontys.limo.simulation.task.Simulation;
import nl.fontys.sofa.limo.domain.component.SupplyChain;
import nl.fontys.sofa.limo.view.chain.ChainBuilder;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.Lookup;
import org.openide.util.NbBundle.Messages;
import org.openide.util.Utilities;

@ActionID(
        category = "Edit",
        id = "nl.fontys.sofa.limo.view.action.SimulateAction"
)
@ActionRegistration(
        iconBase = "icons/gui/simulate.png",
        displayName = "#CTL_SimulateAction"
)
@ActionReferences({
    @ActionReference(path = "Menu/File", position = 20),
    @ActionReference(path = "Shortcuts", name = "D-R")
})
@Messages("CTL_SimulateAction=Simulate")
public final class SimulateAction implements ActionListener {

    private SupplyChain supplyChain;

    @Override
    public void actionPerformed(ActionEvent e) {
        Lookup global = Utilities.actionsGlobalContext();
        ChainBuilder chainBuilder = global.lookup(ChainBuilder.class);

        if (chainBuilder != null && chainBuilder.validate()) {
            supplyChain = chainBuilder.getSupplyChain();
            Simulation simulation = new Simulation(supplyChain, 100);
            simulation.run();
        } else {
//            DialogDisplayer.getDefault().notify(new NotifyDescriptor)
        }
    }

    public void setSupplyChain(SupplyChain supplyChain) {
        this.supplyChain = supplyChain;
    }
}
