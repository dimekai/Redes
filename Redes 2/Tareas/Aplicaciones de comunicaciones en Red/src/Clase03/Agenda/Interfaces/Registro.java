package Clase03.Agenda.Interfaces;

import Clase03.Agenda.Clases.Persona;
import Clase03.Agenda.Consultas_BD;
import Clase03.Agenda.Interfaces.*;
import Clase03.Dato;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Kaimorts
 */
public class Registro extends javax.swing.JFrame {

    public Registro() {
        initComponents();
        this.setLocationRelativeTo(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jComboBox1 = new javax.swing.JComboBox<>();
        jCalendar1 = new com.toedter.calendar.JCalendar();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel5 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        TXT_name = new javax.swing.JTextField();
        TXT_surname = new javax.swing.JTextField();
        TXT_email = new javax.swing.JTextField();
        TXT_password = new javax.swing.JPasswordField();
        TXT_phone = new javax.swing.JTextField();
        TXT_UserName = new javax.swing.JTextField();
        LBL_name1 = new javax.swing.JLabel();
        LBL_surname = new javax.swing.JLabel();
        LBL_email = new javax.swing.JLabel();
        LBL_password = new javax.swing.JLabel();
        LBL_month = new javax.swing.JLabel();
        LBL_birth = new javax.swing.JLabel();
        LBL_EXIT = new javax.swing.JLabel();
        YearC_year = new com.toedter.calendar.JYearChooser();
        MonthC_month = new com.toedter.calendar.JMonthChooser();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator5 = new javax.swing.JSeparator();
        jSeparator6 = new javax.swing.JSeparator();
        jSeparator7 = new javax.swing.JSeparator();
        BTN_signUp = new javax.swing.JButton();
        LBL_phone = new javax.swing.JLabel();
        LBL_Username = new javax.swing.JLabel();
        LBL_day = new javax.swing.JLabel();
        TXT_day = new javax.swing.JTextField();
        LBL_year = new javax.swing.JLabel();
        jSeparator8 = new javax.swing.JSeparator();

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(36, 47, 65));
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(97, 212, 195));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Clase03/Agenda/Images/Calendar_96px.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Plans your own life");

        jSeparator1.setBackground(new java.awt.Color(255, 153, 153));

        jSeparator2.setBackground(new java.awt.Color(255, 153, 153));

