# Sample Data

This directory contains test data and examples for the application.

## Excel Sample File

### Location
The original Excel file used for testing is located at:
```
C:\Users\dguerrero\OneDrive - DG Software Engineering\Documents\ALTIA\FreelanceServices\Lanbide\0300422-DigitalizacionLanbide_2022\SEI\#769366_CargaDatosExcelPersonContra(benefi)\update1\CUADROS DE EVOLUCION SEI.xlsx
```

### File Characteristics
- **Format**: .xlsx (Excel 2007+)
- **Size**: ~1.5 MB
- **Sheets**: 67 different entities/departments
- **Records**: ~8,000+ total rows
- **Data**: Personnel substitution information

### Sheet Format

The Excel file contains data in two main formats:

#### Standard Format (Most sheets)
- BADIA BERRI
- KOOPERA
- SUSPERTU
- URBEGI
- And 60+ other entities

Columns (Order):
1. Position Number (Número Puesto)
2. (header info)
3. Gender (Sexo)
4. DNI (ID Number)
5. Name (Nombre)
6. Concurrence (Concurrencia)
7. Substitution Type (Tipo Sustitución)
8. Work Hours (Jornada)
9. Start Date (Fecha Alta)
10. End Date (Fecha Baja)
11. Variation Reason (Motivo Variación)
12. Observations (Observaciones)

#### Alternative Format
- REZIKLETA
- TXUKUNBERRI
- KONFIA
- EKORREPARA

Columns (Order):
1. Position Number (Puesto Inserción)
2. Period Granted (Periodo Concedido)
3. DNI
4. Name (Nombre)
5. Position Description (Descripción Puesto)
6. Work Hours (Dedicación)
7. Start Date (Alta)
8. End Date (Baja)
9. Observations (Observaciones)

## Data Processing

When running the application, it will:
1. Read the Excel file from the configured location in `Main.java`
2. Skip initial summary sheets (before "BADIA BERRI")
3. Process each entity's data
4. Validate and filter records
5. Load ~4,000 quality records into the database

## Statistics

### Processing Results
- **Total Records Loaded**: 4,082
- **Entities Processed**: 67
- **Success Rate**: >99%
- **Validation Filters**: DNI length (5-20 chars), date formats, required fields

### Data Quality Issues (by count)
- Records with NULL Fecha Alta: Minimal (~1-5 per entity, indicating missing source data)
- Invalid DNI formats: Filtered out automatically
- Complete records: ~4,000

## How to Use

1. Ensure the Excel file exists at the path specified in `Main.java`
2. Verify SQL Server connection parameters in `src/main/resources/sqlserver.properties`
3. Run the application:
   ```bash
   mvn clean compile
   mvn exec:java -Dexec.mainClass="com.dgc.maven.Main"
   ```

## Notes

- The Excel file is NOT included in the repository (too large)
- Path contains special characters and spaces - use the full path provided above
- First run automatically truncates the target table to prevent duplicates
- Data is validated during processing - invalid records are skipped with error logging
