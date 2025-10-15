# ProfitIQ: Unified Business, Placement, and Research Analytics Platform

ProfitIQ is a comprehensive analytics platform that combines company profit & growth analytics, college placement evaluations, and research citation tracking into one intelligent dashboard.

## 🚀 Features

- **🔐 Authentication System**: Secure signup and login with password hashing
- **🧮 Business Analytics**: Track company revenue, profit, and growth metrics
- **🎓 College Placement Analytics**: Monitor placement rates, salaries, and recruiter data
- **📚 Research Analytics**: Track research paper citations and impact
- **📈 Unified Dashboard**: Combined view of all analytics with scoring system
- **📊 Interactive Charts**: Visualize data with JFreeChart
- **💾 PostgreSQL Backend**: Robust data storage and management
- **🎨 Modern UI**: FlatLaf dark theme for elegant user experience

## 🛠️ Technical Requirements

- **Java JDK 21+**
- **PostgreSQL 15+**
- **Libraries** (place in `lib` directory):
  - `jfreechart.jar` - Chart generation
  - `flatlaf.jar` - Modern UI theme
  - `postgresql-connector.jar` - Database connectivity

## 📁 Project Structure

```
ProfitIQ/
├── lib/                       # Third-party JAR files
├── ProfitIQ.java              # Main application entry point
├── DatabaseConnection.java    # Database connection utility
├── LoginFrame.java            # Login interface
├── SignupFrame.java           # User registration interface
├── MainDashboard.java         # Main application dashboard
├── BusinessAnalyticsPanel.java# Business analytics module
├── CollegeAnalyticsPanel.java # College placement analytics module
├── ResearchAnalyticsPanel.java# Research citation tracking module
├── SummaryPanel.java          # Unified dashboard summary
├── Company.java               # Company data model
├── College.java               # College data model
├── ResearchPaper.java         # Research paper data model
├── CompanyTableModel.java     # Table model for companies
├── CollegeTableModel.java     # Table model for colleges
├── ResearchTableModel.java    # Table model for research papers
├── database_setup.sql         # Database initialization script
├── build.bat                  # Build script for Windows
├── run.bat                    # Run script for Windows
├── TestDatabaseConnection.java# Database connection test utility
├── README.md                  # This file
└── LICENSE                    # License information
```

## 🛠️ Setup Instructions

### 1. Database Setup

1. Install PostgreSQL 15+ on your system
2. Create a new database:
   ```sql
   CREATE DATABASE profitiq;
   ```
3. Connect to the database and run the `database_setup.sql` script:
   ```bash
   psql -U postgres -d profitiq -f database_setup.sql
   ```

### 2. Configure Database Connection

Update the database connection details in `DatabaseConnection.java` if needed:
```java
private static final String URL = "jdbc:postgresql://localhost:5432/profitiq";
private static final String USER = "postgres";
private static final String PASSWORD = "postgres"; // Change to your PostgreSQL password
```

### 3. Add JAR Dependencies

Download the required JAR files and place them in the `lib` directory:
- `jfreechart.jar`
- `flatlaf.jar`
- `postgresql-connector.jar`

### 4. Compile and Run

On Windows, you can use the provided scripts:

**Build the application:**
```bash
build.bat
```

**Run the application:**
```bash
run.bat
```

Or compile and run manually:

Compile all Java files:
```bash
javac -cp ".;lib/*" *.java
```

Run the application:
```bash
java -cp ".;lib/*" ProfitIQ
```

## 🎯 Usage

1. **Sign Up**: Create a new account with username, email, and password
2. **Log In**: Access your personalized dashboard
3. **Navigate Modules**: Use the sidebar to switch between:
   - Business Analytics
   - College Placement Analytics
   - Research Analytics
   - Summary Dashboard
4. **Add Data**: Use the "+ Add Data" button on each tab to input new records
5. **View Analytics**: See real-time charts and ranked data
6. **Export Reports**: Generate summary reports (future feature)

## 📊 Analytics Scoring System

### Business Score
```
Company Score = (profit / revenue) × growth_percent
```

### College Score
```
Placement Score = (placement_rate × avg_salary) / 100
```

### Research Score
```
Research Score = citations / (currentYear - year + 1)
```

## 🌟 Future Enhancements

- AI-Powered Predictions for trend analysis
- Cloud Integration for remote data storage
- CSV Import/Export functionality
- Real-Time Sync with automatic refresh
- User Roles & Access Levels
- Enhanced Visual Dashboard with animations
- Notifications & Insights Panel

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 🙏 Acknowledgments

- [JFreeChart](https://www.jfree.org/jfreechart/) for charting capabilities
- [FlatLaf](https://github.com/JFormDesigner/FlatLaf) for the modern UI theme
- [PostgreSQL](https://www.postgresql.org/) for the database backend