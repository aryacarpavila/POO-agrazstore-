package principal;

import vista.Loggin;
import modelo.Producto;
import modelo.Moneda;
import modelo.Almacenamiento;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import modelo.Transaccion;
import modelo.Usuario;
import otros.ProductoCarrito;
import vista.ResumenPedido;
import vista.TransaccionVisual;

public class TiendaAgraz_cliente extends JFrame {
    private static Usuario cliente;
    private JPanel panel_productos;
    private JTextField campo_busqueda, tasa_dolar;
    private JComboBox<String> combo_categoria, combo_precio;
    private final DefaultListModel<ProductoCarrito> modelo_carrito = new DefaultListModel<>();
    private JPanel panel_carrito_contenido;
    private JLabel total_carrito;
    private double totalCarrito = 0.0;
    private JButton boton_pagar;

    public TiendaAgraz_cliente(Usuario cliente) {
        super("Tienda Agraz - Cliente");
        setIconImage(new ImageIcon(getClass().getResource("/iconos/icon.png")).getImage());
        setSize(1000, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        this.cliente = cliente;

        // PANEL ENCABEZADO 
        JPanel encabezado = new JPanel(new BorderLayout());
        encabezado.setBackground(Color.WHITE);
        encabezado.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        
        // CUADROS DE JOptionPane CON FONDO BLANCO 
        UIManager.put("Panel.background", Color.WHITE);
        UIManager.put("OptionPane.background", Color.WHITE);
        UIManager.put("OptionPane.messageForeground", Color.BLACK);
        UIManager.put("Label.foreground", Color.BLACK);
        UIManager.put("OptionPane.informationIcon", null);
        UIManager.put("OptionPane.errorIcon", null);
        UIManager.put("OptionPane.warningIcon", null);
        UIManager.put("OptionPane.questionIcon", null);
        UIManager.put("Button.background", Color.BLACK);
        UIManager.put("Button.foreground", Color.WHITE);
        UIManager.put("Button.focus", Color.DARK_GRAY);
        UIManager.put("Button.select", Color.DARK_GRAY);

        // DOLAR
        JPanel panelTasa = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelTasa.setBackground(Color.WHITE);
        panelTasa.add(new JLabel("Tasa del Dólar (Bs): "));
        tasa_dolar = new JTextField(String.valueOf(Almacenamiento.tasaDolar), 6);
        tasa_dolar.setEditable(false);
        panelTasa.add(tasa_dolar);
        URL dolar_icon = getClass().getResource("/iconos/dolar.png");
        if (dolar_icon != null) {
            ImageIcon ic = new ImageIcon(dolar_icon);
            panelTasa.add(new JLabel(new ImageIcon(
                ic.getImage().getScaledInstance(30,30,Image.SCALE_SMOOTH)
            )));
        }
        encabezado.add(panelTasa, BorderLayout.WEST);

        // FILTROS
        JPanel filtros = new JPanel(new FlowLayout());
        filtros.setBackground(Color.WHITE);
        combo_categoria = new JComboBox<>(new String[]{"Todas","Zapatos","Ropa"});
        combo_precio    = new JComboBox<>(new String[]{"Todos","< 50","50 - 100","> 100"});
        campo_busqueda  = new JTextField(15);
        JButton boton_filtrar = new JButton("Filtrar");
        boton_filtrar.setBackground(Color.BLACK);
        boton_filtrar.setForeground(Color.WHITE);
        filtros.add(new JLabel("Categoría:")); filtros.add(combo_categoria);
        filtros.add(new JLabel("Precio:"));    filtros.add(combo_precio);
        filtros.add(new JLabel("Buscar:"));    filtros.add(campo_busqueda);
        filtros.add(boton_filtrar);
        encabezado.add(filtros, BorderLayout.CENTER);

        JButton boton_cerrarsesion = new JButton("Cerrar sesión");
        boton_cerrarsesion.setBackground(Color.RED);
        boton_cerrarsesion.setForeground(Color.WHITE);
        boton_cerrarsesion.addActionListener(e -> {
            Almacenamiento.tasaDolar = Double.parseDouble(tasa_dolar.getText());
            new Loggin().setVisible(true);
            dispose();
        });
        encabezado.add(boton_cerrarsesion, BorderLayout.EAST);
        add(encabezado, BorderLayout.NORTH);

      // PANEL CARRITO 
        panel_carrito_contenido = new JPanel();
        panel_carrito_contenido.setLayout(new BoxLayout(panel_carrito_contenido, BoxLayout.Y_AXIS));
        panel_carrito_contenido.setBackground(Color.WHITE);

        JScrollPane scrollCar = new JScrollPane(panel_carrito_contenido);
        scrollCar.setPreferredSize(new Dimension(270, 0)); 
        scrollCar.setBorder(BorderFactory.createEmptyBorder()); 
        scrollCar.getViewport().setBackground(Color.WHITE); 

        JPanel panel_carrito = new JPanel(new BorderLayout());
        panel_carrito.setBorder(BorderFactory.createTitledBorder("🛒 Carrito"));
        JPanel panel_botones_superiores = new JPanel(new BorderLayout());
        panel_botones_superiores.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));

