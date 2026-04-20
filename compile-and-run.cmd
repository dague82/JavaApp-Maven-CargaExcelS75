@echo off
REM Script para compilar y ejecutar la aplicación con Java 8 y Maven desde D:\Desarrollo\Maven
REM Sin cambiar JAVA_HOME global

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
    echo ERROR en compilación
    pause
    exit /b 1
)

echo.
echo ========================================
echo Compilación exitosa. Ejecutando desde BADIA BERRI...
echo ========================================
echo.

REM Ejecutar con Java 8 (especificado en JAVA_HOME local)
call %MAVEN_HOME%\bin\mvn exec:java -Dexec.mainClass="com.dgc.maven.Main" -q

pause
