import modelo.Producto;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.List;
import java.util.ArrayList;

public class TiendaAgraz_empleado extends JFrame {

    private JPanel panelProductos;
    private JTextField campoBusqueda, tfTasaDolar;
    private JComboBox<String> comboCategoria, comboPrecio;
    private DefaultListModel<Producto> modeloCarrito;
    private JLabel lblTotal;
    private double tasaDolar = 1.0;
    private double totalCarrito = 0.0;
    private List<Producto> productos = new ArrayList<>();

    public TiendaAgraz_empleado() {
        setSize(1000, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setIconImage(new ImageIcon(getClass().getResource("/iconos/icon_1.png")).getImage());
        setLocationRelativeTo(null);

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(Color.WHITE);

        JPanel encabezado = new JPanel(new BorderLayout());
        encabezado.setBackground(Color.WHITE);
        encabezado.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        JPanel panelTasa = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelTasa.setBackground(Color.WHITE);
        panelTasa.add(new JLabel("Tasa del Dólar (Bs): "));
        tfTasaDolar = new JTextField("1.0", 5);
        tfTasaDolar.setFont(new Font("Arial", Font.PLAIN, 12));
        panelTasa.add(tfTasaDolar);

        URL urlDolar = getClass().getResource("/iconos/dolar.png");
        if (urlDolar != null) {
            ImageIcon dolarIcon = new ImageIcon(urlDolar);
            Image img = dolarIcon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
            panelTasa.add(new JLabel(new ImageIcon(img)));
        }

        encabezado.add(panelTasa, BorderLayout.WEST);

        JPanel filtros = new JPanel(new FlowLayout());
        filtros.setBackground(Color.WHITE);

        comboCategoria = new JComboBox<>(new String[]{"Todas", "Zapatos", "Ropa"});
        comboPrecio = new JComboBox<>(new String[]{"Todos", "< 50", "50 - 100", "> 100"});
        campoBusqueda = new JTextField(15);
        campoBusqueda.setToolTipText("Buscar producto");
        comboCategoria.setBackground(Color.BLACK);
        comboCategoria.setForeground(Color.WHITE);
        comboPrecio.setBackground(Color.BLACK);
        comboPrecio.setForeground(Color.WHITE);

        JButton botonFiltrar = new JButton("Filtrar");
        botonFiltrar.setBackground(Color.BLACK);
        botonFiltrar.setForeground(Color.WHITE);
        filtros.add(new JLabel("Categoría:"));
        filtros.add(comboCategoria);
        filtros.add(new JLabel("Precio:"));
        filtros.add(comboPrecio);
        filtros.add(new JLabel("Buscar:"));
        filtros.add(campoBusqueda);
        filtros.add(botonFiltrar);

        encabezado.add(filtros, BorderLayout.CENTER);
        panelPrincipal.add(encabezado, BorderLayout.NORTH);

        // PANEL CARRITO
        JPanel panelCarrito = new JPanel(new BorderLayout());
        panelCarrito.setBackground(Color.WHITE);
        panelCarrito.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(10, 10, 10, 10), 
            BorderFactory.createTitledBorder("🛒 Carrito")   
        ));
        panelCarrito.setPreferredSize(new Dimension(250, 0));

        modeloCarrito = new DefaultListModel<>();
        JList<Producto> listaCarrito = new JList<>(modeloCarrito);
        JScrollPane scrollCarrito = new JScrollPane(listaCarrito);
        scrollCarrito.getViewport().setBackground(Color.WHITE);
        panelCarrito.add(scrollCarrito, BorderLayout.CENTER);

        JButton btnEliminar = new JButton("Eliminar Producto");
        btnEliminar.setBackground(Color.BLACK);
        btnEliminar.setForeground(Color.WHITE);
        btnEliminar.addActionListener(e -> {
            Producto seleccionado = listaCarrito.getSelectedValue();
            if (seleccionado != null) {
                modeloCarrito.removeElement(seleccionado);
                seleccionado.incrementarStock();
                totalCarrito -= seleccionado.getPrecioBs(tasaDolar);
                actualizarTotal();
                mostrarProductos();
            }
        });
        panelCarrito.add(btnEliminar, BorderLayout.NORTH);

        lblTotal = new JLabel("Total: Bs. 0.00");
        lblTotal.setFont(new Font("Arial", Font.BOLD, 14));
        lblTotal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelCarrito.add(lblTotal, BorderLayout.SOUTH);

        // BOTÓN CREAR PRODUCTO NUEVO
        JButton btnCrearProducto = new JButton("Crear Producto Nuevo");
        btnCrearProducto.setBackground(Color.BLACK);
        btnCrearProducto.setForeground(Color.WHITE);
        btnCrearProducto.addActionListener(e -> mostrarDialogoCrearProducto());

        JPanel panelBotonCrear = new JPanel();
        panelBotonCrear.setBackground(Color.WHITE);
        panelBotonCrear.add(btnCrearProducto);
        panelCarrito.add(panelBotonCrear, BorderLayout.AFTER_LAST_LINE);

        panelPrincipal.add(panelCarrito, BorderLayout.WEST);

        // PANEL PRODUCTOS
        panelProductos = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panelProductos.setBackground(Color.WHITE);
        panelProductos.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane scroll = new JScrollPane(panelProductos);
        scroll.getViewport().setBackground(Color.WHITE);
        panelPrincipal.add(scroll, BorderLayout.CENTER);

        add(panelPrincipal);

        // PRODUCTOS DE PRUEBA
        productos.add(new Producto("Walter Original", "Zapatos cómodos", 100, "Zapatos", "Negro", 'M', "toad.jpg", 5));
        productos.add(new Producto("Walter Pro", "Zapatos para correr", 120, "Zapatos", "Rojo", 'L', "toad.jpg", 0));
        productos.add(new Producto("Agraz Hoodie", "Ropa urbana", 80, "Ropa", "Negro", 'M', "toad.jpg", 2));

