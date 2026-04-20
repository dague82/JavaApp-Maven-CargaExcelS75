package com.dgc.maven.test.s75.excel.sustituciones;

import java.util.Date;

public class S75Sustituciones {
    /**
     SUS_ID	NUMBER(4,0)	IDENTIFICADOR
     NUM_EXP	VARCHAR2(20)	NÚMERO DE EXPEDIENTE
     SUS_TIPOSUS	VARCHAR2(3)	TIPO SUSTITUCIÓN
     SUS_NUMPST	NUMBER(15,0)	CÓDIGO PUESTO
     SUS_TIPOID	VARCHAR2(1)	TIPO IDENTIFICACIÓN
     SUS_DNI	VARCHAR2(10)	Nº IDENTIFICACIÓN
     SUS_NOMBRE	VARCHAR2(50)	NOMBRE
     SUS_APE1	VARCHAR2(80)	PRIMER APELLIDO
     SUS_APE2	VARCHAR2(80)	SEGUNDO APELLIDO
     SUS_SEXO	VARCHAR2(1)	SEXO
     SUS_JORNADA	NUMBER(3,0)	PORCENTAJE DE LA JORNADA LABORAL
     SUS_ENTIDAD	VARCHAR2(100)	ENTIDAD CONTRATANTE
     SUS_TIPOENT	NUMBER(1,0)	TIPO ENTIDAD CONTRATANTE
     SUS_TIPOCONT	NUMBER(1,0)	TIPO CONTRATO
     SUS_FECALTA	DATE	FECHA ALTA EN PUESTO
     SUS_FECBAJA	DATE	FECHA BAJA EN PUESTO
     SUS_FORMACION	VARCHAR2(100)	FORMACIÓN QUE ACREDITA
     SUS_COSTE	NUMBER(4,0)	COSTE PREVISTO
     SUS_CONCURRENCIA	VARCHAR2(1)	INFORME CONCURRENCIA
     SUS_MOTVARIACION	VARCHAR2(1000)	MOTIVO VARIACIÓN
     SUS_DNI_SUSTITUIDO VARCHAR2(25)	DNI SUSTITUID: PUESTO DE INSERCION O TECNICO PRODUCCION/INSERCION
     SUS_OBSERVACIONES  VARCHAR2(1000)	OBSERVACIONES

     */


    private int SUS_ID;
    private String NUM_EXP;
    private String SUS_TIPOSUS;
    private int SUS_NUMPST;
    private String SUS_TIPOID;
    private String SUS_DNI;
    private String SUS_NOMBRE;
    private String SUS_APE1;
    private String SUS_APE2;
    private String SUS_SEXO;
    private double SUS_JORNADA;
    private String SUS_ENTIDAD;
    private int SUS_TIPOENT;
    private int SUS_TIPOCONT;
    private Date SUS_FECALTA;
    private Date SUS_FECBAJA;
    private String SUS_FORMACION;
    private double SUS_COSTE;
    private String SUS_CONCURRENCIA;
    private String SUS_MOTVARIACION;
    private String SUS_DNI_SUSTITUIDO;
    private String SUS_OBSERVACIONES;

    public int getSUS_ID() {
        return SUS_ID;
    }

    public void setSUS_ID(int SUS_ID) {
        this.SUS_ID = SUS_ID;
    }

    public String getNUM_EXP() {
        return NUM_EXP;
    }

    public void setNUM_EXP(String NUM_EXP) {
        this.NUM_EXP = NUM_EXP;
    }

    public String getSUS_TIPOSUS() {
        return SUS_TIPOSUS;
    }

    public void setSUS_TIPOSUS(String SUS_TIPOSUS) {
        this.SUS_TIPOSUS = SUS_TIPOSUS;
    }

    public int getSUS_NUMPST() {
        return SUS_NUMPST;
    }

    public void setSUS_NUMPST(int SUS_NUMPST) {
        this.SUS_NUMPST = SUS_NUMPST;
    }

    public String getSUS_TIPOID() {
        return SUS_TIPOID;
    }

    public void setSUS_TIPOID(String SUS_TIPOID) {
        this.SUS_TIPOID = SUS_TIPOID;
    }

    public String getSUS_DNI() {
        return SUS_DNI;
    }

    public void setSUS_DNI(String SUS_DNI) {
        this.SUS_DNI = SUS_DNI;
    }

    public String getSUS_NOMBRE() {
        return SUS_NOMBRE;
    }

    public void setSUS_NOMBRE(String SUS_NOMBRE) {
        this.SUS_NOMBRE = SUS_NOMBRE;
    }

    public String getSUS_APE1() {
        return SUS_APE1;
    }

    public void setSUS_APE1(String SUS_APE1) {
        this.SUS_APE1 = SUS_APE1;
    }

    public String getSUS_APE2() {
        return SUS_APE2;
    }

    public void setSUS_APE2(String SUS_APE2) {
        this.SUS_APE2 = SUS_APE2;
    }

