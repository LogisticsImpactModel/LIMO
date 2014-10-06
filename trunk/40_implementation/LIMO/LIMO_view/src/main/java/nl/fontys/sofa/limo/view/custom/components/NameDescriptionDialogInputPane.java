/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.view.custom.components;

/**
 *
 * @author d3vil
 */
public class NameDescriptionDialogInputPane extends javax.swing.JPanel {

	/**
	 * Creates new form DialogInputPane
	 */
	public NameDescriptionDialogInputPane() {
		initComponents();
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
        nameTF = new javax.swing.JTextField();
        nameLB = new javax.swing.JLabel();
        descriptionLB = new javax.swing.JLabel();
        descriptionTF = new javax.swing.JTextField();

        nameTF.setText(org.openide.util.NbBundle.getMessage(NameDescriptionDialogInputPane.class, "NameDescriptionDialogInputPane.nameTF.text")); // NOI18N

        nameLB.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        org.openide.awt.Mnemonics.setLocalizedText(nameLB, org.openide.util.NbBundle.getMessage(NameDescriptionDialogInputPane.class, "NameDescriptionDialogInputPane.nameLB.text")); // NOI18N

        descriptionLB.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        org.openide.awt.Mnemonics.setLocalizedText(descriptionLB, org.openide.util.NbBundle.getMessage(NameDescriptionDialogInputPane.class, "NameDescriptionDialogInputPane.descriptionLB.text")); // NOI18N

        descriptionTF.setText(org.openide.util.NbBundle.getMessage(NameDescriptionDialogInputPane.class, "NameDescriptionDialogInputPane.descriptionTF.text")); // NOI18N

        javax.swing.GroupLayout contentPaneLayout = new javax.swing.GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contentPaneLayout.createSequentialGroup()
                .addGroup(contentPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(descriptionLB, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(nameLB, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(contentPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(nameTF)
                    .addComponent(descriptionTF, javax.swing.GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE))
                .addContainerGap())
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contentPaneLayout.createSequentialGroup()
                .addGroup(contentPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nameTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nameLB))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(contentPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(descriptionLB)
                    .addComponent(descriptionTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 12, Short.MAX_VALUE))
        );

        add(contentPane);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel contentPane;
    private javax.swing.JLabel descriptionLB;
    private javax.swing.JTextField descriptionTF;
    private javax.swing.JLabel nameLB;
    private javax.swing.JTextField nameTF;
    // End of variables declaration//GEN-END:variables

	public String getNameFieldValue(){
		return nameTF.getText();
	}

	public String getDescriptionFieldValue(){
		return descriptionTF.getText();
	}
}
