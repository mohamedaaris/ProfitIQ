@echo off
echo ProfitIQ JavaFX Application Launcher
echo ====================================

REM Check if JavaFX SDK is available
if exist "C:\javafx-sdk-21\lib\javafx.controls.jar" (
    echo JavaFX SDK found at C:\javafx-sdk-21\
    echo Starting JavaFX application...
    java --module-path "C:\javafx-sdk-21\lib" --add-modules javafx.controls,javafx.fxml -cp ".;lib/*" ProfitIQFX
    goto :end
)

REM Check if DLL files are in project directory
if exist "javafx_font.dll" (
    echo JavaFX DLL files found in project directory
    echo Starting JavaFX application...
    java -cp ".;lib/*" ProfitIQFX
    goto :end
)

REM Try with system JavaFX if available
echo Attempting to run with system JavaFX...
java -cp ".;lib/*" ProfitIQFX
if %errorlevel% neq 0 (
    echo.
    echo JavaFX runtime failed. Please try one of these solutions:
    echo.
    echo 1. Download JavaFX SDK from https://openjfx.io/
    echo    Extract to C:\javafx-sdk-21\ and run this script again
    echo.
    echo 2. Copy JavaFX DLL files to this project directory
    echo    (javafx_font.dll, javafx_iio.dll, etc.)
    echo.
    echo 3. Run the original Swing version instead:
    echo    java -cp ".;lib/*" ProfitIQ
    echo.
    echo Error code: %errorlevel%
)

:end
pause


