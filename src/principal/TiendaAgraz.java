import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import modelo.Producto;

public class TiendaAgraz extends JFrame {

    private ArrayList<Producto> productos = new ArrayList<>();
    private JPanel panelProductos;
    private DefaultListModel<Producto> carritoModel = new DefaultListModel<>();
    private JList<Producto> carritoList;
    private JComboBox<String> filtroCategoria;
    private JComboBox<String> filtroPrecio;
    private final String rutaImagenPorDefecto = getClass().getResource("/iconos/Toad.jpg").toString();
    private double tasaDolar = 1.0;
    private JTextField tfTasaDolar;

    public TiendaAgraz() {
        setTitle("Tienda Agraz");
        setSize(900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panelTop = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelTop.setBackground(Color.WHITE);
         
        panelTop.add(new JLabel("Tasa del dolar(Bs): "));
        tfTasaDolar = new JTextField("1.0", 6);
        
        tfTasaDolar.addActionListener(e -> actualizarPreciosDolar());
        panelTop.add(tfTasaDolar);

        cargarProductosDeEjemplo();

        JPanel panelFiltros = new JPanel();
        panelFiltros.setBackground(Color.WHITE);
        filtroCategoria = new JComboBox<>(new String[] {"Todas", "Calzado", "Ropa", "Accesorios"});
        filtroPrecio = new JComboBox<>(new String[] {"Todos", "< 50000", "50000 - 100000", "> 100000"});

        JLabel titleCard = new JLabel("AGRAZ STORE", SwingConstants.CENTER);
        titleCard.setFont(new Font("Arial", Font.BOLD, 36));
        titleCard.setForeground(Color.BLACK);
        titleCard.setOpaque(false);
        titleCard.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JPanel eastWrapper = new JPanel();
        eastWrapper.setLayout(new BoxLayout(eastWrapper, BoxLayout.X_AXIS));
        eastWrapper.setBackground(Color.WHITE);
        eastWrapper.add(panelFiltros);
        eastWrapper.add(Box.createRigidArea(new Dimension(20, 0)));
        eastWrapper.add(titleCard);

        JPanel northWrapper = new JPanel(new BorderLayout(10, 0));
        northWrapper.setBackground(Color.WHITE);
        northWrapper.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        northWrapper.add(panelTop, BorderLayout.CENTER);
        northWrapper.add(eastWrapper, BorderLayout.EAST);

        this.add(northWrapper, BorderLayout.NORTH);

        JButton btnFiltrar = new JButton("Filtrar");
        btnFiltrar.addActionListener(e -> actualizarProductosFiltrados());

        panelFiltros.add(new JLabel("Categoría:"));
        panelFiltros.add(filtroCategoria);
        panelFiltros.add(new JLabel("Precio:"));
        panelFiltros.add(filtroPrecio);
        panelFiltros.add(btnFiltrar);

        panelProductos = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 15));
        panelProductos.setBackground(Color.WHITE);
        JScrollPane scrollProductos = new JScrollPane(panelProductos);
        scrollProductos.getViewport().setBackground(Color.WHITE);
        add(scrollProductos, BorderLayout.CENTER);

        JPanel panelDerecho = new JPanel();
        panelDerecho.setLayout(new BoxLayout(panelDerecho, BoxLayout.Y_AXIS));
        panelDerecho.setPreferredSize(new Dimension(250, getHeight()));
        panelDerecho.setBackground(Color.WHITE);

        JLabel labelCarrito = new JLabel("Carrito de compra:");
        carritoList = new JList<>(carritoModel);
        JScrollPane scrollCarrito = new JScrollPane(carritoList);
        scrollCarrito.setPreferredSize(new Dimension(230, 300));
        scrollCarrito.getViewport().setBackground(Color.WHITE);

        JButton btnEliminarProducto = new JButton("Eliminar Producto del Carrito");
        btnEliminarProducto.addActionListener(e -> eliminarProductoDelCarrito());

        JButton btnCrearProducto = new JButton("Crear Producto");
        btnCrearProducto.addActionListener(e -> mostrarDialogoCrearProducto());

