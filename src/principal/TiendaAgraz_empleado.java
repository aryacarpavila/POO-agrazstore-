package principal;

import modelo.Almacenamiento;
import modelo.Producto;
import modelo.Moneda;
import vista.Loggin;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

public class TiendaAgraz_empleado extends JFrame {
    private JPanel panel_productos;
    private JTextField campo_busqueda, tasa_dolar;
    private JComboBox<String> combo_categoria, combo_precio;

    public TiendaAgraz_empleado() {
        super("Tienda Agraz – Empleado");
        setIconImage(new ImageIcon(getClass().getResource("/iconos/icon.png")).getImage());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
     
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
        
        // PANEL FILTROS 
        JPanel panel_filtros = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panel_filtros.setBackground(Color.WHITE);

        panel_filtros.add(new JLabel("Tasa Dólar (Bs):"));
        tasa_dolar = new JTextField(String.valueOf(Almacenamiento.tasaDolar), 6);
        tasa_dolar.setBackground(Color.WHITE);
        panel_filtros.add(tasa_dolar);

        panel_filtros.add(new JLabel("Categoría:"));
        combo_categoria = new JComboBox<>(new String[]{"Todas", "Zapatos", "Ropa", "Accesorios"});
        panel_filtros.add(combo_categoria);

        panel_filtros.add(new JLabel("Precio $:"));
        combo_precio = new JComboBox<>(new String[]{"Todos", "< 50", "50 - 100", "> 100"});
        panel_filtros.add(combo_precio);

        panel_filtros.add(new JLabel("Buscar:"));
        campo_busqueda = new JTextField(15);
        panel_filtros.add(campo_busqueda);

        JButton boton_filtrar = new JButton("Filtrar");
        boton_filtrar.setBackground(Color.BLACK);
        boton_filtrar.setForeground(Color.WHITE);
        panel_filtros.add(boton_filtrar);

        add(panel_filtros, BorderLayout.NORTH);

        // PANEL PRODUCTOS 
        panel_productos = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panel_productos.setBackground(Color.WHITE);
        JScrollPane scroll = new JScrollPane(panel_productos);
        scroll.getViewport().setBackground(Color.WHITE);
        add(scroll, BorderLayout.CENTER);

        // PANEL ACCIONES 
        JPanel panel_acciones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        panel_acciones.setBackground(Color.WHITE);

        JButton boton_nuevo_producto = new JButton("Nuevo Producto");
        boton_nuevo_producto.setBackground(Color.BLACK);
        boton_nuevo_producto.setForeground(Color.WHITE);
        boton_nuevo_producto.addActionListener(e -> mostrarDialogoCrearProducto());
        panel_acciones.add(boton_nuevo_producto);

        JButton boton_cerrarsesion = new JButton("Cerrar sesión");
        boton_cerrarsesion.setBackground(Color.WHITE);
        boton_cerrarsesion.setForeground(Color.BLACK);
        boton_cerrarsesion.addActionListener(e -> {
            try {
                Almacenamiento.tasaDolar = Double.parseDouble(tasa_dolar.getText());
            } catch (NumberFormatException ignored) { }
            new Loggin().setVisible(true);
            dispose();
        });
        panel_acciones.add(boton_cerrarsesion);
        add(panel_acciones, BorderLayout.SOUTH);
        
        boton_filtrar.addActionListener(e -> {
            try {
                Almacenamiento.tasaDolar = Double.parseDouble(tasa_dolar.getText().trim());
                mostrarProductos();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(
                    this,
                    "La tasa de dólar ingresada no es válida.",
                    "Error",
                JOptionPane.ERROR_MESSAGE
                );
            }
        });
        
        // BOTÓN DE AYUDA FLOTANTE
        JButton boton_ayuda = new JButton();
        boton_ayuda.setToolTipText("Ayuda");
        boton_ayuda.setBounds(930, 470, 40, 40); 


        URL ayuda_icon = getClass().getResource("/iconos/ayuda.png");
        ImageIcon icono = new ImageIcon(ayuda_icon);
        boton_ayuda.setIcon(new ImageIcon(icono.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
           
        boton_ayuda.setContentAreaFilled(false);
        boton_ayuda.setBorderPainted(false);
        boton_ayuda.setFocusPainted(false);
        boton_ayuda.setOpaque(false);

        boton_ayuda.addActionListener(e -> {
            String ayuda = """
                🔎 FILTRAR PRODUCTOS:
                - Usa la barra "Buscar" para encontrar productos por nombre.
                - Filtra por categoría o rango de precios en dólares.
                - Cambia la tasa del dólar (Bs) y presiona 'Filtrar' para actualizar precios en bolívares.
                
                🆕 CREAR NUEVO PRODUCTO:
                - Presiona 'Nuevo Producto'.
                - Ingresa todos los campos requeridos: nombre, descripción, precio ($), categoría, talla, color, stock.
                - Si ya existe un producto con el mismo nombre, talla y precio, solo se actualizará el stock.
                
                🗑 ELIMINAR PRODUCTO:
                - Haz clic sobre cualquier producto mostrado para ver sus detalles.
                - Puedes eliminarlo directamente desde la tarjeta con el botón rojo 'Eliminar'.
                
                🔁 ACTUALIZAR TASA DE CAMBIO:
                - Modifica el campo "Tasa Dólar (Bs)".
                - Presiona el botón 'Filtrar' para recalcular todos los precios.
                
                🚪 CERRAR SESIÓN:
                - Presiona 'Cerrar sesión' para volver a la pantalla de inicio.
                
                ℹ️ NOTA:
                - Los precios están en formato: $ Dólar / Bs Bolívar (según tasa actual).
                - Asegúrate de ingresar valores numéricos válidos para precios, stock y tasa.
                
                🛍 ¡Gracias por mantener la tienda al día!
                """;
            JOptionPane.showMessageDialog(this, ayuda, "Ayuda", JOptionPane.INFORMATION_MESSAGE, null);
        });

        JLayeredPane layeredPane = getLayeredPane();
        layeredPane.add(boton_ayuda, JLayeredPane.PALETTE_LAYER);
        
        mostrarProductos();
    }

    private void mostrarProductos() {
        panel_productos.removeAll();
        String categoria_producto = combo_categoria.getSelectedItem().toString();
        String precio_producto = combo_precio.getSelectedItem().toString();
        String texto_busqueda_producto = campo_busqueda.getText().trim().toLowerCase();
        double tasa = Almacenamiento.tasaDolar;

        for (Producto producto_objeto : Almacenamiento.productos) {
            boolean categoria_texto = categoria_producto.equals("Todas")
                || producto_objeto.getCategoria().equalsIgnoreCase(categoria_producto);
            double pd = producto_objeto.getPrecioDolar();
            boolean precio_texto = switch (precio_producto) {
                case "< 50"    -> pd < 50;
                case "50 - 100"-> pd >= 50 && pd <= 100;
                case "> 100"   -> pd > 100;
                default        -> true;
            };
            boolean busqueda_texto = producto_objeto.getNombre().toLowerCase().contains(texto_busqueda_producto);

            if (categoria_texto && precio_texto && busqueda_texto) {
                panel_productos.add(crearTarjetaProducto(producto_objeto, tasa));
            }
        }
        panel_productos.revalidate();
        panel_productos.repaint();
    }

    private JPanel crearTarjetaProducto(Producto producto_objeto, double tasa) {
            JPanel tarjeta = new JPanel();
            tarjeta.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            mostrarDetalleProducto(producto_objeto);
        }
    });
            
        tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
        tarjeta.setBackground(Color.WHITE);
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.BLACK),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

        // Imagen del producto
        JLabel imagen = new JLabel();
        imagen.setAlignmentX(Component.CENTER_ALIGNMENT);
        URL imagen_url = getClass().getResource("/iconos/" + producto_objeto.getRutaImagen());
        if (imagen_url != null) {
            ImageIcon ic = new ImageIcon(imagen_url);
            Image img = ic.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            imagen.setIcon(new ImageIcon(img));
        } else {
            imagen.setText("[Imagen]");
        }

    JLabel nombre_producto = new JLabel(producto_objeto.getNombre(), SwingConstants.CENTER);
    nombre_producto.setFont(new Font("Arial", Font.BOLD, 14));
    nombre_producto.setAlignmentX(Component.CENTER_ALIGNMENT);

    String precio_producto = "$ " + Moneda.formatear(producto_objeto.getPrecioDolar()) + " / Bs " + Moneda.formatear(producto_objeto.getPrecioBs(tasa));
    JLabel precio_total = new JLabel(precio_producto, SwingConstants.CENTER);
    precio_total.setFont(new Font("Arial", Font.PLAIN, 12));
    precio_total.setAlignmentX(Component.CENTER_ALIGNMENT);

    JLabel talla_producto = new JLabel("Talla: " + producto_objeto.getTalla());
    talla_producto.setFont(new Font("Arial", Font.PLAIN, 12));
    talla_producto.setAlignmentX(Component.CENTER_ALIGNMENT);

    // BOTON DE ELIMINAR PRODUCTO
    JButton boton_eliminar = new JButton("Eliminar");
    boton_eliminar.setBackground(Color.RED);
    boton_eliminar.setForeground(Color.WHITE);
    boton_eliminar.setAlignmentX(Component.CENTER_ALIGNMENT);
    boton_eliminar.addActionListener(e -> {
        int confirm = JOptionPane.showConfirmDialog(this,
            "¿Eliminar “" + producto_objeto.getNombre() + "”?",
            "Confirmar eliminación",
            JOptionPane.YES_NO_OPTION
        );
        if (confirm == JOptionPane.YES_OPTION) {
            Almacenamiento.productos.remove(producto_objeto);
            mostrarProductos();
            JOptionPane.showMessageDialog(this, "Producto eliminado ✅", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        }
    });

    tarjeta.add(Box.createVerticalStrut(5));
    tarjeta.add(imagen);
    tarjeta.add(nombre_producto);
    tarjeta.add(precio_total);
    tarjeta.add(talla_producto);
    tarjeta.add(Box.createVerticalStrut(5));
    tarjeta.add(boton_eliminar);
    tarjeta.add(Box.createVerticalStrut(5));

    return tarjeta;
}
    
    private void mostrarDetalleProducto(Producto p) {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);

        // Imagen grande
        JLabel imagen = new JLabel();
        imagen.setHorizontalAlignment(JLabel.CENTER);
        URL imagen_url = getClass().getResource("/iconos/" + p.getRutaImagen());
        if (imagen_url != null) {
            ImageIcon ic = new ImageIcon(imagen_url);
            Image img = ic.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            imagen.setIcon(new ImageIcon(img));
        } else {
            imagen.setText("[Sin imagen]");
        }
        
    // Detalles del producto
    StringBuilder detalles = new StringBuilder();
    detalles.append("Nombre: ").append(p.getNombre()).append("\n");
    detalles.append("Descripción: ").append(p.getDescripcion()).append("\n");
    detalles.append("Categoría: ").append(p.getCategoria()).append("\n");
    detalles.append("Talla: ").append(p.getTalla()).append("\n");
    detalles.append("Color: ").append(p.getColor()).append("\n");
    detalles.append("Stock: ").append(p.getStock()).append("\n");
    detalles.append("Precio: $ ").append(Moneda.formatear(p.getPrecioDolar())).append(" / Bs. ").append(Moneda.formatear(p.getPrecioBs(Almacenamiento.tasaDolar)));

    JTextArea info = new JTextArea(detalles.toString());
    info.setEditable(false);
    info.setBackground(Color.WHITE);
    info.setFont(new Font("Arial", Font.PLAIN, 13));

    panel.add(imagen, BorderLayout.NORTH);
    panel.add(new JScrollPane(info), BorderLayout.CENTER);

    JOptionPane.showMessageDialog(this, panel, "Detalles del Producto", JOptionPane.INFORMATION_MESSAGE);
}

    private void mostrarDialogoCrearProducto() {
        JTextField nombre_nuevoproducto      = new JTextField();
        JTextField descripcion_nuevoproducto = new JTextField();
        JTextField precio_nuevoproducto      = new JTextField();
        JComboBox<String> categoria_nuevoproducto  = new JComboBox<>(
            new String[]{"top", "bottom", "zapatos", "accesorios"});
        precio_nuevoproducto.setToolTipText("Ingresa precio en USD");
        JComboBox<String> talla_nuevoproducto = new JComboBox<>(new String[]{"S","M","L"});
        JTextField color_nuevoproducto       = new JTextField();
        JTextField stock_nuevoproducto       = new JTextField();

        JPanel form = new JPanel(new GridLayout(0,2,5,5));
        form.setBackground(Color.WHITE);
        form.add(new JLabel("Nombre:"));     form.add(nombre_nuevoproducto);
        form.add(new JLabel("Descripción:"));form.add(descripcion_nuevoproducto);
        form.add(new JLabel("Precio ($):")); form.add(precio_nuevoproducto);
        form.add(new JLabel("Categoría:"));  form.add(categoria_nuevoproducto);
        form.add(new JLabel("Talla:"));      form.add(talla_nuevoproducto);
        form.add(new JLabel("Color:"));      form.add(color_nuevoproducto);
        form.add(new JLabel("Stock:"));      form.add(stock_nuevoproducto);

        int confirmacion = JOptionPane.showConfirmDialog(
            this, form, "Nuevo Producto",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE
        );
        if (confirmacion != JOptionPane.OK_OPTION) return;

        try {
            String nombre    = nombre_nuevoproducto.getText().trim();
            String descripcion      = descripcion_nuevoproducto.getText().trim();
            int precio_dolar = Integer.parseInt(precio_nuevoproducto.getText().trim());
            String categoria = categoria_nuevoproducto.getSelectedItem().toString();
            char talla       = ((String)talla_nuevoproducto.getSelectedItem()).charAt(0);
            String color     = color_nuevoproducto.getText().trim();
            int stock        = Integer.parseInt(stock_nuevoproducto.getText().trim());

            String imagen;
            imagen = switch (categoria.toLowerCase()) {
                case "top" -> "top_default.jpg";
                case "bottom" -> "bottom_default.jpg";
                case "zapatos" -> "Zapatos_default.jpg";
                case "accesorios" -> "accesorios_default.jpg";
                default -> "default.png";
            };

            boolean found = false;
            for (Producto producto_objeto : Almacenamiento.productos) {
                if (producto_objeto.getNombre().equalsIgnoreCase(nombre)
                 && producto_objeto.getPrecioDolar() == precio_dolar
                 && producto_objeto.getTalla() == talla) {
                    producto_objeto.setStock(producto_objeto.getStock() + stock);
                    found = true;
                    break;
                }
            }

            if (found) {
                mostrarProductos();
                JOptionPane.showMessageDialog(this, "Stock actualizado ✅");
            } else {
                Producto nuevo = new Producto(
                    nombre, descripcion, precio_dolar,
                    categoria, color, talla,
                    imagen, stock
                );
                Almacenamiento.productos.add(nuevo);
                mostrarProductos();
                JOptionPane.showMessageDialog(this, "Producto creado ✅");
            }
        } catch (HeadlessException | NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Datos inválidos ❌");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->
            new TiendaAgraz_empleado().setVisible(true)
        );
    }
}