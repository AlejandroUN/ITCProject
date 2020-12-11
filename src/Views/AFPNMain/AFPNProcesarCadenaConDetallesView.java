/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views.AFPNMain;

import Entidades.AFPN;

/**
 *
 * @author aleja
 */
public class AFPNProcesarCadenaConDetallesView extends javax.swing.JFrame {

    AFPN AFPN;
    /**
     * Creates new form AFDProcesarCadenaConDetallesView
     */
    public AFPNProcesarCadenaConDetallesView(AFPN AFPN) {
        initComponents();
        this.AFPN = AFPN;
        stringProcessTf.hide();
        questionLabel.hide();
        answerLabel.hide();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        stringTf = new javax.swing.JTextField();
        processButton = new javax.swing.JButton();
        stringProcessTf = new javax.swing.JTextField();
        comeBackButton = new javax.swing.JButton();
        questionLabel = new javax.swing.JLabel();
        answerLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("AFPN Procesar Cadena con Detalles");

        jLabel2.setText("Por favor ingrese la cadena");

        stringTf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stringTfActionPerformed(evt);
            }
        });

        processButton.setText("Listo");
        processButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                processButtonActionPerformed(evt);
            }
        });

        comeBackButton.setText("Volver");
        comeBackButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comeBackButtonActionPerformed(evt);
            }
        });

        questionLabel.setText("¿Fue aceptada la cadena?");

        answerLabel.setText("jLabel3");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(95, 95, 95)
                        .addComponent(jLabel1)
                        .addGap(0, 79, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(stringTf, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(questionLabel)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(answerLabel)
                            .addComponent(processButton))))
                .addGap(50, 50, 50))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(stringProcessTf)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(comeBackButton)
                .addGap(37, 37, 37))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(stringTf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(processButton))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(questionLabel)
                    .addComponent(answerLabel))
                .addGap(14, 14, 14)
                .addComponent(stringProcessTf, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(comeBackButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void stringTfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stringTfActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_stringTfActionPerformed

    private void processButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_processButtonActionPerformed
        questionLabel.show();
        answerLabel.setText(Boolean.toString(AFPN.procesarCadenaConDetalles(stringTf.getText())));
        answerLabel.show();
        stringProcessTf.setText(AFPN.procesarCadenaConDetallesString(stringTf.getText()));
        stringProcessTf.show();
    }//GEN-LAST:event_processButtonActionPerformed

    private void comeBackButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comeBackButtonActionPerformed
        AFPNOptionsView afdOptionsView = new AFPNOptionsView(AFPN);
        afdOptionsView.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_comeBackButtonActionPerformed
 
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel answerLabel;
    private javax.swing.JButton comeBackButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JButton processButton;
    private javax.swing.JLabel questionLabel;
    private javax.swing.JTextField stringProcessTf;
    private javax.swing.JTextField stringTf;
    // End of variables declaration//GEN-END:variables
}
