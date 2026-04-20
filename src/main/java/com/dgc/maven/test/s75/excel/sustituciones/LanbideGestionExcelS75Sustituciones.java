package com.dgc.maven.test.s75.excel.sustituciones;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public class LanbideGestionExcelS75Sustituciones {

    public static final String codigoProcedimiento = "SEI";
    public static final String TIPO_SUSTITUCION_PUI = "PUI";
    public static final String TIPO_SUSTITUCION_TAI = "TAI";
    public static final String TIPO_SUSTITUCION_TAP = "TAP";
    public static final SimpleDateFormat formatoddMMyyyy = new SimpleDateFormat("dd/MM/yyyy");

    //private static final DataBaseGestor dataBaseGestor = new DataBaseGestor();
    private static final SQLServerConnectionManager sqlServerConnectionManager = new SQLServerConnectionManager();
    private static final Logger log = Logger.getLogger(LanbideGestionExcelS75Sustituciones.class);

    public void tratarExcelSustitucionesS75(String fileLocation) throws IOException, SQLException {
        FileInputStream file = new FileInputStream(new File(fileLocation));
        Workbook workbook = new XSSFWorkbook(file);

        for (Iterator<Sheet> it = workbook.sheetIterator(); it.hasNext(); ) {
            Sheet sheet = it.next();
            System.out.println("Leyendo Hoja: " + sheet.getSheetName());
            log.info("Leyendo Hoja: " + sheet.getSheetName());

            System.out.println("Tratando Hoja: " + sheet.getSheetName());
            log.info("Tratando Hoja: " + sheet.getSheetName());

            String sheetName = sheet.getSheetName().toUpperCase();
            if (sheetName.contains("REZIKLETA") || sheetName.contains("TXUKUNBERRI") ||
                sheetName.contains("KONFIA") || sheetName.contains("EKORREPARA")) {
                tratarHojaAlternativa(sheet);
            } else {
                tratarHojaStandard(sheet);
            }
        }
    }

    private void tratarHojaStandard(Sheet sheet) throws SQLException {
        int i = 1;
        String numeroPuestoTratado = "";
        String tipoSustitucionTratado = "";
        String nombrePuestoTratado = "";
        String dniPuestoTratado = "";
        String sexoPuestoTratado = "";
        String numeroExpediente = "";
        S75Sustituciones s75SustitucionesAnterior = new S75Sustituciones();

        for (Row row : sheet) {
            if (row.getCell(12) != null && row.getCell(12).getCellType() == CellType.STRING &&
                row.getCell(12).getStringCellValue() != null &&
                row.getCell(12).getStringCellValue().toUpperCase().startsWith("CONVOCATORIA")) {
                String ejercicioText = row.getCell(12).getStringCellValue();
                String numeroExpedienteText = row.getCell(11).getStringCellValue();
                ejercicioText = (ejercicioText != null && !ejercicioText.isEmpty() && ejercicioText.length() > 13 &&
                    ejercicioText.toUpperCase().startsWith("CONVOCATORIA") ?
                    ejercicioText.toUpperCase().substring(ejercicioText.lastIndexOf("CONVOCATORIA") + 13) : "");
                numeroExpedienteText = (numeroExpedienteText != null && !numeroExpedienteText.isEmpty() ?
                    numeroExpedienteText.substring(numeroExpedienteText.lastIndexOf(":") + 1) : "");
                numeroExpediente = ejercicioText + "/" + codigoProcedimiento + "/" +
                    StringUtils.leftPad(numeroExpedienteText.trim(), 6, "0");
                i++;
                continue;
            }

            String cell0Value = getCellValueAsString(row.getCell(0));
            if (row.getCell(0) != null && !cell0Value.isEmpty() && cell0Value.toUpperCase().contains("PUESTO INSERCION")) {
                i++;
                continue;
            }

            if (row.getCell(7) != null && getCellValueAsString(row.getCell(7)) != null &&
                !getCellValueAsString(row.getCell(7)).isEmpty()) {
                String cell0ValuePuesto = getCellValueAsString(row.getCell(0));
                numeroPuestoTratado = (row.getCell(0) != null && !cell0ValuePuesto.isEmpty() ? cell0ValuePuesto : "");
                tipoSustitucionTratado = (row.getCell(7) != null ? getTipoSustitucion(getCellValueAsString(row.getCell(7))) : "");
                dniPuestoTratado = (row.getCell(4) != null ? getCellValueAsString(row.getCell(4)) : "");
                nombrePuestoTratado = (row.getCell(5) != null ? getCellValueAsString(row.getCell(5)) : "");
                sexoPuestoTratado = (row.getCell(3) != null ? getCellValueAsString(row.getCell(3)) : "");
                i++;
                continue;
            }

            String dniCandidate = getCellValueAsString(row.getCell(4));
            if (row.getCell(4) != null && dniCandidate != null &&
                !dniCandidate.isEmpty() && dniCandidate.length() <= 20) {
                S75Sustituciones s75Sustituciones = new S75Sustituciones();
                s75Sustituciones.setNUM_EXP(numeroExpediente);
                s75Sustituciones.setSUS_TIPOSUS(tipoSustitucionTratado);

                int numPuesto = 0;
                try {
                    if (numeroPuestoTratado != null && !numeroPuestoTratado.isEmpty())
                        numPuesto = Integer.parseInt(numeroPuestoTratado);
                } catch (Exception nfe) {
                    nfe.printStackTrace();
                }
                s75Sustituciones.setSUS_NUMPST(numPuesto);
                s75Sustituciones.setSUS_DNI(limpiarEspaciosPalabra(getCellValueAsString(row.getCell(4))));
                s75Sustituciones.setSUS_TIPOID(String.valueOf(getTipoDocumentoRegexlan(s75Sustituciones.getSUS_DNI())));
                s75Sustituciones.setSUS_NOMBRE(getCellValueAsString(row.getCell(5)));

                if (!getCellValueAsString(row.getCell(8)).isEmpty()) {
                    try {
                        s75Sustituciones.setSUS_JORNADA(Double.parseDouble(getCellValueAsString(row.getCell(8))));
                    } catch (NumberFormatException nfe) {
                        System.out.println("Jornada con formato incorrecto: " + getCellValueAsString(row.getCell(8)));
                        log.warn("Jornada con formato incorrecto: " + getCellValueAsString(row.getCell(8)));
                    }
                }

                s75Sustituciones.setSUS_ENTIDAD(sheet.getSheetName());

                String fecha = getCellValueAsString(row.getCell(9));
                if (fecha.length() == 10) {
                    try {
                        s75Sustituciones.setSUS_FECALTA(formatoddMMyyyy.parse(fecha));
                    } catch (ParseException pex) {
                        System.out.println("Fecha de alta incorrecta: " + fecha);
                        log.error("Fecha de alta incorrecta: " + fecha, pex);
                    }
                }

                fecha = getCellValueAsString(row.getCell(10));
                if (fecha.length() == 10) {
                    try {
                        s75Sustituciones.setSUS_FECBAJA(formatoddMMyyyy.parse(fecha));
                    } catch (ParseException pex) {
                        System.out.println("Fecha de baja incorrecta: " + fecha);
                    }
                }

                s75Sustituciones.setSUS_CONCURRENCIA(getCellValueAsString(row.getCell(6)));
                s75Sustituciones.setSUS_MOTVARIACION(getCellValueAsString(row.getCell(11)));
                s75Sustituciones.setSUS_DNI_SUSTITUIDO(limpiarEspaciosPalabra(dniPuestoTratado));
                s75Sustituciones.setSUS_OBSERVACIONES(getCellValueAsString(row.getCell(12)));

                s75SustitucionesAnterior = s75Sustituciones;
                try {
                    insertarLineaSustituciones(s75Sustituciones);
                } catch (Exception ex) {
                    System.out.println("Error al Guardar: " + ex.getMessage());
                    log.error("Error al Guardar", ex);
                }
            } else if (row.getCell(8) != null && !getCellValueAsString(row.getCell(8)).isEmpty() &&
                       row.getCell(9) != null && !getCellValueAsString(row.getCell(9)).isEmpty()) {
                S75Sustituciones s75Sustituciones = s75SustitucionesAnterior;
                try {
                    s75Sustituciones.setSUS_JORNADA(Double.parseDouble(getCellValueAsString(row.getCell(8))));
                } catch (NumberFormatException nfe) {
                    System.out.println("Jornada con formato incorrecto: " + getCellValueAsString(row.getCell(8)));
                    log.warn("Jornada con formato incorrecto: " + getCellValueAsString(row.getCell(8)));
                }

                String fecha = getCellValueAsString(row.getCell(9));
                if (fecha.length() == 10) {
                    try {
                        s75Sustituciones.setSUS_FECALTA(formatoddMMyyyy.parse(fecha));
                    } catch (ParseException pex) {
                        System.out.println("Fecha de alta incorrecta: " + fecha);
                        log.error("Fecha de alta incorrecta: " + fecha, pex);
                    }
                }

                fecha = getCellValueAsString(row.getCell(10));
                if (fecha.length() == 10) {
                    try {
                        s75Sustituciones.setSUS_FECBAJA(formatoddMMyyyy.parse(fecha));
                    } catch (ParseException pex) {
                        System.out.println("Fecha de baja incorrecta: " + fecha);
                    }
                }

                s75SustitucionesAnterior = s75Sustituciones;
                try {
                    insertarLineaSustituciones(s75Sustituciones);
                } catch (Exception ex) {
                    System.out.println("Error al Guardar: " + ex.getMessage());
                    log.error("Error al Guardar", ex);
                }
            }
        }
    }

    private void tratarHojaAlternativa(Sheet sheet) throws SQLException {
        boolean headerFound = false;
        for (Row row : sheet) {
            String cell0 = getCellValueAsString(row.getCell(0));
            String cell2 = getCellValueAsString(row.getCell(2));

            if (!headerFound) {
                if (cell0.toUpperCase().contains("PUESTO")) {
                    headerFound = true;
                }
                continue;
            }

            if (cell2 == null || cell2.isEmpty() || cell2.length() < 5 || cell2.length() > 20) {
                continue;
            }

            S75Sustituciones s75Sustituciones = new S75Sustituciones();
            s75Sustituciones.setNUM_EXP("");
            s75Sustituciones.setSUS_ENTIDAD(sheet.getSheetName());

            int numPuesto = 0;
            try {
                if (cell0 != null && !cell0.isEmpty())
                    numPuesto = Integer.parseInt(cell0);
            } catch (Exception e) {
            }
            s75Sustituciones.setSUS_NUMPST(numPuesto);
            s75Sustituciones.setSUS_DNI(limpiarEspaciosPalabra(cell2));
            s75Sustituciones.setSUS_TIPOID(String.valueOf(getTipoDocumentoRegexlan(s75Sustituciones.getSUS_DNI())));
            s75Sustituciones.setSUS_NOMBRE(getCellValueAsString(row.getCell(3)));

            String jornada = getCellValueAsString(row.getCell(5));
            if (!jornada.isEmpty()) {
                try {
                    s75Sustituciones.setSUS_JORNADA(Double.parseDouble(jornada));
                } catch (NumberFormatException e) {
                }
            }

            s75Sustituciones.setSUS_TIPOSUS("");

            String fecha = getCellValueAsString(row.getCell(6));
            if (fecha != null && fecha.length() == 10) {
                try {
                    s75Sustituciones.setSUS_FECALTA(formatoddMMyyyy.parse(fecha));
                } catch (ParseException e) {
                }
            }

            fecha = getCellValueAsString(row.getCell(7));
            if (fecha != null && fecha.length() == 10) {
                try {
                    s75Sustituciones.setSUS_FECBAJA(formatoddMMyyyy.parse(fecha));
                } catch (ParseException e) {
                }
            }

            s75Sustituciones.setSUS_CONCURRENCIA("");
            s75Sustituciones.setSUS_MOTVARIACION("");
            s75Sustituciones.setSUS_DNI_SUSTITUIDO("");
            s75Sustituciones.setSUS_OBSERVACIONES(getCellValueAsString(row.getCell(8)));

            try {
                insertarLineaSustituciones(s75Sustituciones);
            } catch (Exception ex) {
            }
        }
    }

    private String getTipoSustitucion(String stringCellValue) {
        if(stringCellValue!= null && !stringCellValue.isEmpty()){
            switch (stringCellValue.trim().toUpperCase()){
                case TIPO_SUSTITUCION_PUI: case ("PUESTO DE INSERCION"): case ("PUESTO INSERCION"):
                    return TIPO_SUSTITUCION_PUI;
                case TIPO_SUSTITUCION_TAI:
                    return TIPO_SUSTITUCION_TAI;
                case TIPO_SUSTITUCION_TAP:
                    return TIPO_SUSTITUCION_TAP;
                default:
                    return "";
            }
        }else
            return "";
    }

    private String getCellValueAsString(Cell celdaExcel){
        String respuesta = "";
        try {
            if(celdaExcel!=null){
                switch (celdaExcel.getCellType()) {
                    case STRING:
                        respuesta = celdaExcel.getStringCellValue(); break;
                    case NUMERIC:
                        if(DateUtil.isCellDateFormatted(celdaExcel)){
                            Date valorDate = celdaExcel.getDateCellValue();
                            respuesta =formatoddMMyyyy.format(valorDate);
                        }else{
                            respuesta = String.valueOf(celdaExcel.getNumericCellValue());
                        }
                        break;
                    case BOOLEAN:
                        respuesta = String.valueOf(celdaExcel.getBooleanCellValue()); break;
                    case FORMULA:
                        respuesta = celdaExcel.getCellFormula(); break;
                    default:
                        respuesta = "";
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
            respuesta="";
        }
        return  respuesta;
    }

    public void  testConexionBD(){
        Connection connection = null;
        try {
            connection = sqlServerConnectionManager.getConnection();
            if(connection!=null){
                String query = "Select * from s75sustituciones ";
                Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery(query);
                List<S75Sustituciones> sustituciones = new ArrayList<>();
                while (rs.next()){
                    S75Sustituciones s75Sustitucion = new S75Sustituciones();
                    s75Sustitucion.setSUS_ID(rs.getInt("SUS_ID"));
                    s75Sustitucion.setNUM_EXP(rs.getString("NUM_EXP"));
                    s75Sustitucion.setSUS_TIPOSUS(rs.getString("SUS_TIPOSUS"));
                    s75Sustitucion.setSUS_NUMPST(rs.getInt("SUS_NUMPST"));
                    s75Sustitucion.setSUS_TIPOID(rs.getString("SUS_TIPOID"));
                    s75Sustitucion.setSUS_DNI(rs.getString("SUS_DNI"));
                    s75Sustitucion.setSUS_NOMBRE(rs.getString("SUS_NOMBRE"));
                    s75Sustitucion.setSUS_APE1(rs.getString("SUS_APE1"));
                    s75Sustitucion.setSUS_APE2(rs.getString("SUS_APE2"));
                    s75Sustitucion.setSUS_SEXO(rs.getString("SUS_SEXO"));
                    s75Sustitucion.setSUS_JORNADA(rs.getDouble("SUS_JORNADA"));
                    s75Sustitucion.setSUS_ENTIDAD(rs.getString("SUS_ENTIDAD"));
                    s75Sustitucion.setSUS_TIPOENT(rs.getInt("SUS_TIPOENT"));
                    s75Sustitucion.setSUS_TIPOCONT(rs.getInt("SUS_TIPOCONT"));
                    s75Sustitucion.setSUS_FECALTA(rs.getDate("SUS_FECALTA"));
                    s75Sustitucion.setSUS_FECBAJA(rs.getDate("SUS_FECBAJA"));
                    s75Sustitucion.setSUS_FORMACION(rs.getString("SUS_FORMACION"));
                    s75Sustitucion.setSUS_COSTE(rs.getDouble("SUS_COSTE"));
                    s75Sustitucion.setSUS_CONCURRENCIA(rs.getString("SUS_CONCURRENCIA"));
                    s75Sustitucion.setSUS_MOTVARIACION(rs.getString("SUS_MOTVARIACION"));
                    sustituciones.add(s75Sustitucion);
                }
                System.out.println("Sustituciones en BD: "
                 + Arrays.toString(sustituciones.toArray()));
                log.info("Sustituciones en BD: "
                        + Arrays.toString(sustituciones.toArray()));
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            try{
                if(connection != null && !connection.isClosed() ){
                    connection.close();
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }

    public void insertarLineaSustituciones(S75Sustituciones sustituciones) throws SQLException {
        Connection con = null;
        try {
            con= sqlServerConnectionManager.getConnection();
            String query="Insert into S75SUSTITUCIONES_CARGA_2 (NUM_EXP ,SUS_TIPOSUS ,SUS_NUMPST ,SUS_TIPOID ,SUS_DNI ,SUS_NOMBRE ,SUS_APE1 ,SUS_APE2 ,SUS_SEXO "
                    + ",SUS_JORNADA ,SUS_ENTIDAD ,SUS_TIPOENT ,SUS_TIPOCONT ,SUS_FECALTA ,SUS_FECBAJA ,SUS_FORMACION ,SUS_COSTE ,SUS_CONCURRENCIA ,SUS_MOTVARIACION ,SUS_DNI_SUSTITUIDO ,SUS_OBSERVACIONES) "
                    + " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query);
            int paramsCont = 1;
            ps.setString(paramsCont++,sustituciones.getNUM_EXP());
            ps.setString(paramsCont++,sustituciones.getSUS_TIPOSUS());
            ps.setInt(paramsCont++,sustituciones.getSUS_NUMPST());
            ps.setString(paramsCont++,sustituciones.getSUS_TIPOID());
            ps.setString(paramsCont++,sustituciones.getSUS_DNI());
            ps.setString(paramsCont++,sustituciones.getSUS_NOMBRE());
            ps.setString(paramsCont++,sustituciones.getSUS_APE1());
            ps.setString(paramsCont++,sustituciones.getSUS_APE2());
            ps.setString(paramsCont++,sustituciones.getSUS_SEXO());
            ps.setDouble(paramsCont++,sustituciones.getSUS_JORNADA());
            ps.setString(paramsCont++,sustituciones.getSUS_ENTIDAD());
            ps.setInt(paramsCont++,sustituciones.getSUS_TIPOENT());
            ps.setInt(paramsCont++,sustituciones.getSUS_TIPOCONT());
            if(sustituciones.getSUS_FECALTA()!= null)
                ps.setDate(paramsCont++,new java.sql.Date(sustituciones.getSUS_FECALTA().getTime()));
            else
                ps.setNull(paramsCont++, Types.DATE);
            if(sustituciones.getSUS_FECBAJA()!= null)
                ps.setDate(paramsCont++,new java.sql.Date(sustituciones.getSUS_FECBAJA().getTime()));
            else
                ps.setNull(paramsCont++, Types.DATE);
            ps.setString(paramsCont++,sustituciones.getSUS_FORMACION());
            ps.setDouble(paramsCont++,sustituciones.getSUS_COSTE());
            ps.setString(paramsCont++,sustituciones.getSUS_CONCURRENCIA());
            ps.setString(paramsCont++,sustituciones.getSUS_MOTVARIACION());
            ps.setString(paramsCont++,sustituciones.getSUS_DNI_SUSTITUIDO());
            ps.setString(paramsCont++,sustituciones.getSUS_OBSERVACIONES());
            ps.executeUpdate();
        }catch (Exception ex){
            ex.printStackTrace();
            try {
                if(con!=null && !con.isClosed()){
                    con.rollback();
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            throw ex;
        }finally {
            try {
                if(con!=null && !con.isClosed()){
                    con.commit();
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    private String limpiarEspaciosPalabra(String textoLimpiar){
        String respuesta="";
        try {
            if(textoLimpiar != null){
                respuesta = StringUtils.deleteWhitespace(textoLimpiar);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return respuesta;
    }

    /**
     * 0	SIN DOCUMENTO
     * 1	N.I.F.
     * 2	PASAPORTE
     * 3	TARJETA RESIDENCIA
     * 4	C.I.F.
     * 5	C.I.F. ENT. PUBLICA
     * 6	NEMOT�CNICO
     * 7	UOR
     * 8	W
     * 9	U
     * 19	PRUEBA MODIFICADA
     * 99	DOCUMENTO MODIFICADO DE PRUEBA
     * @param documento
     * @return
     */
    private Integer getTipoDocumentoRegexlan(String documento){
        Integer respuesta = 0;
        try {
            if(documento!= null && !documento.isEmpty()){
                int tamanhoId = documento.length();
                String primerCaracter = documento.substring(0, 1);
                String ultimoCaracter = documento.substring(tamanhoId-1);
                if(tamanhoId == 9) {
                    try {
                        Integer.parseInt(primerCaracter);
                        // Si no da error entonces es numero, el documento es dni (tipo 1)
                        respuesta = 1;
                    } catch (NumberFormatException nfeC1) {
                        // Si da error entonces es letra, tenemos que comprobar el ultimo caracter
                        try {
                            Integer.parseInt(ultimoCaracter);
                            // Si no da error entonces es numero, el documento es cif (tipo 4)
                            respuesta = 4;
                        } catch (NumberFormatException nfeC9) {
                            //Si el primer caracter es una X, Y o Z el documento es nie (tipo 3), en caso contrario es cif (tipo 4)
                            if ("X".equals(primerCaracter) || "Y".equals(primerCaracter) || "Z".equals(primerCaracter)) {
                                respuesta = 3;
                            } else {
                                respuesta = 4;
                            }
                        }
                    }
                }else{
                    // En otros casos retornamos tipo Pasaporte
                    respuesta=2;
                }
            }
        }catch (Exception ex){
            log.error("Error al obtener del tipo de documento ... " + documento, ex);
        }
        return respuesta;
    }
}
