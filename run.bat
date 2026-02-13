@echo off
echo Searching for Java runtime...
set JAVA_PATH=

REM Check common JDK installation paths
for %%d in ("C:\Program Files\Java" "C:\Program Files (x86)\Java" "C:\Program Files\AdoptOpenJDK" "C:\Program Files\Eclipse Adoptium") do (
    if exist "%%d" (
        for /r "%%d" %%f in (java.exe) do (
            set JAVA_PATH=%%f
            goto :found
        )
    )
)

REM Check JAVA_HOME
if defined JAVA_HOME (
    if exist "%JAVA_HOME%\bin\java.exe" (
        set JAVA_PATH=%JAVA_HOME%\bin\java.exe
        goto :found
    )
)

REM Try to find java in PATH
where java >nul 2>&1
if %errorlevel% == 0 (
    set JAVA_PATH=java
    goto :found
)

:found
if "%JAVA_PATH%"=="" (
    echo ERROR: Java runtime not found!
    echo Please install JRE 8 or higher.
    pause
    exit /b 1
)

echo Running Library Application...
"%JAVA_PATH%" -cp .;sqlite-jdbc.jar LibraryApp
pause
