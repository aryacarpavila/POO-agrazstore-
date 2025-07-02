package otros;

import java.awt.*;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

/**
 * FlowLayout que envuelve componentes al llegar al borde.
 * Basado en WrapLayout de Rob Camick (https://tips4java.wordpress.com/2008/11/06/wrap-layout/)
 */
public class WrapLayout extends FlowLayout {

    public WrapLayout()                     { super(); }
    public WrapLayout(int align)            { super(align); }
    public WrapLayout(int align, int h, int v) { super(align, h, v); }

    @Override public Dimension preferredLayoutSize(Container target)  { return layoutSize(target, true); }
    @Override public Dimension minimumLayoutSize  (Container target)  {
        Dimension d = layoutSize(target, false);
        d.width -= (getHgap() + 1);
        return d;
    }

    private Dimension layoutSize(Container target, boolean preferred) {
        synchronized (target.getTreeLock()) {
            // 1) Averigua un ancho “real”
            int targetWidth = target.getWidth();
            if (targetWidth == 0) {
                // Busca hasta hallar un padre con ancho > 0 (ScrollPane → Viewport → …)
                Container c = target;
                while (c.getWidth() == 0 && c.getParent() != null)
                    c = c.getParent();
                targetWidth = c.getWidth();
                if (targetWidth == 0) targetWidth = Integer.MAX_VALUE;
            }

            int hgap = getHgap(), vgap = getVgap();
            Insets in = target.getInsets();
            int maxWidth = targetWidth - (in.left + in.right + hgap * 2);

            Dimension dim = new Dimension(0, 0);
            int rowWidth = 0, rowHeight = 0;

            for (Component m : target.getComponents()) {
                if (!m.isVisible()) continue;
                Dimension d = preferred ? m.getPreferredSize() : m.getMinimumSize();

                if (rowWidth + d.width > maxWidth) {          // ↙️  nueva fila
                    addRow(dim, rowWidth, rowHeight);
                    rowWidth = rowHeight = 0;
                }
                if (rowWidth != 0) rowWidth += hgap;          // gap ⬅️
                rowWidth += d.width;
                rowHeight = Math.max(rowHeight, d.height);
            }
            addRow(dim, rowWidth, rowHeight);

            dim.width  += in.left + in.right + hgap * 2;
            dim.height += in.top  + in.bottom + vgap * 2;

            // Ajuste cuando estamos dentro de un JScrollPane
            if (SwingUtilities.getAncestorOfClass(JScrollPane.class, target) != null && target.isValid()) {
                dim.width -= (hgap + 1);
            }
            return dim;
        }
    }

    private void addRow(Dimension dim, int rowWidth, int rowHeight) {
        dim.width  = Math.max(dim.width, rowWidth);
        if (dim.height > 0) dim.height += getVgap();
        dim.height += rowHeight;
    }
}
