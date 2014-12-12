package nl.fontys.sofa.limo.view.wizard.types.leg;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import nl.fontys.sofa.limo.api.service.provider.LegTypeService;
import nl.fontys.sofa.limo.domain.component.type.LegType;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;
import org.openide.util.Lookup;

public final class NewOrDuplicatedLegTypePanel extends JPanel {

    private List<LegType> lt;

    public NewOrDuplicatedLegTypePanel() {
        initComponents();
    }

    @Override
    public String getName() {
        return LIMOResourceBundle.getString("LEG_TYPE");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    private void initComponents() {
        buttonGroup1 = new javax.swing.ButtonGroup();
        rbFromScratch = new javax.swing.JRadioButton();
        rbCopyFrom = new javax.swing.JRadioButton();
        cmbLegType = new javax.swing.JComboBox();

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

        buttonGroup1.add(rbCopyFrom);
        rbCopyFrom.setText(LIMOResourceBundle.getString("COPY_EXISTING"));
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 1;
        add(rbCopyFrom, c);

        rbCopyFrom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (rbCopyFrom.isSelected()) {
                    cmbLegType.setEnabled(true);
                }
            }
        });

        LegTypeService legTypeService = Lookup.getDefault().lookup(LegTypeService.class);
        lt = legTypeService.findAll();
        List<String> legTypeList = new ArrayList<>();
        for (LegType lt2 : lt) {
            legTypeList.add(lt2.getName());
        }

        cmbLegType.setModel(new javax.swing.DefaultComboBoxModel(legTypeList.toArray()));
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 2;
        add(cmbLegType, c);
        cmbLegType.setEnabled(false);
        
        if(lt.isEmpty()){
            rbCopyFrom.setEnabled(false);
            cmbLegType.setEditable(false);
        }

    }

    public LegType getLegType() {
        if (rbCopyFrom.isSelected()) {
            return lt.get(cmbLegType.getSelectedIndex());
        } else {
            return null;
        }
    }

    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JRadioButton rbFromScratch;
    private javax.swing.JRadioButton rbCopyFrom;
    private javax.swing.JComboBox cmbLegType;

}
