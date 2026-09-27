# Uso desde PowerShell: . .\activar-entorno.ps1
# Solo configura esta terminal; no cambia variables permanentes de Windows.
$toolsPath = [IO.Path]::GetFullPath((Join-Path $PSScriptRoot '../.tools'))
$jdkPath = Join-Path $toolsPath 'java/jdk-11.0.32.1+1'
$mavenPath = Join-Path $toolsPath 'apache-maven-3.9.11/bin'
if (!(Test-Path (Join-Path $jdkPath 'bin/java.exe')) -or !(Test-Path (Join-Path $mavenPath 'mvn.cmd'))) {
    throw 'No se encontraron las herramientas portatiles. Instale JDK 11+ y Maven 3.9+ en PATH para ejecutar mvn clean test.'
}
$env:JAVA_HOME = $jdkPath
$env:Path = "$jdkPath\bin;$mavenPath;$env:Path"
$repositoryPath = Join-Path $toolsPath 'm2'
$env:MAVEN_OPTS = "-Dmaven.repo.local=`"$repositoryPath`""
$env:SE_CACHE_PATH = Join-Path $toolsPath 'selenium'
Write-Host 'Java, Maven y cache de Selenium configurados para esta terminal.'
