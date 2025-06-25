package controlador;

import java.util.Scanner;
import modelo.Producto;

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
}
