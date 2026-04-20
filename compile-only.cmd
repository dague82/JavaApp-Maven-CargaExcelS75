@echo off
REM Script para compilar la aplicación con Java 8 y Maven
REM Sin ejecutar (requiere VPN conectada)

setlocal enabledelayedexpansion

REM Configurar rutas locales (solo dentro de este script)
set JAVA_8_HOME=C:\Program Files\Java\jdk1.8.0_05
set MAVEN_HOME=D:\Desarrollo\Maven\apache-maven-3.9.10
set PATH=%JAVA_8_HOME%\bin;%MAVEN_HOME%\bin;%PATH%

cd /d "D:\Desarrollo\Claude-Code\JavaApp-Maven-CargaExcelS75"

echo.
echo ========================================
echo Compilando proyecto con Maven...
echo Java 8: %JAVA_8_HOME%
echo Maven: %MAVEN_HOME%
echo ========================================
echo.

REM Compilar con Java 8 (especificado en JAVA_HOME local)
call %MAVEN_HOME%\bin\mvn compile -DskipTests -q

if %errorlevel% neq 0 (
    echo.
    echo ERROR en compilacion
    pause
    exit /b 1
)

echo.
echo ========================================
echo Compilacion exitosa!
echo.
echo Cuando tengas la VPN conectada, ejecuta:
echo %MAVEN_HOME%\bin\mvn exec:java -Dexec.mainClass="com.dgc.maven.Main"
echo ========================================
echo.

pause
