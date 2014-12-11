package nl.fontys.sofa.limo.view.chain;

import java.awt.Component;
import java.awt.Dimension;
import java.text.NumberFormat;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JToolBar;
import javax.swing.text.NumberFormatter;
import nl.fontys.sofa.limo.view.action.SimulateAction;

/**
 * ChainToolbar represents a {@link javax.swing.JToolBar} in the
 * {@link nl.fontys.sofa.limo.view.topcomponent.ChainBuilderTopComponent}.
 * <p>
 * It contains a {@link javax.swing.JFormattedTextField} and a
 * {@link java.awt.Component} retrieved from the
 * {@link nl.fontys.sofa.limo.view.action.SimulateAction} getToolbarPresenter()
 * method.
 *
 * @author Sebastiaan Heijmann
 */
public class ChainToolbar extends JToolBar {

    private SimulateAction simulateAction;

    /**
     * Creates new ChainToolbar with the appropriate components and initializes
     * a new {@link nl.fontys.sofa.limo.view.action.SimulateAction}
     */
    public ChainToolbar() {
        initComponents();
        initCustomComponents();
    }

    private void initCustomComponents() {
        NumberFormat format = NumberFormat.getIntegerInstance();
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setValueClass(Integer.class);
        formatter.setMinimum(0);
        formatter.setMaximum(Integer.MAX_VALUE);
        JFormattedTextField inputRunsTF = new JFormattedTextField(formatter);
        inputRunsTF.setMaximumSize(new Dimension(100, 50));
        simulateAction = new SimulateAction(inputRunsTF);

        JLabel timesLB = new JLabel("times...");
        Component toolbarPresenter = simulateAction.getToolbarPresenter();

        add(toolbarPresenter);
        add(new JToolBar.Separator());
        add(inputRunsTF);
        add(timesLB);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
