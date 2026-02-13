@echo off
echo Searching for Java compiler...
set JAVAC_PATH=

REM Check common JDK installation paths
for %%d in ("C:\Program Files\Java" "C:\Program Files (x86)\Java" "C:\Program Files\AdoptOpenJDK" "C:\Program Files\Eclipse Adoptium") do (
    if exist "%%d" (
        for /r "%%d" %%f in (javac.exe) do (
            set JAVAC_PATH=%%f
            goto :found
        )
    )
)

REM Check JAVA_HOME
if defined JAVA_HOME (
    if exist "%JAVA_HOME%\bin\javac.exe" (
        set JAVAC_PATH=%JAVA_HOME%\bin\javac.exe
        goto :found
    )
)

REM Try to find javac in PATH
where javac >nul 2>&1
if %errorlevel% == 0 (
    set JAVAC_PATH=javac
    goto :found
)

:found
if "%JAVAC_PATH%"=="" (
    echo ERROR: Java compiler (javac) not found!
    echo Please install JDK 8 or higher.
    echo You can install it using: winget install AdoptOpenJDK.OpenJDK.8
    pause
    exit /b 1
)

echo Found Java compiler at: %JAVAC_PATH%
echo.
echo Compiling Java files...
"%JAVAC_PATH%" -cp sqlite-jdbc.jar *.java

if %errorlevel% == 0 (
    echo.
    echo Compilation successful!
    echo.
    echo To run the application, use: run.bat
) else (
    echo.
    echo Compilation failed!
    pause
    exit /b 1
)
