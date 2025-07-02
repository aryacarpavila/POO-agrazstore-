package vista;

import controlador.Validacion;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;
import modelo.Usuario;

public class TransaccionVisual extends JFrame {

    private ImageIcon background;

    public TransaccionVisual(Usuario cliente, double total,  Consumer<String> onConfirm, Runnable onCancel) {
        setIconImage(new ImageIcon(getClass().getResource("/iconos/icon.png")).getImage());
        setSize(800, 500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        background = new ImageIcon(getClass().getResource("/iconos/background_transaccion.png"));
        JPanel background_transaccion = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (background != null && background.getImage() != null) {
                    g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        background_transaccion.setLayout(new BorderLayout()); 
        setContentPane(background_transaccion);

        // PANEL PRINCIPAL
        JPanel panel_contenido = new JPanel(new GridBagLayout());
        panel_contenido.setOpaque(false); 
        panel_contenido.setBorder(BorderFactory.createEmptyBorder(35, 20, 20, 20)); 

        GridBagConstraints auxiliar = new GridBagConstraints();
        auxiliar.insets = new Insets(2, 5, 2, 5); 
        auxiliar.gridx = 0;
        auxiliar.gridy = 0; 
        auxiliar.anchor = GridBagConstraints.CENTER; 
        auxiliar.fill = GridBagConstraints.HORIZONTAL;
        auxiliar.weightx = 1.0; 
        auxiliar.gridwidth = 1; 

        JLabel metodo_pago = titulo("Método de Pago");    
        panel_contenido.add(metodo_pago, auxiliar);
        
        JRadioButton tarjeta = new JRadioButton("Tarjeta");
        JRadioButton transferencia = new JRadioButton("Transferencia");
        boton_negritas(tarjeta);
        boton_negritas(transferencia);
        tarjeta.setOpaque(false);
        transferencia.setOpaque(false);

        ButtonGroup tipo_pago = new ButtonGroup();
        tipo_pago.add(tarjeta);
        tipo_pago.add(transferencia);
        JPanel panelRadio = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        panelRadio.setOpaque(false); 
        panelRadio.add(tarjeta);
        panelRadio.add(transferencia);
        auxiliar.gridy++;
        panel_contenido.add(panelRadio, auxiliar); 
        auxiliar.gridy++; 
        panel_contenido.add(Box.createVerticalStrut(10), auxiliar);
        auxiliar.gridy++; 
        panel_contenido.add(titulo("Tipo de cuenta"), auxiliar);

        JComboBox<String> tipo_de_cuenta = new JComboBox<>(new String[]{
                "Cta. de Ahorros",
                "Cta. Corriente",
                "Cta. Internacional"
        });
        combo(tipo_de_cuenta);
        auxiliar.gridy++; 
        panel_contenido.add(tipo_de_cuenta, auxiliar);

        auxiliar.gridy++; 
        panel_contenido.add(texto("Número de cuenta"), auxiliar);
        JTextField numero_de_cuenta = new JTextField();
        negritas(numero_de_cuenta);
        auxiliar.gridy++; 
        panel_contenido.add(numero_de_cuenta, auxiliar);
        
        auxiliar.gridy++; 
        panel_contenido.add(Box.createVerticalStrut(10), auxiliar);

        auxiliar.gridy++; 
        panel_contenido.add(titulo("Tipo de documento"), auxiliar);

        JComboBox<String> tipo_documento = new JComboBox<>(new String[]{
                "Cédula",
                "Pasaporte",
                "RIF"
        });
        combo(tipo_documento);
        auxiliar.gridy++; 
        panel_contenido.add(tipo_documento, auxiliar);

        auxiliar.gridy++;
        panel_contenido.add(texto("Número de documento"), auxiliar);
        JTextField numero_de_documento = new JTextField();
        negritas(numero_de_documento);
        auxiliar.gridy++; 
        panel_contenido.add(numero_de_documento, auxiliar);
        auxiliar.gridy++; 
        auxiliar.weighty = 1.0; 
        panel_contenido.add(Box.createVerticalGlue(), auxiliar);

        // BOTONES DE CONFIRMAR PAGO Y CANCELAR
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        panelBotones.setOpaque(false); 

        JButton boton_confirmar = new JButton("Confirmar Pago");
        JButton boton_cancelar = new JButton("Cancelar");

        boton_apariencia(boton_confirmar, Color.BLACK, Color.WHITE, true);
        boton_apariencia(boton_cancelar, Color.BLACK, Color.WHITE, false);

        // CONFIRMAR        
        boton_confirmar.addActionListener(e -> {
    List<String> errores = Validacion.validarDatosBancarios(
        numero_de_cuenta.getText(),
        numero_de_documento.getText(),
        (String) tipo_documento.getSelectedItem() 
    );

    if (!errores.isEmpty()) {
        Validacion.mostrarErrores(this, errores);
        return;
    }

    String metodoSeleccionado = tarjeta.isSelected() ? "Tarjeta" :
                                transferencia.isSelected() ? "Transferencia" : "Desconocido";

    onConfirm.accept(metodoSeleccionado);
    dispose(); 
});


        // CANCELAR
        boton_cancelar.addActionListener(e -> {
            onCancel.run();
            dispose(); 
        });

        panelBotones.add(boton_cancelar);
        panelBotones.add(boton_confirmar);

        JPanel panelBotonesConMargen = new JPanel(new BorderLayout());
        panelBotonesConMargen.setOpaque(false); 
        panelBotonesConMargen.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        panelBotonesConMargen.add(panelBotones, BorderLayout.CENTER);

        background_transaccion.add(panel_contenido, BorderLayout.CENTER);
        background_transaccion.add(panelBotonesConMargen, BorderLayout.SOUTH);
    }

    
    private JLabel titulo(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Open Sans", Font.BOLD, 16));
        label.setHorizontalAlignment(SwingConstants.LEFT);
        label.setOpaque(false);
        return label;
    }

    private JLabel texto(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Open Sans", Font.PLAIN, 14));
        label.setHorizontalAlignment(SwingConstants.LEFT);
        label.setOpaque(false); 
        return label;
    }

    private void combo(JComboBox<String> combo) {
        combo.setFont(new Font("Open Sans", Font.PLAIN, 14));
        combo.setBackground(Color.WHITE);
        combo.setPreferredSize(new Dimension(280, 35));
        combo.setMaximumSize(new Dimension(350, 35));
    }

    private void negritas(JTextField campo) {
        campo.setFont(new Font("Open Sans", Font.PLAIN, 14));
        campo.setPreferredSize(new Dimension(280, 35));
        campo.setMaximumSize(new Dimension(350, 35));
    }

    private void boton_negritas(JRadioButton radio) {
        radio.setFont(new Font("Open Sans", Font.PLAIN, 14));
        radio.setBackground(Color.WHITE); 
        radio.setOpaque(false);
    }

    private void boton_apariencia(JButton boton, Color fondo, Color texto, boolean negrita) {
        boton.setFont(new Font("Open Sans", negrita ? Font.BOLD : Font.PLAIN, 14));
        boton.setBackground(fondo);
        boton.setForeground(texto);
        boton.setFocusPainted(false);
        boton.setPreferredSize(new Dimension(160, 40));
    }
}