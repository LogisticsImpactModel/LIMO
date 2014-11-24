package nl.fontys.sofa.limo.view.custom.panel;

import java.util.ResourceBundle;
import org.netbeans.validation.api.Problem;
import org.netbeans.validation.api.builtin.stringvalidation.StringValidators;
import org.netbeans.validation.api.ui.ValidationUI;
import org.netbeans.validation.api.ui.swing.SwingValidationGroup;

/**
 * Reusable JPanel for inputting name and description.
 *
 * @author Sebastiaan Heijmann
 */
public class NameDescriptionDialogInputPanel extends javax.swing.JPanel
        implements ValidationUI {

    private SwingValidationGroup group;

    /**
     * Creates new form DialogInputPane
     */
    public NameDescriptionDialogInputPanel() {
        initComponents();
        customInit();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        contentPane = new javax.swing.JPanel();
        nameLB = new javax.swing.JLabel();
        descriptionLB = new javax.swing.JLabel();
        nameTF = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        descriptionTF = new javax.swing.JTextArea();

        setMaximumSize(new java.awt.Dimension(450, 125));
        setMinimumSize(new java.awt.Dimension(450, 125));
        setPreferredSize(new java.awt.Dimension(450, 125));
        setRequestFocusEnabled(false);
        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.LINE_AXIS));

        contentPane.setMaximumSize(new java.awt.Dimension(450, 100));
        contentPane.setMinimumSize(new java.awt.Dimension(450, 100));
        contentPane.setPreferredSize(new java.awt.Dimension(450, 100));

        nameLB.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        org.openide.awt.Mnemonics.setLocalizedText(nameLB, org.openide.util.NbBundle.getMessage(NameDescriptionDialogInputPanel.class, "NameDescriptionDialogInputPanel.nameLB.text")); // NOI18N

        descriptionLB.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        org.openide.awt.Mnemonics.setLocalizedText(descriptionLB, org.openide.util.NbBundle.getMessage(NameDescriptionDialogInputPanel.class, "NameDescriptionDialogInputPanel.descriptionLB.text")); // NOI18N

        nameTF.setName("Name"); // NOI18N

        descriptionTF.setColumns(20);
        descriptionTF.setRows(5);
        descriptionTF.setName("Description"); // NOI18N
        jScrollPane1.setViewportView(descriptionTF);

        javax.swing.GroupLayout contentPaneLayout = new javax.swing.GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contentPaneLayout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(contentPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(nameLB, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(descriptionLB, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(contentPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nameTF, javax.swing.GroupLayout.DEFAULT_SIZE, 247, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addGap(40, 40, 40))
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contentPaneLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(contentPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nameTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nameLB))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(contentPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(contentPaneLayout.createSequentialGroup()
                        .addComponent(descriptionLB)
                        .addContainerGap(54, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
        );

        add(contentPane);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel contentPane;
    private javax.swing.JLabel descriptionLB;
    private javax.swing.JTextArea descriptionTF;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel nameLB;
    private javax.swing.JTextField nameTF;
    // End of variables declaration//GEN-END:variables

    public String getNameFieldValue() {
        return nameTF.getText();
    }

    public String getDescriptionFieldValue() {
        return descriptionTF.getText();
    }

    private void customInit() {
        ResourceBundle bundle = ResourceBundle.getBundle("nl/fontys/sofa/limo/view/custom/panel/Bundle");
        nameTF.setName(bundle.getString("NAME"));
        descriptionTF.setName(bundle.getString("DESCRIPTION"));
        group = SwingValidationGroup.create(this);
        group.add(nameTF, StringValidators.REQUIRE_NON_EMPTY_STRING);
        group.add(descriptionTF, StringValidators.REQUIRE_NON_EMPTY_STRING);
    }

    public SwingValidationGroup getValidationGroup() {
        return group;
    }

    @Override
    public void showProblem(Problem prblm) {
        System.out.println(prblm.getMessage());
    }

    @Override
    public void clearProblem() {
    }

}
