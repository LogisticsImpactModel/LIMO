package nl.fontys.sofa.limo.view.action;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import nl.fontys.sofa.limo.domain.component.SupplyChain;
import nl.fontys.sofa.limo.simulation.Simulator;
import nl.fontys.sofa.limo.simulation.SimulatorTask;
import nl.fontys.sofa.limo.simulation.SimulatorTaskListener;
import nl.fontys.sofa.limo.simulation.result.SimulationResult;
import nl.fontys.sofa.limo.view.chain.ChainBuilder;
import nl.fontys.sofa.limo.view.chain.ChainGraphScene;
import nl.fontys.sofa.limo.view.topcomponent.ResultTopComponent;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.util.Lookup;
import org.openide.util.Utilities;
import org.openide.util.actions.Presenter;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;

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
public final class SimulateAction extends AbstractAction
        implements Presenter.Toolbar, SimulatorTaskListener {

    public static final int DEFAULT_NUM_RUNS = 1000;

    private final JFormattedTextField inputRunsTF;
    private ChainGraphScene scene;

    /**
     * Constructor sets the input text field from where to get the number of
     * simulation runs from.
     *
     * @param inputRunsTF the formatted text field.
     */
    public SimulateAction(JFormattedTextField inputRunsTF) {
        this.inputRunsTF = inputRunsTF;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Invokes the simulate action.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Lookup global = Utilities.actionsGlobalContext();
        scene = global.lookup(ChainGraphScene.class);
        int numberOfRuns = DEFAULT_NUM_RUNS;

        if (scene != null) {
            if (inputRunsTF.isEditValid()) {
                Object input = inputRunsTF.getValue();
                if (input != null) {
                    numberOfRuns = (int) input;
                }
                performSimulation(scene, numberOfRuns);
            }

        }
    }

    @Override
    public void taskFinished(SimulatorTask task) {
        TopComponent tc = (TopComponent) scene.getParent();
        tc.makeBusy(false);
        scene.setEnabled(true);
        scene.setBackground(Color.WHITE);

        if (task.getCancelled().get()) {
            return;
        }

        for (final SimulationResult simResult : task.getResults()) {
            WindowManager.getDefault().invokeWhenUIReady(new Runnable() {
                
                @Override
                public void run() {
                    ResultTopComponent rtc = new ResultTopComponent(simResult);
                    rtc.open();
                    rtc.requestActive();
                }
                
            });
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * Creates a new simulation button to invoke this action from.
     *
     * @return the component as a {@link javax.swing.JButton}.
     */
    @Override
    public Component getToolbarPresenter() {
        JButton button = new JButton(this);
        button.setIcon(new ImageIcon(getClass().getClassLoader().getResource("icons/gui/simulate.png")));
        button.setText(LIMOResourceBundle.getString("SIMULATE"));
        button.setOpaque(false);
        button.setBorder(null);
        return button;
    }

    private void performSimulation(ChainGraphScene scene, int numberOfRuns) {
        ChainBuilder chainBuilder = scene.getChainBuilder();

        if (chainBuilder != null && chainBuilder.validate()) {
            TopComponent tc = (TopComponent) scene.getParent();
            SupplyChain supplyChain = chainBuilder.getSupplyChain();

            tc.makeBusy(true);
            scene.setEnabled(false);
            scene.setBackground(Color.LIGHT_GRAY);
            SimulatorTask task = Simulator.simulate(numberOfRuns, supplyChain);
            task.addTaskListener(this);
        } else {
            DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message(
                    LIMOResourceBundle.getString("CHAIN_VALIDATION_FAILED"),
                    NotifyDescriptor.WARNING_MESSAGE));
        }
    }

}
