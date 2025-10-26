import javafx.scene.Node;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.animation.*;
import javafx.util.Duration;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * Modern JavaFX Chart Components for ProfitIQ Dashboard
 * Provides advanced visualizations with animations and modern styling
 */
public class ModernChartFactory {
    
    // Color palette for charts
    private static final Color[] CHART_COLORS = {
        Color.web("#3b82f6"), // Blue
        Color.web("#0ea5e9"), // Sky blue
        Color.web("#22c55e"), // Green
        Color.web("#eab308"), // Yellow
        Color.web("#ef4444"), // Red
        Color.web("#8b5cf6"), // Purple
        Color.web("#f97316"), // Orange
        Color.web("#06b6d4")  // Cyan
    };
    
    /**
     * Creates an animated bar chart with modern styling
     */
    public static BarChart<String, Number> createAnimatedBarChart(String title, Map<String, Number> data) {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        
        BarChart<String, Number> chart = new BarChart<>(xAxis, yAxis);
        chart.setTitle(title);
        chart.setAnimated(true);
        chart.setLegendVisible(false);
        
        // Apply modern styling
        chart.setStyle("-fx-background-color: transparent;");
        chart.getStylesheets().add(ModernChartFactory.class.getResource("css/charts.css").toExternalForm());
        
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Data");
        
        for (Map.Entry<String, Number> entry : data.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }
        
        chart.getData().add(series);
        
        // Apply color styling to bars
        applyBarColors(chart);
        
        return chart;
    }
    
    /**
     * Creates an animated line chart with gradient effects
     */
    public static LineChart<String, Number> createAnimatedLineChart(String title, Map<String, Number> data) {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        
        LineChart<String, Number> chart = new LineChart<>(xAxis, yAxis);
        chart.setTitle(title);
        chart.setAnimated(true);
        chart.setLegendVisible(false);
        
        // Apply modern styling
        chart.setStyle("-fx-background-color: transparent;");
        
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Trend");
        
        for (Map.Entry<String, Number> entry : data.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }
        
        chart.getData().add(series);
        
        // Apply line styling
        applyLineStyling(chart);
        
        return chart;
    }
    
    /**
     * Creates a modern pie chart with custom styling
     */
    public static PieChart createModernPieChart(String title, Map<String, Number> data) {
        PieChart chart = new PieChart();
        chart.setTitle(title);
        chart.setAnimated(true);
        
        ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList();
        
        int colorIndex = 0;
        for (Map.Entry<String, Number> entry : data.entrySet()) {
            PieChart.Data slice = new PieChart.Data(entry.getKey(), entry.getValue().doubleValue());
            pieData.add(slice);
            
            // Apply custom colors
            slice.getNode().setStyle("-fx-pie-color: " + toHex(CHART_COLORS[colorIndex % CHART_COLORS.length]) + ";");
            colorIndex++;
        }
        
        chart.setData(pieData);
        
        // Add hover effects
        addPieChartHoverEffects(chart);
        
        return chart;
    }
    
    /**
     * Creates a modern area chart with gradient fill
     */
    public static AreaChart<String, Number> createModernAreaChart(String title, Map<String, Number> data) {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        
        AreaChart<String, Number> chart = new AreaChart<>(xAxis, yAxis);
        chart.setTitle(title);
        chart.setAnimated(true);
        chart.setLegendVisible(false);
        
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Area");
        
        for (Map.Entry<String, Number> entry : data.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }
        
        chart.getData().add(series);
        
        // Apply area styling
        applyAreaStyling(chart);
        
        return chart;
    }
    
    /**
     * Creates a scatter chart with custom markers
     */
    public static ScatterChart<String, Number> createModernScatterChart(String title, Map<String, Number> data) {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        
        ScatterChart<String, Number> chart = new ScatterChart<>(xAxis, yAxis);
        chart.setTitle(title);
        chart.setAnimated(true);
        chart.setLegendVisible(false);
        
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Points");
        
        for (Map.Entry<String, Number> entry : data.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }
        
        chart.getData().add(series);
        
        // Apply scatter styling
        applyScatterStyling(chart);
        
        return chart;
    }
    
    /**
     * Creates a custom gauge chart for KPI display
     */
    public static VBox createGaugeChart(String title, double value, double maxValue, String unit) {
        VBox container = new VBox();
        container.setSpacing(10);
        container.setAlignment(javafx.geometry.Pos.CENTER);
        
        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-text-fill: #f1f5f9; -fx-font-size: 14px; -fx-font-weight: bold;");
        
        // Create gauge circle
        Circle gaugeCircle = new Circle(60);
        gaugeCircle.setStroke(Color.web("#334155"));
        gaugeCircle.setStrokeWidth(8);
        gaugeCircle.setFill(Color.TRANSPARENT);
        
        // Create progress arc
        double percentage = value / maxValue;
        double angle = percentage * 360;
        
        Circle progressArc = new Circle(60);
        progressArc.setStroke(Color.web("#3b82f6"));
        progressArc.setStrokeWidth(8);
        progressArc.setFill(Color.TRANSPARENT);
        // Note: setStrokeDashArray is not available in all JavaFX versions
        // progressArc.setStrokeDashArray(angle, 360 - angle);
        
        // Value label
        Label valueLabel = new Label(String.format("%.1f%s", value, unit));
        valueLabel.setStyle("-fx-text-fill: white; -fx-font-size: 20px; -fx-font-weight: bold;");
        
        // Percentage label
        Label percentageLabel = new Label(String.format("%.1f%%", percentage * 100));
        percentageLabel.setStyle("-fx-text-fill: #94a3b8; -fx-font-size: 12px;");
        
        StackPane gaugePane = new StackPane();
        gaugePane.getChildren().addAll(gaugeCircle, progressArc, valueLabel);
        
        container.getChildren().addAll(titleLabel, gaugePane, percentageLabel);
        
        // Add animation
        animateGauge(progressArc, angle);
        
        return container;
    }
    
