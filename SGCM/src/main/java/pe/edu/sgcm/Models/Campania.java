/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.sgcm.Models;

import java.time.LocalDate;

/**
 *
 * @author alexis
 */

public class Campania extends Auditoria {

    private Integer idCampania;
    private Integer idEstadoCampania;
    private Integer idTipoCampania;

    private String nombre;
    private String descripcion;
    private String objetivo;

    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    private String estado;
    private String tipo;
    private String fechaInicioText;
    private String fechaFinText;
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

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getFechaInicioText() {
        return fechaInicioText;
    }

    public void setFechaInicioText(String fechaInicioText) {
        this.fechaInicioText = fechaInicioText;
    }

    public String getFechaFinText() {
        return fechaFinText;
    }

    public void setFechaFinText(String fechaFinText) {
        this.fechaFinText = fechaFinText;
    }

    public Double getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(Double presupuesto) {
        this.presupuesto = presupuesto;
    }


}