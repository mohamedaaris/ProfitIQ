@echo off
echo Building ProfitIQ Application...

REM Compile all Java files with libraries from lib folder
echo Compiling Java files with libraries...
javac -cp ".;lib/*" -d . *.java swing/ui/*.java swing/model/*.java swing/util/*.java javafx/ui/controllers/*.java javafx/model/*.java javafx/util/*.java

if %errorlevel% neq 0 (
    echo Compilation failed!
    pause
    exit /b %errorlevel%
)

echo Compilation successful!

echo.
echo Choose which version to run:
echo 1. Swing version (ProfitIQ)
echo 2. JavaFX version (ProfitIQFX)
echo.

choice /c 12 /m "Enter your choice"

if errorlevel 2 (
    echo Running JavaFX version...
    java -cp ".;lib/*" --module-path "lib" --add-modules javafx.controls,javafx.fxml ProfitIQFX
) else (
    echo Running Swing version...
    java -cp ".;lib/*" ProfitIQ
)

pause