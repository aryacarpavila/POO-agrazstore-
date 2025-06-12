
import java.awt.Color;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JButton;



public class SeleccionarTipoUsuario extends javax.swing.JFrame {

    public SeleccionarTipoUsuario() {
        
        initComponents();
        this.getContentPane().setBackground(java.awt.Color.WHITE);
        setIconImage(new ImageIcon(getClass().getResource("/iconos/icon_1.png")).getImage());
        setResizable(false);  // hace que no se pueda agrandar o achicar
        this.setLocationRelativeTo(null);
        AnimacionBotones();
        personalizarComponentes(); 
        configurarBotones(); 
    }
    
    private void AnimacionBotones() {
        JButton nuevoCliente = new BotonAnimado("CLIENTE");
        JButton nuevoEmpleado = new BotonAnimado("EMPLEADO");

        nuevoCliente.setForeground(Color.WHITE);
        nuevoEmpleado.setForeground(Color.WHITE);
        nuevoCliente.setFont(new Font("Arial", Font.BOLD, 20));
        nuevoEmpleado.setFont(new Font("Arial", Font.BOLD, 20));

        nuevoCliente.setBounds(RegistrarCliente.getBounds());
        nuevoEmpleado.setBounds(RegistrarEmpleado.getBounds());

        nuevoCliente.addActionListener((java.awt.event.ActionEvent evt) -> {
            new RegistrarCliente().setVisible(true);
            dispose();
        });

        nuevoEmpleado.addActionListener((java.awt.event.ActionEvent evt) -> {
            new RegistrarEmpleado().setVisible(true);
            dispose();
        });

        getContentPane().remove(RegistrarCliente);
        getContentPane().remove(RegistrarEmpleado);
        getContentPane().add(nuevoCliente);
        getContentPane().add(nuevoEmpleado);
        getContentPane().revalidate();
        getContentPane().repaint();
    }

    
    private void configurarBotones() {
        VolverAlMenu.addActionListener((java.awt.event.ActionEvent evt) -> {
            new Loggin().setVisible(true); // Abre la ventana principal
            dispose(); // Cierra esta ventana
        });          
    }

    
    
private void personalizarComponentes() {
    
    VolverAlMenu.setBackground(new java.awt.Color(255, 255, 255)); 
    VolverAlMenu.setBorderPainted(false); // Sin borde
    VolverAlMenu.setContentAreaFilled(false); // Sin relleno
    VolverAlMenu.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR)); // Cursor tipo mano
}
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        TextoGenerico = new javax.swing.JLabel();
        RegistrarCliente = new javax.swing.JButton();
        VolverAlMenu = new javax.swing.JButton();
        RegistrarEmpleado = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        TextoGenerico.setFont(new java.awt.Font("Arial", 1, 40)); // NOI18N
        TextoGenerico.setText("Elige tu tipo de usuario");

        RegistrarCliente.setBackground(new java.awt.Color(0, 0, 0));
        RegistrarCliente.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        RegistrarCliente.setForeground(new java.awt.Color(255, 255, 255));
        RegistrarCliente.setText("Cliente");

        VolverAlMenu.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        VolverAlMenu.setText("Volver");
        VolverAlMenu.setBorder(null);
        VolverAlMenu.setBorderPainted(false);

        RegistrarEmpleado.setBackground(new java.awt.Color(0, 0, 0));
        RegistrarEmpleado.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        RegistrarEmpleado.setForeground(new java.awt.Color(255, 255, 255));
        RegistrarEmpleado.setText("Empleado");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(199, 199, 199)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(55, 55, 55)
                                .addComponent(RegistrarCliente)
                                .addGap(43, 43, 43)
                                .addComponent(RegistrarEmpleado))
                            .addComponent(TextoGenerico)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(375, 375, 375)
                        .addComponent(VolverAlMenu)))
                .addContainerGap(216, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(145, 145, 145)
                .addComponent(TextoGenerico)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(RegistrarCliente)
                    .addComponent(RegistrarEmpleado))
                .addGap(85, 85, 85)
                .addComponent(VolverAlMenu)
                .addContainerGap(100, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


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
            java.util.logging.Logger.getLogger(SeleccionarTipoUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SeleccionarTipoUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SeleccionarTipoUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SeleccionarTipoUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new SeleccionarTipoUsuario().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton RegistrarCliente;
    private javax.swing.JButton RegistrarEmpleado;
    private javax.swing.JLabel TextoGenerico;
    private javax.swing.JButton VolverAlMenu;
    // End of variables declaration//GEN-END:variables
}
