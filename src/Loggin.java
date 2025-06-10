
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class Loggin extends javax.swing.JFrame {

    private ImageIcon backgroundIcon;
    
    public Loggin() {
        // Cargar imagen de fondo antes del initComponents
        backgroundIcon = new ImageIcon(getClass().getResource("/iconos/background_1.png")); // Asegúrate de esta ruta

        // Establecer panel de fondo personalizado
        setContentPane(new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundIcon.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        });
        initComponents();
        AnimacionBotones();
        
        setIconImage(new ImageIcon(getClass().getResource("/iconos/icon_1.png")).getImage());
        setResizable(false);  // hace que no se pueda agrandar o achicar
        this.setLocationRelativeTo(null); // centrado

        // Agrega el ActionListener para el botón
        Ingresar_Bottom.addActionListener(evt -> validarLogin());
    }

    private void validarLogin() {
            JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectas. Intenta de nuevo.", "Error", JOptionPane.ERROR_MESSAGE);
        
    }

    private void AnimacionBotones() {
    JButton nuevoIngresar = new BotonAnimado("INGRESAR");
    JButton nuevoRegistrar = new BotonAnimado("REGISTRARSE");

    // Cambiar el tamaño del texto después de la creación
    nuevoIngresar.setForeground(Color.WHITE);
    nuevoRegistrar.setForeground(Color.WHITE);
    nuevoIngresar.setFont(new Font("Arial", Font.BOLD, 8));
    nuevoRegistrar.setFont(new Font("Arial", Font.BOLD, 8));

    // Copiar propiedades y eventos de los originales
    nuevoIngresar.setBounds(Ingresar_Bottom.getBounds());
    nuevoRegistrar.setBounds(Registrar_Bottom.getBounds());

    nuevoIngresar.addActionListener(evt -> validarLogin());
    nuevoRegistrar.addActionListener(evt -> Registrar_BottomActionPerformed(evt));

    getContentPane().remove(Ingresar_Bottom);
    getContentPane().remove(Registrar_Bottom);
    
    getContentPane().add(nuevoIngresar);
    getContentPane().add(nuevoRegistrar);


    getContentPane().revalidate();
    getContentPane().repaint();
    }


    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Ingresa = new javax.swing.JLabel();
        textogenerico = new javax.swing.JLabel();
        Usuario = new javax.swing.JLabel();
        IngresarPassword = new javax.swing.JTextField();
        IngresarUsuario = new javax.swing.JTextField();
        Password = new javax.swing.JLabel();
        Ingresar_Bottom = new javax.swing.JButton();
        Registrar_Bottom = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setForeground(java.awt.Color.white);

        Ingresa.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        Ingresa.setText("Ingresa");

        textogenerico.setFont(new java.awt.Font("Arial", 1, 8)); // NOI18N
        textogenerico.setText("inicia sesión para continuar");

        Usuario.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        Usuario.setText("Usuario");

        IngresarPassword.setBackground(new java.awt.Color(0, 0, 0));
        IngresarPassword.setForeground(new java.awt.Color(255, 255, 255));

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(486, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Ingresa)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(textogenerico))))
                    .addComponent(Usuario)
                    .addComponent(IngresarPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Password)
                    .addComponent(IngresarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(Ingresar_Bottom))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(Registrar_Bottom)))
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
                .addComponent(IngresarPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Password)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(IngresarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Ingresar_Bottom)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Registrar_Bottom)
                .addContainerGap(115, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void Registrar_BottomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Registrar_BottomActionPerformed
        this.dispose();
        // Abre la ventana de selección de usuario
        new SeleccionarTipoUsuario().setVisible(true);
    }//GEN-LAST:event_Registrar_BottomActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new Loggin().setVisible(true));
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Ingresa;
    private javax.swing.JTextField IngresarPassword;
    private javax.swing.JTextField IngresarUsuario;
    private javax.swing.JButton Ingresar_Bottom;
    private javax.swing.JLabel Password;
    private javax.swing.JButton Registrar_Bottom;
    private javax.swing.JLabel Usuario;
    private javax.swing.JLabel textogenerico;
    // End of variables declaration//GEN-END:variables
}
