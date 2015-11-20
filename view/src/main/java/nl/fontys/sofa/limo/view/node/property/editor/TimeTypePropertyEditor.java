package nl.fontys.sofa.limo.view.node.property.editor;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyEditorSupport;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import nl.fontys.sofa.limo.domain.component.procedure.TimeType;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;
import org.openide.nodes.PropertyEditorRegistration;

/**
 * Property editor for the location object of hubs.
 *
 * @author Dominik Kaisers {@literal <d.kaisers@student.fontys.nl>}
 */
@PropertyEditorRegistration(targetType = TimeType.class)
public class TimeTypePropertyEditor extends PropertyEditorSupport {

    @Override
    public boolean supportsCustomEditor() {
        return true;
    }

    @Override
    public Component getCustomEditor() {
        final TimeTypeEditor tte = new TimeTypeEditor((TimeType) getValue());
        tte.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                // Only save if wanted
                if (tte.shouldSafe) {
                    setValue(tte.procedureTimeType);
                }
            }

        });
        return tte;
    }

    @Override
    public String getAsText() {
        TimeType tt = ((TimeType) getValue());
        return tt.getName();
    }

    /**
     * Custom location editor.
     */
    private static class TimeTypeEditor extends JDialog implements ActionListener {

        private TimeType procedureTimeType;
        private boolean shouldSafe;

        private JButton btnOk;
        private JButton btnCancel;

        private JComboBox cmbTimeType;

        /**
         * Create location editor and init UI.
         *
         * @param procedureTimeType Old location.
         */
        public TimeTypeEditor(TimeType procedureTimeType) {
            this.procedureTimeType = procedureTimeType;
            this.shouldSafe = false;
            setModal(true);
            setPreferredSize(new Dimension(480, 240));
            setTitle("Edit of TimeType");
            initComponents();
            setLocationRelativeTo(null);
        }

        /**
         * Get updated location.
         *
         * @return Location.
         */
        public TimeType getProcedureTimeType() {
            return procedureTimeType;
        }

        /**
         * Should the new location be saved or not.
         *
         * @return Save true/false.
         */
        public boolean isShouldSafe() {
            return shouldSafe;
        }

        /**
         * Initialize UI.
         */
        public final void initComponents() {
            JPanel container = new JPanel();
            container.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
            setLayout(new BorderLayout());
            add(container, BorderLayout.CENTER);

            btnOk = new JButton(LIMOResourceBundle.getString("OK"));
            btnOk.addActionListener(this);

            btnCancel = new JButton(LIMOResourceBundle.getString("CANCEL"));
            btnCancel.addActionListener(this);

            JPanel buttonBar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            buttonBar.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
            buttonBar.add(btnCancel);
            buttonBar.add(btnOk);

            cmbTimeType = new JComboBox(TimeType.values());
            cmbTimeType.setSelectedItem(procedureTimeType);

            //<editor-fold defaultstate="collapsed" desc="Layout">
            container.setLayout(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();
            c.fill = GridBagConstraints.HORIZONTAL;

            c.gridx = 0;
            c.gridy = 0;
            c.gridwidth = 1;
            c.weightx = 0.0;
            container.add(new JLabel(LIMOResourceBundle.getString("CONTINENT")), c);
            c.gridx = 1;
            c.gridy = 0;
            c.gridwidth = 4;
            c.weightx = 0.8;
            container.add(cmbTimeType, c);

            add(buttonBar, BorderLayout.SOUTH);
//</editor-fold>
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(btnOk)) {
                // Only done when should save data
                shouldSafe = true;

                TimeType tt;
                tt = (TimeType) cmbTimeType.getSelectedItem();

                procedureTimeType = tt;
            }
            // Notify listeners of window close event
            for (WindowListener wl : getWindowListeners()) {
                wl.windowClosing(null);
            }
            // Close window
            setVisible(false);
            dispose();
        }
        
        /**
         * Get possible countries for given continent.
         *
         * @param continent Continent.
         * @return List of countries for continent.
         */
        private TimeType[] getTimeTypes() {
            TimeType[] tts = new TimeType[4];
            tts[0] = TimeType.MINUTES;
            tts[1] = TimeType.HOURS;
            tts[2] = TimeType.DAYS;
            tts[3] = TimeType.WEEKS;
            
            return tts;
        }
    }
}
