@echo off
echo ProfitIQ Application Launcher
echo =============================

REM Check if Java is installed
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo Error: Java is not installed or not in PATH
    pause
    exit /b 1
)

echo Starting ProfitIQ application...
echo Make sure you have:
echo   1. PostgreSQL running with the profitiq database set up
echo   2. Required JAR files in the lib directory:
echo      - jfreechart.jar
echo      - flatlaf.jar
echo      - postgresql-connector.jar
echo.

java -cp ".;lib/*" ProfitIQ

if %errorlevel% neq 0 (
    echo.
    echo Application exited with error code %errorlevel%
)

pause