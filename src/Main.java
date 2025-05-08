
import java.util.Scanner;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

/**
 *
 * @author arya
 */
public class Main {

    /**
     * @param args the command line arguments
     */
        public static void main(String[] args) {
        // Creamos algunos usuarios de prueba
        Usuario admin = new Usuario("admin", "1234", true);
        Usuario cliente = new Usuario("cliente", "abcd", false);

            try (Scanner scanner = new Scanner(System.in)) {
                System.out.println("==== Iniciar Sesion ====");
                System.out.print("Usuario: ");
                String usuarioIngresado = scanner.nextLine();
                
                System.out.print("Contraseña: ");
                String contraseñaIngresada = scanner.nextLine();
                
                // Verificar contra usuarios registrados
                if (admin.validarCredenciales(usuarioIngresado, contraseñaIngresada)) {
                    System.out.println("¡Bienvenido de vuelta, admin!");
                } else if (cliente.validarCredenciales(usuarioIngresado, contraseñaIngresada)) {
                    System.out.println("¡Bienvenido de vuelta, " + cliente.getNombreUsuario() + "!");
                } else {
                    System.out.println("Credenciales incorrectas. Intenta de nuevo.");
                }   }
    }
    
}
