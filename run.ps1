Write-Host "Searching for Java runtime..." -ForegroundColor Cyan

$javaPath = $null

# Check PATH first
$java = Get-Command java -ErrorAction SilentlyContinue
if ($java) {
    $javaPath = $java.Path
    Write-Host "Found java in PATH: $javaPath" -ForegroundColor Green
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
            $java = Get-ChildItem -Path $basePath -Recurse -Filter "java.exe" -ErrorAction SilentlyContinue | Select-Object -First 1
            if ($java) {
                $javaPath = $java.FullName
                Write-Host "Found java at: $javaPath" -ForegroundColor Green
                break
            }
        }
    }
    
    # Check JAVA_HOME
    if (-not $javaPath -and $env:JAVA_HOME) {
        $javaCandidate = Join-Path $env:JAVA_HOME "bin\java.exe"
        if (Test-Path $javaCandidate) {
            $javaPath = $javaCandidate
            Write-Host "Found java via JAVA_HOME: $javaPath" -ForegroundColor Green
        }
    }
}

if (-not $javaPath) {
    Write-Host "ERROR: Java runtime not found!" -ForegroundColor Red
    Write-Host "Please install JRE 8 or higher." -ForegroundColor Yellow
    exit 1
}

Write-Host ""
Write-Host "Running Library Application..." -ForegroundColor Cyan
Write-Host ""
& $javaPath -cp ".;sqlite-jdbc.jar" LibraryApp
