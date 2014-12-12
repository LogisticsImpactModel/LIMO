package nl.fontys.sofa.limo.view.wizard.leg.normal;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import nl.fontys.sofa.limo.api.service.provider.LegTypeService;
import nl.fontys.sofa.limo.domain.component.type.LegType;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;
import org.openide.util.Lookup;

public final class NewOrFromTypePanel extends JPanel {

    /**
     * Creates new form NormalLegVisualPanel1
     */
    private ButtonGroup buttonGroup1;
    private JComboBox cmbLegType;
    private JRadioButton rbFromLegType;
    private JRadioButton rbFromScratch;
    private List<LegType> htl;

    public NewOrFromTypePanel() {
        initComponents();
    }

    @Override
    public String getName() {
        return LIMOResourceBundle.getString("CREATE_LEG");
    }

    private void initComponents() {
        buttonGroup1 = new ButtonGroup();
        rbFromScratch = new JRadioButton();
        rbFromLegType = new JRadioButton();
        cmbLegType = new JComboBox();
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 0;
        buttonGroup1.add(rbFromScratch);
        rbFromScratch.setText(LIMOResourceBundle.getString("FROM_SCRATCH"));
        add(rbFromScratch, c);
        rbFromScratch.setSelected(true);

        rbFromScratch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (rbFromScratch.isSelected()) {
                    cmbLegType.setEnabled(false);
                }
            }
        });

        buttonGroup1.add(rbFromLegType);
        rbFromLegType.setText(LIMOResourceBundle.getString("FROM_LEGTYPE"));
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 3;
        add(rbFromLegType, c);

        rbFromLegType.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (rbFromLegType.isSelected()) {
                    cmbLegType.setEnabled(true);
                }
            }
        });

        LegTypeService legTypeService = Lookup.getDefault().lookup(LegTypeService.class);
        htl = legTypeService.findAll();
        ArrayList<String> legTypeList = new ArrayList<>();
        for (LegType legType : htl) {
            legTypeList.add(legType.getName());
        }

        cmbLegType.setModel(new DefaultComboBoxModel(legTypeList.toArray()));
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 4;
        add(cmbLegType, c);
        cmbLegType.setEnabled(false);
        if(htl.isEmpty()){
            rbFromLegType.setEnabled(false);
            cmbLegType.setEditable(false);
        }
    }

    public LegType getLegType() {
        if (rbFromLegType.isSelected()) {
            return htl.get(cmbLegType.getSelectedIndex());
        } else {
            return null;
        }
    }
}
