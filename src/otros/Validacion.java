package controlador;

import modelo.Producto;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.JOptionPane;
import javax.swing.JFrame;


public class Validacion {

    private static final Scanner scanner = new Scanner(System.in);

    public static boolean esStringValido(String s) {
        return s != null && !s.trim().isEmpty();
    }

    public static boolean esPrecioValido(int precio) {
        return precio > 0;
    }

    // Valida que la talla sea una de las permitidas.
    public static boolean esTallaValida(char talla) {
        return talla == 'S' || talla == 'M' || talla == 'L' || talla == 'X';
    }

    public static boolean esProductoValido(Producto p) {
        if (p == null) return false;
        return esStringValido(p.getNombre())
                && esStringValido(p.getDescripcion())
                && esPrecioValido(p.getPrecioDolar())
                && esStringValido(p.getCategoria())
                && esStringValido(p.getColor())
                && esTallaValida(p.getTalla());
    }

    public static String pedirStringValido(String mensaje) {
        String input;
        do {
            System.out.print(mensaje);
            input = scanner.nextLine();
        } while (!esStringValido(input));
        return input;
    }

    public static int pedirPrecioValido(String mensaje) {
        int precio = -1;
        while (true) {
            try {
                System.out.print(mensaje);
                precio = Integer.parseInt(scanner.nextLine());
                if (esPrecioValido(precio)) break;
            } catch (NumberFormatException e) {
            }
            System.out.println("❌ Precio inválido. Debe ser un número mayor que 0.");
        }
        return precio;
    }

    public static char pedirTallaValida(String mensaje) {
        char talla;
        while (true) {
            System.out.print(mensaje);
            String input = scanner.nextLine().toUpperCase();
            if (input.length() == 1) {
                talla = input.charAt(0);
                if (esTallaValida(talla)) break;
            }
            System.out.println("❌ Talla inválida. Usa una de las siguientes: S, M, L, X.");
        }
        return talla;
    }
    
     public static List<String> validarDatosBancarios(String numeroCuenta, String numeroDocumento, 
                                                   String tipoDocumento) {
        List<String> errores = new ArrayList<>();
        
        // Validar número de cuenta
        if (numeroCuenta.trim().isEmpty()) {
            errores.add("El número de cuenta es obligatorio");
        } else if (!numeroCuenta.matches("[0-9]+")) {
            errores.add("El número de cuenta solo debe contener dígitos");
        } else if (numeroCuenta.length() < 5 || numeroCuenta.length() > 20) {
            errores.add("El número de cuenta debe tener entre 5 y 20 dígitos");
        }
        
        // Validar número de documento
        if (numeroDocumento.trim().isEmpty()) {
            errores.add("El número de documento es obligatorio");
        } else {
            switch (tipoDocumento) {
                case "Cédula" -> {
                    if (!numeroDocumento.matches("[0-9]{6,8}")) {
                        errores.add("La cédula debe tener entre 6 y 8 dígitos");
                    }
                }
                case "Pasaporte" -> {
                    if (!numeroDocumento.matches("[a-zA-Z0-9]{6,12}")) {
                        errores.add("El pasaporte debe tener entre 6 y 12 caracteres alfanuméricos");
                    }
                }
                case "RIF" -> {
                    if (!numeroDocumento.matches("[JGVEP][-]?[0-9]{8}[-]?[0-9]")) {
                        errores.add("El RIF debe tener formato J-12345678-9");
                    }
                }
            }
        }
        
        return errores;
    }
    
    public static void mostrarErrores(JFrame parent, List<String> errores) {
        StringBuilder mensaje = new StringBuilder("Por favor corrija los siguientes errores:\n\n");
        for (String error : errores) {
            mensaje.append("• ").append(error).append("\n");
        }
        JOptionPane.showMessageDialog(parent, 
            mensaje.toString(), 
            "Error en el formulario", 
            JOptionPane.ERROR_MESSAGE);
    }

}
