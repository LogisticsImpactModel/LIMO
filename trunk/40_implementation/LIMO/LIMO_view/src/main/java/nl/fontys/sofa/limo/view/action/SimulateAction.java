package nl.fontys.sofa.limo.view.action;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import nl.fontys.limo.simulation.Simulator;
import nl.fontys.sofa.limo.domain.component.SupplyChain;
import nl.fontys.sofa.limo.view.chain.ChainBuilder;
import nl.fontys.sofa.limo.view.chain.ChainGraphScene;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.util.Lookup;
import org.openide.util.Utilities;
import org.openide.util.actions.Presenter;
import org.openide.windows.TopComponent;

/**
 * Action which is responsible for running the simulation.
 * <p>
 * It looks in the global lookup which {@link org.openide.windows.TopComponent}
 * currently is active and retrieves the
 * {@link nl.fontys.sofa.limo.view.chain.ChainGraphScene} from it.
 * <p>
 * The validation of the chain is done by the
 * {@link nl.fontys.sofa.limo.view.chain.ChainBuilder} and if this succeeds the
 * simulation is run and the corresponding
 * {@link org.openide.windows.TopComponent} is disabled.
 *
 * @author Sebastiaan Heijmann
 */
public final class SimulateAction extends AbstractAction implements Presenter.Toolbar {

    private final JFormattedTextField inputRunsTF;

    public SimulateAction(JFormattedTextField inputRunsTF) {
        this.inputRunsTF = inputRunsTF;
    }

    /**
     * Invokes the simulate action.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        SupplyChain supplyChain;
        Lookup global = Utilities.actionsGlobalContext();
        ChainGraphScene scene = global.lookup(ChainGraphScene.class);

        if (scene != null) {
            TopComponent tc = scene.getParent();
            ChainBuilder chainBuilder = scene.getChainBuilder();
            if (chainBuilder != null && chainBuilder.validate()) {
                supplyChain = chainBuilder.getSupplyChain();
                int numberOfRuns = (int) inputRunsTF.getValue();

                tc.makeBusy(true);
                scene.setEnabled(false);
                scene.setBackground(Color.LIGHT_GRAY);
                Simulator.simulate(numberOfRuns, supplyChain);
            } else {
                DialogDisplayer.getDefault().notify(
                        new NotifyDescriptor.Message(
                                "Validation of chain failed.\n\nPlease make sure you"
                                + " have built your chain correctly.",
                                NotifyDescriptor.WARNING_MESSAGE));
            }
        }
    }

    /**
     * {@inheritDoc}
     */
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
