Write-Host "Searching for Java compiler..." -ForegroundColor Cyan

$javacPath = $null

# Check PATH first
$javac = Get-Command javac -ErrorAction SilentlyContinue
if ($javac) {
    $javacPath = $javac.Path
    Write-Host "Found javac in PATH: $javacPath" -ForegroundColor Green
} else {
    # Search common installation paths
    $searchPaths = @(
        "C:\Program Files\Java",
        "C:\Program Files (x86)\Java",
        "C:\Program Files\AdoptOpenJDK",
        "C:\Program Files\Eclipse Adoptium",
        "C:\Program Files\Microsoft",
        "$env:LOCALAPPDATA\Programs\Eclipse Adoptium"
    )
    
    foreach ($basePath in $searchPaths) {
        if (Test-Path $basePath) {
            $javac = Get-ChildItem -Path $basePath -Recurse -Filter "javac.exe" -ErrorAction SilentlyContinue | Select-Object -First 1
            if ($javac) {
                $javacPath = $javac.FullName
                Write-Host "Found javac at: $javacPath" -ForegroundColor Green
                break
            }
        }
    }
    
    # Check JAVA_HOME
    if (-not $javacPath -and $env:JAVA_HOME) {
        $javacCandidate = Join-Path $env:JAVA_HOME "bin\javac.exe"
        if (Test-Path $javacCandidate) {
            $javacPath = $javacCandidate
            Write-Host "Found javac via JAVA_HOME: $javacPath" -ForegroundColor Green
        }
    }
}

if (-not $javacPath) {
    Write-Host "ERROR: Java compiler (javac) not found!" -ForegroundColor Red
    Write-Host "Please install JDK 8 or higher." -ForegroundColor Yellow
    Write-Host "You can install it using: winget install AdoptOpenJDK.OpenJDK.8" -ForegroundColor Yellow
    Write-Host "After installation, restart your terminal and try again." -ForegroundColor Yellow
    exit 1
}

Write-Host ""
Write-Host "Compiling Java files..." -ForegroundColor Cyan
& $javacPath -cp sqlite-jdbc.jar *.java

if ($LASTEXITCODE -eq 0) {
    Write-Host ""
    Write-Host "Compilation successful!" -ForegroundColor Green
    Write-Host ""
    Write-Host "To run the application, use: .\run.ps1 or run.bat" -ForegroundColor Cyan
} else {
    Write-Host ""
    Write-Host "Compilation failed!" -ForegroundColor Red
    exit 1
}
