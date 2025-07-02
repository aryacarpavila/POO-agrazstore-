package vista;

import modelo.Usuario;
import modelo.Almacenamiento;
import modelo.Moneda;
import otros.ProductoCarrito;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ResumenPedido extends JDialog {

    public ResumenPedido(JFrame owner, List<ProductoCarrito> carrito, double total, Usuario comprador, Runnable onPagar) {
        setSize(380, 380); // Altura reducida por el menor espaciado
        setResizable(false);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout());

        // ---------- Panel cabecera ----------
        JPanel cabecera = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.BLACK);
                g.fillRect(0, 0, getWidth(), 60);

                g.setColor(Color.WHITE);
                g.setFont(new Font("Open Sans", Font.BOLD, 18));
                String texto = "RESUMEN DEL PEDIDO";
                FontMetrics fm = g.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(texto)) / 2;
                int y = 35;
                g.drawString(texto, x, y);

                g.setColor(Color.WHITE);
                g.fillRect(0, 60, getWidth(), 2);
                g.setColor(Color.BLACK);
                g.fillRect(0, 62, getWidth(), 2);
            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(380, 64);
            }
        };
        add(cabecera, BorderLayout.NORTH);

        // ---------- Panel de contenido ----------
        JPanel contenidoPrincipal = new JPanel(new BorderLayout());
        contenidoPrincipal.setBackground(Color.WHITE);
        
        JPanel productosPanel = new JPanel();
        productosPanel.setLayout(new BoxLayout(productosPanel, BoxLayout.Y_AXIS));
        productosPanel.setBackground(Color.WHITE);
        productosPanel.setBorder(BorderFactory.createEmptyBorder(15, 40, 15, 40));

        for (ProductoCarrito producto : carrito) {
            JPanel panelItem = new JPanel();
            panelItem.setLayout(new BoxLayout(panelItem, BoxLayout.Y_AXIS));
            panelItem.setBackground(Color.WHITE);
            panelItem.setAlignmentX(Component.CENTER_ALIGNMENT);
            panelItem.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0)); // Reducido el padding vertical

            JLabel nombre = new JLabel("Producto: " + producto.getProducto().getNombre());
            JLabel cantidad = new JLabel("Cantidad: " + producto.getCantidad());
            JLabel precio = new JLabel("Precio: Bs. " + String.format("%,.2f", producto.getTotalBs(Almacenamiento.tasaDolar)).replace(",", "."));

            Font fuente = new Font("Open Sans", Font.PLAIN, 14);
            nombre.setFont(fuente);
            cantidad.setFont(fuente);
            precio.setFont(fuente);

            nombre.setAlignmentX(Component.CENTER_ALIGNMENT);
            cantidad.setAlignmentX(Component.CENTER_ALIGNMENT);
            precio.setAlignmentX(Component.CENTER_ALIGNMENT);

            nombre.setBorder(BorderFactory.createEmptyBorder(0, 0, 2, 0)); // Reducido
            cantidad.setBorder(BorderFactory.createEmptyBorder(0, 0, 2, 0)); // Reducido
            precio.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0));   // Reducido

            panelItem.add(nombre);
            panelItem.add(cantidad);
            panelItem.add(precio);
            
            productosPanel.add(panelItem);
            productosPanel.add(Box.createVerticalStrut(5)); // Espaciado reducido entre productos
        }

        // Total
        JPanel totalPanel = new JPanel();
        totalPanel.setLayout(new BoxLayout(totalPanel, BoxLayout.Y_AXIS));
        totalPanel.setBackground(Color.WHITE);
        totalPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        totalPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 10, 0)); // Reducido
        
        JSeparator separator = new JSeparator();
        separator.setMaximumSize(new Dimension(250, 1));
        separator.setAlignmentX(Component.CENTER_ALIGNMENT);
        separator.setForeground(new Color(200, 200, 200));
        totalPanel.add(separator);
        totalPanel.add(Box.createVerticalStrut(5)); // Reducido
        
        JLabel totalText = new JLabel("Total:");
        totalText.setFont(new Font("Open Sans", Font.BOLD, 16));
        totalText.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel totalAmount = new JLabel("Bs. " + String.format("%,.2f", total).replace(",", "."));
        totalAmount.setFont(new Font("Open Sans", Font.BOLD, 16));
        totalAmount.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        totalPanel.add(totalText);
        totalPanel.add(totalAmount);
        
        productosPanel.add(totalPanel);
        
        JScrollPane scrollPane = new JScrollPane(productosPanel);
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        contenidoPrincipal.add(scrollPane, BorderLayout.CENTER);

        // ---------- Botón de pago ----------
        JPanel abajo = new JPanel();
        abajo.setLayout(new BoxLayout(abajo, BoxLayout.Y_AXIS));
        abajo.setBackground(Color.WHITE);
        abajo.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        
        JButton pagar = new JButton("Ir a pagar");
        pagar.setAlignmentX(Component.CENTER_ALIGNMENT);
        pagar.setFocusPainted(false);
        pagar.setBackground(Color.BLACK);
        pagar.setForeground(Color.WHITE);
        pagar.setFont(new Font("Open Sans", Font.BOLD, 14));
        pagar.setPreferredSize(new Dimension(150, 40));
        pagar.setMaximumSize(new Dimension(150, 40));
        pagar.addActionListener(e -> {
            dispose();
            onPagar.run();
        });
        
        abajo.add(pagar);
        contenidoPrincipal.add(abajo, BorderLayout.SOUTH);
        
        add(contenidoPrincipal, BorderLayout.CENTER);
    }
}