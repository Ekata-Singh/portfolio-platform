@echo off
setlocal EnableDelayedExpansion
title Portfolio Platform
cd /d "%~dp0"

rem ------------------------------------------------------------------
rem  Starts the portfolio on this computer:
rem    API      -> http://localhost:8080
rem    Website  -> http://localhost:5173   (Admin login: ekta / admin123)
rem  Needs: Java 21+, Node.js 20+, and MySQL running.
rem ------------------------------------------------------------------

where java >nul 2>nul || (echo [!] Java not found. Install Java 21+ from https://adoptium.net & pause & exit /b 1)
where npm  >nul 2>nul || (echo [!] Node.js not found. Install it from https://nodejs.org & pause & exit /b 1)

set "LOCALCFG=backend\src\main\resources\application-local.properties"
if not exist "%LOCALCFG%" (
    echo.
    echo First-time setup: enter your MySQL login. The database is created automatically.
    set "DBUSER=root"
    set /p "DBUSER=MySQL username [root]: "
    set /p "DBPASS=MySQL password: "
    > "%LOCALCFG%" echo # Local settings - this file is NOT committed to git.
    >> "%LOCALCFG%" echo spring.datasource.username=!DBUSER!
    >> "%LOCALCFG%" echo spring.datasource.password=!DBPASS!
    echo Saved to %LOCALCFG%  ^(edit or delete it to change^)
)

echo Starting API in a new window...
start "Portfolio API" cmd /k "cd /d "%~dp0backend" && mvnw.cmd spring-boot:run"

cd frontend
if not exist node_modules (
    echo Installing website dependencies ^(first run only^)...
    call npm install || (pause & exit /b 1)
)

echo.
echo Website: http://localhost:5173   ^|   Admin login: ekta / admin123
echo The API window takes ~30s to start the first time. Close both windows to stop.
echo.
start "" http://localhost:5173
call npm run dev
