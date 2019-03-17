/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Clase03.Agenda.Interfaces;

import Clase03.Agenda.Conector_BD;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Home
 */
public class Agenda extends javax.swing.JFrame {
    
    SessionNow session;
    
    /** Creates new form Agenda */
    public Agenda() {
        initComponents();
    }
    
    public Agenda(SessionNow session_now){
        session = session_now;
        initComponents();
        TXT_username.setText(session_now.getUser_now());
        setLocationRelativeTo(null);
    }
    
    Conector_BD conectar = new Conector_BD();
    Connection cn = conectar.getConnection();
    
    void mostrarAmigos(SessionNow session){
        
        System.out.println("Email de sesión: "+session.getEmail_now());
        
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Name");
        modelo.addColumn("Surname");
        modelo.addColumn("Phone");
        modelo.addColumn("Birthday");
        modelo.addColumn("Email");
        modelo.addColumn("User");
        
        TABLE_FRIEND.setModel(modelo);
        
        String query = "SELECT NAME,SURNAME,PHONE,BIRTHDAY,EMAIL FROM AMIGO "+
                        "WHERE (EMAIL_AMIGO = '"+session.getEmail_now()+"')";
        
        String[] campos = new String[5];
        
        try{
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(query);
            
            while(rs.next()){
                campos[0] = rs.getString(1);
                campos[1] = rs.getString(2);
                campos[2] = rs.getString(3);
                campos[3] = rs.getString(4);
                campos[4] = rs.getString(5);
                
                modelo.addRow(campos);
            }
            
            TABLE_FRIEND.setModel(modelo);
            
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        LBL_cerrarSesion = new javax.swing.JLabel();
        LBL_exit = new javax.swing.JLabel();
        LBL_username = new javax.swing.JLabel();
        TXT_username = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TABLE_FRIEND = new javax.swing.JTable();
        BTN_addFriend = new javax.swing.JButton();
        BTN_deleteFriend = new javax.swing.JButton();
        BTN_editFriend = new javax.swing.JButton();
        BTN_contacts = new javax.swing.JButton();
        LBL_ADD = new javax.swing.JLabel();
        LBL_DELETE = new javax.swing.JLabel();
        LBL_EDIT = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(97, 212, 195));

        LBL_cerrarSesion.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        LBL_cerrarSesion.setForeground(new java.awt.Color(255, 255, 255));
        LBL_cerrarSesion.setText("Cerrar sesión");

        LBL_exit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Clase03/Agenda/Images/Delete_20px.png"))); // NOI18N
        LBL_exit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LBL_exitMouseClicked(evt);
            }
        });

        LBL_username.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        LBL_username.setForeground(new java.awt.Color(255, 255, 255));
        LBL_username.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Clase03/Agenda/Images/Anonymous Mask_30px.png"))); // NOI18N

        TXT_username.setBackground(new java.awt.Color(97, 212, 195));
        TXT_username.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        TXT_username.setEnabled(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(LBL_cerrarSesion)
                .addGap(113, 113, 113)
                .addComponent(LBL_username)
                .addGap(1, 1, 1)
                .addComponent(TXT_username)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(LBL_exit))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(TXT_username, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(LBL_cerrarSesion))
                        .addComponent(LBL_exit)
                        .addComponent(LBL_username)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(36, 47, 65));

        TABLE_FRIEND.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(TABLE_FRIEND);

        BTN_addFriend.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Clase03/Agenda/Images/Add User Male_15px.png"))); // NOI18N
        BTN_addFriend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTN_addFriendActionPerformed(evt);
            }
        });

        BTN_deleteFriend.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Clase03/Agenda/Images/Denied_15px.png"))); // NOI18N
        BTN_deleteFriend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTN_deleteFriendActionPerformed(evt);
            }
        });

        BTN_editFriend.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Clase03/Agenda/Images/Registration_15px.png"))); // NOI18N
        BTN_editFriend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTN_editFriendActionPerformed(evt);
            }
        });

        BTN_contacts.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Clase03/Agenda/Images/Contacts_30px.png"))); // NOI18N
        BTN_contacts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTN_contactsActionPerformed(evt);
            }
        });

        LBL_ADD.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        LBL_ADD.setForeground(new java.awt.Color(255, 255, 255));
        LBL_ADD.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LBL_ADD.setText("Add");

        LBL_DELETE.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        LBL_DELETE.setForeground(new java.awt.Color(255, 255, 255));
        LBL_DELETE.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LBL_DELETE.setText("Delete");

        LBL_EDIT.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        LBL_EDIT.setForeground(new java.awt.Color(255, 255, 255));
        LBL_EDIT.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LBL_EDIT.setText("Edit");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(BTN_contacts)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(LBL_ADD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(BTN_addFriend, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(43, 43, 43)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(BTN_deleteFriend, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(LBL_DELETE, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(43, 43, 43)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(BTN_editFriend, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(LBL_EDIT, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(BTN_contacts)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(BTN_deleteFriend, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(BTN_addFriend, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(BTN_editFriend, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(LBL_ADD)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(LBL_DELETE)
                                .addComponent(LBL_EDIT)))))
                .addContainerGap(201, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BTN_addFriendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTN_addFriendActionPerformed
        this.setVisible(false);
        Agenda_Add add = new Agenda_Add(session);
        add.setVisible(true);
    }//GEN-LAST:event_BTN_addFriendActionPerformed

    private void LBL_exitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LBL_exitMouseClicked
        System.exit(0);
    }//GEN-LAST:event_LBL_exitMouseClicked

    private void BTN_contactsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTN_contactsActionPerformed
        System.out.println("User in Session: "+this.session.getUser_now());;
        mostrarAmigos(this.session);
    }//GEN-LAST:event_BTN_contactsActionPerformed

    private void BTN_deleteFriendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTN_deleteFriendActionPerformed
        this.setVisible(false);
        Agenda_Delete delete = new Agenda_Delete();
        delete.setVisible(true);
    }//GEN-LAST:event_BTN_deleteFriendActionPerformed

    private void BTN_editFriendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTN_editFriendActionPerformed
        this.setVisible(false);
        Agenda_Edit edit = new Agenda_Edit();
        edit.setVisible(true);
    }//GEN-LAST:event_BTN_editFriendActionPerformed

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
            java.util.logging.Logger.getLogger(Agenda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Agenda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Agenda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Agenda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Agenda().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BTN_addFriend;
    private javax.swing.JButton BTN_contacts;
    private javax.swing.JButton BTN_deleteFriend;
    private javax.swing.JButton BTN_editFriend;
    private javax.swing.JLabel LBL_ADD;
    private javax.swing.JLabel LBL_DELETE;
    private javax.swing.JLabel LBL_EDIT;
    private javax.swing.JLabel LBL_cerrarSesion;
    private javax.swing.JLabel LBL_exit;
    private javax.swing.JLabel LBL_username;
    private javax.swing.JTable TABLE_FRIEND;
    private javax.swing.JTextField TXT_username;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

}