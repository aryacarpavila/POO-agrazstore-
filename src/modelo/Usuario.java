import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Usuario {
    protected String nombre;
    protected String correo;
    protected String user;
    protected String contraseña;

    public Usuario(String nombre, String user, String correo, String contraseña) {
        this.nombre = nombre;
        this.user = user;
        this.correo = correo;
        this.contraseña = contraseña;
    }

    // Getters
    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public String getUser() {
        return user;
    }

    public String getContraseña() {
        return contraseña;
    }

    // Guardar el usuario en un archivo  (nomas de ejemplo para el prototipo)
    public void guardarUsuario() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("usuarios.txt", true))) {
            writer.write(this.nombre + "," + this.user + "," + this.correo + "," + this.contraseña);
            writer.newLine();
        } catch (IOException e) {
        }
    }
}