        mostrarProductos();

        // FILTRAR
        botonFiltrar.addActionListener(e -> {
            try {
                tasaDolar = Double.parseDouble(tfTasaDolar.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Tasa inválida. Se usará 1.0");
                tasaDolar = 1.0;
            }
            mostrarProductos();
        });
    }

    private void mostrarProductos() {
        panelProductos.removeAll();
        String categoria = (String) comboCategoria.getSelectedItem();
        String filtroPrecio = (String) comboPrecio.getSelectedItem();
        String busqueda = campoBusqueda.getText().trim().toLowerCase();

        for (Producto p : productos) {
            boolean catOk = categoria.equals("Todas") || p.getCategoria().equalsIgnoreCase(categoria);
            boolean precioOk = switch (filtroPrecio) {
                case "< 50" -> p.getPrecioDolar() < 50;
                case "50 - 100" -> p.getPrecioDolar() >= 50 && p.getPrecioDolar() <= 100;
                case "> 100" -> p.getPrecioDolar() > 100;
                default -> true;
            };
            boolean coincideBusqueda = p.getNombre().toLowerCase().contains(busqueda);

            if (catOk && precioOk && coincideBusqueda) {
                panelProductos.add(crearTarjetaProducto(p));
            }
        }

        panelProductos.revalidate();
        panelProductos.repaint();
    }

    private JPanel crearTarjetaProducto(Producto p) {
        JPanel tarjeta = new JPanel();
        tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
        tarjeta.setBackground(Color.WHITE);
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.BLACK),
            BorderFactory.createEmptyBorder(5, 5, 5, 5) // Margen interior
        ));
        
        JLabel imagen = new JLabel();
        imagen.setAlignmentX(Component.CENTER_ALIGNMENT);
        URL url = getClass().getResource("/iconos/" + p.getRutaImagen());
        if (url != null) {
            ImageIcon icon = new ImageIcon(url);
            Image img = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            imagen.setIcon(new ImageIcon(img));
        } else {
            imagen.setText("[Imagen]");
        }

        JLabel nombre = new JLabel(p.getNombre(), SwingConstants.CENTER);
        nombre.setFont(new Font("Arial", Font.BOLD, 14));
        nombre.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel precio = new JLabel("Bs. " + p.getPrecioBs(tasaDolar) + " / $" + p.getPrecioDolar());
        precio.setFont(new Font("Arial", Font.PLAIN, 12));
        precio.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnAgregar = new JButton("Agregar");
        btnAgregar.setBackground(Color.BLACK);
        btnAgregar.setForeground(Color.WHITE);
        btnAgregar.setAlignmentX(Component.CENTER_ALIGNMENT);

        if (p.getStock() > 0) {
            btnAgregar.addActionListener(e -> {
                modeloCarrito.addElement(p);
                totalCarrito += p.getPrecioBs(tasaDolar);
                p.reducirStock();
                actualizarTotal();
                mostrarProductos();
                JOptionPane.showMessageDialog(this, "Producto agregado con éxito ✅");
            });
        } else {
            btnAgregar.setEnabled(false);
            btnAgregar.setText("SOLD OUT");
        }

        tarjeta.add(Box.createVerticalStrut(10));
        tarjeta.add(imagen);
        tarjeta.add(nombre);
        tarjeta.add(precio);
        tarjeta.add(Box.createVerticalStrut(10));
        tarjeta.add(btnAgregar);
        tarjeta.add(Box.createVerticalStrut(10));
        return tarjeta;
    }

    private void actualizarTotal() {
        lblTotal.setText(String.format("Total: Bs. %.2f", totalCarrito));
    }

    private void mostrarDialogoCrearProducto() {
        JTextField tfNombre = new JTextField();
        JTextField tfDescripcion = new JTextField();
        JTextField tfPrecio = new JTextField();
        JTextField tfCategoria = new JTextField();
        JTextField tfColor = new JTextField();
        JTextField tfTalla = new JTextField();
        JTextField tfImagen = new JTextField();
        JTextField tfStock = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.add(new JLabel("Nombre:"));
        panel.add(tfNombre);
        panel.add(new JLabel("Descripción:"));
        panel.add(tfDescripcion);
        panel.add(new JLabel("Precio en $:"));
        panel.add(tfPrecio);
        panel.add(new JLabel("Categoría:"));
        panel.add(tfCategoria);
        panel.add(new JLabel("Color:"));
        panel.add(tfColor);
        panel.add(new JLabel("Talla (una letra):"));
        panel.add(tfTalla);
        panel.add(new JLabel("Nombre de imagen:"));
        panel.add(tfImagen);
        panel.add(new JLabel("Stock:"));
        panel.add(tfStock);

        int result = JOptionPane.showConfirmDialog(this, panel, "Nuevo Producto", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                String nombre = tfNombre.getText().trim();
                String descripcion = tfDescripcion.getText().trim();
                int precioDolar = (int)Double.parseDouble(tfPrecio.getText().trim());
                String categoria = tfCategoria.getText().trim();
                String color = tfColor.getText().trim();
                char talla = tfTalla.getText().trim().charAt(0);
                String imagen = tfImagen.getText().trim();
                int stock = Integer.parseInt(tfStock.getText().trim());

                Producto nuevo = new Producto(nombre, descripcion, precioDolar, categoria, color, talla, imagen, stock);
                productos.add(nuevo);
                mostrarProductos();
                JOptionPane.showMessageDialog(this, "Producto creado con éxito ✅");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Datos inválidos. Intenta de nuevo ❌");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TiendaAgraz_empleado().setVisible(true));
    }
}
