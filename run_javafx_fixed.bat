@echo off
echo ProfitIQ JavaFX Application Launcher
echo ====================================

REM Check if Java is installed
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo Error: Java is not installed or not in PATH
    pause
    exit /b 1
)

echo Starting ProfitIQ JavaFX application...
echo Make sure you have:
echo   1. PostgreSQL running with the profitiq database set up
echo   2. Required JAR files in the lib directory
echo.

REM Try to run with JavaFX in classpath
echo Attempting to run with JavaFX libraries...
java -cp ".;lib/*" --add-exports javafx.graphics/com.sun.javafx.application=ALL-UNNAMED --add-exports javafx.graphics/com.sun.javafx.stage=ALL-UNNAMED ProfitIQFX

if %errorlevel% neq 0 (
    echo.
    echo JavaFX runtime failed. Trying alternative approach...
    echo.
    
    REM Try running the original Swing version instead
    echo Running original Swing version as fallback...
    java -cp ".;lib/*" ProfitIQ
    
    if %errorlevel% neq 0 (
        echo.
        echo Both JavaFX and Swing versions failed.
        echo Please check:
        echo   1. JavaFX is properly installed
        echo   2. Database connection is available
        echo   3. All required JAR files are present
        echo.
        echo Error code: %errorlevel%
    )
)

pause


