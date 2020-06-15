package com.example.resourcesdonor;

public class DonacionesClass {
    private String de;
    private String descripcion;
    private String para;
    private String recibido;

    public DonacionesClass() {

    }

    public DonacionesClass(String de, String para, String descripcion, String recibido) {
        this.de = de;
        this.para = para;
        this.descripcion = descripcion;
        this.recibido = recibido;
    }

    public String getDe() {
        return de;
    }

    public void setDe(String de) {
        this.de = de;
    }

    public String getPara() {
        return para;
    }

    public void setPara(String para) {
        this.para = para;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getRecibido() {
        return recibido;
    }

    public void setRecibido(String recibido) {
        this.recibido = recibido;
    }
}
