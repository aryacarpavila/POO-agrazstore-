import modelo.Producto;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.List;
import java.util.ArrayList;

public class TiendaAgraz_cliente extends JFrame {

    private JPanel panelProductos;
    private JTextField campoBusqueda, tfTasaDolar;
    private JComboBox<String> comboCategoria, comboPrecio;
    private DefaultListModel<Producto> modeloCarrito;
    private JLabel lblTotal;
    private double tasaDolar = 1.0;
    private double totalCarrito = 0.0;
    private List<Producto> productos = new ArrayList<>();

    public TiendaAgraz_cliente() {
        setSize(1000, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setIconImage(new ImageIcon(getClass().getResource("/iconos/icon.png")).getImage());
        setLocationRelativeTo(null);

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(Color.WHITE);

        JPanel encabezado = new JPanel(new BorderLayout());
        encabezado.setBackground(Color.WHITE);
        encabezado.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Panel de tasa del dolar + icono
        JPanel panelTasa = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelTasa.setBackground(Color.WHITE);
        panelTasa.add(new JLabel("Tasa del Dólar (Bs): "));
        tfTasaDolar = new JTextField("1.0", 6);
        tfTasaDolar.setFont(new Font("Arial", Font.PLAIN, 12));
        panelTasa.add(tfTasaDolar);

        URL urlDolar = getClass().getResource("/iconos/dolar.png");
        if (urlDolar != null) {
            ImageIcon dolarIcon = new ImageIcon(urlDolar);
            Image img = dolarIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            panelTasa.add(new JLabel(new ImageIcon(img)));
        }

        encabezado.add(panelTasa, BorderLayout.WEST);

        // Filtros + búsqueda
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
        panelCarrito.setBorder(BorderFactory.createTitledBorder("🛒 Carrito"));
        panelCarrito.setPreferredSize(new Dimension(250, 0));

        modeloCarrito = new DefaultListModel<>();
        JList<Producto> listaCarrito = new JList<>(modeloCarrito);
        JScrollPane scrollCarrito = new JScrollPane(listaCarrito);
        scrollCarrito.getViewport().setBackground(Color.WHITE);
        panelCarrito.add(scrollCarrito, BorderLayout.CENTER);
        
        // Panel para botones eliminar y vaciar
        JPanel panelBotonesCarrito = new JPanel(new GridLayout(2, 1, 5, 5));
        panelBotonesCarrito.setBackground(Color.WHITE);

        JButton boton_Eliminar = new JButton("Eliminar Producto");
        boton_Eliminar.setBackground(Color.BLACK);
        boton_Eliminar.setForeground(Color.WHITE);
        boton_Eliminar.setEnabled(false); // Deshabilitado hasta que se seleccione un producto
        
        boton_Eliminar.addActionListener(e -> {
            Producto seleccionado = listaCarrito.getSelectedValue();
            if (seleccionado != null) {
                modeloCarrito.removeElement(seleccionado);
                seleccionado.incrementarStock();
                totalCarrito -= seleccionado.getPrecioBs(tasaDolar);
                actualizarTotal();
                mostrarProductos();
            }
        });

        JButton boton_Vaciar = new JButton("Vaciar Carrito");
        boton_Vaciar.setBackground(Color.BLACK);
        boton_Vaciar.setForeground(Color.WHITE);
        boton_Vaciar.addActionListener(e -> {
            // Incrementar stock para todos los productos en carrito
            for (int i = 0; i < modeloCarrito.getSize(); i++) {
                Producto p = modeloCarrito.getElementAt(i);
                p.incrementarStock();
            }
            modeloCarrito.clear();
            totalCarrito = 0.0;
            actualizarTotal();
            mostrarProductos();
        });

        panelBotonesCarrito.add(boton_Eliminar);
        panelBotonesCarrito.add(boton_Vaciar);

        panelCarrito.add(panelBotonesCarrito, BorderLayout.NORTH);

        lblTotal = new JLabel("Total: Bs. 0.00");
        lblTotal.setFont(new Font("Arial", Font.BOLD, 14));
        lblTotal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelCarrito.add(lblTotal, BorderLayout.SOUTH);

        panelPrincipal.add(panelCarrito, BorderLayout.WEST);

        // PANEL PRODUCTOS
        panelProductos = new JPanel(new GridLayout(0, 3, 20, 20));
        panelProductos.setBackground(Color.WHITE);
        panelProductos.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

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
        
        listaCarrito.addListSelectionListener(e -> boton_Eliminar.setEnabled(listaCarrito.getSelectedIndex() != -1));
        listaCarrito.addListSelectionListener(e -> boton_Vaciar.setEnabled(listaCarrito.getSelectedIndex() != -1));
        // con 0 queda 1 producto aun, debe ser un numero menor al que debe quedar, osea -1
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
        tarjeta.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        tarjeta.setPreferredSize(new Dimension(200, 240));

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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TiendaAgraz_cliente().setVisible(true));
    }
}
