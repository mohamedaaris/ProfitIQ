@echo off
echo ProfitIQ Build Script
echo ====================

REM Check if Java is installed
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo Error: Java is not installed or not in PATH
    pause
    exit /b 1
)

REM Check if javac is available
javac -version >nul 2>&1
if %errorlevel% neq 0 (
    echo Error: Java compiler (javac) is not available
    pause
    exit /b 1
)

echo Compiling ProfitIQ application...
echo Make sure you have placed the required JAR files in the lib directory:
echo   - jfreechart.jar
echo   - flatlaf.jar
echo   - postgresql-connector.jar
echo.
javac -cp ".;lib/*" *.java

if %errorlevel% equ 0 (
    echo Compilation successful!
    echo.
    echo To run the application, use:
    echo   run.bat
    echo.
) else (
    echo Compilation failed!
    echo Please check for errors above.
)

pause