package modelo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Usuario {
    protected String nombre;
    protected String correo;
    protected String user;
    protected String contraseña;
    protected String tipo;

    public Usuario(String tipo, String nombre, String user, String correo, String contraseña) {
        this.tipo = tipo;
        this.nombre = nombre;
        this.user = user;
        this.correo = correo;
        this.contraseña = contraseña;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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

    // Guardar en archivo JSON
    public void guardarUsuarioJSON() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        List<Usuario> usuarios = new ArrayList<>();
     
        try (FileReader reader = new FileReader("base_de_datos/usuarios.json")) {
            Usuario[] existentes = gson.fromJson(reader, Usuario[].class);
            if (existentes != null) {
                usuarios.addAll(Arrays.asList(existentes));
            }
        } catch (IOException e) {         
            }

        usuarios.add(this);

        // Guardar todo en JSON nuevamente
        try (FileWriter writer = new FileWriter("base_de_datos/usuarios.json")) {
            gson.toJson(usuarios, writer);
        } catch (IOException e) {
        }
    }
}