    public String getSUS_SEXO() {
        return SUS_SEXO;
    }

    public void setSUS_SEXO(String SUS_SEXO) {
        this.SUS_SEXO = SUS_SEXO;
    }

    public double getSUS_JORNADA() {
        return SUS_JORNADA;
    }

    public void setSUS_JORNADA(double SUS_JORNADA) {
        this.SUS_JORNADA = SUS_JORNADA;
    }

    public String getSUS_ENTIDAD() {
        return SUS_ENTIDAD;
    }

    public void setSUS_ENTIDAD(String SUS_ENTIDAD) {
        this.SUS_ENTIDAD = SUS_ENTIDAD;
    }

    public int getSUS_TIPOENT() {
        return SUS_TIPOENT;
    }

    public void setSUS_TIPOENT(int SUS_TIPOENT) {
        this.SUS_TIPOENT = SUS_TIPOENT;
    }

    public int getSUS_TIPOCONT() {
        return SUS_TIPOCONT;
    }

    public void setSUS_TIPOCONT(int SUS_TIPOCONT) {
        this.SUS_TIPOCONT = SUS_TIPOCONT;
    }

    public Date getSUS_FECALTA() {
        return SUS_FECALTA;
    }

    public void setSUS_FECALTA(Date SUS_FECALTA) {
        this.SUS_FECALTA = SUS_FECALTA;
    }

    public Date getSUS_FECBAJA() {
        return SUS_FECBAJA;
    }

    public void setSUS_FECBAJA(Date SUS_FECBAJA) {
        this.SUS_FECBAJA = SUS_FECBAJA;
    }

    public String getSUS_FORMACION() {
        return SUS_FORMACION;
    }

    public void setSUS_FORMACION(String SUS_FORMACION) {
        this.SUS_FORMACION = SUS_FORMACION;
    }

    public double getSUS_COSTE() {
        return SUS_COSTE;
    }

    public void setSUS_COSTE(double SUS_COSTE) {
        this.SUS_COSTE = SUS_COSTE;
    }

    public String getSUS_CONCURRENCIA() {
        return SUS_CONCURRENCIA;
    }

    public void setSUS_CONCURRENCIA(String SUS_CONCURRENCIA) {
        this.SUS_CONCURRENCIA = SUS_CONCURRENCIA;
    }

    public String getSUS_MOTVARIACION() {
        return SUS_MOTVARIACION;
    }

    public void setSUS_MOTVARIACION(String SUS_MOTVARIACION) {
        this.SUS_MOTVARIACION = SUS_MOTVARIACION;
    }

    public String getSUS_DNI_SUSTITUIDO() {
        return SUS_DNI_SUSTITUIDO;
    }

    public void setSUS_DNI_SUSTITUIDO(String SUS_DNI_SUSTITUIDO) {
        this.SUS_DNI_SUSTITUIDO = SUS_DNI_SUSTITUIDO;
    }

    public String getSUS_OBSERVACIONES() {
        return SUS_OBSERVACIONES;
    }

    public void setSUS_OBSERVACIONES(String SUS_OBSERVACIONES) {
        this.SUS_OBSERVACIONES = SUS_OBSERVACIONES;
    }

    @Override
    public String toString() {
        return "S75Sustituciones{" +
                "SUS_ID=" + SUS_ID +
                ", NUM_EXP='" + NUM_EXP + '\'' +
                ", SUS_TIPOSUS='" + SUS_TIPOSUS + '\'' +
                ", SUS_NUMPST=" + SUS_NUMPST +
                ", SUS_TIPOID='" + SUS_TIPOID + '\'' +
                ", SUS_DNI='" + SUS_DNI + '\'' +
                ", SUS_NOMBRE='" + SUS_NOMBRE + '\'' +
                ", SUS_APE1='" + SUS_APE1 + '\'' +
                ", SUS_APE2='" + SUS_APE2 + '\'' +
                ", SUS_SEXO='" + SUS_SEXO + '\'' +
                ", SUS_JORNADA=" + SUS_JORNADA +
                ", SUS_ENTIDAD='" + SUS_ENTIDAD + '\'' +
                ", SUS_TIPOENT=" + SUS_TIPOENT +
                ", SUS_TIPOCONT=" + SUS_TIPOCONT +
                ", SUS_FECALTA=" + SUS_FECALTA +
                ", SUS_FECBAJA=" + SUS_FECBAJA +
                ", SUS_FORMACION='" + SUS_FORMACION + '\'' +
                ", SUS_COSTE=" + SUS_COSTE +
                ", SUS_CONCURRENCIA='" + SUS_CONCURRENCIA + '\'' +
                ", SUS_MOTVARIACION='" + SUS_MOTVARIACION + '\'' +
                ", SUS_DNI_SUSTITUIDO='" + SUS_DNI_SUSTITUIDO + '\'' +
                ", SUS_OBSERVACIONES='" + SUS_OBSERVACIONES + '\'' +
                '}';
    }
}
