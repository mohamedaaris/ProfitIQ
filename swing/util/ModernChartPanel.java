// Temporarily comment out JFreeChart imports until we add the libraries
/*
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;

/**
 * Modern chart panel for creating interactive data visualizations
 */
public class ModernChartPanel extends JPanel {
    private JPanel chartPanel;
    private JPanel headerPanel;
    private JLabel titleLabel;
    private String chartType;
    
    public ModernChartPanel(String chartType) {
        this("Chart", chartType);
    }
    
    public ModernChartPanel(String title, String chartType) {
        this.chartType = chartType;
        setLayout(new BorderLayout());
        setBackground(ProfitIQ.CARD_DARK);
        setBorder(new RoundedBorder(new Color(51, 65, 85), 10));
        
        // Create header panel
        headerPanel = createHeaderPanel(title);
        add(headerPanel, BorderLayout.NORTH);
        
        // Create placeholder panel instead of charts until JFreeChart is available
        JPanel placeholderPanel = new JPanel(new BorderLayout());
        placeholderPanel.setBackground(ProfitIQ.CARD_DARK);
        placeholderPanel.setPreferredSize(new Dimension(400, 300));
        
        JLabel placeholderLabel = new JLabel("Chart: " + chartType, SwingConstants.CENTER);
        placeholderLabel.setForeground(ProfitIQ.TEXT_LIGHT);
        placeholderLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        placeholderPanel.add(placeholderLabel, BorderLayout.CENTER);
        
        add(placeholderPanel, BorderLayout.CENTER);
        
        // Create appropriate chart based on type
        if (chartType.equals("line")) {
            createPlaceholderLineChart(placeholderPanel);
        } else if (chartType.equals("bar")) {
            createPlaceholderBarChart(placeholderPanel);
        } else if (chartType.equals("donut") || chartType.equals("pie")) {
            createPlaceholderDonutChart(placeholderPanel);
        } else if (chartType.equals("map")) {
            createPlaceholderMapChart(placeholderPanel);
        }
    }
    
