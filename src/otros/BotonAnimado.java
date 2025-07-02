package otros;
import java.awt.*;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BotonAnimado extends JButton {

    public BotonAnimado(String texto) {
        super(texto);

        setBackground(Color.BLACK);
        setForeground(Color.WHITE);
        setFocusPainted(false);
        setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Animación al pasar el mouse
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                setBackground(new Color(30, 30, 30));
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                setBackground(Color.BLACK);
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
    }
}


