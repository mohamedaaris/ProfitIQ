@echo off
echo Starting ProfitIQ JavaFX Application...

REM Compile JavaFX files with libraries from lib folder
echo Compiling JavaFX files...
javac -cp ".;lib/javafx.base.jar;lib/javafx.controls.jar;lib/javafx.fxml.jar;lib/javafx.graphics.jar" --module-path "lib" --add-modules javafx.controls,javafx.fxml -d . ProfitIQFX.java javafx/ui/controllers/*.java javafx/model/*.java javafx/data/*.java DatabaseConnection.java

if %errorlevel% neq 0 (
    echo Compilation failed!
    pause
    exit /b %errorlevel%
)

echo Compilation successful!

REM Copy the complete FXML file to the main FXML file
echo Copying complete FXML file...
copy /Y fxml\MainDashboard_complete.fxml fxml\MainDashboard.fxml

if %errorlevel% neq 0 (
    echo Failed to copy FXML file!
    pause
    exit /b %errorlevel%
)

echo FXML file copied successfully!

REM Run JavaFX application with libraries from lib folder
echo Starting JavaFX application...
java -cp ".;lib/javafx.base.jar;lib/javafx.controls.jar;lib/javafx.fxml.jar;lib/javafx.graphics.jar" --module-path "lib" --add-modules javafx.controls,javafx.fxml ProfitIQFX

pause