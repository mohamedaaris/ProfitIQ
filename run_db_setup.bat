@echo off
echo ProfitIQ Database Setup
echo ======================

echo This script will set up the ProfitIQ database.
echo Make sure PostgreSQL is running and you have psql in your PATH.
echo.

set /p pg_password="Enter your PostgreSQL password: "

echo Running database initialization script...
set PGPASSWORD=%pg_password%
psql -U postgres -f init_db.sql

if %errorlevel% equ 0 (
    echo.
    echo Database setup completed successfully!
    echo You can now run the ProfitIQ application.
) else (
    echo.
    echo Database setup failed. Please check the error messages above.
)

pause