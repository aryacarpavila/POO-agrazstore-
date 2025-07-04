package vista;

import java.awt.Graphics;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import modelo.Empleado;
import modelo.Usuario;
import otros.TextoGuia;


public class RegistrarEmpleado extends javax.swing.JFrame {

    private ImageIcon background;
    
    public RegistrarEmpleado() {
        background = new ImageIcon(getClass().getResource("/iconos/background_empleado.png")); 

        setContentPane(new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        });
        initComponents();
        TextoGuia.aplicarTextoGuia(CorreoEmpleado, "Ingresa tu correo");
        TextoGuia.aplicarTextoGuia(nombreEmpleado, "Ingresa tu nombre");
        TextoGuia.aplicarTextoGuia(usuarioEmpleado, "Ingresa tu usuario");
        TextoGuia.aplicarTextoGuia(IDEmpleado, "Ingresa tu ID de empleado");
        TextoGuia.aplicarTextoGuia(PasswordEmpleado, "Ingresa tu contraseña");
        this.getContentPane().setBackground(java.awt.Color.WHITE);
        setIconImage(new ImageIcon(getClass().getResource("/iconos/icon.png")).getImage());
        setResizable(false);  // hace que no se pueda agrandar o achicar
        this.setLocationRelativeTo(null); // centrado
        personalizarComponentes(); 
        configurarBotones(); 
        llenarFecha();
    }

      
    private void personalizarComponentes() {
        VolverAlMenu.setBackground(new java.awt.Color(255, 255, 255)); // Fondo blanco
        VolverAlMenu.setBorderPainted(false); // Sin borde
        VolverAlMenu.setContentAreaFilled(false); // Sin relleno
        VolverAlMenu.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR)); 
    }

    private void configurarBotones() {
        VolverAlMenu.addActionListener((java.awt.event.ActionEvent evt) -> {
            new Loggin().setVisible(true); 
            dispose(); 
        });
    }   

    private void llenarFecha(){
        Dia.removeAllItems();
        Mes.removeAllItems();
        Year.removeAllItems();
    
         // DIAS
        for (int i=1; i <=31; i++){
               Dia.addItem(String.valueOf(i));
        }
    
        //MESES
        String[] meses = { "Enero" , "Febrero" , "Marzo", "Abril", "Mayo","Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre" };
            for (String mes : meses){
                 Mes.addItem(mes);
        }
         
        // AÑOS
        int yearactual = java.time.Year.now().getValue();
        for (int i= yearactual; i   > 1900;i--){
            Year.addItem(String.valueOf(i));
        }
          
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        FechaDeNacimiento = new javax.swing.JLabel();
        nombreEmpleado = new javax.swing.JTextField();
        PasswordEmpleado = new javax.swing.JTextField();
        Mes = new javax.swing.JComboBox<>();
        Password = new javax.swing.JLabel();
        nombre = new javax.swing.JLabel();
        Year = new javax.swing.JComboBox<>();
        RegistrarNuevoEmpleado = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        IDEmpleado = new javax.swing.JTextField();
        Usuario = new javax.swing.JLabel();
        usuarioEmpleado = new javax.swing.JTextField();
        Correo = new javax.swing.JLabel();
        CorreoEmpleado = new javax.swing.JTextField();
        Dia = new javax.swing.JComboBox<>();
        VolverAlMenu = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        FechaDeNacimiento.setFont(new java.awt.Font("Arial Black", 1, 12)); // NOI18N
        FechaDeNacimiento.setText("Fecha de Nacimiento");

        nombreEmpleado.setBackground(new java.awt.Color(0, 0, 0));
        nombreEmpleado.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        nombreEmpleado.setForeground(new java.awt.Color(255, 255, 255));

        PasswordEmpleado.setBackground(new java.awt.Color(0, 0, 0));
        PasswordEmpleado.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        PasswordEmpleado.setForeground(new java.awt.Color(255, 255, 255));

        Mes.setBackground(new java.awt.Color(0, 0, 0));
        Mes.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        Mes.setForeground(new java.awt.Color(255, 255, 255));
        Mes.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        Mes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MesActionPerformed(evt);
            }
        });

        Password.setFont(new java.awt.Font("Arial Black", 1, 12)); // NOI18N
        Password.setText("Contraseña");

        nombre.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        nombre.setText("Nombre");

        Year.setBackground(new java.awt.Color(0, 0, 0));
        Year.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        Year.setForeground(new java.awt.Color(255, 255, 255));
        Year.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        Year.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                YearActionPerformed(evt);
            }
        });

        RegistrarNuevoEmpleado.setBackground(new java.awt.Color(0, 0, 0));
        RegistrarNuevoEmpleado.setFont(new java.awt.Font("Arial Black", 1, 12)); // NOI18N
        RegistrarNuevoEmpleado.setForeground(new java.awt.Color(255, 255, 255));
        RegistrarNuevoEmpleado.setText("Registrarme");
        RegistrarNuevoEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RegistrarNuevoEmpleadoActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Arial Black", 1, 12)); // NOI18N
        jLabel2.setText("ID");

        IDEmpleado.setBackground(new java.awt.Color(0, 0, 0));
        IDEmpleado.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        IDEmpleado.setForeground(new java.awt.Color(255, 255, 255));

        Usuario.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        Usuario.setText("Usuario");

        usuarioEmpleado.setBackground(new java.awt.Color(0, 0, 0));
        usuarioEmpleado.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        usuarioEmpleado.setForeground(new java.awt.Color(255, 255, 255));

        Correo.setFont(new java.awt.Font("Arial Black", 1, 12)); // NOI18N
        Correo.setText("Correo");

        CorreoEmpleado.setBackground(new java.awt.Color(0, 0, 0));
        CorreoEmpleado.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        CorreoEmpleado.setForeground(new java.awt.Color(255, 255, 255));

        Dia.setBackground(new java.awt.Color(0, 0, 0));
        Dia.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        Dia.setForeground(new java.awt.Color(255, 255, 255));
        Dia.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        Dia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DiaActionPerformed(evt);
            }
        });

        VolverAlMenu.setFont(new java.awt.Font("Arial Black", 1, 12)); // NOI18N
        VolverAlMenu.setText("Volver");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(323, 323, 323)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(IDEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nombreEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nombre)
                    .addComponent(Usuario)
                    .addComponent(usuarioEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CorreoEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Correo)
                    .addComponent(PasswordEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Password)
                    .addComponent(FechaDeNacimiento)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(Dia, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Mes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Year, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addComponent(VolverAlMenu))
                            .addComponent(RegistrarNuevoEmpleado))))
                .addGap(347, 347, 347))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(110, Short.MAX_VALUE)
                .addComponent(nombre)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nombreEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(IDEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Usuario)
                .addGap(2, 2, 2)
                .addComponent(usuarioEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Correo)
                .addGap(4, 4, 4)
                .addComponent(CorreoEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Password)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PasswordEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(FechaDeNacimiento)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Dia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Mes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Year, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(RegistrarNuevoEmpleado)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(VolverAlMenu)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void DiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DiaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DiaActionPerformed

    private void MesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MesActionPerformed

    private void YearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_YearActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_YearActionPerformed

    private void RegistrarNuevoEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RegistrarNuevoEmpleadoActionPerformed
        String nombre_empleado = nombreEmpleado.getText();
        String correo = CorreoEmpleado.getText();
        String user = usuarioEmpleado.getText();
        String password = PasswordEmpleado.getText();
        String ID = IDEmpleado.getText();
        String dia = (String) Dia.getSelectedItem();
        String mes = (String) Mes.getSelectedItem();
        String year = (String) Year.getSelectedItem();
        String fechaNacimiento = dia + " de " + mes + " de " + year;
        String tipo = "Empleado"; 

        if (nombre_empleado.isEmpty() || correo.isEmpty() || password.isEmpty() ||
            dia.equals("Día") || mes.equals("Mes") || year.equals("Año")) {
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.");
                return;
        }

        int yearNacimiento = Integer.parseInt(year);
        int yearActual = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);

        if (yearActual - yearNacimiento < 18) {
            JOptionPane.showMessageDialog(this, "El empleado debe ser mayor de 18 años.");
                return;
        }

        if (!correo.matches("^[\\w.-]+@[\\w.-]+\\.\\w{2,}$")) {
            JOptionPane.showMessageDialog(this, "Correo electrónico inválido.");
                return;
        }

        if (!password.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$")) {
            JOptionPane.showMessageDialog(this, "La contraseña debe tener al menos 6 caracteres, incluyendo letras y números.");
                return;
        }
        
        if (!ID.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "ID inválido.");
                return;
        }

        // Archivo JSON
        Empleado nuevoEmpleado = new Empleado(tipo, nombre_empleado.toLowerCase(), user, correo, password, ID, fechaNacimiento);

        // Gson para trabajar con JSON
        com.google.gson.Gson gson = new com.google.gson.GsonBuilder().setPrettyPrinting().create();
        java.io.File archivo = new java.io.File("base_de_datos/usuarios.json");
        java.util.List<Usuario> usuarios = new java.util.ArrayList<>();

        // Leer los usuarios existentes (si existe el archivo)
        if (archivo.exists()) {
            try (java.io.FileReader reader = new java.io.FileReader(archivo)) {
                Usuario[] existentes = gson.fromJson(reader, Usuario[].class);
                    if (existentes != null) {
                        usuarios.addAll(Arrays.asList(existentes));
                    }
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(this, "Error al leer usuarios existentes.");
                        }
                    }

            usuarios.add(nuevoEmpleado);

            
        try (java.io.FileWriter writer = new java.io.FileWriter(archivo)) {
            gson.toJson(usuarios, writer);
                JOptionPane.showMessageDialog(this, "Empleado registrado con éxito.");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al guardar el cliente.");
            }
  
    }//GEN-LAST:event_RegistrarNuevoEmpleadoActionPerformed

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
            java.util.logging.Logger.getLogger(RegistrarEmpleado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RegistrarEmpleado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RegistrarEmpleado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RegistrarEmpleado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new RegistrarEmpleado().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Correo;
    private javax.swing.JTextField CorreoEmpleado;
    private javax.swing.JComboBox<String> Dia;
    private javax.swing.JLabel FechaDeNacimiento;
    private javax.swing.JTextField IDEmpleado;
    private javax.swing.JComboBox<String> Mes;
    private javax.swing.JLabel Password;
    private javax.swing.JTextField PasswordEmpleado;
    private javax.swing.JButton RegistrarNuevoEmpleado;
    private javax.swing.JLabel Usuario;
    private javax.swing.JButton VolverAlMenu;
    private javax.swing.JComboBox<String> Year;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel nombre;
    private javax.swing.JTextField nombreEmpleado;
    private javax.swing.JTextField usuarioEmpleado;
    // End of variables declaration//GEN-END:variables
}
