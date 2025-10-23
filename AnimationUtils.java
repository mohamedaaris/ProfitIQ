import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Consumer;

/**
 * Utility class for adding animations to Swing components
 */
public class AnimationUtils {
    
    /**
     * Creates a fade-in animation for a component
     * @param component The component to animate
     * @param duration Duration in milliseconds
     */
    public static void fadeIn(JComponent component, int duration) {
        component.setOpaque(false);
        component.setVisible(false);
        
        float[] alpha = new float[]{0.0f};
        Timer timer = new Timer(20, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                alpha[0] += 0.05f;
                if (alpha[0] >= 1.0f) {
                    alpha[0] = 1.0f;
                    ((Timer)e.getSource()).stop();
                }
                component.setVisible(true);
                component.putClientProperty("alpha", alpha[0]);
                component.repaint();
            }
        });
        timer.setInitialDelay(0);
        timer.start();
    }
    
    /**
     * Creates a slide-in animation for a component
     * @param component The component to animate
     * @param direction Direction to slide from ("left", "right", "top", "bottom")
     * @param duration Duration in milliseconds
     */
    public static void slideIn(JComponent component, String direction, int duration) {
        Rectangle originalBounds = component.getBounds();
        Rectangle startBounds = new Rectangle(originalBounds);
        
        switch (direction.toLowerCase()) {
            case "left":
                startBounds.x = -originalBounds.width;
                break;
            case "right":
                startBounds.x = component.getParent().getWidth();
                break;
            case "top":
                startBounds.y = -originalBounds.height;
                break;
            case "bottom":
                startBounds.y = component.getParent().getHeight();
                break;
        }
        
        component.setBounds(startBounds);
        
        int steps = duration / 20;
        int xStep = (originalBounds.x - startBounds.x) / steps;
        int yStep = (originalBounds.y - startBounds.y) / steps;
        
        Timer timer = new Timer(20, null);
        timer.addActionListener(new ActionListener() {
            int step = 0;
            
            @Override
            public void actionPerformed(ActionEvent e) {
                step++;
                if (step >= steps) {
                    component.setBounds(originalBounds);
                    timer.stop();
                    return;
                }
                
                Rectangle currentBounds = component.getBounds();
                currentBounds.x += xStep;
                currentBounds.y += yStep;
                component.setBounds(currentBounds);
            }
        });
        
        timer.setInitialDelay(0);
        timer.start();
    }
    
    /**
     * Creates a pulse animation for a component
     * @param component The component to animate
     * @param duration Duration in milliseconds
     */
    public static void pulse(JComponent component, int duration) {
        Timer timer = new Timer(50, null);
        timer.addActionListener(new ActionListener() {
            int step = 0;
            int maxSteps = duration / 50;
            float originalScale = 1.0f;
            float targetScale = 1.1f;
            
            @Override
            public void actionPerformed(ActionEvent e) {
                step++;
                if (step >= maxSteps) {
                    component.putClientProperty("scale", originalScale);
                    component.repaint();
                    timer.stop();
                    return;
                }
                
                float progress = (float)step / maxSteps;
                float scale;
                
                if (progress < 0.5f) {
                    // Scale up
                    scale = originalScale + (targetScale - originalScale) * (progress * 2);
                } else {
                    // Scale down
                    scale = targetScale - (targetScale - originalScale) * ((progress - 0.5f) * 2);
                }
                
                component.putClientProperty("scale", scale);
                component.repaint();
            }
        });
        
        timer.setInitialDelay(0);
        timer.start();
    }
    
    /**
     * Animates a numeric value change
     * @param startValue Starting value
     * @param endValue Ending value
     * @param duration Duration in milliseconds
     * @param updateConsumer Consumer function to handle value updates
     */
    public static void animateValue(double startValue, double endValue, int duration, Consumer<Double> updateConsumer) {
        Timer timer = new Timer(20, null);
        timer.addActionListener(new ActionListener() {
            int step = 0;
            int maxSteps = duration / 20;
            
            @Override
            public void actionPerformed(ActionEvent e) {
                step++;
                if (step >= maxSteps) {
                    updateConsumer.accept(endValue);
                    timer.stop();
                    return;
                }
                
                double progress = (double)step / maxSteps;
                double currentValue = startValue + (endValue - startValue) * progress;
                updateConsumer.accept(currentValue);
            }
        });
        
        timer.setInitialDelay(0);
        timer.start();
    }
}