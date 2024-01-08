/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import aula_03_sockets_multicliente.Cliente;
import aula_03_sockets_multicliente.Mensagem;
import dao.ClienteDAO;
import java.awt.CardLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingWorker;

/**
 *
 * @author 0068948
 */
public class Frame extends javax.swing.JFrame {

    private static JScrollPane painelRolagem;
    private static JPanel painelTroca;
    private static CardLayout baralhoPaineis;
    private static Hashtable<String, JPanel> historicoPaineis;
    public String nome;
    private Cliente novoCliente = new Cliente();
    private ArrayList<String> clientesConectados;
    private DefaultListModel<String> clientesListModel;
    private DefaultListModel<Mensagem> smsListModel;
    private String nomeCliente;
    private ButtonGroup grupoBtn;
    private ArrayList<Mensagem> mensagensRecebidas = new ArrayList<>();
    private ArrayList<Mensagem> mensagensGerais = new ArrayList<>();
    private static SwingWorker atualizaClientesSwing;
    private static SwingWorker atualizaSMSSwing;
    private ArrayList<String> nomes = new ArrayList<>();
    private boolean veri;
    private boolean exit;

    public Frame() {
        initComponents();

        this.grupoBtn = new ButtonGroup();
        this.grupoBtn.add(smsDireto);
        this.grupoBtn.add(smsGeral);
        smsListModel = new DefaultListModel<>();

        String nome = JOptionPane.showInputDialog("Informe seu nome:");
        this.nome = nome;
        this.veri = false;
        this.exit = true;
        conecta();
        this.clientesListModel = new DefaultListModel<>();
        AtualizaClientes();
        AtualizaSMS();

        atualizaClientesSwing.execute();
        atualizaSMSSwing.execute();

        addWindowListener(new MeuWindowAdapter());
    }

    private class MeuWindowAdapter extends WindowAdapter {

        @Override
        public void windowClosing(WindowEvent e) {
            // Exibir uma mensagem de confirmação
            int resposta = JOptionPane.showConfirmDialog(null, "Você realmente deseja sair?", "Confirmação", JOptionPane.YES_NO_OPTION);

            // Verificar se o usuário realmente quer sair
            if (resposta == JOptionPane.YES_OPTION) {
                try {
                    Mensagem mensagem = new Mensagem(nome, "2");
                    novoCliente.enviar_mensagem(mensagem);
                    System.exit(0); // Fechar o aplicativo se o usuário confirmar
                } catch (Exception ex) {
                    Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    private void conecta() {
        try {
            this.novoCliente = new Cliente("10.90.37.93", 15500);
            Mensagem mensagem = new Mensagem(this.nome, "1");
            this.novoCliente.enviar_mensagem(mensagem);
        } catch (Exception ex) {
            Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void AtualizaClientes() {
        atualizaClientesSwing = new SwingWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                while (true) {
                    Mensagem mensagemListaConectados = new Mensagem(nome, "4");
                    if (veri == false) {
                        veri = true;
                        novoCliente.enviar_mensagem(mensagemListaConectados);
                        nomes = (ArrayList<String>) novoCliente.receber_mensagem();
                        veri = false;

                        if (!clientesListModel.equals(nomes)) {
                            clientesListModel.removeAllElements();
                            clientesListModel.addAll(nomes);
                            listaClientes.setModel(clientesListModel);
                        }
                        Thread.sleep(3000);
                    }
                }
            }
        };
    }

    public void AtualizaSMS() {
        this.atualizaSMSSwing = new SwingWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                Mensagem mensagem;
                while (true) {
                    System.out.println("");
                    if (veri == false && smsGeral.isSelected()) {
                        veri = true;

                        mensagem = new Mensagem(nome, "3");
                        novoCliente.enviar_mensagem(mensagem);

                        ArrayList<Mensagem> novasMensagensGerais = (ArrayList<Mensagem>) novoCliente.receber_mensagem();

                        if (!novasMensagensGerais.equals(mensagensGerais)) {
                            mensagensGerais = novasMensagensGerais;
                            String smsGeral = "";
                            smsListModel.removeAllElements();
                            smsListModel.addAll(mensagensGerais);
                            listaSMS.setModel(smsListModel);
                        }
                        veri = false;

                        Thread.sleep(1000);
                    }
                    if (veri == false && smsDireto.isSelected()) {
                        veri = true;

                        mensagem = new Mensagem(nome, "7");
                        novoCliente.enviar_mensagem(mensagem);

                        ArrayList<Mensagem> novasMensagensDiretas = new ArrayList<>();
                        novasMensagensDiretas = (ArrayList<Mensagem>) novoCliente.receber_mensagem();

                        if (!novasMensagensDiretas.equals(mensagensRecebidas)) {
                            mensagensRecebidas = novasMensagensDiretas;
                            smsListModel.removeAllElements();
                            smsListModel.addAll(mensagensRecebidas);
                            listaSMS.setModel(smsListModel);
                        }

                        veri = false;

                        Thread.sleep(1000);
                    }
                }
            }
        };
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        enviarBtn = new javax.swing.JToggleButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        textArea = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        listaClientes = new javax.swing.JList<>();
        jScrollPane4 = new javax.swing.JScrollPane();
        listaSMS = new javax.swing.JList<>();
        smsDireto = new javax.swing.JRadioButton();
        smsGeral = new javax.swing.JRadioButton();
        nomeDestino = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 102, 153));

