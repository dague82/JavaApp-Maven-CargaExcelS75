-- Asignar permisos al usuario usuario-s75 en la tabla S75SUSTITUCIONES_CARGA_2
USE [S75-Test];
GO

-- Asignar permisos básicos
GRANT SELECT, INSERT, UPDATE, DELETE ON dbo.S75SUSTITUCIONES_CARGA_2 TO [usuario-s75];
GO

-- Asignar permisos en la secuencia (si la usa)
GRANT SELECT ON OBJECT::dbo.SEQ_S75SUSTITUCIONES TO [usuario-s75];
GO

-- Verificar permisos
SELECT
    USER_NAME(grantee_principal_id) AS Usuario,
    permission_name AS Permiso,
    state_desc AS Estado
FROM sys.database_permissions
WHERE class_desc = 'OBJECT_OR_COLUMN'
    AND OBJECT_NAME(major_id) = 'S75SUSTITUCIONES_CARGA_2'
    AND USER_NAME(grantee_principal_id) = 'usuario-s75';

PRINT 'Permisos asignados a usuario-s75 en S75SUSTITUCIONES_CARGA_2';
GO
