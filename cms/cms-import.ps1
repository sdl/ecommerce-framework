<#
.SYNOPSIS
   Import E-Commerce FW DXA items into the Content Manager System.
.DESCRIPTION
   This script imports E-Commerce DXA items into the CMS using the CM Import/Export service and Core Service.
.EXAMPLE
   & .\cms-import.ps1 -cmsUrl "http://localhost:81/" -moduleZip "module.zip"
.NOTES
   Right now the script only supports the standard DXA publication names. Publication mappings will come in a later version.
#>

[CmdletBinding(SupportsShouldProcess=$true, PositionalBinding=$false)]
param (

    # Enter your cms url
    [Parameter(Mandatory=$true, HelpMessage="URL of the CMS you want to import in")]
    [string]$cmsUrl,

    # By default, the current Windows user's credentials are used, but it is possible to specify alternative credentials:
    [Parameter(Mandatory=$false, HelpMessage="CMS User name")]
    [string]$cmsUserName,
    [Parameter(Mandatory=$false, HelpMessage="CMS User password")]
    [string]$cmsUserPassword,
    [Parameter(Mandatory=$false, HelpMessage="CMS Authentication type")]
    [ValidateSet("Windows", "Basic")]
    [string]$cmsAuth = "Windows",

    # DXA Module ZIP filename
    [Parameter(Mandatory=$true, HelpMessage="DXA Module ZIP filename")]
    [string]$moduleZip
)


#Include functions from ContentManagerUtils.ps1
$PSScriptDir = Split-Path $MyInvocation.MyCommand.Path
$importExportFolder = Join-Path $PSScriptDir "ImportExport"
. (Join-Path $importExportFolder "ContentManagerUtils.ps1")

#Terminate script on first occurred exception
$ErrorActionPreference = "Stop"

#Process 'WhatIf' and 'Confirm' options
if (!($pscmdlet.ShouldProcess($cmsUrl, "Import E-Commerce FW DXA items"))) { return }


$tempFolder = Get-TempFolder "DXA"

Initialize-ImportExport $importExportFolder $tempFolder

# Create Core Service client and default read options
$cmsUrl = $cmsUrl.TrimEnd("/") + "/"
$coreServiceClient = Get-CoreServiceClient "Service"
$defaultReadOptions = New-Object Tridion.ContentManager.CoreService.Client.ReadOptions

$importPackageFullPath = Join-Path $PSScriptDir $moduleZip
Write-Verbose "Import Package location: '$importPackageFullPath'"

$permissionsFullPath = Join-Path $PSScriptDir  $moduleZip.Replace(".zip", "_permissions.xml")
Write-Verbose "Permissions file location: '$permissionsFullPath'"

Import-CmPackage $importPackageFullPath $tempFolder

if (Test-Path "$permissionsFullPath")
{
    Import-Security $permissionsFullPath $coreServiceClient
}

$coreServiceClient.Dispose()
