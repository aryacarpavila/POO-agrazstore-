
package modelo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Moneda {

    private static final Pattern FORMATO_NUMERO = Pattern.compile("(\\d+)(\\.|,)?(\\d{0,2})?");

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

}

