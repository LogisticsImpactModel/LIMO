package nl.fontys.sofa.limo.view.wizard.hub;

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
import nl.fontys.sofa.limo.api.service.provider.HubService;
import nl.fontys.sofa.limo.api.service.provider.HubTypeService;
import nl.fontys.sofa.limo.domain.component.hub.Hub;
import nl.fontys.sofa.limo.domain.component.type.HubType;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;
import org.openide.util.Lookup;

/**
 * 1th Panel for Hub. New, delete or copy
 *
 * @author Pascal Lindner
 */
public final class NewDuplicatedOrHubTypeHubPanel extends JPanel {

    private JComboBox hubCb;
    private JComboBox hubTypeCb;
    private JRadioButton hubCopySelection;
    private JRadioButton hubFromTypeSelection;
    private JRadioButton hubFromScratchSelection;

    private List<Hub> hubs;
    private List<HubType> hubTypes;

    public NewDuplicatedOrHubTypeHubPanel() {
        initComponents();
    }

    @Override
    public String getName() {
        return LIMOResourceBundle.getString("HUB");
    }

    private void initComponents() {
        ButtonGroup buttonGroup1 = new ButtonGroup();
        hubFromScratchSelection = new JRadioButton();
        hubFromTypeSelection = new JRadioButton();
        hubCopySelection = new JRadioButton();
        hubTypeCb = new JComboBox();
        hubCb = new JComboBox();
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 0;
        buttonGroup1.add(hubFromScratchSelection);
        hubFromScratchSelection.setText(LIMOResourceBundle.getString("FROM_SCRATCH"));
        add(hubFromScratchSelection, c);
        hubFromScratchSelection.setSelected(true);

        buttonGroup1.add(hubCopySelection);
        hubCopySelection.setText(LIMOResourceBundle.getString("COPY_HUB"));
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 1;
        add(hubCopySelection, c);

        HubService hubService = Lookup.getDefault().lookup(HubService.class);
        hubs = hubService.findAll();
        List<String> hubNameList = new ArrayList<>();
        for (Hub hub : hubs) {
            hubNameList.add(hub.getName());
        }

        hubCb.setModel(new DefaultComboBoxModel(hubNameList.toArray()));
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 2;
        add(hubCb, c);
        hubCb.setEnabled(false);

        buttonGroup1.add(hubFromTypeSelection);
        hubFromTypeSelection.setText(LIMOResourceBundle.getString("FROM_HUBTYPE"));
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 3;
        add(hubFromTypeSelection, c);

        HubTypeService hubTypeService = Lookup.getDefault().lookup(HubTypeService.class);
        hubTypes = hubTypeService.findAll();
        ArrayList<String> hubTypeList = new ArrayList<>();
        for (HubType hubType : hubTypes) {
            hubTypeList.add(hubType.getName());
        }

        hubTypeCb.setModel(new DefaultComboBoxModel(hubTypeList.toArray()));
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 4;
        add(hubTypeCb, c);
        hubTypeCb.setEnabled(false);

        if (hubNameList.isEmpty()) {
            hubCb.setEditable(false);
            hubCopySelection.setEnabled(false);
        }

        if (hubTypeList.isEmpty()) {
            hubTypeCb.setEditable(false);
            hubFromTypeSelection.setEnabled(false);
        }
        setActionsListener();
    }

    private void setActionsListener() {
        hubFromTypeSelection.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (hubFromTypeSelection.isSelected()) {
                    hubTypeCb.setEnabled(true);
                    hubCb.setEnabled(false);
                }
            }
        });
        hubFromScratchSelection.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (hubFromScratchSelection.isSelected()) {
                    hubTypeCb.setEnabled(false);
                    hubCb.setEnabled(false);
                }
            }
        });
        hubCopySelection.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (hubCopySelection.isSelected()) {
                    hubTypeCb.setEnabled(false);
                    hubCb.setEnabled(true);

                }
            }
        });
    }

    public HubType getHubType() {
        if (hubFromTypeSelection.isSelected()) {
            return hubTypes.get(hubTypeCb.getSelectedIndex());
        } else {
            return null;
        }
    }

    public boolean isHubCopySelected() {
        return hubCopySelection.isSelected();
    }

    public boolean isHubTypeSelected() {
        return hubFromTypeSelection.isSelected();
    }

    /**
     *
     * @return Hub which should be copied
     */
    public Hub getHub() {
        Hub hub = null;
        if (hubCopySelection.isSelected()) {
            hub = hubs.get(hubCb.getSelectedIndex());
            hub.setId(null);
            hub.setUniqueIdentifier(null);
        }
        return hub;
    }
}
