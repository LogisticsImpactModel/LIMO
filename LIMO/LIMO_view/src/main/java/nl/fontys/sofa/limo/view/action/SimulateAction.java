package nl.fontys.sofa.limo.view.action;

import java.awt.Component;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import nl.fontys.limo.simulation.task.Simulation;
import nl.fontys.sofa.limo.domain.component.SupplyChain;
import nl.fontys.sofa.limo.view.chain.ChainBuilder;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.awt.ActionID;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.NbBundle.Messages;
import org.openide.util.Utilities;
import org.openide.util.actions.Presenter;

@ActionID(
        category = "File",
        id = "nl.fontys.sofa.limo.view.action.SimulateAction"
)
//@ActionRegistration(
//        iconBase = "icons/gui/simulate.png",
//        displayName = "#CTL_SimulateAction"
//)
//@ActionReferences({
//    @ActionReference(path = "Menu/File", position = 20),
//    @ActionReference(path = "Shortcuts", name = "D-R")
//})

@Messages("CTL_SimulateAction=Simulate")

public final class SimulateAction extends AbstractAction
        implements Presenter.Toolbar, Presenter.Menu {

    private Image image;
    private SupplyChain supplyChain;

    public SimulateAction() {
        try {
            this.image = ImageIO.read(getClass().getResource("icons/gui/simulate.png"));
            putValue(Action.SMALL_ICON, image);
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

    @Override
    public Component getToolbarPresenter() {
        return new SimulateButton();
    }

    @Override
    public JMenuItem getMenuPresenter() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private class SimulateButton extends JButton {

        SimulateButton() {
            this.setText("Simulate");
            this.addActionListener(SimulateAction.this);
        }
    }

}
