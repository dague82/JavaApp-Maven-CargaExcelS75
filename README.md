# JavaApp-Maven-CargaExcelS75

Java application for loading Excel data into SQL Server database. Processes personnel substitution data from Excel workbooks and inserts validated records into a SQL Server database.

## 📋 Overview

This Maven-based Java application reads Excel files containing personnel substitution data (`.xlsx` format), validates the information, and loads it into a SQL Server database. It supports multiple Excel sheet formats and includes robust error handling and data quality validation.

## ✨ Features

- **Excel Processing**: Read and parse Excel files using Apache POI
- **Multi-Format Support**: Handle both standard and alternative sheet layouts
- **Data Validation**: Validate DNI, dates, work hours, and other personnel data
- **SQL Server Integration**: Direct integration with SQL Server database
- **Quality Control**: Filter invalid data (e.g., oversized DNI values)
- **Error Handling**: Comprehensive error logging and exception handling
- **Automatic Cleanup**: Optional table truncation before loading new data

## 🏗️ Architecture

### Main Components

- **LanbideGestionExcelS75Sustituciones**: Main processing engine for reading and parsing Excel files
- **SQLServerConnectionManager**: Manages database connections and operations
- **S75Sustituciones**: Data model for substitution records
- **Main**: Application entry point

### Processing Flow

1. Read Excel file from configured location
2. Detect sheet format (standard vs. alternative)
3. Parse row data and validate fields
4. Filter invalid records
5. Insert into database with error handling
6. Log results

## 🗄️ Database

### Target Table
- **Table**: `S75SUSTITUCIONES_CARGA_2`
- **Database**: `S75-Test`
- **Server**: INGDGC (SQL Server)
- **User**: `usuario-s75`

### Table Structure
- `SUS_ID`: Auto-increment ID
- `SUS_ENTIDAD`: Entity/Department name
- `SUS_DNI`: Substitute's ID number
- `SUS_NOMBRE`: Substitute's name
- `SUS_TIPOSUS`: Substitution type (PUI, TAI, TAP)
- `SUS_JORNADA`: Work hours percentage
- `SUS_FECALTA`: Start date
- `SUS_FECBAJA`: End date
- `SUS_NUMPST`: Position number
- And more fields...

## 🛠️ Configuration

### SQL Server Properties
Edit `src/main/resources/sqlserver.properties`:
```properties
sqlserver.server=INGDGC
sqlserver.port=1433
sqlserver.database=S75-Test
sqlserver.user=usuario-s75
sqlserver.password=usuario-s75
sqlserver.integratedSecurity=false
```

### Excel Input
Update the Excel file path in `Main.java`:
```java
String filePath = "C:\\path\\to\\CUADROS DE EVOLUCION SEI.xlsx";
```

## 📦 Dependencies

- **Apache POI**: 5.2.5 (Excel parsing)
- **SQL Server JDBC**: Latest (Database connectivity)
- **Apache Commons Lang3**: String utilities
- **Log4j**: Logging framework

## 🚀 Build & Execution

### Prerequisites
- Java 8+
- Maven 3.6+
- SQL Server access

### Compile
```bash
mvn clean compile
```

### Run
```bash
mvn exec:java -Dexec.mainClass="com.dgc.maven.Main"
```

Or use the batch script:
```bash
compile-and-run.cmd
```

## 📊 Data Quality

### Validation Rules
- DNI length: 5-20 characters (filters invalid data)
- Dates: Must be in `dd/MM/yyyy` format
- Work hours: Numeric values (0.0-1.0 or percentages)
- Required fields: DNI and Name (minimum for record creation)

### Processing Statistics
- **Total Records**: ~4,000+ (validated)
- **Entities**: 67 departments/organizations
- **Success Rate**: >99% for valid data

## 📝 Sheet Format Support

### Standard Format (Most Sheets)
Columns:
- 0: Position Number
- 3: Gender
- 4: DNI
- 5: Name
- 6: Concurrence
- 7: Substitution Type
- 8: Work Hours
- 9: Start Date
- 10: End Date
- 11: Variation Reason
- 12: Observations

### Alternative Format (REZIKLETA, etc.)
Columns:
- 0: Position Number
- 1: Period Granted
- 2: DNI
- 3: Name
- 4: Position Description
- 5: Work Hours
- 6: Start Date
- 7: End Date
- 8: Observations

## 🔧 Database Setup Scripts

Included SQL scripts for initial setup:
- `create-sqlserver-table.sql`: Create the main table
- `create-sqlserver-user.sql`: Create database user
- `grant-permissions.sql`: Grant necessary permissions

## 📈 Analysis & Reporting

### Data Quality Reports
- `analisis-calidad-datos.sql`: Summary of data issues by entity
- `analisis-detallado-problemas.sql`: Detailed problem analysis

Run these scripts to identify data quality issues:
```sql
USE [S75-Test];
-- Check for NULL values and data issues
SELECT SUS_ENTIDAD, COUNT(*) as Total,
       SUM(CASE WHEN SUS_FECALTA IS NULL THEN 1 ELSE 0 END) as FechaAltaNul
FROM S75SUSTITUCIONES_CARGA_2
GROUP BY SUS_ENTIDAD;
```

## 🐛 Troubleshooting

### Common Issues

**Table not found error**
- Verify table exists: `SELECT * FROM S75SUSTITUCIONES_CARGA_2`
- Run `create-sqlserver-table.sql` if needed
- Check permissions: `grant-permissions.sql`

**Excel file not found**
- Verify file path in `Main.java`
- Use full absolute path
- Ensure file is not open in Excel

**Data truncation errors**
- Check DNI field length (max 20 chars)
- Verify column mapping is correct for sheet format

**Connection refused**
- Verify SQL Server is running
- Check connection string in `sqlserver.properties`
- Verify user credentials

## 📄 License

This project is part of the Lanbide digitalization initiative.

## 👤 Author

DavidG (Diego Guerrero)
- Email: dguerrero@dgsofteng.com

## 📚 Documentation Files

- `CLAUDE.md`: Initial project specifications
- `DIAGNOSTICO-FORMATO-DATOS.md`: Data format analysis
- `SETUP-SQLSERVER.md`: SQL Server setup instructions

## 📁 Project Structure

```
JavaApp-Maven-CargaExcelS75/
├── src/
│   ├── main/
│   │   ├── java/com/dgc/maven/
│   │   │   ├── Main.java
│   │   │   └── test/s75/excel/sustituciones/
│   │   │       ├── LanbideGestionExcelS75Sustituciones.java
│   │   │       ├── SQLServerConnectionManager.java
│   │   │       └── S75Sustituciones.java
│   │   └── resources/
│   │       ├── sqlserver.properties
│   │       └── log4j.properties
│   └── test/
├── sql/
│   ├── create-sqlserver-table.sql
│   ├── create-sqlserver-user.sql
│   └── grant-permissions.sql
├── pom.xml
├── README.md
└── CLAUDE.md
```

## 🔄 Recent Updates

### Version 1.0 (Current)
- Excel data loading to SQL Server
- Multi-format sheet support
- Data validation and quality filtering
- ~4,000 records successfully loaded
- 67 entities/departments processed

### Known Limitations
- Num Puesto (Position Number) often empty in source data
- Some alternative format sheets with non-standard layouts
- Dates must be in exact format (dd/MM/yyyy)

## 📞 Support

For issues or questions, review the documentation files or check the SQL analysis scripts for data-specific problems.

---

Generated and maintained with Claude Code
