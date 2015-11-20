package nl.fontys.sofa.limo.view.wizard.types.hub;

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
import nl.fontys.sofa.limo.api.service.provider.HubTypeService;
import nl.fontys.sofa.limo.domain.component.type.HubType;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;
import org.openide.util.Lookup;

/**
 * Selection for New or Copy LegType
 *
 * @author Pascal Lindner
 */
public final class NewOrDuplicatedHubTypePanel extends JPanel {

    private List<HubType> ht;

    public NewOrDuplicatedHubTypePanel() {
        initComponents();
    }

    @Override
    public String getName() {
        return LIMOResourceBundle.getString("HUB_TYPE");
    }

    private void initComponents() {
        buttonGroup1 = new ButtonGroup();
        rbFromScratch = new JRadioButton();
        rbCopyFrom = new JRadioButton();
        cmbHubType = new JComboBox();

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

        buttonGroup1.add(rbCopyFrom);
        rbCopyFrom.setText(LIMOResourceBundle.getString("COPY_EXISTING"));
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 1;
        add(rbCopyFrom, c);
        setActionListener();
        HubTypeService hubTypeService = Lookup.getDefault().lookup(HubTypeService.class);
        ht = hubTypeService.findAll();
        List<String> hubTypeList = new ArrayList<>();
        ht.stream().forEach((ht2) -> {
            hubTypeList.add(ht2.getName());
        });

        cmbHubType.setModel(new DefaultComboBoxModel(hubTypeList.toArray()));
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 2;
        add(cmbHubType, c);
        cmbHubType.setEnabled(false);

        if (ht.isEmpty()) {
            rbCopyFrom.setEnabled(false);
            cmbHubType.setEditable(false);
        }
    }

    public HubType getHubType() {
        if (rbCopyFrom.isSelected()) {
            return ht.get(cmbHubType.getSelectedIndex());
        } else {
            return null;
        }
    }

    //AddActionListener
    public void setActionListener() {
        rbFromScratch.addActionListener((ActionEvent e) -> {
            if (rbFromScratch.isSelected()) {
                cmbHubType.setEnabled(false);
            }
        });
        rbCopyFrom.addActionListener((ActionEvent e) -> {
            if (rbCopyFrom.isSelected()) {
                cmbHubType.setEnabled(true);
            }
        });
    }

    private ButtonGroup buttonGroup1;
    private JRadioButton rbFromScratch;
    private JRadioButton rbCopyFrom;
    private JComboBox cmbHubType;

}
