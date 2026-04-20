-- Script para crear usuario SQL Server en INGDGC
-- Base de datos: S75-Test
-- Usuario: usuario-s75
-- Contraseña: usuario-s75

USE master;
GO

-- Crear login SQL
IF NOT EXISTS (SELECT * FROM sys.sql_logins WHERE name = 'usuario-s75')
BEGIN
    CREATE LOGIN [usuario-s75] WITH PASSWORD = 'usuario-s75';
    PRINT 'Login [usuario-s75] creado exitosamente.';
END
ELSE
BEGIN
    PRINT 'El login [usuario-s75] ya existe.';
END
GO

-- Cambiar a la BD S75-Test
USE [S75-Test];
GO

-- Crear usuario en la BD
IF NOT EXISTS (SELECT * FROM sys.database_principals WHERE name = 'usuario-s75')
BEGIN
    CREATE USER [usuario-s75] FOR LOGIN [usuario-s75];
    PRINT 'Usuario [usuario-s75] creado en BD S75-Test.';
END
ELSE
BEGIN
    PRINT 'El usuario [usuario-s75] ya existe en BD S75-Test.';
END
GO

-- Asignar permisos sobre la tabla
GRANT SELECT, INSERT, UPDATE, DELETE ON dbo.S75SUSTITUCIONES_CARGA_2 TO [usuario-s75];
GRANT EXECUTE ON OBJECT::dbo.S75SUSTITUCIONES_CARGA_2 TO [usuario-s75];
PRINT 'Permisos asignados a [usuario-s75].';
GO

-- Verificar
SELECT name, type_desc FROM sys.database_principals WHERE name = 'usuario-s75';
PRINT 'Usuario S75-Test creado y configurado.';
