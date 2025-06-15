package principal;

import modelo.Almacenamiento;
import modelo.Producto;
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
        JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        panelAcciones.setBackground(Color.WHITE);

        JButton btnNuevo = new JButton("Nuevo Producto");
        btnNuevo.setBackground(Color.BLACK);
        btnNuevo.setForeground(Color.WHITE);
        btnNuevo.addActionListener(e -> mostrarDialogoCrearProducto());
        panelAcciones.add(btnNuevo);

        JButton btnCerrar = new JButton("Cerrar sesión");
        btnCerrar.setBackground(Color.WHITE);
        btnCerrar.setForeground(Color.BLACK);
        btnCerrar.addActionListener(e -> {
            try {
                Almacenamiento.tasaDolar = Double.parseDouble(tasa_dolar.getText());
            } catch (NumberFormatException ignored) { }
            new Loggin().setVisible(true);
            dispose();
        });
        panelAcciones.add(btnCerrar);
        add(panelAcciones, BorderLayout.SOUTH);
        boton_filtrar.addActionListener(e -> mostrarProductos());
        mostrarProductos();
    }

    private void mostrarProductos() {
        panel_productos.removeAll();
        String cat = combo_categoria.getSelectedItem().toString();
        String pre = combo_precio.getSelectedItem().toString();
        String txt = campo_busqueda.getText().trim().toLowerCase();
        double tasa = Almacenamiento.tasaDolar;

        for (Producto p : Almacenamiento.productos) {
            boolean okCat = cat.equals("Todas")
                || p.getCategoria().equalsIgnoreCase(cat);
            double pd = p.getPrecioDolar();
            boolean okPre = switch (pre) {
                case "< 50"    -> pd < 50;
                case "50 - 100"-> pd >= 50 && pd <= 100;
                case "> 100"   -> pd > 100;
                default        -> true;
            };
            boolean okTxt = p.getNombre().toLowerCase().contains(txt);

            if (okCat && okPre && okTxt) {
                panel_productos.add(crearTarjetaProducto(p, tasa));
            }
        }
        panel_productos.revalidate();
        panel_productos.repaint();
    }

    private JPanel crearTarjetaProducto(Producto p, double tasa) {
            JPanel tarjeta = new JPanel();
            tarjeta.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            mostrarDetalleProducto(p);
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
        URL imagen_url = getClass().getResource("/iconos/" + p.getRutaImagen());
        if (imagen_url != null) {
            ImageIcon ic = new ImageIcon(imagen_url);
            Image img = ic.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            imagen.setIcon(new ImageIcon(img));
        } else {
            imagen.setText("[Imagen]");
        }

    JLabel nombre_producto = new JLabel(p.getNombre(), SwingConstants.CENTER);
    nombre_producto.setFont(new Font("Arial", Font.BOLD, 14));
    nombre_producto.setAlignmentX(Component.CENTER_ALIGNMENT);

    String precio_producto = String.format("$ %.2f / Bs %.2f", (double) p.getPrecioDolar(), (double) p.getPrecioBs(tasa));
    JLabel precio_total = new JLabel(precio_producto, SwingConstants.CENTER);
    precio_total.setFont(new Font("Arial", Font.PLAIN, 12));
    precio_total.setAlignmentX(Component.CENTER_ALIGNMENT);

    JLabel talla_producto = new JLabel("Talla: " + p.getTalla());
    talla_producto.setFont(new Font("Arial", Font.PLAIN, 12));
    talla_producto.setAlignmentX(Component.CENTER_ALIGNMENT);

    // BOTON DE ELIMINAR PRODUCTO
    JButton boton_eliminar = new JButton("Eliminar");
    boton_eliminar.setBackground(Color.RED);
    boton_eliminar.setForeground(Color.WHITE);
    boton_eliminar.setAlignmentX(Component.CENTER_ALIGNMENT);
    boton_eliminar.addActionListener(e -> {
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "¿Eliminar “" + p.getNombre() + "”?",
            "Confirmar eliminación",
            JOptionPane.YES_NO_OPTION
        );
        if (confirm == JOptionPane.YES_OPTION) {
            Almacenamiento.productos.remove(p);
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
    detalles.append(String.format("Precio: $ %.2f / Bs. %.2f",
        (double) p.getPrecioDolar(),
        (double) p.getPrecioBs(Almacenamiento.tasaDolar)));

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
            for (Producto p : Almacenamiento.productos) {
                if (p.getNombre().equalsIgnoreCase(nombre)
                 && p.getPrecioDolar() == precio_dolar
                 && p.getTalla() == talla) {
                    p.setStock(p.getStock() + stock);
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