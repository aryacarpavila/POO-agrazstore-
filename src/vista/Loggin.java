package vista;

import otros.BotonAnimado;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import otros.TextoGuia;
import principal.TiendaAgraz_empleado;
import principal.TiendaAgraz_cliente;


public class Loggin extends javax.swing.JFrame {

    private ImageIcon backgroundIcon;
    
    public Loggin() {
        backgroundIcon = new ImageIcon(getClass().getResource("/iconos/background_1.png")); 
        setContentPane(new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                    if (backgroundIcon != null) {
                        g.drawImage(backgroundIcon.getImage(), 0, 0, getWidth(), getHeight(), this);
                    }
                }
            });
        setIconImage(new ImageIcon(getClass().getResource("/iconos/icon.png")).getImage());
        initComponents();
        TextoGuia.aplicarTextoGuia(IngresarUsuario, "Ingresa tu usuario");
        TextoGuia.aplicarTextoGuia(ingresarPassword, "Ingresa tu contraseña");
        AnimacionBotones();
        setResizable(false);
        setLocationRelativeTo(null);
        Ingresar_Bottom.addActionListener(evt -> validarLogin());
    }

        private void validarLogin() {
        String usuario = IngresarUsuario.getText();
        String contraseña = String.valueOf(ingresarPassword.getPassword());

        if (usuario.equals("cliente") && contraseña.equals("1234")) {
            new TiendaAgraz_cliente().setVisible(true);
            this.dispose(); 
        } 
        else if (usuario.equals("empleado") && contraseña.equals("1234")) {
            new TiendaAgraz_empleado().setVisible(true);
            this.dispose(); 
        } else {
            JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectas. Intenta de nuevo.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        private void AnimacionBotones() {
            JButton nuevo_ingresar = new BotonAnimado("INGRESAR");
            JButton nuevo_registrar = new BotonAnimado("REGISTRARSE");

            nuevo_ingresar.setForeground(Color.WHITE);
            nuevo_registrar.setForeground(Color.WHITE);
            nuevo_ingresar.setFont(new Font("Arial", Font.BOLD, 8));
            nuevo_registrar.setFont(new Font("Arial", Font.BOLD, 8));

            nuevo_ingresar.setBounds(Ingresar_Bottom.getBounds());
            nuevo_registrar.setBounds(Registrar_Bottom.getBounds());

            nuevo_ingresar.addActionListener(evt -> validarLogin());
            nuevo_registrar.addActionListener(evt -> Registrar_BottomActionPerformed(evt));

            getContentPane().remove(Ingresar_Bottom);
            getContentPane().remove(Registrar_Bottom);
            getContentPane().add(nuevo_ingresar);
            getContentPane().add(nuevo_registrar);
            getContentPane().revalidate();
            getContentPane().repaint();
        }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Ingresa = new javax.swing.JLabel();
        textogenerico = new javax.swing.JLabel();
        Usuario = new javax.swing.JLabel();
        IngresarUsuario = new javax.swing.JTextField();
        Password = new javax.swing.JLabel();
        Ingresar_Bottom = new javax.swing.JButton();
        Registrar_Bottom = new javax.swing.JButton();
        ingresarPassword = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setForeground(java.awt.Color.white);

        Ingresa.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        Ingresa.setText("Ingresa");

        textogenerico.setFont(new java.awt.Font("Arial", 1, 8)); // NOI18N
        textogenerico.setText("inicia sesión para continuar");

        Usuario.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        Usuario.setText("Usuario");

        IngresarUsuario.setBackground(new java.awt.Color(0, 0, 0));
        IngresarUsuario.setForeground(new java.awt.Color(255, 255, 255));

        Password.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        Password.setText("Contraseña");

        Ingresar_Bottom.setBackground(new java.awt.Color(0, 0, 0));
        Ingresar_Bottom.setFont(new java.awt.Font("Arial", 1, 10)); // NOI18N
        Ingresar_Bottom.setForeground(new java.awt.Color(255, 255, 255));
        Ingresar_Bottom.setText("INGRESAR");

        Registrar_Bottom.setBackground(new java.awt.Color(0, 0, 0));
        Registrar_Bottom.setFont(new java.awt.Font("Arial", 1, 10)); // NOI18N
        Registrar_Bottom.setForeground(new java.awt.Color(255, 255, 255));
        Registrar_Bottom.setText("REGISTRARSE");
        Registrar_Bottom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Registrar_BottomActionPerformed(evt);
            }
        });

        ingresarPassword.setBackground(new java.awt.Color(0, 0, 0));
        ingresarPassword.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        ingresarPassword.setForeground(new java.awt.Color(255, 255, 255));
        ingresarPassword.setText("");
        ingresarPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ingresarPasswordActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(486, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(IngresarUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Ingresa)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(textogenerico))))
                    .addComponent(Usuario)
                    .addComponent(Password)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(Ingresar_Bottom))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(Registrar_Bottom))
                    .addComponent(ingresarPassword))
                .addGap(159, 159, 159))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(109, 109, 109)
                .addComponent(Ingresa)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textogenerico, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(Usuario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(IngresarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(Password)
                .addGap(4, 4, 4)
                .addComponent(ingresarPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(Ingresar_Bottom)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Registrar_Bottom)
                .addContainerGap(107, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void Registrar_BottomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Registrar_BottomActionPerformed
        this.dispose();
        // Abre la ventana de selección de usuario
        new SeleccionarTipoUsuario().setVisible(true);
    }//GEN-LAST:event_Registrar_BottomActionPerformed

    private void ingresarPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ingresarPasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ingresarPasswordActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new Loggin().setVisible(true));
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Ingresa;
    private javax.swing.JTextField IngresarUsuario;
    private javax.swing.JButton Ingresar_Bottom;
    private javax.swing.JLabel Password;
    private javax.swing.JButton Registrar_Bottom;
    private javax.swing.JLabel Usuario;
    private javax.swing.JPasswordField ingresarPassword;
    private javax.swing.JLabel textogenerico;
    // End of variables declaration//GEN-END:variables
}
