package modelo;

import java.io.File;
import java.io.FileWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.google.gson.Gson;
import java.io.FileReader;
import java.io.IOException;

public class Moneda {

    private static final Pattern FORMATO_NUMERO = Pattern.compile("(\\d+)(\\.|,)?(\\d{0,2})?");
    private static final String RUTA_ARCHIVO = "base_de_datos/tasa_dolar.json";
    private static final Gson gson = new Gson();

    public static String formatear(String numero) {
        Matcher matcher = FORMATO_NUMERO.matcher(numero.replace(",", "."));
        if (matcher.matches()) {
            String enteros = matcher.group(1);
            String decimales = matcher.group(3);

            if (decimales == null) decimales = "";
            if (decimales.length() == 0) decimales = "00";
            else if (decimales.length() == 1) decimales += "0";
            else if (decimales.length() > 2) decimales = decimales.substring(0,2);

            return enteros + "," + decimales;
        } else {
            return numero + ",00"; 
        }
    }

    public static String formatear(double numero) {
        return formatear(String.format("%.2f", numero));
    }
    
    public static void guardarTasa(double tasa) {
        try {
            File carpeta = new File("base_de_datos");
            if (!carpeta.exists()) {
                carpeta.mkdirs();
            }

            try (FileWriter writer = new FileWriter(RUTA_ARCHIVO)) {
                gson.toJson(tasa, writer);
            }
        } catch (IOException e) {
        }
    }

    public static double cargarTasa() {
        try {
            File archivo = new File(RUTA_ARCHIVO);
            if (!archivo.exists()) {
                return 1.0; // valor por defecto si no existe
            }
            double tasa;
            try (FileReader reader = new FileReader(archivo)) {
                tasa = gson.fromJson(reader, Double.class);
            }
            return tasa;
        } catch (IOException e) {
            return 1.0;
        }
    }

}

