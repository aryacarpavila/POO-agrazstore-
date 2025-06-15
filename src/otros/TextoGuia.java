
package otros;
import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class TextoGuia {

    public static void aplicarTextoGuia(JTextField campoTexto, String textoGuia) {
        campoTexto.setForeground(Color.GRAY);
        campoTexto.setText(textoGuia);

        campoTexto.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (campoTexto.getText().equals(textoGuia)) {
                    campoTexto.setText("");
                    campoTexto.setForeground(Color.WHITE);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (campoTexto.getText().isEmpty()) {
                    campoTexto.setForeground(Color.GRAY);
                    campoTexto.setText(textoGuia);
                }
            }
        });
    }

    public static void aplicarTextoGuia(JPasswordField campoContraseña, String textoGuia) {
        campoContraseña.setForeground(Color.GRAY);
        campoContraseña.setText(textoGuia);
        campoContraseña.setEchoChar((char) 0); 

        campoContraseña.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (String.valueOf(campoContraseña.getPassword()).equals(textoGuia)) {
                    campoContraseña.setText("");
                    campoContraseña.setForeground(Color.WHITE);
                    campoContraseña.setEchoChar('●'); 
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (String.valueOf(campoContraseña.getPassword()).isEmpty()) {
                    campoContraseña.setForeground(Color.GRAY);
                    campoContraseña.setEchoChar((char) 0); 
                    campoContraseña.setText(textoGuia);
                }
            }
        });
    }
}
