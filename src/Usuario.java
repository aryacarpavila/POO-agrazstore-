import java.io.*;

public class Usuario {
    protected String nombre;
    protected String correo;
    protected String user;
    protected String contraseña;
    
    // Constructor
    public Usuario(String nombre, String user, String correo, String contraseña) {
        this.nombre = nombre;
        this.user = user;
        this.correo = correo;
        this.contraseña = contraseña;
    }

    public Usuario(String user) {
        this.user = user;
    }

    // Métodos para obtener datos
    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public String getContraseña() {
        return contraseña;
    }
    
    // Guardar un nuevo usuario en un archivo
    public void guardarUsuario() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("usuarios.txt", true))) {
            writer.write(this.nombre + "," + this.user + "," + this.correo + "," + this.contraseña);
            writer.newLine();
        } catch (IOException e) {
        }
    }

    // Verificar si el usuario existe y su contraseña es correcta
    public static boolean verificarUsuario(String correo, String contraseña) {
        try (BufferedReader reader = new BufferedReader(new FileReader("usuarios.txt"))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos[1].equals(correo) && datos[2].equals(contraseña)) {
                    return true;
                }
            }
        } catch (IOException e) {
        }
        return false;
    }
}

