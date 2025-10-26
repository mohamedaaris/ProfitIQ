# ProfitIQ Analytics Platform

ProfitIQ is a comprehensive analytics platform that provides business intelligence, college placement analytics, and research paper analysis. The platform is available in both Swing and JavaFX versions.

## Project Structure

```
ProfitIQ/
├── swing/                 # Swing implementation
│   ├── ui/                # User interface components
│   ├── model/             # Data models
│   └── util/              # Utility classes
├── javafx/                # JavaFX implementation
│   ├── ui/
│   │   └── controllers/   # FXML controllers
│   ├── model/             # Data models
│   ├── util/              # Utility classes
│   ├── css/               # CSS styling files
│   └── fxml/              # FXML layout files
├── fxml/                  # Shared FXML files
├── css/                   # Shared CSS files
└── lib/                   # External libraries (JavaFX, JFreeChart, etc.)
```

## Features

- **Business Analytics**: Track company revenue, profit, and growth metrics
- **College Placement Analytics**: Monitor college placement rates, salaries, and recruiter information
- **Research Analytics**: Analyze research paper citations and impact scores
- **Dashboard**: Unified dashboard with interactive charts and data visualization
- **Real-time Data**: Simulated real-time data updates
- **Responsive UI**: Modern, responsive user interface with dark theme

## Running the Application

### Option 1: Using Build Scripts

Double-click on `build_and_run.bat` to compile and run the application. You'll be prompted to choose between Swing and JavaFX versions.

### Option 2: Running Specific Versions

- **Swing version**: Double-click `run_app.bat`
- **JavaFX version**: Double-click `run_javafx.bat`

### Option 3: Manual Compilation and Execution

```bash
# Compile all files with libraries from lib folder
javac -cp ".;lib/*" -d . *.java swing/ui/*.java swing/model/*.java swing/util/*.java javafx/ui/controllers/*.java javafx/model/*.java javafx/util/*.java

# Run Swing version
java -cp ".;lib/*" ProfitIQ

# Run JavaFX version
java -cp ".;lib/*" --module-path "lib" --add-modules javafx.controls,javafx.fxml ProfitIQFX
```

## Key Components

### Swing Version
- `ProfitIQ.java`: Main application class
- `LoginFrame.java`: Login screen
- `SignupFrame.java`: User registration screen
- `MainDashboard.java`: Main dashboard with navigation

### JavaFX Version
- `ProfitIQFX.java`: Main application class
- `JavaFXLauncher.java`: Alternative launcher
- `LoginController.java`: Login screen controller
- `SignupController.java`: Signup screen controller
- `MainDashboardController.java`: Main dashboard controller

## Styling

Both versions use a modern dark theme with:
- Blue accent colors
- Smooth animations and transitions
- Responsive layouts
- Custom-styled components

The JavaFX version uses CSS for styling, while the Swing version uses custom painting and styling.

## Data Model

The application uses three primary data models:
- `Company`: Business analytics data
- `College`: College placement data
- `ResearchPaper`: Research publication data

Each model includes metrics for analysis and scoring algorithms to rank entities.

## Requirements

- Java 8 or higher
- Libraries in the `lib` folder (JavaFX, JFreeChart, etc.)

## Development

The project is organized to allow easy maintenance of both Swing and JavaFX versions. Shared components are in the root directory, while version-specific components are in their respective directories.