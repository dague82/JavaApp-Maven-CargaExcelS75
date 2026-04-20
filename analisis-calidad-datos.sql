-- Análisis de calidad de datos cargados en S75SUSTITUCIONES_CARGA_2
USE [S75-Test];
GO

-- Resumen por entidad: Total de registros y registros con problemas
SELECT
    SUS_ENTIDAD as 'Entidad/Hoja',
    COUNT(*) as 'Total Registros',
    SUM(CASE WHEN SUS_DNI IS NULL OR SUS_DNI = '' THEN 1 ELSE 0 END) as 'DNI Vacío',
    SUM(CASE WHEN SUS_NOMBRE IS NULL OR SUS_NOMBRE = '' THEN 1 ELSE 0 END) as 'Nombre Vacío',
    SUM(CASE WHEN SUS_TIPOSUS IS NULL OR SUS_TIPOSUS = '' THEN 1 ELSE 0 END) as 'Tipo Sustitución Vacío',
    SUM(CASE WHEN SUS_FECALTA IS NULL THEN 1 ELSE 0 END) as 'Fecha Alta Nula',
    SUM(CASE WHEN SUS_JORNADA IS NULL OR SUS_JORNADA = 0 THEN 1 ELSE 0 END) as 'Jornada Nula/Cero',
    SUM(CASE WHEN SUS_DNI_SUSTITUIDO IS NULL OR SUS_DNI_SUSTITUIDO = '' THEN 1 ELSE 0 END) as 'DNI Sustituido Vacío',
    SUM(CASE WHEN SUS_NUMPST IS NULL OR SUS_NUMPST = 0 THEN 1 ELSE 0 END) as 'Num Puesto Nulo/Cero'
FROM S75SUSTITUCIONES_CARGA_2
GROUP BY SUS_ENTIDAD
ORDER BY 'Total Registros' DESC;

GO

-- Registros específicos con problemas (muestra IDs del 8062 al 8121 si existen)
SELECT
    SUS_ID,
    SUS_ENTIDAD,
    NUM_EXP,
    SUS_DNI,
    SUS_NOMBRE,
    SUS_TIPOSUS,
    SUS_JORNADA,
    SUS_FECALTA,
    SUS_DNI_SUSTITUIDO,
    CASE
        WHEN SUS_DNI IS NULL OR SUS_DNI = '' THEN 'DNI Vacío'
        WHEN SUS_NOMBRE IS NULL OR SUS_NOMBRE = '' THEN 'Nombre Vacío'
        WHEN SUS_TIPOSUS IS NULL OR SUS_TIPOSUS = '' THEN 'Tipo Sustitución Vacío'
        WHEN SUS_FECALTA IS NULL THEN 'Fecha Alta Nula'
        WHEN SUS_JORNADA IS NULL OR SUS_JORNADA = 0 THEN 'Jornada Nula/Cero'
        WHEN SUS_DNI_SUSTITUIDO IS NULL OR SUS_DNI_SUSTITUIDO = '' THEN 'DNI Sustituido Vacío'
        WHEN SUS_NUMPST IS NULL OR SUS_NUMPST = 0 THEN 'Num Puesto Nulo/Cero'
        ELSE 'Posible Problema'
    END as 'Problema'
FROM S75SUSTITUCIONES_CARGA_2
WHERE SUS_ID BETWEEN 8062 AND 8121
ORDER BY SUS_ID;

GO

-- Contar total de registros cargados
SELECT COUNT(*) as 'Total Registros Cargados', MAX(SUS_ID) as 'ID Máximo'
FROM S75SUSTITUCIONES_CARGA_2;
