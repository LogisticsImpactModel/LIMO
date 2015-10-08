package nl.fontys.sofa.limo.view.action;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import nl.fontys.sofa.limo.domain.component.SupplyChain;
import nl.fontys.sofa.limo.simulation.Simulator;
import nl.fontys.sofa.limo.simulation.SimulatorTask;
import nl.fontys.sofa.limo.simulation.SimulatorTaskListener;
import nl.fontys.sofa.limo.view.chain.ChainBuilder;
import nl.fontys.sofa.limo.view.chain.ChainGraphScene;
import nl.fontys.sofa.limo.view.topcomponent.ResultTopComponent;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.Exceptions;
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
@ActionID(category = "File", id = "nl.fontys.sofa.limo.view.action.SimulateAction")
@ActionRegistration(lazy = false, displayName = "NOT-USED")
@ActionReferences({
    @ActionReference(path = "Shortcuts", name = "D-F5")})
public final class SimulateAction extends AbstractAction
        implements Presenter.Toolbar, SimulatorTaskListener {

    public static final int DEFAULT_NUM_RUNS = 100000;

    private final JFormattedTextField inputRunsTF;
    private List<ChainGraphScene> scenes;

    /**
     * Constructor sets the input text field from where to get the number of
     * simulation runs from.
     *
     * @param inputRunsTF the formatted text field.
     */
    public SimulateAction(JFormattedTextField inputRunsTF) {
        this.inputRunsTF = inputRunsTF;
    }

    public SimulateAction() {
        this.inputRunsTF = null;
    }

    public SimulateAction(List<ChainGraphScene> scene) {
        this();
        this.scenes = scene;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Invokes the simulate action.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (scenes == null) {
            scenes = new ArrayList<>();
            Lookup global = Utilities.actionsGlobalContext();
            ChainGraphScene scene = global.lookup(ChainGraphScene.class);
            if(scene == null){
                return;
            }
            
            scenes.add(scene);
        }

        int numberOfRuns = DEFAULT_NUM_RUNS;
        Object input = null;
        if (inputRunsTF != null && inputRunsTF.isEditValid()) {
            input = inputRunsTF.getValue();
        }

        if (input != null) {
            numberOfRuns = (int) input;
        }
        performSimulation(scenes, numberOfRuns);
    }

    @Override
    public void taskFinished(final SimulatorTask task) {
        for (ChainGraphScene scene : scenes) {
            TopComponent tc = (TopComponent) scene.getParent();
            tc.makeBusy(false);
            scene.setEnabled(true);
            scene.setBackground(Color.WHITE);
        }
        if (task.getCancelled().get()) {
            return;
        }

        //for (final SimulationResult simResult : task.getResults()) {
        WindowManager.getDefault().invokeWhenUIReady(new Runnable() {

            @Override
            public void run() {
                try {
                    ResultTopComponent rtc = new ResultTopComponent(task.getResults());
                    rtc.open();
                    rtc.requestActive();
                } catch (InstantiationException ex) {
                    Exceptions.printStackTrace(ex);
                }
            }

        });
        //}
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

    private void performSimulation(List<ChainGraphScene> scenes, int numberOfRuns) {
        List<SupplyChain> chain = new ArrayList<>();
        for (ChainGraphScene scene : scenes) {
            ChainBuilder chainBuilder = scene.getChainBuilder();

            if (chainBuilder != null && chainBuilder.validate()) {
                TopComponent tc = (TopComponent) scene.getParent();
                SupplyChain supplyChain = chainBuilder.getSupplyChain();
                chain.add(supplyChain);
                tc.makeBusy(true);
                scene.setEnabled(false);
                scene.setBackground(Color.LIGHT_GRAY);

            } else {
                DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message(
                        LIMOResourceBundle.getString("CHAIN_VALIDATION_FAILED"),
                        NotifyDescriptor.WARNING_MESSAGE));
                return;
            }
        }
        SupplyChain [] c = chain.toArray(new SupplyChain[chain.size()]);
        SimulatorTask task = Simulator.simulate(numberOfRuns,c);
        task.addTaskListener(this);
    }

}