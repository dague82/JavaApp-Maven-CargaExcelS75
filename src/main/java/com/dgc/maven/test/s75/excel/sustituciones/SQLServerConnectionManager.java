package com.dgc.maven.test.s75.excel.sustituciones;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SQLServerConnectionManager {

    private static final Logger log = Logger.getLogger(SQLServerConnectionManager.class);
    private static final ResourceBundle props = ResourceBundle.getBundle("sqlserver");

    private static final String DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static final String SERVER = props.getString("sqlserver.server");
    private static final String PORT = props.getString("sqlserver.port");
    private static final String DATABASE = props.getString("sqlserver.database");
    private static final String USER = props.getString("sqlserver.user");
    private static final String PASSWORD = props.getString("sqlserver.password");
    private static final boolean INTEGRATED = Boolean.parseBoolean(props.getString("sqlserver.integratedSecurity"));

    private static final String JDBC_URL = "jdbc:sqlserver://" + SERVER + ":" + PORT +
            ";databaseName=" + DATABASE +
            ";integratedSecurity=" + INTEGRATED +
            ";trustServerCertificate=true;" +
            ";encrypt=false;";

    public Connection getConnection() throws SQLException {
        try {
            Class.forName(DRIVER);
            System.out.println("Conectando a SQL Server: " + SERVER + " BD: " + DATABASE);
            log.info("Conectando a SQL Server: " + SERVER + " BD: " + DATABASE);

            Connection connection;
            if (INTEGRATED) {
                connection = DriverManager.getConnection(JDBC_URL);
            } else {
                connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
            }

            System.out.println("Conexión exitosa a SQL Server");
            log.info("Conexión exitosa a SQL Server");
            return connection;

        } catch (ClassNotFoundException e) {
            System.err.println("Driver JDBC de SQL Server no encontrado: " + e.getMessage());
            log.error("Driver JDBC de SQL Server no encontrado", e);
            throw new SQLException("Driver SQL Server no disponible", e);
        } catch (SQLException e) {
            System.err.println("Error de conexión a SQL Server: " + e.getMessage());
            log.error("Error de conexión a SQL Server", e);
            throw e;
        }
    }

    public void limpiarTabla() throws SQLException {
        Connection connection = null;
        try {
            connection = getConnection();
            String query = "TRUNCATE TABLE dbo.S75SUSTITUCIONES_CARGA_2";
            java.sql.Statement stmt = connection.createStatement();
            stmt.execute(query);
            connection.setAutoCommit(true);
            System.out.println("✓ Tabla S75SUSTITUCIONES_CARGA_2 limpiada (datos anteriores eliminados)");
            log.info("✓ Tabla S75SUSTITUCIONES_CARGA_2 limpiada (datos anteriores eliminados)");
        } catch (SQLException e) {
            System.err.println("Error al limpiar tabla: " + e.getMessage());
            log.error("Error al limpiar tabla", e);
        } finally {
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void testConexion() {
        SQLServerConnectionManager manager = new SQLServerConnectionManager();
        Connection connection = null;
        try {
            connection = manager.getConnection();
            if (connection != null && !connection.isClosed()) {
                System.out.println("✓ Conexión a SQL Server INGDGC - BD S75-Test: OK");
                log.info("✓ Conexión a SQL Server INGDGC - BD S75-Test: OK");
            }
        } catch (SQLException e) {
            System.err.println("✗ Error en test de conexión: " + e.getMessage());
            log.error("✗ Error en test de conexión", e);
        } finally {
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