    private JPanel createHeaderPanel(String title) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(ProfitIQ.CARD_DARK);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 10, 15));
        
        titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setForeground(Color.WHITE);
        
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        controlPanel.setBackground(ProfitIQ.CARD_DARK);
        
        // Add controls like filters, date range, etc.
        JButton moreBtn = new JButton("•••");
        moreBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        moreBtn.setForeground(new Color(148, 163, 184));
        moreBtn.setBackground(ProfitIQ.CARD_DARK);
        moreBtn.setBorder(BorderFactory.createEmptyBorder());
        moreBtn.setFocusPainted(false);
        moreBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        controlPanel.add(moreBtn);
        
        panel.add(titleLabel, BorderLayout.WEST);
        panel.add(controlPanel, BorderLayout.EAST);
        
        return panel;
    }
    
    private void createPlaceholderLineChart(JPanel container) {
        // Create a simple placeholder for a line chart
        JPanel chartPlaceholder = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                int width = getWidth();
                int height = getHeight();
                int padding = 30;
                
                // Draw axes
                g2d.setColor(new Color(100, 116, 139));
                g2d.drawLine(padding, height - padding, width - padding, height - padding); // X-axis
                g2d.drawLine(padding, padding, padding, height - padding); // Y-axis
                
                // Draw line chart
                int[] points = {50, 80, 40, 90, 60, 75, 45};
                int pointCount = points.length;
                int xStep = (width - 2 * padding) / (pointCount - 1);
                
                g2d.setColor(ProfitIQ.PRIMARY_COLOR);
                g2d.setStroke(new BasicStroke(2f));
                
                for (int i = 0; i < pointCount - 1; i++) {
                    int x1 = padding + i * xStep;
                    int y1 = height - padding - (points[i] * (height - 2 * padding) / 100);
                    int x2 = padding + (i + 1) * xStep;
                    int y2 = height - padding - (points[i + 1] * (height - 2 * padding) / 100);
                    
                    g2d.drawLine(x1, y1, x2, y2);
                    
                    // Draw points
                    g2d.setColor(Color.WHITE);
                    g2d.fillOval(x1 - 4, y1 - 4, 8, 8);
                    if (i == pointCount - 2) {
                        g2d.fillOval(x2 - 4, y2 - 4, 8, 8);
                    }
                    g2d.setColor(ProfitIQ.PRIMARY_COLOR);
                }
                
                g2d.dispose();
            }
        };
        
        chartPlaceholder.setBackground(ProfitIQ.CARD_DARK);
        container.add(chartPlaceholder, BorderLayout.CENTER);
    }
    
    private void createPlaceholderBarChart(JPanel container) {
        // Create a simple placeholder for a bar chart
        JPanel chartPlaceholder = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                int width = getWidth();
                int height = getHeight();
                int padding = 30;
                
                // Draw axes
                g2d.setColor(new Color(100, 116, 139));
                g2d.drawLine(padding, height - padding, width - padding, height - padding); // X-axis
                g2d.drawLine(padding, padding, padding, height - padding); // Y-axis
                
                // Draw bars
                int[] values = {60, 80, 40, 90, 30, 70};
                int barCount = values.length;
                int barWidth = (width - 2 * padding) / (barCount * 2);
                int gap = barWidth / 2;
                
                for (int i = 0; i < barCount; i++) {
                    int x = padding + gap + i * (barWidth + gap);
                    int barHeight = values[i] * (height - 2 * padding) / 100;
                    int y = height - padding - barHeight;
                    
                    g2d.setColor(ProfitIQ.PRIMARY_COLOR);
                    g2d.fillRoundRect(x, y, barWidth, barHeight, 8, 8);
                    
                    // Draw value
                    g2d.setColor(Color.WHITE);
                    g2d.setFont(new Font("Segoe UI", Font.PLAIN, 10));
                    String valueStr = String.valueOf(values[i]);
                    FontMetrics fm = g2d.getFontMetrics();
                    int textWidth = fm.stringWidth(valueStr);
                    g2d.drawString(valueStr, x + (barWidth - textWidth) / 2, y - 5);
                }
                
                g2d.dispose();
            }
        };
        
        chartPlaceholder.setBackground(ProfitIQ.CARD_DARK);
        container.add(chartPlaceholder, BorderLayout.CENTER);
    }
    
    private void createPlaceholderDonutChart(JPanel container) {
        // Create a simple placeholder for a donut chart
        JPanel chartPlaceholder = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                int width = getWidth();
                int height = getHeight();
                int size = Math.min(width, height) - 60;
                int x = (width - size) / 2;
                int y = (height - size) / 2;
                
                // Draw donut segments
                Color[] colors = {
                    ProfitIQ.PRIMARY_COLOR,
                    ProfitIQ.SUCCESS_COLOR,
                    ProfitIQ.DANGER_COLOR,
                    new Color(79, 70, 229) // Indigo
                };
                
                int[] values = {40, 25, 20, 15};
                int total = 0;
                for (int value : values) {
                    total += value;
                }
                
                int startAngle = 0;
                for (int i = 0; i < values.length; i++) {
                    int arcAngle = values[i] * 360 / total;
                    
                    g2d.setColor(colors[i % colors.length]);
                    g2d.fillArc(x, y, size, size, startAngle, arcAngle);
                    
                    startAngle += arcAngle;
                }
                
                // Draw inner circle for donut hole
                g2d.setColor(ProfitIQ.CARD_DARK);
                int innerSize = size / 2;
                int innerX = x + (size - innerSize) / 2;
                int innerY = y + (size - innerSize) / 2;
                g2d.fillOval(innerX, innerY, innerSize, innerSize);
                
                // Draw percentage in center
                g2d.setColor(Color.WHITE);
                g2d.setFont(new Font("Segoe UI", Font.BOLD, 20));
                String percentText = "75%";
                FontMetrics fm = g2d.getFontMetrics();
                int textWidth = fm.stringWidth(percentText);
                int textHeight = fm.getHeight();
                g2d.drawString(percentText, 
                    width / 2 - textWidth / 2, 
                    height / 2 + textHeight / 4);
                
                g2d.dispose();
            }
        };
        
        chartPlaceholder.setBackground(ProfitIQ.CARD_DARK);
        container.add(chartPlaceholder, BorderLayout.CENTER);
    }
    
    private void createPlaceholderMapChart(JPanel container) {
        // Create a simple placeholder for a map chart
        JPanel chartPlaceholder = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                int width = getWidth();
                int height = getHeight();
                
                // Draw map outline
                g2d.setColor(new Color(51, 65, 85));
                g2d.fillRoundRect(30, 30, width - 60, height - 60, 10, 10);
                
                // Draw some random "regions"
                g2d.setColor(new Color(100, 116, 139));
                g2d.fillRoundRect(50, 50, 80, 60, 5, 5);
                g2d.fillRoundRect(150, 70, 100, 80, 5, 5);
                g2d.fillRoundRect(70, 130, 120, 50, 5, 5);
                g2d.fillRoundRect(210, 150, 90, 70, 5, 5);
                
                // Draw some "data points"
                int[] xPoints = {80, 190, 120, 250};
                int[] yPoints = {70, 100, 150, 180};
                int[] sizes = {20, 35, 15, 25};
                
                for (int i = 0; i < xPoints.length; i++) {
                    g2d.setColor(ProfitIQ.PRIMARY_COLOR);
                    g2d.fillOval(xPoints[i] - sizes[i]/2, yPoints[i] - sizes[i]/2, sizes[i], sizes[i]);
                    
                    g2d.setColor(new Color(255, 255, 255, 100));
                    g2d.fillOval(xPoints[i] - sizes[i]/2 + 3, yPoints[i] - sizes[i]/2 + 3, sizes[i] - 6, sizes[i] - 6);
                }
                
                g2d.dispose();
            }
        };
        
        chartPlaceholder.setBackground(ProfitIQ.CARD_DARK);
        container.add(chartPlaceholder, BorderLayout.CENTER);
    }
}