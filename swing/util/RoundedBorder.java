import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

/**
 * Custom border class to create rounded corners for panels and components
 */
public class RoundedBorder extends AbstractBorder {
    private Color color;
    private int radius;
    private int thickness;
    
    public RoundedBorder(Color color, int radius) {
        this(color, radius, 1);
    }
    
    public RoundedBorder(Color color, int radius, int thickness) {
        this.color = color;
        this.radius = radius;
        this.thickness = thickness;
    }
    
    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(color);
        g2d.setStroke(new BasicStroke(thickness));
        g2d.draw(new RoundRectangle2D.Double(x, y, width - 1, height - 1, radius, radius));
        g2d.dispose();
    }
    
    @Override
    public Insets getBorderInsets(Component c) {
        int value = radius / 2;
        return new Insets(value, value, value, value);
    }
    
    @Override
    public Insets getBorderInsets(Component c, Insets insets) {
        insets.left = insets.right = insets.top = insets.bottom = radius / 2;
        return insets;
    }
}