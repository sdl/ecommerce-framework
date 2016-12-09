<#
.SYNOPSIS
   Create NuGet packages for ECommerce DXA.NET
.EXAMPLE
   .\nuget.ps1 -outputDirectory "c:\nuget" -dxaVersion "1.2.3" -exampleViewsVersion "1.0.1" -Verbose
#>

Param(
    [Parameter(Mandatory=$false, HelpMessage="Artifact output directory.")]
    [string]$outputDirectory = "C:\temp\artifacts\",

    [Parameter(Mandatory=$false, HelpMessage="Sdl.ECommerce.Dxa version.")]
    [string]$dxaVersion = "0.0.1",

    [Parameter(Mandatory=$false, HelpMessage="Sdl.ECommerce.ExampleViews version.")]
    [string]$exampleViewsVersion = "0.0.1"
)

$basePath = "..\ecommerce-framework-dotnet"
$packageDestination = Join-Path $PSScriptRoot "packages"

$nuGetPackage = Get-Package -Name NuGet.CommandLine -Destination $packageDestination -ErrorAction SilentlyContinue

if ($nuGetPackage -eq $null) {
    Write-Host "Could not find NuGet executable. Trying to install it..."
    Write-Host

    Install-Package -Name NuGet.CommandLine -Provider Nuget -Source https://www.nuget.org/api/v2 -Destination $packageDestination -Force
}

$nuGetFile = Get-ChildItem -Path (Join-Path $PSScriptRoot "packages") -Filter "nuget.exe" -Recurse | Where-Object { !$_PSIsContainer } | Select-Object -First 1

& $nuGetFile.FullName pack 'Sdl.ECommerce.Dxa.nuspec' -version $dxaVersion -basepath $basePath -outputdirectory $outputDirectory
& $nuGetFile.FullName pack 'Sdl.ECommerce.ExampleViews.nuspec' -version $exampleViewsVersion -basepath $basePath -outputdirectory $outputDirectory