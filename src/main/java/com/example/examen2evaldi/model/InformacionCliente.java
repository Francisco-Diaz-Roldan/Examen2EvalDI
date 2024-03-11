package com.example.examen2evaldi.model;

public class InformacionCliente {

    private Integer id;
    private String nombre;
    private Double peso;
    private Integer edad;
    private Double talla;
    private String tipoActividad;
    private String observaciones;
    private Double GER;
    private Double GET;

    public InformacionCliente(Integer id, String nombre, Double peso, Integer edad, Double talla, String tipoActividad, String observaciones, Double GER, Double GET) {
        this.id = id;
        this.nombre = nombre;
        this.peso = peso;
        this.edad = edad;
        this.talla = talla;
        this.tipoActividad = tipoActividad;
        this.observaciones = observaciones;
        this.GER = GER;
        this.GET = GET;
    }

    public InformacionCliente() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public Double getTalla() {
        return talla;
    }

    public void setTalla(Double talla) {
        this.talla = talla;
    }

    public String getTipoActividad() {
        return tipoActividad;
    }

    public void setTipoActividad(String tipoActividad) {
        this.tipoActividad = tipoActividad;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Double getGER() {
        return GER;
    }

    public void setGER(Double GER) {
        this.GER = GER;
    }

    public Double getGET() {
        return GET;
    }

    public void setGET(Double GET) {
        this.GET = GET;
    }

    @Override
    public String toString() {
        return "InformacionCliente{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", peso=" + peso +
                ", edad=" + edad +
                ", talla=" + talla +
                ", tipoActividad='" + tipoActividad + '\'' +
                ", observaciones='" + observaciones + '\'' +
                ", GER=" + GER +
                ", GET=" + GET +
                '}';
    }
}
