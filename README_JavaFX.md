# ProfitIQ JavaFX Dashboard Migration

## 🚀 Modern Analytics Dashboard with Real-time Data Integration

This project represents a complete migration from Java Swing + FlatLaf to JavaFX, transforming the ProfitIQ dashboard into a modern, dynamic, and visually appealing analytics interface.

## ✨ Key Features

### 🎨 Modern UI/UX Design
- **JavaFX-based Interface**: Clean, modern design with smooth animations
- **Dark Theme**: Professional dark theme with subtle gradients and shadows
- **Responsive Layout**: Adaptive design that works across different screen sizes
- **Smooth Animations**: Fade transitions, hover effects, and loading animations

### 📊 Advanced Visualizations
- **JavaFX Charts**: Modern bar charts, line charts, pie charts, and area charts
- **Interactive Elements**: Hover tooltips, clickable data points, and animated transitions
- **Custom Chart Components**: Gauge charts, progress bars, and heatmap visualizations
- **Real-time Updates**: Charts automatically update with new data

### 🔄 Real-time Data Integration
- **Dynamic Data Service**: Background service for continuous data fetching
- **Database Integration**: Live connection to PostgreSQL database
- **Automatic Refresh**: Data updates every 30 seconds
- **Fallback Support**: Sample data when database is unavailable

### 📱 Dashboard Panels
- **Business Analytics**: Company profit analysis with interactive charts
- **College Analytics**: Placement rate analysis and salary trends
- **Research Analytics**: Citation tracking and research impact metrics
- **Summary Dashboard**: KPI cards and comprehensive business overview

## 🏗️ Architecture

### Core Components

```
ProfitIQFX.java                 # Main JavaFX Application
├── MainDashboardController.java # Main dashboard controller
├── DataService.java            # Real-time data service
├── ModernChartFactory.java     # Advanced chart components
├── fxml/
│   └── MainDashboard.fxml      # Main dashboard layout
├── css/
│   ├── dashboard.css           # Main dashboard styling
│   └── charts.css              # Chart-specific styling
└── icons/                      # Application icons
```

### Data Flow

```
Database (PostgreSQL)
    ↓
DataService (Background Service)
    ↓
Real-time Listeners
    ↓
JavaFX Controllers
    ↓
UI Components (Charts, Tables, KPIs)
```

## 🛠️ Technology Stack

- **Frontend**: JavaFX 11+ with FXML and CSS
- **Backend**: Java 11+ with JDBC
- **Database**: PostgreSQL
- **Charts**: JavaFX built-in charts + custom components
- **Styling**: CSS3 with animations and transitions
- **Architecture**: MVC pattern with service layer

## 📋 Prerequisites

### Required Software
- Java 11 or higher
- JavaFX 11 or higher
- PostgreSQL 12 or higher

### Required JAR Files
- `postgresql-connector.jar` (PostgreSQL JDBC driver)
- JavaFX libraries (if using modular JavaFX)

## 🚀 Getting Started

### 1. Database Setup
```bash
# Run the database setup script
psql -U postgres -f database_setup.sql
```

### 2. Configure Database Connection
Update `DatabaseConnection.java` with your PostgreSQL credentials:
```java
private static final String URL = "jdbc:postgresql://localhost:5432/profitiq";
private static final String USER = "your_username";
private static final String PASSWORD = "your_password";
```

### 3. Run the Application

#### Option 1: Using the JavaFX launcher script
```bash
run_javafx.bat
```

#### Option 2: Manual execution
```bash
# With JavaFX modules
java --module-path "path/to/javafx" --add-modules javafx.controls,javafx.fxml -cp ".;lib/*" ProfitIQFX

# Without modules (if JavaFX is in classpath)
java -cp ".;lib/*" ProfitIQFX
```

## 🎯 Key Improvements Over Swing Version

### 1. **Modern UI Design**
- Replaced Swing components with JavaFX equivalents
- Added CSS styling for consistent theming
- Implemented smooth animations and transitions
- Created responsive layout system

### 2. **Enhanced Charts**
- Migrated from JFreeChart to JavaFX charts
- Added interactive features (hover, click, zoom)
- Implemented custom chart components (gauges, heatmaps)
- Added real-time chart updates

### 3. **Real-time Data Integration**
- Created `DataService` for background data fetching
- Implemented listener pattern for UI updates
- Added automatic data refresh every 30 seconds
- Provided fallback to sample data

