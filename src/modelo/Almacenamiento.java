package modelo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Almacenamiento {
    public static double tasaDolar = 105.45;
    public static List<Producto> productos = new ArrayList<>();

    static {
        try {
            cargarProductosDesdeJSON();
        } catch (IOException ex) {
            Logger.getLogger(Almacenamiento.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Cargar productos desde JSON
    private static void cargarProductosDesdeJSON() throws IOException {
    try (FileReader reader = new FileReader("base_de_datos/productos.json")) {
        Gson gson = new Gson();
        productos = gson.fromJson(reader, new TypeToken<List<Producto>>() {}.getType());
        if (productos == null) { 
            productos = new ArrayList<>();
        }
    } catch (IOException e) {
        
        }
    }

    // Guardar productos en JSON
    public static void guardarProductosEnJSON() {
    try (FileWriter writer = new FileWriter("base_de_datos/productos.json")) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create(); 
        gson.toJson(productos, writer);
    } catch (IOException e) {
        System.err.println("Error al guardar productos en JSON.");
        }
    }
}
