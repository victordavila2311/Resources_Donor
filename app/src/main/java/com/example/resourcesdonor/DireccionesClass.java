package com.example.resourcesdonor;

/**
 * @author victor manuel davila 1001218585
 * @version 1.0
 * Esta es la clase usada para convertir los JSON de direcciones de Firebase en objetos
 */

public class DireccionesClass {
    private String latitud;
    private String longitud;
    private String descripcion;

    /**
     * se neccesitan crear dos constructores dentro de este tipo de clases para convertir los JSON
     */
    public DireccionesClass() {

    }

    /**
     * segundo constructor para los JSON
     * @param latitud -latitud del marcador
     * @param longitud - longitud del marcador
     * @param descripcion -string:"usuario:xxxx correo:xxxx
     */
    public DireccionesClass(String latitud, String longitud, String descripcion) {
        this.latitud = latitud;
        this.longitud = longitud;
        this.descripcion = descripcion;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
