# Configuración SQL Server INGDGC

## Estado Actual
- ✅ Clase `SQLServerConnectionManager` creada
- ✅ Dependencia mssql-jdbc agregada a pom.xml
- ✅ `LanbideGestionExcelS75Sustituciones` actualizada para usar SQL Server
- ✅ Script SQL para crear tabla generado
- ✅ Código ajustado para iniciar desde hoja "BADIA BERRI"

## Pasos a Seguir

### 1. Crear Base de Datos y Tabla en SQL Server
```sql
-- Conectarse a SQL Server con SQL Server Management Studio
-- Usuario: dguerrero (Autenticación Windows)
-- Servidor: INGDGC

-- Ejecutar el script: create-sqlserver-table.sql
-- Este crea:
--   - Base de datos: S75-Test
--   - Tabla: S75SUSTITUCIONES_CARGA_2
--   - Secuencia: SEQ_S75SUSTITUCIONES
```

**Ubicación del script:**
```
D:\Desarrollo\Claude-Code\JavaApp-Maven-CargaExcelS75\create-sqlserver-table.sql
```

### 2. Compilar el Proyecto
```batch
D:\Desarrollo\Claude-Code\JavaApp-Maven-CargaExcelS75\compile-only.cmd
```

### 3. Ejecutar la Aplicación
```batch
D:\Desarrollo\Claude-Code\JavaApp-Maven-CargaExcelS75\compile-and-run.cmd
```

## Configuración de Conexión

**Servidor:** INGDGC  
**Base de Datos:** S75-Test  
**Autenticación:** Windows (dguerrero)  
**Puente:** 1433  
**Driver JDBC:** mssql-jdbc 12.4.1 (Java 8)

## Archivo de Configuración

La conexión está configurada en:
```
SQLServerConnectionManager.java
```

Parámetros:
```java
private static final String SERVER = "INGDGC";
private static final String DATABASE = "S75-Test";
private static final String PORT = "1433";
```

## Notas Importantes

- ✅ Autenticación Windows integrada (sin contraseña en JDBC)
- ✅ El proceso inicia desde la hoja "BADIA BERRI"
- ✅ Compatible con Java 8 y Maven 3.9.10
- ⚠️ Requiere que SQL Server esté corriendo en INGDGC
- ⚠️ La tabla se crea automáticamente con el script SQL

## Testing

Para probar la conexión antes de ejecutar:
```java
SQLServerConnectionManager.testConexion();
```

## Cambios Respecto a Oracle

| Aspecto | Oracle | SQL Server |
|--------|--------|-----------|
| Driver | ojdbc8 | mssql-jdbc |
| Secuencia | `SEQ_S75SUSTITUCIONES.nextval` | `NEXT VALUE FOR SEQ_S75SUSTITUCIONES` |
| Autenticación | Usuario/Contraseña SQL | Windows integrada |
| Base de Datos | LBDR.BATERA.LANBIDE.EUS | S75-Test |