    /**
     * Creates a modern progress bar with gradient
     */
    public static VBox createProgressBar(String title, double value, double maxValue) {
        VBox container = new VBox();
        container.setSpacing(5);
        
        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-text-fill: #f1f5f9; -fx-font-size: 12px;");
        
        // Create a simple rectangle as progress bar
        Rectangle progressBar = new Rectangle(200, 8);
        progressBar.setFill(Color.web("#334155"));
        progressBar.setStroke(Color.web("#3b82f6"));
        progressBar.setStrokeWidth(1);
        
        // Create progress fill
        double progress = value / maxValue;
        Rectangle progressFill = new Rectangle(200 * progress, 8);
        progressFill.setFill(Color.web("#3b82f6"));
        
        StackPane progressPane = new StackPane();
        progressPane.getChildren().addAll(progressBar, progressFill);
        
        Label valueLabel = new Label(String.format("%.1f / %.1f", value, maxValue));
        valueLabel.setStyle("-fx-text-fill: #94a3b8; -fx-font-size: 10px;");
        
        container.getChildren().addAll(titleLabel, progressPane, valueLabel);
        
        return container;
    }
    
    /**
     * Creates a custom heatmap visualization
     */
    public static VBox createHeatmap(String title, int rows, int cols, double[][] data) {
        VBox container = new VBox();
        container.setSpacing(10);
        
        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-text-fill: #f1f5f9; -fx-font-size: 14px; -fx-font-weight: bold;");
        
        GridPane heatmap = new GridPane();
        heatmap.setHgap(2);
        heatmap.setVgap(2);
        
        // Find min and max values for color scaling
        double minValue = Double.MAX_VALUE;
        double maxValue = Double.MIN_VALUE;
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                minValue = Math.min(minValue, data[i][j]);
                maxValue = Math.max(maxValue, data[i][j]);
            }
        }
        
        // Create heatmap cells
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Rectangle cell = new Rectangle(20, 20);
                
                // Calculate color based on value
                double normalizedValue = (data[i][j] - minValue) / (maxValue - minValue);
                Color cellColor = interpolateColor(Color.web("#1e293b"), Color.web("#3b82f6"), normalizedValue);
                cell.setFill(cellColor);
                
                heatmap.add(cell, j, i);
            }
        }
        
        container.getChildren().addAll(titleLabel, heatmap);
        
        return container;
    }
    
    // Helper methods for styling
    private static void applyBarColors(BarChart<String, Number> chart) {
        chart.getData().forEach(series -> {
            series.getData().forEach(data -> {
                Node node = data.getNode();
                if (node != null) {
                    node.setStyle("-fx-bar-fill: #3b82f6;");
                }
            });
        });
    }
    
    private static void applyLineStyling(LineChart<String, Number> chart) {
        chart.getData().forEach(series -> {
            series.getData().forEach(data -> {
                Node node = data.getNode();
                if (node != null) {
                    node.setStyle("-fx-background-color: #3b82f6;");
                }
            });
        });
    }
    
    private static void applyAreaStyling(AreaChart<String, Number> chart) {
        chart.getData().forEach(series -> {
            series.getData().forEach(data -> {
                Node node = data.getNode();
                if (node != null) {
                    node.setStyle("-fx-fill: #3b82f6; -fx-opacity: 0.7;");
                }
            });
        });
    }
    
    private static void applyScatterStyling(ScatterChart<String, Number> chart) {
        chart.getData().forEach(series -> {
            series.getData().forEach(data -> {
                Node node = data.getNode();
                if (node != null) {
                    node.setStyle("-fx-background-color: #3b82f6; -fx-background-radius: 50%;");
                }
            });
        });
    }
    
    private static void addPieChartHoverEffects(PieChart chart) {
        chart.getData().forEach(data -> {
            Node node = data.getNode();
            if (node != null) {
                node.setOnMouseEntered(e -> {
                    ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), node);
                    scaleTransition.setToX(1.1);
                    scaleTransition.setToY(1.1);
                    scaleTransition.play();
                });
                
                node.setOnMouseExited(e -> {
                    ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), node);
                    scaleTransition.setToX(1.0);
                    scaleTransition.setToY(1.0);
                    scaleTransition.play();
                });
            }
        });
    }
    
    private static void animateGauge(Circle progressArc, double angle) {
        // Animate the gauge filling
        Timeline timeline = new Timeline();
        // Note: strokeDashArrayProperty is not available in all JavaFX versions
        // KeyValue keyValue = new KeyValue(progressArc.strokeDashArrayProperty(), 
        //     new javafx.scene.shape.StrokeDashArray(0, 360));
        // KeyFrame keyFrame = new KeyFrame(Duration.millis(1000), keyValue);
        // timeline.getKeyFrames().add(keyFrame);
        timeline.play();
    }
    
    private static Color interpolateColor(Color start, Color end, double ratio) {
        double red = start.getRed() + (end.getRed() - start.getRed()) * ratio;
        double green = start.getGreen() + (end.getGreen() - start.getGreen()) * ratio;
        double blue = start.getBlue() + (end.getBlue() - start.getBlue()) * ratio;
        double opacity = start.getOpacity() + (end.getOpacity() - start.getOpacity()) * ratio;
        
        return new Color(red, green, blue, opacity);
    }
    
    private static String toHex(Color color) {
        return String.format("#%02X%02X%02X",
            (int) (color.getRed() * 255),
            (int) (color.getGreen() * 255),
            (int) (color.getBlue() * 255));
    }
}
