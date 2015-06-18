package nl.fontys.sofa.limo.view.custom.panel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicOptionPaneUI;
import nl.fontys.sofa.limo.view.topcomponent.SavableComponent;
import org.openide.util.Exceptions;
import org.openide.windows.TopComponent;

/**
 *
 * @author Merlissa Maas
 */
public class SavablePanel extends JPanel {

    private List<SavableComponent> allSavableComponents;
    private DefaultListModel<SavableComponent> selectionList;
    private JList list;
    private JPanel textContainer;

    public SavablePanel() {
//        setPreferredSize(new Dimension(250, 250));
        initSelectionList();
        this.setLayout(new BorderLayout());
        textContainer = new JPanel(new BorderLayout());
        JLabel closeQuestion = new JLabel("Are you sure you want to close LIMO?");
        closeQuestion.setBorder(new EmptyBorder(5, 5, 5, 5));
        textContainer.add(closeQuestion, BorderLayout.NORTH);
        if (!allSavableComponents.isEmpty()) {
            createSelectionList();
            createButtons();
        }
        this.add(textContainer, BorderLayout.NORTH);
    }

    private void initSelectionList() {
        allSavableComponents = new ArrayList<>();
        for (TopComponent tc : TopComponent.getRegistry().getOpened()) {
            SavableComponent scene = tc.getLookup().lookup(SavableComponent.class);
            if (scene != null) {
                allSavableComponents.add(scene);
            }
        }
    }

    private void createSelectionList() {
        JLabel text = new JLabel("Several supply chains are still open.");
        text.setBorder(new EmptyBorder(5, 5, 5, 5));
        selectionList = new DefaultListModel();
        for (int i = 0; i < allSavableComponents.size(); i++) {
            selectionList.add(i, allSavableComponents.get(i));
        }
        list = new JList(selectionList);
        list.setPreferredSize(new Dimension(0, 100));
        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        textContainer.add(text, BorderLayout.SOUTH);
        this.add(scrollPane, BorderLayout.CENTER);
    }

    private void createButtons() {
        JPanel buttomPanel = new JPanel(new BasicOptionPaneUI.ButtonAreaLayout(true, 1));
        JButton saveOne = new JButton("Save");
        saveOne.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<SavableComponent> selectedValuesList = list.getSelectedValuesList();
                for (SavableComponent s : selectedValuesList) {
                    try {
                        s.save();
                        selectedValuesList.remove(s);
                        selectionList.removeElement(s);
                    } catch (IOException ex) {
                        Exceptions.printStackTrace(ex);
                    }
                }
            }
        });
        JButton saveAll = new JButton("Save all");
        saveAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (SavableComponent s : allSavableComponents) {
                    try {
                        s.save();
                        allSavableComponents.remove(s);
                        selectionList.removeElement(s);
                    } catch (IOException ex) {
                        Exceptions.printStackTrace(ex);
                    }
                }
            }
        });
        JButton discardOne = new JButton("Discard");
        discardOne.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<SavableComponent> selectedValuesList = list.getSelectedValuesList();
                for (SavableComponent s : selectedValuesList) {
                    selectedValuesList.remove(s);
                    selectionList.removeElement(s);
                }
            }
        });
        JButton discardAll = new JButton("Discard all");
        discardAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (SavableComponent s : allSavableComponents) {
                    allSavableComponents.remove(s);
                    selectionList.removeElement(s);
                }
            }
        });
        buttomPanel.add(saveOne);
        buttomPanel.add(saveAll);
        buttomPanel.add(discardOne);
        buttomPanel.add(discardAll);
        this.add(buttomPanel, BorderLayout.SOUTH);
    }

}