        panelDerecho.add(labelCarrito);
        panelDerecho.add(scrollCarrito);
        panelDerecho.add(Box.createRigidArea(new Dimension(0, 20)));
        panelDerecho.add(btnEliminarProducto);
        panelDerecho.add(Box.createRigidArea(new Dimension(0, 10)));
        panelDerecho.add(btnCrearProducto);

        add(panelDerecho, BorderLayout.EAST);

        aplicarEstiloSwing(this.getRootPane());
        actualizarProductosFiltrados();
    }

    private void aplicarEstiloSwing(JComponent comp) {
        Font fuente = new Font("Arial", Font.BOLD, 12);
        comp.setFont(fuente);
        comp.setBackground(Color.WHITE);
        comp.setForeground(Color.BLACK);

        if (comp instanceof JButton) {
            comp.setBackground(Color.BLACK);
            comp.setForeground(Color.WHITE);
        } else if (comp instanceof JTextArea) {
            comp.setBackground(Color.WHITE);
            comp.setForeground(Color.BLACK);
            comp.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        }

        if (comp instanceof Container) {
            for (Component child : ((Container) comp).getComponents()) {
                if (child instanceof JComponent) {
                    aplicarEstiloSwing((JComponent) child);
                }
            }
        }
    }

    private void actualizarPreciosDolar() {
        try {
            tasaDolar = Double.parseDouble(tfTasaDolar.getText());
            for (Producto p : productos) {
                p.actualizarDolar(tasaDolar);
            }
            refrescarProductos();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Tasa inválida");
        }
    }

    private void refrescarProductos() {
        panelProductos.removeAll();
        for (Producto p : productos) {
            panelProductos.add(crearPanelProducto(p));
        }
        panelProductos.revalidate();
        panelProductos.repaint();
    }

    private void cargarProductosDeEjemplo() {
        productos.add(new Producto("Walter Original", "Zapatos cómodos", 100000, 100, "Calzado", "Negro", 'M', rutaImagenPorDefecto, 2));
        productos.add(new Producto("Walter Pro", "Zapatos para correr", 120000, 120, "Calzado", "Azul", 'L', rutaImagenPorDefecto, 0));
    }

    private void actualizarProductosFiltrados() {
        panelProductos.removeAll();

        String categoria = (String) filtroCategoria.getSelectedItem();
        String rangoPrecio = (String) filtroPrecio.getSelectedItem();

        for (Producto p : productos) {
            if (!categoria.equals("Todas") && !p.getCategoria().equalsIgnoreCase(categoria)) continue;
            int precio = p.getPrecioBs();
            if (rangoPrecio.equals("< 50000") && precio >= 50000) continue;
            if (rangoPrecio.equals("50000 - 100000") && (precio < 50000 || precio > 100000)) continue;
            if (rangoPrecio.equals("> 100000") && precio <= 100000) continue;

            panelProductos.add(crearPanelProducto(p));
        }

        panelProductos.revalidate();
        panelProductos.repaint();
    }

    private JPanel crearPanelProducto(Producto p) {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(180, 250));
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        URL url = getClass().getResource("/iconos/Toad.jpg");
        ImageIcon icon;
        if (url != null) {
            icon = new ImageIcon(url);
        } else {
            BufferedImage imgDummy = new BufferedImage(180, 150, BufferedImage.TYPE_INT_RGB);
            Graphics g = imgDummy.getGraphics();
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(0, 0, 180, 150);
            g.dispose();
            icon = new ImageIcon(imgDummy);
        }
        Image imgEscalada = icon.getImage().getScaledInstance(180, 150, Image.SCALE_SMOOTH);
        JLabel labelImagen = new JLabel(new ImageIcon(imgEscalada));
        labelImagen.setHorizontalAlignment(JLabel.CENTER);
        labelImagen.setPreferredSize(new Dimension(180, 150));
        panel.add(labelImagen, BorderLayout.NORTH);

        JTextArea info = new JTextArea(
            p.getNombre() + "\n" +
            p.getDescripcion() + "\n" +
            "Bs " + p.getPrecioBs() + " | $ " + p.getPrecioDolar()
        );
        info.setEditable(false);
        info.setFont(new Font("Arial", Font.BOLD, 12));
        info.setForeground(Color.BLACK);
        info.setBackground(Color.WHITE);
        panel.add(info, BorderLayout.CENTER);

        if (p.getStock() > 0) {
            JButton btnAgregar = new JButton("Agregar al carrito");
            btnAgregar.addActionListener(e -> {
                if (p.getStock() > 0) {
                    p.reducirStock();
                    carritoModel.addElement(p);
                    JOptionPane.showMessageDialog(this, p.getNombre() + " agregado al carrito.");
                } else {
                    JOptionPane.showMessageDialog(this, "Producto agotado.");
                }
            });
            panel.add(btnAgregar, BorderLayout.SOUTH);
        } else {
            JLabel soldOutLabel = new JLabel("SOLD OUT", SwingConstants.CENTER);
            soldOutLabel.setFont(new Font("Arial", Font.BOLD, 16));
            soldOutLabel.setForeground(Color.RED);
            panel.add(soldOutLabel, BorderLayout.SOUTH);
        }

        return panel;
    }

    private void mostrarDialogoCrearProducto() {
        JTextField tfNombre = new JTextField();
        JTextField tfDescripcion = new JTextField();
        JTextField tfPrecioBs = new JTextField();
        JTextField tfPrecioDolar = new JTextField();
        JTextField tfCategoria = new JTextField();
        JTextField tfColor = new JTextField();
        JTextField tfTalla = new JTextField();
        JTextField tfStock = new JTextField();

        Object[] mensaje = {
            "Nombre:", tfNombre,
            "Descripción:", tfDescripcion,
            "Precio Bs:", tfPrecioBs,
            "Precio $:", tfPrecioDolar,
            "Categoría:", tfCategoria,
            "Color:", tfColor,
            "Talla (S,M,L,X):", tfTalla,
            "Stock inicial:", tfStock
        };

        int opcion = JOptionPane.showConfirmDialog(this, mensaje, "Crear Producto",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (opcion != JOptionPane.OK_OPTION) return;

        try {
            String nombre = tfNombre.getText().trim();
            String descripcion = tfDescripcion.getText().trim();
            int precioBs = Integer.parseInt(tfPrecioBs.getText().trim());
            int precioDolar = Integer.parseInt(tfPrecioDolar.getText().trim());
            String categoria = tfCategoria.getText().trim();
            String color = tfColor.getText().trim();
            char talla = tfTalla.getText().trim().toUpperCase().charAt(0);
            int stockNuevo = Integer.parseInt(tfStock.getText().trim());
            String rutaImagen = "/imagenes/Toad.jpeg";

            for (Producto p : productos) {
                if (p.getNombre().equalsIgnoreCase(nombre)) {
                    p.setStock(p.getStock() + stockNuevo);
                    JOptionPane.showMessageDialog(this, "Se aumentó el stock de \"" + nombre + "\" en " + stockNuevo);
                    refrescarProductos();
                    actualizarProductosFiltrados();
                    return;
                }
            }

            Producto nuevoProducto = new Producto(nombre, descripcion, precioBs, precioDolar, categoria, color, talla, rutaImagen, stockNuevo);
            productos.add(nuevoProducto);

            refrescarProductos();
            actualizarProductosFiltrados();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Precio y stock deben ser números enteros.", "Error de formato", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al crear producto: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarProductoDelCarrito() {
        Producto seleccionado = carritoList.getSelectedValue();
        if (seleccionado != null) {
            int confirm = JOptionPane.showConfirmDialog(this,
                "¿Eliminar producto seleccionado del carrito?",
                "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                seleccionado.incrementarStock();
                carritoModel.removeElement(seleccionado);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un producto en el carrito para eliminar.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TiendaAgraz().setVisible(true));
    }

}