        enviarBtn.setBackground(new java.awt.Color(102, 255, 255));
        enviarBtn.setForeground(new java.awt.Color(255, 102, 153));
        enviarBtn.setText("ENVIAR");
        enviarBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enviarBtnActionPerformed(evt);
            }
        });

        textArea.setBackground(new java.awt.Color(102, 255, 255));
        textArea.setColumns(20);
        textArea.setRows(5);
        jScrollPane2.setViewportView(textArea);

        listaClientes.setBackground(new java.awt.Color(102, 255, 255));
        listaClientes.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        listaClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listaClientesMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(listaClientes);

        listaSMS.setBackground(new java.awt.Color(102, 255, 255));
        listaSMS.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        listaSMS.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listaSMSMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(listaSMS);

        smsDireto.setForeground(new java.awt.Color(102, 255, 255));
        smsDireto.setText("SMS direto");
        smsDireto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                smsDiretoActionPerformed(evt);
            }
        });

        smsGeral.setForeground(new java.awt.Color(102, 255, 255));
        smsGeral.setText("SMS geral");
        smsGeral.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                smsGeralActionPerformed(evt);
            }
        });

        nomeDestino.setBackground(new java.awt.Color(102, 255, 255));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(smsDireto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(smsGeral))
                    .addComponent(nomeDestino))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 71, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(enviarBtn))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 415, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 391, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(smsDireto)
                            .addComponent(smsGeral))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE)
                    .addComponent(enviarBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE)
                    .addComponent(nomeDestino, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 731, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 515, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void enviarBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_enviarBtnActionPerformed
        if (!textArea.getText().isEmpty()) {
            if (smsDireto.isSelected()) {
                if (nomeDestino.getText() != null) {
                    try {
                        Mensagem mensagemEnviarDireta = new Mensagem(this.nome + "," + nomeDestino.getText(), textArea.getText() + ", 6");
                        novoCliente.enviar_mensagem(mensagemEnviarDireta);
 
                        textArea.setText("");
                        nomeDestino.setText("");
                    } catch (Exception ex) {
                        System.out.println("mensagem direta nao enviada");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Selecione o destinatário", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            } else if (smsGeral.isSelected()) {
                try {
                    Mensagem mensagemEnviarDireta = new Mensagem(this.nome, "5 - " + textArea.getText());
                    novoCliente.enviar_mensagem(mensagemEnviarDireta);

                    textArea.setText("");

                } catch (Exception ex) {
                    System.out.println("mensagem geral nao enviada");
                }
            }
        } else if (textArea.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha o campo de mensagem.", "ERROR", JOptionPane.ERROR_MESSAGE);
        }


    }//GEN-LAST:event_enviarBtnActionPerformed

    private void listaClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listaClientesMouseClicked
        int indice = listaClientes.getSelectedIndex();
        String destino = nomes.get(indice);
        nomeDestino.setText(destino);
    }//GEN-LAST:event_listaClientesMouseClicked

    private void listaSMSMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listaSMSMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_listaSMSMouseClicked

    private void smsGeralActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_smsGeralActionPerformed
        if (!smsListModel.isEmpty()) {
            smsListModel.removeAllElements();
        }
        atualizaSMSSwing.execute();
    }//GEN-LAST:event_smsGeralActionPerformed

    private void smsDiretoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_smsDiretoActionPerformed
        if (!smsListModel.isEmpty()) {
            smsListModel.removeAllElements();
        }
        atualizaSMSSwing.execute();
    }//GEN-LAST:event_smsDiretoActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Frame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton enviarBtn;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JList<String> listaClientes;
    private javax.swing.JList<Mensagem> listaSMS;
    private javax.swing.JTextField nomeDestino;
    private javax.swing.JRadioButton smsDireto;
    private javax.swing.JRadioButton smsGeral;
    private javax.swing.JTextArea textArea;
    // End of variables declaration//GEN-END:variables
}
