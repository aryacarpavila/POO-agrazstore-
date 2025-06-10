package Interfaz.proyecto;



/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author leona
 */


import com.proyecto.Producto;
import com.proyecto.Validacion;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class TiendaAgraz extends JFrame {

    private ArrayList<Producto> productos = new ArrayList<>();
    private JPanel panelProductos;
    private DefaultListModel<Producto> carritoModel = new DefaultListModel<>();
    private JList<Producto> carritoList;

    private JComboBox<String> filtroCategoria;
    private JComboBox<String> filtroPrecio;

    private final String rutaImagenPorDefecto = getClass().getResource("/imagenes/Toad.jpeg").toString();



    public TiendaAgraz() {


        setTitle("Tienda Agraz");
        setSize(900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        cargarProductosDeEjemplo();

        JPanel panelFiltros = new JPanel();
        filtroCategoria = new JComboBox<>(new String[] {"Todas", "Calzado", "Ropa", "Accesorios"});
        filtroPrecio = new JComboBox<>(new String[] {"Todos", "< 50000", "50000 - 100000", "> 100000"});

        JButton btnFiltrar = new JButton("Filtrar");
        btnFiltrar.addActionListener(e -> actualizarProductosFiltrados());

        panelFiltros.add(new JLabel("Categoría:"));
        panelFiltros.add(filtroCategoria);
        panelFiltros.add(new JLabel("Precio:"));
        panelFiltros.add(filtroPrecio);
        panelFiltros.add(btnFiltrar);

        add(panelFiltros, BorderLayout.NORTH);

        panelProductos = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 15));
        JScrollPane scrollProductos = new JScrollPane(panelProductos);
        add(scrollProductos, BorderLayout.CENTER);

        JPanel panelDerecho = new JPanel();
        panelDerecho.setLayout(new BoxLayout(panelDerecho, BoxLayout.Y_AXIS));
        panelDerecho.setPreferredSize(new Dimension(250, getHeight()));

        JLabel labelCarrito = new JLabel("Carrito de compra:");
        carritoList = new JList<>(carritoModel);
        JScrollPane scrollCarrito = new JScrollPane(carritoList);
        scrollCarrito.setPreferredSize(new Dimension(230, 300));

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

        actualizarProductosFiltrados();
    }

    private void cargarProductosDeEjemplo() {
        productos.add(new Producto("Walter Original", "Zapatos cómodos", 100000, 100, "Calzado", "Negro", 'M', rutaImagenPorDefecto));
        productos.add(new Producto("Walter Pro", "Zapatos para correr", 120000, 120, "Calzado", "Azul", 'L', rutaImagenPorDefecto));
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
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        ImageIcon icon = new ImageIcon(p.getRutaImagen());
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
        info.setBackground(panel.getBackground());
        panel.add(info, BorderLayout.CENTER);

        JButton btnAgregar = new JButton("Agregar al carrito");
        btnAgregar.addActionListener(e -> {
            carritoModel.addElement(p);
            JOptionPane.showMessageDialog(this, p.getNombre() + " agregado al carrito.");
        });
        panel.add(btnAgregar, BorderLayout.SOUTH);

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

        Object[] mensaje = {
            "Nombre:", tfNombre,
            "Descripción:", tfDescripcion,
            "Precio Bs:", tfPrecioBs,
            "Precio Dólar:", tfPrecioDolar,
            "Categoría:", tfCategoria,
            "Color:", tfColor,
            "Talla (S, M, L, X):", tfTalla
        };

        int opcion = JOptionPane.showConfirmDialog(this, mensaje, "Crear Producto", JOptionPane.OK_CANCEL_OPTION);
        if (opcion == JOptionPane.OK_OPTION) {
            try {
                String nombre = tfNombre.getText();
                String descripcion = tfDescripcion.getText();
                int precioBs = Integer.parseInt(tfPrecioBs.getText());
                int precioDolar = Integer.parseInt(tfPrecioDolar.getText());
                String categoria = tfCategoria.getText();
                String color = tfColor.getText();
                char talla = tfTalla.getText().toUpperCase().charAt(0);
                String rutaImagen = rutaImagenPorDefecto;

                Producto nuevoProducto = new Producto(nombre, descripcion, precioBs, precioDolar, categoria, color, talla, rutaImagen);
                productos.add(nuevoProducto);
                actualizarProductosFiltrados();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al crear producto: " + ex.getMessage());
            }
        }
    }

    private void eliminarProductoDelCarrito() {
        Producto seleccionado = carritoList.getSelectedValue();
        if (seleccionado != null) {
            int confirm = JOptionPane.showConfirmDialog(this, "¿Eliminar producto seleccionado del carrito?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
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
