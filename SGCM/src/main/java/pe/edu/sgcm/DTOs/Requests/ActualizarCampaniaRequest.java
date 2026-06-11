/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.sgcm.DTOs.Requests;

/**
 *
 * @author alexis
 */

public class ActualizarCampaniaRequest {

    private Integer idCampania;
    private Integer idEstadoCampania;
    private Integer idTipoCampania;

    private String nombre;
    private String descripcion;
    private String objetivo;

    private String fechaInicio;
    private String fechaFin;
    private Double presupuesto;

    public Integer getIdCampania() {
        return idCampania;
    }

    public void setIdCampania(Integer idCampania) {
        this.idCampania = idCampania;
    }

    public Integer getIdEstadoCampania() {
        return idEstadoCampania;
    }

    public void setIdEstadoCampania(Integer idEstadoCampania) {
        this.idEstadoCampania = idEstadoCampania;
    }

    public Integer getIdTipoCampania() {
        return idTipoCampania;
    }

    public void setIdTipoCampania(Integer idTipoCampania) {
        this.idTipoCampania = idTipoCampania;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Double getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(Double presupuesto) {
        this.presupuesto = presupuesto;
    }

}
