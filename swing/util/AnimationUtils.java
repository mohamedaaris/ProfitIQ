package swing.util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AnimationUtils {
    
    /**
     * Fade in animation for components
     */
    public static void fadeIn(Component component, int duration) {
        component.setVisible(true);
        component.setOpaque(false);
        
        Timer timer = new Timer(50, new ActionListener() {
            float alpha = 0.0f;
            
            @Override
            public void actionPerformed(ActionEvent e) {
                alpha += 0.1f;
                if (alpha >= 1.0f) {
                    alpha = 1.0f;
                    ((Timer)e.getSource()).stop();
                }
                // Note: This is a simplified fade effect
                component.repaint();
            }
        });
        timer.start();
    }
    
    /**
     * Slide in animation from left
     */
    public static void slideInFromLeft(Component component, int duration) {
        component.setVisible(true);
        
        Timer timer = new Timer(50, new ActionListener() {
            int position = -component.getWidth();
            
            @Override
            public void actionPerformed(ActionEvent e) {
                position += 20;
                if (position >= 0) {
                    position = 0;
                    ((Timer)e.getSource()).stop();
                }
                component.setLocation(position, component.getY());
            }
        });
        timer.start();
    }
    
    /**
     * Slide in animation from right
     */
    public static void slideInFromRight(Component component, int duration) {
        component.setVisible(true);
        
        Timer timer = new Timer(50, new ActionListener() {
            int position = component.getParent().getWidth();
            
            @Override
            public void actionPerformed(ActionEvent e) {
                position -= 20;
                if (position <= component.getX()) {
                    position = component.getX();
                    ((Timer)e.getSource()).stop();
                }
                component.setLocation(position, component.getY());
            }
        });
        timer.start();
    }
    
    /**
     * Slide in animation from top
     */
    public static void slideInFromTop(Component component, int duration) {
        component.setVisible(true);
        
        Timer timer = new Timer(50, new ActionListener() {
            int position = -component.getHeight();
            
            @Override
            public void actionPerformed(ActionEvent e) {
                position += 20;
                if (position >= component.getY()) {
                    position = component.getY();
                    ((Timer)e.getSource()).stop();
                }
                component.setLocation(component.getX(), position);
            }
        });
        timer.start();
    }
    
    /**
     * Slide in animation from bottom
     */
    public static void slideInFromBottom(Component component, int duration) {
        component.setVisible(true);
        
        Timer timer = new Timer(50, new ActionListener() {
            int position = component.getParent().getHeight();
            
            @Override
            public void actionPerformed(ActionEvent e) {
                position -= 20;
                if (position <= component.getY()) {
                    position = component.getY();
                    ((Timer)e.getSource()).stop();
                }
                component.setLocation(component.getX(), position);
            }
        });
        timer.start();
    }
}