import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

/**
 * A modern interactive panel with hover effects and animations
 */
public class InteractivePanel extends JPanel {
    private Color normalBorderColor;
    private Color hoverBorderColor;
    private Color normalBackground;
    private Color hoverBackground;
    private int cornerRadius;
    private boolean isHovered = false;
    
    public InteractivePanel(Color normalBackground, Color hoverBackground, 
                           Color normalBorderColor, Color hoverBorderColor, 
                           int cornerRadius) {
        this.normalBackground = normalBackground;
        this.hoverBackground = hoverBackground;
        this.normalBorderColor = normalBorderColor;
        this.hoverBorderColor = hoverBorderColor;
        this.cornerRadius = cornerRadius;
        
        setOpaque(false);
        setBackground(normalBackground);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                isHovered = true;
                AnimationUtils.pulse(InteractivePanel.this, 300);
                repaint();
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                isHovered = false;
                repaint();
            }
            
            @Override
            public void mousePressed(MouseEvent e) {
                setBackground(hoverBackground.darker());
                repaint();
            }
            
            @Override
            public void mouseReleased(MouseEvent e) {
                setBackground(isHovered ? hoverBackground : normalBackground);
                repaint();
            }
        });
    }
    
    public InteractivePanel() {
        this(ProfitIQ.CARD_DARK, ProfitIQ.CARD_DARK.brighter(), 
             new Color(51, 65, 85), ProfitIQ.PRIMARY_COLOR, 10);
    }
    
    public void setPadding(int padding) {
        setBorder(BorderFactory.createEmptyBorder(padding, padding, padding, padding));
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Apply scale if being animated
        Object scaleObj = getClientProperty("scale");
        float scale = scaleObj != null ? (float) scaleObj : 1.0f;
        
        if (scale != 1.0f) {
            int centerX = getWidth() / 2;
            int centerY = getHeight() / 2;
            g2.translate(centerX, centerY);
            g2.scale(scale, scale);
            g2.translate(-centerX, -centerY);
        }
        
        // Apply alpha if being animated
        Object alphaObj = getClientProperty("alpha");
        float alpha = alphaObj != null ? (float) alphaObj : 1.0f;
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        
        // Fill background
        g2.setColor(isHovered ? hoverBackground : normalBackground);
        g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius));
        
        // Draw border
        g2.setColor(isHovered ? hoverBorderColor : normalBorderColor);
        g2.setStroke(new BasicStroke(1.5f));
        g2.draw(new RoundRectangle2D.Float(1, 1, getWidth() - 2, getHeight() - 2, cornerRadius, cornerRadius));
        
        g2.dispose();
    }
}