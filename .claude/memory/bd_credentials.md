---
name: BD Credentials Configuration
description: Oracle BD connection details for DES environment (172.16.64.16:1530)
type: project
---

**BD Environment**: DES (Desarrollo)

**Connection Details**:
- Host: 172.16.64.16
- Port: 1530
- SID: LBDR.BATERA.LANBIDE.EUS
- JDBC URL: `jdbc:oracle:thin:@172.16.64.16:1530/LBDR.BATERA.LANBIDE.EUS`

**Credentials**:
- DES/0: User `flbpru`, Password `flbpru` (Prueba)
- DES/1: User `flb`, Password `flb` (Producción)

**Why**: These are the official credentials for the Lanbide Excel data loading application targeting the development Oracle database at Batera.

**How to apply**: Keep dataBase.properties synchronized with these values. When connecting, use organizacion=0 for flbpru or organizacion=1 for flb user.

**Tables**:
- S75SUSTITUCIONES_CARGA_1: Original carga table
- S75SUSTITUCIONES_CARGA_2: New version (current)
