# Diagnóstico de Problemas de Formato de Datos

## Resumen Ejecutivo

Se han identificado **problemas de alineación de columnas** en varias hojas del Excel:
- **130 registros totales** afectados por NULL en campos críticos
- **REZIKLETA es crítica**: 120 registros con Tipo Sustitución y Fecha Alta NULL
- **Problema de Num Puesto global**: TODAS las 8,199 registros tienen Num Puesto = 0

---

## 1. ENTIDADES CON PROBLEMAS IDENTIFICADOS

### 🔴 CRÍTICO: REZIKLETA (IDs 8062-8121)
- **Registros afectados**: 120 (100%)
- **Campos NULL**:
  - `SUS_TIPOSUS`: NULL (Tipo Sustitución debería estar aquí)
  - `SUS_FECALTA`: NULL (Fecha de Alta debería estar aquí)
  - `SUS_NUMPST`: 0 (Número de Puesto vacío)
- **Campos con datos**: `SUS_DNI`, `SUS_NOMBRE`, `SUS_DNI_SUSTITUIDO` (contiene datos sustituido)
- **Diagnóstico**: Columnas DESPLAZADAS - Los datos están en columnas diferentes

### 🟡 IMPORTANTE: TXUKUNBERRI
- **Registros afectados**: 6
- **Campos NULL**: `SUS_TIPOSUS`

### 🟡 IMPORTANTE: KONFIA
- **Registros afectados**: 2
- **Campos NULL**: `SUS_TIPOSUS`

### 🟡 IMPORTANTE: EKORREPARA
- **Registros afectados**: 2
- **Campos NULL**: `SUS_TIPOSUS`

---

## 2. MAPEO ACTUAL DE COLUMNAS EN CÓDIGO

El archivo `LanbideGestionExcelS75Sustituciones.java` espera:

```
Columna 0:  Número de Puesto (SUS_NUMPST)
Columna 3:  Sexo
Columna 4:  DNI Sustituto (SUS_DNI)  ← LÍNEA VERIFICADORA
Columna 5:  Nombre (SUS_NOMBRE)
Columna 6:  Concurrencia
Columna 7:  Tipo Sustitución (SUS_TIPOSUS)
Columna 8:  Jornada (SUS_JORNADA)
Columna 9:  Fecha Alta (SUS_FECALTA)
Columna 10: Fecha Baja (SUS_FECBAJA)
Columna 11: Motivo Variación / Expediente
Columna 12: Observaciones / Convocatoria
```

---

## 3. PROBLEMA GLOBAL: SUS_NUMPST = 0 PARA TODOS

- **Registros afectados**: 8,199 (100%)
- **Causa probable**: La Columna 0 (Número de Puesto) está vacía en el Excel O
  - El parsing falla y por defecto se asigna 0
  - Ver líneas 108-115 en el código (catch silencioso)

---

## 4. DATOS QUE NECESITO PARA DIAGNOSTICAR

Para identificar el problema de columnas desplazadas en REZIKLETA y otras hojas:

### A. Información del Excel (REVISIÓN VISUAL REQUERIDA)
1. **¿Qué columnas contiene REZIKLETA?** (encabezados y primeros 3-5 datos)
   - Especialmente: ¿dónde está "Tipo Sustitución"?
   - ¿Dónde está "Fecha Alta"?
   - ¿Dónde está "Número Puesto"?

2. **¿Cuántas columnas tiene REZIKLETA comparado con BADIA BERRI?**
   - ¿Faltan columnas al inicio?
   - ¿Hay columnas extras?

3. **¿Hay encabezados en REZIKLETA?**
   - ¿En qué fila comienzan los datos reales?

### B. Validación con SQL
Ejecutar: `analisis-detallado-problemas.sql`

Este script proporcionará:
1. Resumen de qué entidades tienen campos NULL
2. Detalles específicos de REZIKLETA
3. Muestra de datos correctos (BADIA BERRI) vs problemáticos (REZIKLETA)

---

## 5. PRÓXIMOS PASOS

### Fase 1: Diagnóstico (necesita información del Excel)
1. Abre el archivo Excel
2. Compara encabezados de BADIA BERRI vs REZIKLETA
3. Identifica si las columnas están en diferente orden
4. Reporta: ¿En qué columna está "Tipo Sustitución" en REZIKLETA?

### Fase 2: Solución Técnica
Si REZIKLETA tiene columnas en diferente orden:
- Crear lógica de detección de encabezados dinámicos
- O crear mapeo específico por hoja
- O crear 2 procesadores (uno para hojas estándar, otro para REZIKLETA)

### Fase 3: Validación
1. Re-procesar datos
2. Verificar que Tipo Sustitución y Fecha Alta se cargan correctamente
3. Investigar por qué Num Puesto está vacío

---

## 6. INFORMACIÓN ADICIONAL ÚTIL

### Para reproducir el problema:
```sql
-- Ver detalles de REZIKLETA
SELECT TOP 10 *
FROM S75SUSTITUCIONES_CARGA_2
WHERE SUS_ENTIDAD = 'REZIKLETA'
ORDER BY SUS_ID;

-- Comparar con BADIA BERRI
SELECT TOP 10 *
FROM S75SUSTITUCIONES_CARGA_2
WHERE SUS_ENTIDAD = 'BADIA BERRI'
ORDER BY SUS_ID;
```

### Columnas de interés:
- `SUS_TIPOSUS`: ¿NULL en REZIKLETA, relleno en BADIA BERRI?
- `SUS_FECALTA`: ¿NULL en REZIKLETA, relleno en BADIA BERRI?
- `SUS_NUMPST`: ¿0 en ambas? → Problema global
- `SUS_DNI_SUSTITUIDO`: ¿Tiene datos en REZIKLETA donde no debería?

---

## Conclusión

La raíz del problema parece ser que **REZIKLETA (y posiblemente TXUKUNBERRI, KONFIA, EKORREPARA) tienen una estructura de columnas diferente** al resto del Excel.

**El código asume un solo formato, pero hay al menos 2 formatos diferentes de hojas en el Excel.**

Necesito: **Ver la estructura del Excel** para identificar exactamente dónde están las columnas en cada hoja problemática.
