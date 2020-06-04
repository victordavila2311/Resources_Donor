package com.example.resourcesdonor;

public class UsuariosClass {
    private String Nombre, Apellido, Correo, Direccion, DireccionD, Tipo, Verificado, Celular;
    private int CantDonaciones;

    public UsuariosClass() {
    }

    public UsuariosClass(String nombre, String apellido, String correo, String direccion, String direccionD, String tipo, String verificado, String celular, int cantDonaciones) {
        this.Nombre = nombre;
        this.Apellido = apellido;
        this.Correo = correo;
        this.Direccion = direccion;
        this.DireccionD = direccionD;
        this.Tipo = tipo;
        this.Verificado = verificado;
        this.Celular = celular;
        this.CantDonaciones = cantDonaciones;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getApellido() {
        return Apellido;
    }

    public void setApellido(String apellido) {
        Apellido = apellido;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String correo) {
        Correo = correo;
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String direccion) {
        Direccion = direccion;
    }

    public String getDireccionD() {
        return DireccionD;
    }

    public void setDireccionD(String direccionD) {
        DireccionD = direccionD;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String tipo) {
        Tipo = tipo;
    }

    public String getVerificado() {
        return Verificado;
    }

    public void setVerificado(String verificado) {
        Verificado = verificado;
    }

    public int getCantDonaciones() {
        return CantDonaciones;
    }

    public void setCantDonaciones(int cantDonaciones) {
        CantDonaciones = cantDonaciones;
    }

    public String getCelular() {
        return Celular;
    }

    public void setCelular(String celular) {
        Celular = celular;
    }
}
