
package View;

import javax.swing.*;
import java.awt.*;


public class dashboard_view extends javax.swing.JFrame {

   
    public dashboard_view() {
        initComponents();
        setLocationRelativeTo(null);

        
        JLabel background = new JLabel(new ImageIcon(getClass().getResource("/images/blure02.png")));
        setContentPane(background);
        background.setLayout(new BorderLayout());

        
        jPanel1.setOpaque(false);
        background.add(jPanel1, BorderLayout.CENTER);
    }

    
    @SuppressWarnings("unchecked")
    
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnitems = new javax.swing.JButton();
        btnorderdetails = new javax.swing.JButton();
        btnreports = new javax.swing.JButton();

        jLabel2.setText("jLabel2");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); 
        jLabel1.setText("CLOTHYFY STORE");

        btnitems.setFont(new java.awt.Font("Segoe UI", 3, 18)); 
        btnitems.setText("ITEMS");
        btnitems.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnitemsActionPerformed(evt);
            }
        });

        btnorderdetails.setFont(new java.awt.Font("Segoe UI", 3, 18));
        btnorderdetails.setText("ORDER DETAILS");
        btnorderdetails.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnorderdetailsActionPerformed(evt);
            }
        });

        btnreports.setFont(new java.awt.Font("Segoe UI", 3, 18)); 
        btnreports.setText("REPORTS");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(102, 102, 102)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnreports, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnitems, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(176, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(126, 126, 126))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(btnorderdetails)
                        .addGap(97, 97, 97))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60)
                .addComponent(btnitems, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                .addComponent(btnorderdetails, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addComponent(btnreports, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(93, 93, 93))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }

    private void btnitemsActionPerformed(java.awt.event.ActionEvent evt) {
       
    }

    private void btnorderdetailsActionPerformed(java.awt.event.ActionEvent evt) {
        
    }

    private void btnreportsActionPerformed(java.awt.event.ActionEvent evt) {                                           
        try {
            Connection con = DriverManager.getConnection(URL,USER,PASSWORD);
            String reportPath = "C:\\Users\\ACER\\JaspersoftWorkspace\\MyReports\\Orders.jrxml";
            
            JasperReport jr = JasperCompileManager.compileReport(reportPath);
            JasperPrint jp = JasperFillManager.fillReport(jr,null,con);
            JasperViewer.viewReport(jp,false);
            
        } catch (Exception ex) {
            
            Logger.getLogger(dashboard_view.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        
    }
    public static void main(String args[]) {
        
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(dashboard_view.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(dashboard_view.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(dashboard_view.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(dashboard_view.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new dashboard_view().setVisible(true);
            }
        });
    }

    public void addItemsListener(java.awt.event.ActionListener listener) {
        btnitems.addActionListener(listener);
    }

    public void addOrderDetailsListener(java.awt.event.ActionListener listener) {
        btnorderdetails.addActionListener(listener);
    }

    public void addReportsListener(java.awt.event.ActionListener listener) {
        btnreports.addActionListener(listener);
    }


    
    private javax.swing.JButton btnitems;
    private javax.swing.JButton btnorderdetails;
    private javax.swing.JButton btnreports;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    
}