        JButton boton_vaciar = new JButton("Vaciar");
        panel_botones_superiores.add(boton_vaciar, BorderLayout.NORTH);

        panel_carrito.add(panel_botones_superiores, BorderLayout.NORTH);
        panel_carrito.add(scrollCar, BorderLayout.CENTER); 

        // PANEL DE TOTAL CARRITO + PAGAR
        JPanel panel_inferior_carrito = new JPanel(new BorderLayout());
        panel_inferior_carrito.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));

        total_carrito = new JLabel("Total: Bs. 0.00");
        panel_inferior_carrito.add(total_carrito, BorderLayout.WEST);

        boton_pagar = new JButton("PAGAR");
        boton_pagar.setBackground(Color.BLACK);
        boton_pagar.setForeground(Color.WHITE);
        boton_pagar.setEnabled(false);
        panel_inferior_carrito.add(boton_pagar, BorderLayout.EAST);
        panel_carrito.add(panel_inferior_carrito, BorderLayout.SOUTH);

       boton_pagar.addActionListener(e -> {
    if (modelo_carrito.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Carrito vacío", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Convertir a lista
    List<ProductoCarrito> carrito = new ArrayList<>();
    for (int i = 0; i < modelo_carrito.getSize(); i++) {
        carrito.add(modelo_carrito.getElementAt(i));
    }

    // Validar stock
    List<String> erroresStock = new ArrayList<>();
    for (ProductoCarrito pc : carrito) {
        if (pc.getProducto().getStock() < pc.getCantidad()) {
            erroresStock.add(pc.getProducto().getNombre() + " - Stock insuficiente");
        }
    }

    if (!erroresStock.isEmpty()) {
        JOptionPane.showMessageDialog(this, 
            String.join("\n", erroresStock), 
            "Error en stock", 
            JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Mostrar resumen y luego transacción
    ResumenPedido resumen = new ResumenPedido(this, carrito, totalCarrito, cliente, () -> {
        TransaccionVisual pago = new TransaccionVisual(
            cliente,
            totalCarrito,
            (metodoPago) -> {
                try {
                    // Actualizar stocks
                    List<Producto> productosComprados = new ArrayList<>();
                    for (ProductoCarrito pc : carrito) {
                        Producto p = pc.getProducto();
                        p.setStock(p.getStock() - pc.getCantidad());
                        productosComprados.add(p);
                        Almacenamiento.actualizarProducto(p);
                    }

                    // Crear y guardar transacción
                    Transaccion t = new Transaccion(LocalDate.now(), metodoPago, productosComprados, totalCarrito, cliente);
                    Transaccion.transaccion_guardada(t, "base_de_datos/transacciones.json");

                
                    JOptionPane.showMessageDialog(this,
                        "✅ Compra completada!\n" +
                        "Método: " + metodoPago + "\n" +
                        "Fecha: " + LocalDate.now() + "\n" +
                        "Total: Bs. " + Moneda.formatear(totalCarrito),
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
                } catch (HeadlessException ex) {
                    JOptionPane.showMessageDialog(this,
                        "Error: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                }
                
                   // Limpiar carrito
                    modelo_carrito.clear();
                    totalCarrito = 0;
                    actualizarTotal();
                    mostrarProductos();
            },
            () -> {
                JOptionPane.showMessageDialog(this, 
                    "Pago cancelado", 
                    "Cancelado", 
                    JOptionPane.INFORMATION_MESSAGE);
            }
        );
        pago.setVisible(true);
    });
    resumen.setVisible(true);
});


        add(panel_carrito, BorderLayout.WEST);


        // PANEL PRODUCTOS 
        panel_productos = new JPanel(new FlowLayout(FlowLayout.LEFT,10,10));
        JScrollPane scrollProd = new JScrollPane(panel_productos);
        add(scrollProd, BorderLayout.CENTER);

        // ACCIONES
        boton_filtrar.addActionListener(e -> mostrarProductos());
        
        boton_vaciar.addActionListener(e -> {
            for (int i = 0; i < modelo_carrito.size(); i++) {
                modelo_carrito.getElementAt(i).disminuirCantidad();
            }
            modelo_carrito.clear();
            totalCarrito = 0;
            actualizarTotal();
            PanelCarrito_Acciones();
            mostrarProductos();
        });

        // BOTÓN DE AYUDA FLOTANTE
        JButton boton_ayuda = new JButton();
        boton_ayuda.setToolTipText("Ayuda");
        boton_ayuda.setBounds(920, 490, 40, 40); 
        URL ayuda_icon = getClass().getResource("/iconos/ayuda.png");
        ImageIcon icono = new ImageIcon(ayuda_icon);
        boton_ayuda.setIcon(new ImageIcon(icono.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
        boton_ayuda.setContentAreaFilled(false);
        boton_ayuda.setBorderPainted(false);
        boton_ayuda.setFocusPainted(false);
        boton_ayuda.setOpaque(false);

        boton_ayuda.addActionListener(e -> {
            String ayuda = """
                🛠 Instrucciones de uso:

                👉 Toca un producto para ver su descripción detallada.
                🛒 Haz clic en 'Agregar' para sumarlo al carrito.
                🗑 Controla el total de productos con los botones "➕" y "➖".
                ❌ 'Vaciar' borra todos los productos del carrito.
                💵 'PAGAR' finaliza la compra con resumen.
                🔍 Usa filtros para buscar por nombre, categoría o precio.
                """;
            JOptionPane.showMessageDialog(this, ayuda, "Ayuda", JOptionPane.INFORMATION_MESSAGE, null);
        });

        JLayeredPane layeredPane = getLayeredPane();
        layeredPane.add(boton_ayuda, JLayeredPane.PALETTE_LAYER);

        mostrarProductos();
    }

    public Usuario getCliente() {
        return cliente;
    }

    public void setCliente(Usuario cliente) {
        this.cliente = cliente;
    }

    
    private void PanelCarrito_Acciones() {
    panel_carrito_contenido.removeAll();
    panel_carrito_contenido.setLayout(new BoxLayout(panel_carrito_contenido, BoxLayout.Y_AXIS));
    panel_carrito_contenido.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0)); // Sin bordes

    for (int i = 0; i < modelo_carrito.size(); i++) {     
        ProductoCarrito carrito = modelo_carrito.getElementAt(i);
        JPanel fila = new JPanel(new BorderLayout());
        fila.setBackground(Color.WHITE);

        JLabel info = new JLabel(
            carrito.getProducto().getNombre() +
            " | Talla: " + carrito.getProducto().getTalla() +
            " | Bs. " + Moneda.formatear(carrito.getTotalBs(Almacenamiento.tasaDolar))
        );
        fila.add(info, BorderLayout.NORTH);

        // BOTONES DE + Y -
        JPanel boton_carrito = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        boton_carrito.setAlignmentX(Component.LEFT_ALIGNMENT);
        JButton eliminar_carrito = new JButton("➖");
        JButton agregar_carrito = new JButton("➕");
        JLabel cantidad_producto = new JLabel(String.valueOf(carrito.getCantidad()));
        boton_carrito.add(eliminar_carrito);
        boton_carrito.add(cantidad_producto);
        boton_carrito.add(agregar_carrito);
        fila.add(boton_carrito, BorderLayout.CENTER);

        // Acciones botones
        eliminar_carrito.addActionListener(e -> {
            carrito.disminuirCantidad();
            if (carrito.getCantidad() == 0) {
                modelo_carrito.removeElement(carrito);
            }
            PanelCarrito_Acciones();
            actualizarTotal();
        });
        agregar_carrito.addActionListener(e -> {
            carrito.aumentarCantidad();
            PanelCarrito_Acciones();
            actualizarTotal();
        });

        panel_carrito_contenido.add(fila);
    }

    panel_carrito_contenido.revalidate();
    panel_carrito_contenido.repaint();
}


    private void mostrarProductos() {
        panel_productos.removeAll();
        String cat = (String) combo_categoria.getSelectedItem();
        String pre = (String) combo_precio.getSelectedItem();
        String txt = campo_busqueda.getText().trim().toLowerCase();

        for (Producto producto_objeto : Almacenamiento.productos) {
            boolean okCat = cat.equals("Todas") || producto_objeto.getCategoria().equalsIgnoreCase(cat);
            double pd = producto_objeto.getPrecioDolar();
            boolean okPre = switch (pre) {
                case "< 50" -> pd < 50;
                case "50 - 100" -> pd >= 50 && pd <= 100;
                case "> 100" -> pd > 100;
                default -> true;
            };
            boolean okTxt = producto_objeto.getNombre().toLowerCase().contains(txt);

            if (okCat && okPre && okTxt) {
                panel_productos.add(crearTarjeta(producto_objeto));
            }
        }

        panel_productos.revalidate();
        panel_productos.repaint();
    }
       

    private JPanel crearTarjeta(Producto p) {
        JPanel auxiliar = new JPanel();
        auxiliar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mostrarDetalleProducto(p);
            }
        });
        auxiliar.setLayout(new BoxLayout(auxiliar, BoxLayout.Y_AXIS));
        auxiliar.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        auxiliar.setBackground(Color.WHITE);

        URL url = getClass().getResource("/iconos/" + p.getRutaImagen());
        JLabel img = new JLabel();
        if (url != null) {
            ImageIcon ic = new ImageIcon(url);
            img.setIcon(new ImageIcon(
                ic.getImage().getScaledInstance(100,100,Image.SCALE_SMOOTH)));
        } else {
            img.setText("Sin imagen");
        }
        img.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel nombre_producto_tarjeta = new JLabel(p.getNombre(), SwingConstants.CENTER);
        nombre_producto_tarjeta.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel precio_producto_tarjeta = new JLabel(
            "Bs." + Moneda.formatear(p.getPrecioBs(Almacenamiento.tasaDolar)) +
            " / $" + Moneda.formatear(p.getPrecioDolar()),
            SwingConstants.CENTER);
        precio_producto_tarjeta.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel talla_producto_tarjeta = new JLabel("Talla: " + p.getTalla());
        talla_producto_tarjeta.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton boton = new JButton(p.getStock() > 0 ? "Agregar" : "SOLD OUT");
        boton.setEnabled(p.getStock() > 0);
        boton.setAlignmentX(Component.CENTER_ALIGNMENT);

        boton.addActionListener(e -> {
        boolean carrito = false;

        for (int i = 0; i < modelo_carrito.size(); i++) {
            ProductoCarrito producto_carrito = modelo_carrito.getElementAt(i);
                if (producto_carrito.getProducto() == p) {
                producto_carrito.aumentarCantidad();
                carrito = true;
                break;
            }
        }

        if (!carrito) {
            ProductoCarrito nuevo = new ProductoCarrito(p);
            modelo_carrito.addElement(nuevo);
        }

        actualizarTotal();
        PanelCarrito_Acciones();
        mostrarProductos();
        JOptionPane.showMessageDialog(this, "Producto agregado ✅");
        });


        auxiliar.add(Box.createVerticalStrut(5));
        auxiliar.add(img);
        auxiliar.add(nombre_producto_tarjeta);
        auxiliar.add(precio_producto_tarjeta);
        auxiliar.add(talla_producto_tarjeta);
        auxiliar.add(Box.createVerticalStrut(5));
        auxiliar.add(boton);
        auxiliar.add(Box.createVerticalStrut(5));
        return auxiliar;
    }

    private void mostrarDetalleProducto(Producto producto_objeto) {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);

        JLabel imagen = new JLabel();
        imagen.setHorizontalAlignment(JLabel.CENTER);
        URL url = getClass().getResource("/iconos/" + producto_objeto.getRutaImagen());
        if (url != null) {
            ImageIcon ic = new ImageIcon(url);
            Image img = ic.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            imagen.setIcon(new ImageIcon(img));
        } else {
            imagen.setText("[Sin imagen]");
        }

        StringBuilder detalles = new StringBuilder();
        detalles.append("Nombre: ").append(producto_objeto.getNombre()).append("\n");
        detalles.append("Descripción: ").append(producto_objeto.getDescripcion()).append("\n");
        detalles.append("Categoría: ").append(producto_objeto.getCategoria()).append("\n");
        detalles.append("Talla: ").append(producto_objeto.getTalla()).append("\n");
        detalles.append("Color: ").append(producto_objeto.getColor()).append("\n");
        detalles.append("Stock: ").append(producto_objeto.getStock()).append("\n");
        detalles.append("Precio: $ ").append(Moneda.formatear(producto_objeto.getPrecioDolar())).append(" / Bs. ").append(Moneda.formatear(producto_objeto.getPrecioBs(Almacenamiento.tasaDolar)));

        JTextArea info = new JTextArea(detalles.toString());
        info.setEditable(false);
        info.setBackground(Color.WHITE);
        info.setFont(new Font("Arial", Font.PLAIN, 13));

        panel.add(imagen, BorderLayout.NORTH);
        panel.add(new JScrollPane(info), BorderLayout.CENTER);

        JOptionPane.showMessageDialog(this, panel, "Detalles del Producto", JOptionPane.INFORMATION_MESSAGE);
    }

    private void actualizarTotal() {
        totalCarrito = 0;
        for (int i = 0; i < modelo_carrito.getSize(); i++) {
            ProductoCarrito producto_carrito = modelo_carrito.getElementAt(i);
            totalCarrito += producto_carrito.getTotalBs(Almacenamiento.tasaDolar);
        }
        total_carrito.setText("Total: Bs. " + Moneda.formatear(totalCarrito));
        boton_pagar.setEnabled(!modelo_carrito.isEmpty());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TiendaAgraz_cliente(cliente).setVisible(true));
    }
}
