package com.dgc.maven.test.s75.excel.sustituciones;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/*
 * DESACTIVADO - Usar SQLServerConnectionManager para SQL Server local
 *
 * Esta clase estaba conectada a Oracle (servidor cliente)
 * Para desarrollo local, usar SQLServerConnectionManager
 * que conecta a SQL Server INGDGC BD: S75-Test
 */
public class DataBaseGestor {

    private static final String slash = "/";

    @Deprecated
    public Connection getDataBaseConnection(String entorno, int organizacion){
        throw new UnsupportedOperationException(
            "DataBaseGestor desactivado. Usar SQLServerConnectionManager para SQL Server INGDGC");
    }

    @Deprecated
    private String getCadenaConexion(String entorno) {
        return "";
    }

    @Deprecated
    private Map<String,String> getDatosAutenticacion(String entorno, int organizacion) {
        return new HashMap<String,String>();
    }
}
