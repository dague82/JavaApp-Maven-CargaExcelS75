package com.dgc.maven;

import com.dgc.maven.test.s75.excel.sustituciones.LanbideGestionExcelS75Sustituciones;
import com.dgc.maven.test.s75.excel.sustituciones.SQLServerConnectionManager;

import java.io.IOException;
import java.sql.SQLException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws IOException, SQLException {
        System.out.printf("Hello and welcome!");
        System.out.println();

        // Limpiar datos anteriores
        System.out.println("Limpiando datos anteriores...");
        SQLServerConnectionManager connManager = new SQLServerConnectionManager();
        connManager.limpiarTabla();
        System.out.println();

        // Procesar Excel
        System.out.println("Iniciando procesamiento de Excel...");
        LanbideGestionExcelS75Sustituciones lanbideGestionExcelS75Sustituciones = new LanbideGestionExcelS75Sustituciones();
        lanbideGestionExcelS75Sustituciones.tratarExcelSustitucionesS75("C:\\Users\\dguerrero\\OneDrive - DG Software Engineering\\Documents\\ALTIA\\FreelanceServices\\Lanbide\\0300422-DigitalizacionLanbide_2022\\SEI\\#769366_CargaDatosExcelPersonContra(benefi)\\update1\\CUADROS DE EVOLUCION SEI.xlsx");

        System.out.println();
        System.out.println("Proceso completado.");
    }
}