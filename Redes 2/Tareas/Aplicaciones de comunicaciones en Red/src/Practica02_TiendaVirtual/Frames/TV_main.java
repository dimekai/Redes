package Practica02_TiendaVirtual.Frames;

import Practica02_TiendaVirtual.Producto;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/*@author kaimorts*/
public class TV_main extends javax.swing.JFrame {
    ArrayList<Producto> set = new ArrayList<Producto>();
    
    public TV_main() {
        initComponents();
        fillProducts();
        initCarrousel();
        setLocationRelativeTo(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PNL_images = new javax.swing.JPanel();
        LBL_Images = new javax.swing.JLabel();
        PNL_Eventos = new javax.swing.JPanel();
        BTN_details = new javax.swing.JToggleButton();
        LBL_details = new javax.swing.JLabel();
        LBL_buy = new javax.swing.JLabel();
        BTN_buy = new javax.swing.JToggleButton();
        LBL_imageTienda = new javax.swing.JLabel();
        LBL_MarKait = new javax.swing.JLabel();
        LBL_ESCOM = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        LBL_exit = new javax.swing.JLabel();
        PNL_description = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TBL_Productos = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        PNL_images.setBackground(new java.awt.Color(255, 199, 51));

        LBL_Images.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LBL_Images.setText("C");

        javax.swing.GroupLayout PNL_imagesLayout = new javax.swing.GroupLayout(PNL_images);
        PNL_images.setLayout(PNL_imagesLayout);
        PNL_imagesLayout.setHorizontalGroup(
            PNL_imagesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PNL_imagesLayout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addComponent(LBL_Images, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PNL_imagesLayout.setVerticalGroup(
            PNL_imagesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PNL_imagesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(LBL_Images, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        PNL_Eventos.setBackground(new java.awt.Color(255, 150, 25));

        BTN_details.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Practica02_TiendaVirtual/icons/More Details_25px.png"))); // NOI18N
        BTN_details.setBorder(null);

        LBL_details.setFont(new java.awt.Font("Century Gothic", 1, 11)); // NOI18N
        LBL_details.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LBL_details.setText("Details");

        LBL_buy.setFont(new java.awt.Font("Century Gothic", 1, 11)); // NOI18N
        LBL_buy.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LBL_buy.setText("Buy");

        BTN_buy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Practica02_TiendaVirtual/icons/Buy_25px.png"))); // NOI18N
        BTN_buy.setBorder(null);
        BTN_buy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTN_buyActionPerformed(evt);
            }
        });

        LBL_imageTienda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Practica02_TiendaVirtual/icons/Walter White_96px.png"))); // NOI18N

        LBL_MarKait.setFont(new java.awt.Font("Century Gothic", 2, 18)); // NOI18N
        LBL_MarKait.setForeground(new java.awt.Color(255, 255, 255));
        LBL_MarKait.setText("MarKAIt ");

        LBL_ESCOM.setText("ESCOM");

        LBL_exit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Practica02_TiendaVirtual/icons/Delete_15px.png"))); // NOI18N
        LBL_exit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LBL_exitMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout PNL_EventosLayout = new javax.swing.GroupLayout(PNL_Eventos);
        PNL_Eventos.setLayout(PNL_EventosLayout);
        PNL_EventosLayout.setHorizontalGroup(
            PNL_EventosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PNL_EventosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PNL_EventosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PNL_EventosLayout.createSequentialGroup()
                        .addGroup(PNL_EventosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(BTN_buy, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE)
                            .addComponent(LBL_buy, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(PNL_EventosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PNL_EventosLayout.createSequentialGroup()
                                .addComponent(BTN_details, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PNL_EventosLayout.createSequentialGroup()
                                .addComponent(LBL_details, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(24, 24, 24))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PNL_EventosLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(LBL_ESCOM))
                    .addGroup(PNL_EventosLayout.createSequentialGroup()
                        .addGroup(PNL_EventosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator1)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PNL_EventosLayout.createSequentialGroup()
                                .addComponent(LBL_MarKait, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(LBL_imageTienda)))
                        .addContainerGap())))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PNL_EventosLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(LBL_exit)
                .addContainerGap())
        );
        PNL_EventosLayout.setVerticalGroup(
            PNL_EventosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PNL_EventosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(LBL_exit)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(PNL_EventosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LBL_imageTienda, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(LBL_MarKait, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(54, 54, 54)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60)
                .addGroup(PNL_EventosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(BTN_buy, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BTN_details, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PNL_EventosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LBL_buy)
                    .addComponent(LBL_details))
                .addGap(81, 81, 81)
                .addComponent(LBL_ESCOM))
        );

        PNL_description.setBackground(new java.awt.Color(255, 255, 255));

        TBL_Productos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Nombre", "Precio", "Existencia", "Descuento"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(TBL_Productos);

        javax.swing.GroupLayout PNL_descriptionLayout = new javax.swing.GroupLayout(PNL_description);
        PNL_description.setLayout(PNL_descriptionLayout);
        PNL_descriptionLayout.setHorizontalGroup(
            PNL_descriptionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 410, Short.MAX_VALUE)
        );
        PNL_descriptionLayout.setVerticalGroup(
            PNL_descriptionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(PNL_description, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(PNL_images, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PNL_Eventos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(PNL_description, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PNL_images, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(PNL_Eventos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void LBL_exitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LBL_exitMouseClicked
        System.exit(0);
    }//GEN-LAST:event_LBL_exitMouseClicked

    private void BTN_buyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTN_buyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BTN_buyActionPerformed

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
            java.util.logging.Logger.getLogger(TV_main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TV_main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TV_main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TV_main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TV_main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton BTN_buy;
    private javax.swing.JToggleButton BTN_details;
    private javax.swing.JLabel LBL_ESCOM;
    private javax.swing.JLabel LBL_Images;
    private javax.swing.JLabel LBL_MarKait;
    private javax.swing.JLabel LBL_buy;
    private javax.swing.JLabel LBL_details;
    private javax.swing.JLabel LBL_exit;
    private javax.swing.JLabel LBL_imageTienda;
    private javax.swing.JPanel PNL_Eventos;
    private javax.swing.JPanel PNL_description;
    private javax.swing.JPanel PNL_images;
    private javax.swing.JTable TBL_Productos;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    // End of variables declaration//GEN-END:variables

    public final void fillProducts(){
        Producto A = new Producto("Marihuana", "Sativa: 75%\nIndica:25%", 550,7,25);
        Producto B = new Producto("Cocaine","From Tepito Rules",1000,9,10);
        Producto C = new Producto("LSD","You'll fly very chill",200,20,15);
        set.add(A);
        set.add(B);
        set.add(C);
        
        showProductos();
    }
    
    public final void showProductos(){
        String matP[][] = new String[set.size()][4];
        for (int i = 0; i < set.size(); i++) {
            matP[i][0] = set.get(i).getNombre();
            matP[i][1] = set.get(i).getPrecio()+"";
            matP[i][2] = set.get(i).getExistencia()+"";
            matP[i][3] = set.get(i).getDescuento()+"";
        }
        TBL_Productos.setModel(new javax.swing.table.DefaultTableModel(matP,
                new String[]{
                    "Nombre","Precio","Existencia","Descuento"
                }
        ));
    }
    
    public void initCarrousel(){
        String PATH = "/Practica02_TiendaVirtual/icons/";
        int velocidad = 3;
        Timer timer;
        TimerTask tarea;
        int velmil = velocidad*1000;
        tarea = new TimerTask(){
            int contador = 0;
            
            @Override
            public void run() {
                Icon icono;
                switch(contador)
                {
                    case 0: 
                        contador = 1;
                        icono = new ImageIcon(getClass().getResource(PATH+"a.png"));
                        LBL_Images.setIcon(icono);
                        break;
                    case 1:
                        contador = 2;
                        icono = new ImageIcon(getClass().getResource(PATH+"b.png"));
                        LBL_Images.setIcon(icono);
                        break;
                    case 2: 
                        contador = 3;
                        icono = new ImageIcon(getClass().getResource(PATH+"c.png"));
                        LBL_Images.setIcon(icono);
                        break;
                    case 3: 
                        contador = 4;
                        icono = new ImageIcon(getClass().getResource(PATH+"d.png"));
                        LBL_Images.setIcon(icono);
                        break;
                    case 4: 
                        contador = 5;
                        icono = new ImageIcon(getClass().getResource(PATH+"e.png"));
                        LBL_Images.setIcon(icono);
                        break;
                    case 5:
                        contador = 0;
                        icono = new ImageIcon(getClass().getResource(PATH+"f.png"));
                        LBL_Images.setIcon(icono);
                        break;
                }
            }
            
        };
        
        timer = new Timer();
        timer.scheduleAtFixedRate(tarea, velmil, velmil);
    }
}
