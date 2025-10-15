@echo off
echo ProfitIQ Application Launcher
echo =============================

set /p pg_password="Enter your PostgreSQL password: "
set PROFITIQ_DB_PASSWORD=%pg_password%

echo Starting ProfitIQ application...
java -cp ".;lib/*" ProfitIQ

if %errorlevel% neq 0 (
    echo.
    echo Application exited with error code %errorlevel%
)

pause