### 4. **Improved Architecture**
- Separated concerns with MVC pattern
- Created reusable chart components
- Implemented service layer for data management
- Added proper error handling and logging

### 5. **Better User Experience**
- Smooth panel transitions
- Loading animations
- Hover effects on interactive elements
- Responsive design for different screen sizes

## 📊 Dashboard Features

### Business Analytics Panel
- **Company Data Table**: Editable table with company information
- **Profit Visualization**: Interactive bar chart showing company profits
- **Real-time Updates**: Data refreshes automatically
- **Export Functionality**: Export data to CSV/Excel (placeholder)

### College Analytics Panel
- **Placement Rate Analysis**: Bar chart showing college placement rates
- **Salary Trends**: Average salary visualization
- **Recruiter Count**: Number of recruiters per college
- **Interactive Charts**: Hover tooltips and clickable elements

### Research Analytics Panel
- **Citation Tracking**: Line chart showing citations over time
- **Research Impact**: Impact score calculations
- **Author Analysis**: Research by author
- **Year-based Filtering**: Filter research by publication year

### Summary Dashboard
- **KPI Cards**: Revenue, customers, orders, and growth metrics
- **Chart Grid**: Sales analysis, revenue breakdown, customer distribution
- **Business Summary**: Automated report generation
- **Real-time KPIs**: Live updates from database

## 🎨 Styling and Theming

### CSS Features
- **Dark Theme**: Professional dark color scheme
- **Gradient Effects**: Subtle gradients for depth
- **Shadow Effects**: Drop shadows for cards and panels
- **Hover Animations**: Smooth transitions on interaction
- **Responsive Design**: Adapts to different screen sizes

### Color Palette
```css
Primary Color: #3b82f6 (Blue)
Secondary Color: #0ea5e9 (Sky Blue)
Success Color: #22c55e (Green)
Warning Color: #eab308 (Yellow)
Danger Color: #ef4444 (Red)
Background: #0f172a (Dark Blue)
Card Background: #1e293b (Lighter Blue)
```

## 🔧 Configuration

### Database Configuration
The application automatically detects database connectivity and falls back to sample data if the database is unavailable.

### Real-time Updates
- **Business Data**: Updates every 30 seconds
- **College Data**: Updates every 30 seconds (offset by 5 seconds)
- **Research Data**: Updates every 30 seconds (offset by 10 seconds)
- **Summary Data**: Updates every 60 seconds (offset by 15 seconds)

### Chart Configuration
Charts are configured with:
- Smooth animations (500ms duration)
- Interactive tooltips
- Hover effects
- Custom color schemes
- Responsive sizing

## 🐛 Troubleshooting

### Common Issues

1. **JavaFX Not Found**
   - Ensure JavaFX is properly installed
   - Check module path configuration
   - Verify JavaFX JAR files are in classpath

2. **Database Connection Failed**
   - Check PostgreSQL service is running
   - Verify database credentials
   - Ensure `profitiq` database exists
   - Check network connectivity

3. **Charts Not Displaying**
   - Verify JavaFX chart libraries are available
   - Check CSS file paths
   - Ensure data is being loaded correctly

### Debug Mode
Enable debug logging by setting system property:
```bash
-Djava.util.logging.config.file=logging.properties
```

## 📈 Performance Considerations

- **Memory Usage**: Optimized with proper object lifecycle management
- **Data Caching**: Efficient caching strategy for database queries
- **UI Thread**: All UI updates run on JavaFX Application Thread
- **Background Processing**: Data fetching runs in separate threads

## 🔮 Future Enhancements

### Planned Features
- [ ] Export functionality (CSV, PDF, Excel)
- [ ] Advanced filtering and search
- [ ] User authentication and authorization
- [ ] Custom dashboard layouts
- [ ] Real-time notifications
- [ ] Mobile-responsive design
- [ ] Dark/light theme toggle
- [ ] Chart customization options

### Technical Improvements
- [ ] Add unit tests
- [ ] Implement logging framework
- [ ] Add configuration management
- [ ] Optimize database queries
- [ ] Add data validation
- [ ] Implement error recovery

## 📝 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## 📞 Support

For support and questions:
- Create an issue in the repository
- Check the troubleshooting section
- Review the documentation

---

**ProfitIQ JavaFX Dashboard** - Modern analytics interface with real-time data integration and beautiful visualizations.


