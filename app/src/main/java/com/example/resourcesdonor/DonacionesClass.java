package com.example.resourcesdonor;
/**
 * Esta es la clase usada para convertir los JSON de donaciones de Firebase en objetos
 * @author victor manuel davila 1001218585
 * @version 1.0
 */

public class DonacionesClass {
    private String de;
    private String descripcion;
    private String para;
    private String recibido;

    /**
     * se neccesitan crear dos constructores dentro de este tipo de clases para convertir los JSON
     */
    public DonacionesClass() {

    }

    /**
     * segundo constructor para el JSON
     * @param de -correo de quien dona
     * @param para -correo de quien recibe la donacion
     * @param descripcion -descripcion donacion
     * @param recibido -se recibio o no
     */
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