        jLabel5.setFont(new java.awt.Font("Century Gothic", 1, 11)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Escuela Superior de Cómputo");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jSeparator1)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(25, 25, 25)
                            .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel2))
                .addContainerGap(54, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addComponent(jLabel2)
                .addGap(30, 30, 30)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(36, 47, 65));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        TXT_name.setBackground(new java.awt.Color(36, 47, 65));
        TXT_name.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        TXT_name.setForeground(new java.awt.Color(255, 255, 255));
        TXT_name.setBorder(null);
        TXT_name.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TXT_nameActionPerformed(evt);
            }
        });
        jPanel2.add(TXT_name, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 7, 140, 20));

        TXT_surname.setBackground(new java.awt.Color(36, 47, 65));
        TXT_surname.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        TXT_surname.setForeground(new java.awt.Color(255, 255, 255));
        TXT_surname.setBorder(null);
        TXT_surname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TXT_surnameActionPerformed(evt);
            }
        });
        jPanel2.add(TXT_surname, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 40, 140, 20));

        TXT_email.setBackground(new java.awt.Color(36, 47, 65));
        TXT_email.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        TXT_email.setForeground(new java.awt.Color(255, 255, 255));
        TXT_email.setBorder(null);
        TXT_email.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TXT_emailActionPerformed(evt);
            }
        });
        jPanel2.add(TXT_email, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 80, 140, 20));

        TXT_password.setBackground(new java.awt.Color(36, 47, 65));
        TXT_password.setFont(new java.awt.Font("Century Gothic", 0, 10)); // NOI18N
        TXT_password.setForeground(new java.awt.Color(255, 255, 255));
        TXT_password.setBorder(null);
        TXT_password.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TXT_passwordActionPerformed(evt);
            }
        });
        jPanel2.add(TXT_password, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 120, 140, 20));

        TXT_phone.setBackground(new java.awt.Color(36, 47, 65));
        TXT_phone.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        TXT_phone.setForeground(new java.awt.Color(255, 255, 255));
        TXT_phone.setBorder(null);
        TXT_phone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TXT_phoneActionPerformed(evt);
            }
        });
        jPanel2.add(TXT_phone, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 160, 140, 20));

        TXT_UserName.setBackground(new java.awt.Color(36, 47, 65));
        TXT_UserName.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        TXT_UserName.setForeground(new java.awt.Color(255, 255, 255));
        TXT_UserName.setBorder(null);
        TXT_UserName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TXT_UserNameActionPerformed(evt);
            }
        });
        jPanel2.add(TXT_UserName, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 300, 140, 20));

        LBL_name1.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        LBL_name1.setForeground(new java.awt.Color(204, 204, 204));
        LBL_name1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Clase03/Agenda/Images/User_15px.png"))); // NOI18N
        LBL_name1.setText("Name");
        jPanel2.add(LBL_name1, new org.netbeans.lib.awtextra.AbsoluteConstraints(17, 11, -1, -1));

        LBL_surname.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        LBL_surname.setForeground(new java.awt.Color(204, 204, 204));
        LBL_surname.setText("Surname");
        jPanel2.add(LBL_surname, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, -1, 20));

        LBL_email.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        LBL_email.setForeground(new java.awt.Color(204, 204, 204));
        LBL_email.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Clase03/Agenda/Images/Post_15px.png"))); // NOI18N
        LBL_email.setText("Email");
        jPanel2.add(LBL_email, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, -1, -1));

        LBL_password.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        LBL_password.setForeground(new java.awt.Color(204, 204, 204));
        LBL_password.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Clase03/Agenda/Images/Password_15px.png"))); // NOI18N
        LBL_password.setText("Password");
        jPanel2.add(LBL_password, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, -1, -1));

        LBL_month.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        LBL_month.setForeground(new java.awt.Color(204, 204, 204));
        LBL_month.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LBL_month.setText("Month");
        jPanel2.add(LBL_month, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 260, 100, -1));

        LBL_birth.setFont(new java.awt.Font("Century Gothic", 1, 11)); // NOI18N
        LBL_birth.setForeground(new java.awt.Color(204, 204, 204));
        LBL_birth.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Clase03/Agenda/Images/Cute Cake_15px.png"))); // NOI18N
        LBL_birth.setText("Birthdate (YYYY/MM/DD) ");
        jPanel2.add(LBL_birth, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, -1, -1));

        LBL_EXIT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Clase03/Agenda/Images/Delete_20px.png"))); // NOI18N
        LBL_EXIT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LBL_EXITMouseClicked(evt);
            }
        });
        jPanel2.add(LBL_EXIT, new org.netbeans.lib.awtextra.AbsoluteConstraints(246, 11, -1, -1));
        jPanel2.add(YearC_year, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 230, 59, -1));
        jPanel2.add(MonthC_month, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 230, -1, -1));
        jPanel2.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 30, 140, -1));
        jPanel2.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 62, 140, 10));
        jPanel2.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 100, 140, 10));
        jPanel2.add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 140, 140, 20));
        jPanel2.add(jSeparator7, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 180, 140, 10));

        BTN_signUp.setBackground(new java.awt.Color(97, 212, 195));
        BTN_signUp.setText("Sign up");
        BTN_signUp.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        BTN_signUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTN_signUpActionPerformed(evt);
            }
        });
        jPanel2.add(BTN_signUp, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 340, 60, 30));

        LBL_phone.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        LBL_phone.setForeground(new java.awt.Color(204, 204, 204));
        LBL_phone.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Clase03/Agenda/Images/iPhone_15px.png"))); // NOI18N
        LBL_phone.setText("Phone");
        jPanel2.add(LBL_phone, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, -1, -1));

        LBL_Username.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        LBL_Username.setForeground(new java.awt.Color(204, 204, 204));
        LBL_Username.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Clase03/Agenda/Images/Anonymous Mask_15px.png"))); // NOI18N
        LBL_Username.setText("Username");
        jPanel2.add(LBL_Username, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 300, -1, -1));

        LBL_day.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        LBL_day.setForeground(new java.awt.Color(204, 204, 204));
        LBL_day.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LBL_day.setText("Day");
        jPanel2.add(LBL_day, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 260, 50, -1));

        TXT_day.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TXT_dayActionPerformed(evt);
            }
        });
        TXT_day.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                TXT_dayKeyTyped(evt);
            }
        });
        jPanel2.add(TXT_day, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 230, 50, -1));

        LBL_year.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        LBL_year.setForeground(new java.awt.Color(204, 204, 204));
        LBL_year.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LBL_year.setText("Year");
        jPanel2.add(LBL_year, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 260, 60, -1));
        jPanel2.add(jSeparator8, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 320, 140, 20));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 381, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TXT_nameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TXT_nameActionPerformed
        TXT_name.transferFocus();
    }//GEN-LAST:event_TXT_nameActionPerformed

    private void TXT_UserNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TXT_UserNameActionPerformed
        TXT_UserName.transferFocus();
    }//GEN-LAST:event_TXT_UserNameActionPerformed

    private void LBL_EXITMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LBL_EXITMouseClicked
        System.exit(0);
    }//GEN-LAST:event_LBL_EXITMouseClicked

    private void TXT_passwordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TXT_passwordActionPerformed
        TXT_password.transferFocus();
    }//GEN-LAST:event_TXT_passwordActionPerformed

    private void BTN_signUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTN_signUpActionPerformed
        /*Creamos el usuario nuevo*/
        Persona persona = new Persona();        
        Calendar month = new GregorianCalendar();
        int monthP = month.get(Calendar.MONTH);
        String format_date = "", mes, anio, dia;
        
        persona.setName(TXT_name.getText().toUpperCase().trim());
        persona.setSurname(TXT_surname.getText().toUpperCase().trim());
        persona.setEmail(TXT_email.getText().trim());
        persona.setPassword(TXT_password.getText().trim());
        persona.setPhone(TXT_phone.getText());
        persona.setUsername(TXT_UserName.getText().trim());
        anio = Integer.toString(YearC_year.getYear());
        mes = Integer.toString(MonthC_month.getMonth()+1);
        dia = TXT_day.getText();
        
        if (Integer.parseInt(mes)<10)
            format_date = anio+"-0"+mes+"-"+dia;
        else
            format_date = anio+"-"+mes+"-"+dia;
        
        persona.setBirthday(format_date);
        
        System.out.println("|------- REGISTRO DE USUARIO -------|");
        System.out.println("Name:\t"+persona.getName());
        System.out.println("Surname:\t"+persona.getSurname());
        System.out.println("Email:\t"+persona.getEmail());
        System.out.println("Password:\t"+persona.getPassword());
        System.out.println("Phone:\t"+persona.getPhone());
        System.out.println("Date:\t"+persona.getBirthday());
        System.out.println("|-----------------------------------|");
        //query.registro(persona); Va en servidor
        SessionNow session_go = getConexion(persona);
        this.setVisible(false);
        Agenda change = new Agenda(session_go);
        change.setVisible(true);
    }//GEN-LAST:event_BTN_signUpActionPerformed

    private void TXT_surnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TXT_surnameActionPerformed
        TXT_surname.transferFocus();
    }//GEN-LAST:event_TXT_surnameActionPerformed

    private void TXT_emailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TXT_emailActionPerformed
        TXT_email.transferFocus();
    }//GEN-LAST:event_TXT_emailActionPerformed

    private void TXT_dayKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TXT_dayKeyTyped
        int limit_days = 2;
        if ( (TXT_day.getText().length()== limit_days) ) {
            if ((Integer.parseInt(TXT_day.getText())<=31 && 
                 Integer.parseInt(TXT_day.getText())>=1)) {
                evt.consume();
                TXT_day.transferFocus();
            }
        }        
    }//GEN-LAST:event_TXT_dayKeyTyped

    private void TXT_dayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TXT_dayActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TXT_dayActionPerformed

    private void TXT_phoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TXT_phoneActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TXT_phoneActionPerformed

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
            java.util.logging.Logger.getLogger(Registro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Registro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Registro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Registro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Registro().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BTN_signUp;
    private javax.swing.JLabel LBL_EXIT;
    private javax.swing.JLabel LBL_Username;
    private javax.swing.JLabel LBL_birth;
    private javax.swing.JLabel LBL_day;
    private javax.swing.JLabel LBL_email;
    private javax.swing.JLabel LBL_month;
    private javax.swing.JLabel LBL_name1;
    private javax.swing.JLabel LBL_password;
    private javax.swing.JLabel LBL_phone;
    private javax.swing.JLabel LBL_surname;
    private javax.swing.JLabel LBL_year;
    private com.toedter.calendar.JMonthChooser MonthC_month;
    private javax.swing.JTextField TXT_UserName;
    private javax.swing.JTextField TXT_day;
    private javax.swing.JTextField TXT_email;
    private javax.swing.JTextField TXT_name;
    private javax.swing.JPasswordField TXT_password;
    private javax.swing.JTextField TXT_phone;
    private javax.swing.JTextField TXT_surname;
    private com.toedter.calendar.JYearChooser YearC_year;
    private com.toedter.calendar.JCalendar jCalendar1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    // End of variables declaration//GEN-END:variables

    public void limpiar(){
        TXT_name.setText("");
        TXT_UserName.setText("");
        TXT_email.setText("");
        TXT_password.setText("");
        TXT_UserName.setText("");
    }
    
    public SessionNow getConexion(Persona usuario){
        try {
            int pto = 8888;
            String dst = "127.0.0.1";
            Socket cliente = new Socket(dst, pto);
            System.out.println("Conexión establecida...\nProduciendo Objeto...\n");
            System.out.println("|---------------------------------------|");
            System.out.println("Se enviara OBJETO USUARIO con los datos");
            System.out.println("Name:\t" + usuario.getName()
                            + "\nSurname:\t" + usuario.getSurname()
                            + "\nPhone:\t" + usuario.getPhone()
                            + "\nEmail(en miles):\t" + usuario.getEmail()
                            + "\nBirthday:\t"+usuario.getBirthday());
            System.out.println("|---------------------------------------|");
            ObjectOutputStream oos = new ObjectOutputStream(cliente.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(cliente.getInputStream());

            oos.writeObject(usuario);
            oos.flush();

            System.out.println("Objeto enviado...");               
            System.out.println("Objeto enviado... preparando para recibir un objeto");
            SessionNow session = (SessionNow) ois.readObject();
            System.out.println("Datos de sesión:");
            System.out.println("User:\t"+session.getUser_now());
            System.out.println("Email:\t"+session.getEmail_now());
            System.out.println("|---------------------------------------|");
            ois.close();
            oos.close();
            cliente.close();
            return session;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
