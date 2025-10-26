@echo off
echo Starting ProfitIQ Swing Application...

REM Compile Swing files with libraries from lib folder
echo Compiling Swing files...
javac -cp ".;lib/*" -d . *.java swing/ui/*.java swing/model/*.java swing/util/*.java

if %errorlevel% neq 0 (
    echo Compilation failed!
    pause
    exit /b %errorlevel%
)

echo Compilation successful!

REM Run Swing application with libraries from lib folder
echo Starting Swing application...
java -cp ".;lib/*" ProfitIQ

pause