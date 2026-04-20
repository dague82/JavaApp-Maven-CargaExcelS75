# JavaApp-Maven-CargaExcelS75

Aplicación Java que carga datos de archivos Excel a una base de datos Oracle.

## Configuración de Base de Datos

### Entorno: DES (Desarrollo)

- **Host**: 172.16.64.16
- **Puerto**: 1530
- **SID**: LBDR.BATERA.LANBIDE.EUS
- **Usuarios disponibles**:
  - `DES/1/user: flb` (contraseña: `flb`)
  - `DES/0/user: flbpru` (contraseña: `flbpru`)

### Archivo de Configuración

Las credenciales están configuradas en: `src/main/resources/dataBase.properties`

### Conexión JDBC

```
jdbc:oracle:thin:@172.16.64.16:1530/LBDR.BATERA.LANBIDE.EUS
```

## Funcionalidad Actual

- Lee archivos Excel (.xlsx) con datos de sustituciones de personal
- Procesa y valida información (DNI, nombre, jornada, fechas)
- Inserta registros en tabla: `S75SUSTITUCIONES_CARGA_2` (nueva versión)

## Excel Soportado

Ubicación del Excel de prueba:
```
C:\Users\dguerrero\OneDrive - DG Software Engineering\Documents\ALTIA\FreelanceServices\Lanbide\0300422-DigitalizacionLanbide_2022\SEI\#769366_CargaDatosExcelPersonContra(benefi)\update1\CUADROS DE EVOLUCION SEI.xlsx
```

## Cambios Realizados

### V2 (Actual)
- Corregidos problemas con lectura de celdas numéricas/texto
- Tabla de destino: `S75SUSTITUCIONES_CARGA_2`
- Manejo mejorado de formatos de datos mixtos

### Errores Conocidos a Resolver

1. **Datos del Excel**: Estructura inconsistente (columnas con datos numéricos/texto)
2. **Conexión BD**: Validar que credenciales estén correctas en dataBase.properties
3. **Fechas**: Algunas fechas en formato numérico en lugar de dd/MM/yyyy

## Build & Ejecución

```bash
# Compilar
mvn clean compile

# Ejecutar
mvn exec:java -Dexec.mainClass="com.dgc.maven.Main"
```

## Dependencias Clave

- Apache POI 5.2.5 (lectura Excel)
- Oracle JDBC 23.4.0
- Log4j 1.2.17
