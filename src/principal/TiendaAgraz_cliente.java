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

public class TiendaAgraz_cliente extends JFrame {
    private JPanel panel_productos;
    private JTextField campo_busqueda, tasa_dolar;
    private JComboBox<String> combo_categoria, combo_precio;
    private DefaultListModel<Producto> modelo_carrito;
    private JLabel total_carrito;
    private double totalCarrito = 0.0;
    private JButton boton_pagar;

    public TiendaAgraz_cliente() {
        super("Tienda Agraz - Cliente");
        setIconImage(new ImageIcon(getClass().getResource("/iconos/icon.png")).getImage());
        setSize(1000, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

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
        modelo_carrito = new DefaultListModel<>();
        JList<Producto> listaCarrito = new JList<>(modelo_carrito);
        listaCarrito.setCellRenderer((list, value, idx, sel, focus) -> {
            JLabel lbl = (JLabel) new DefaultListCellRenderer()
                .getListCellRendererComponent(list,value,idx,sel,focus);
            Producto p = (Producto) value;
            lbl.setText(p.getNombre() +
                " | Talla:" + p.getTalla() +
                " | Bs." + p.getPrecioBs(Almacenamiento.tasaDolar));
            return lbl;
        });

        JScrollPane scrollCar = new JScrollPane(listaCarrito);
        scrollCar.setPreferredSize(new Dimension(250, 0));
        JPanel panelCarrito = new JPanel(new BorderLayout());
        panelCarrito.setBorder(BorderFactory.createTitledBorder("🛒 Carrito"));
        panelCarrito.add(scrollCar, BorderLayout.CENTER);

        JPanel boton_carro = new JPanel(new GridLayout(2,1,5,5));
        JButton boton_eliminar = new JButton("Eliminar");
        boton_eliminar.setEnabled(false);
        JButton boton_vaciar   = new JButton("Vaciar");
        boton_carro.add(boton_eliminar);
        boton_carro.add(boton_vaciar);
        panelCarrito.add(boton_carro, BorderLayout.NORTH);

        // PANEL DE TOTAL CARRITO + PAGAR
        JPanel panel_inferior_carrito = new JPanel(new BorderLayout());
        panel_inferior_carrito.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        total_carrito = new JLabel("Total: Bs. 0.00");
        panel_inferior_carrito.add(total_carrito, BorderLayout.WEST);

        boton_pagar = new JButton("PAGAR");
        boton_pagar.setBackground(Color.BLACK);
        boton_pagar.setForeground(Color.WHITE);
        boton_pagar.setEnabled(false);
        panel_inferior_carrito.add(boton_pagar, BorderLayout.EAST);
        panelCarrito.add(panel_inferior_carrito, BorderLayout.SOUTH);
        boton_pagar.setEnabled(false);
        boton_pagar.addActionListener(e -> {
            if (modelo_carrito.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay productos en el carrito.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            StringBuilder mensaje = new StringBuilder();
            mensaje.append("🧑 Nombre: Cliente\n📧 Correo: cliente@example.com\n\n");
            mensaje.append("🛒 Productos seleccionados:\n");
            
                for (int i = 0; i < modelo_carrito.getSize(); i++) {
                    Producto p = modelo_carrito.getElementAt(i);
                    mensaje.append("- ").append(p.getNombre())
                        .append(" | Talla: ").append(p.getTalla())
                        .append(" | Bs. ").append(Moneda.formatear(p.getPrecioBs(Almacenamiento.tasaDolar))).append("\n");
                }
            mensaje.append("\n📦 Cantidad: ").append(modelo_carrito.getSize()).append("\n");
            mensaje.append("💰 Total: Bs. ").append(Moneda.formatear(totalCarrito)).append("\n");
            mensaje.append("✅ ¡Pedido exitoso!");
            JOptionPane.showMessageDialog(this, mensaje.toString(), "Confirmación", JOptionPane.INFORMATION_MESSAGE, null);                            
            modelo_carrito.clear();
            totalCarrito = 0;
            actualizarTotal();
            mostrarProductos();
        });

        add(panelCarrito, BorderLayout.WEST);

        // PANEL PRODUCTOS 
        panel_productos = new JPanel(new FlowLayout(FlowLayout.LEFT,10,10));
        JScrollPane scrollProd = new JScrollPane(panel_productos);
        add(scrollProd, BorderLayout.CENTER);

        // ACCIONES
        boton_filtrar.addActionListener(e -> mostrarProductos());
        listaCarrito.addListSelectionListener(e -> {
            boolean sel = !listaCarrito.isSelectionEmpty();
            boton_eliminar.setEnabled(sel);
            boton_vaciar.setEnabled(!modelo_carrito.isEmpty());
        });

        boton_eliminar.addActionListener(e -> {
            Producto p = listaCarrito.getSelectedValue();
            if (p != null) {
                modelo_carrito.removeElement(p);
                totalCarrito -= p.getPrecioBs(Almacenamiento.tasaDolar);
                p.incrementarStock();
                actualizarTotal();
                mostrarProductos();
            }
        });

        boton_vaciar.addActionListener(e -> {
            for (int i = 0; i < modelo_carrito.size(); i++) {
                modelo_carrito.getElementAt(i).incrementarStock();
            }
            modelo_carrito.clear();
            totalCarrito = 0;
            actualizarTotal();
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
                🗑 Selecciona un producto del carrito para activar 'Eliminar'.
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
            img.setText("[No img]");
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
            modelo_carrito.addElement(p);
            totalCarrito += p.getPrecioBs(Almacenamiento.tasaDolar);
            p.reducirStock();
            actualizarTotal();
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
            Producto producto_objeto = modelo_carrito.getElementAt(i);
            totalCarrito += producto_objeto.getPrecioBs(Almacenamiento.tasaDolar);
        }
        total_carrito.setText("Total: Bs. " + Moneda.formatear(totalCarrito));
        boton_pagar.setEnabled(!modelo_carrito.isEmpty());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TiendaAgraz_cliente().setVisible(true));
    }
}
