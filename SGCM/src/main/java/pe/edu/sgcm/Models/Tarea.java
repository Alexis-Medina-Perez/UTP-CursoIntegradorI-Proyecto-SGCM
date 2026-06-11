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

public class Tarea extends Auditoria {

    private Integer idTarea;

    private Integer idCampania;
    private Integer idEstadoTarea;
    private Integer idPrioridadTarea;
    private Integer idUsuarioResponsable;

    private String nombre;
    private String descripcion;

    private LocalDate fechaInicio;
    private LocalDate fechaLimite;
    private LocalDate fechaFinalizacion;

    private Integer porcentajeAvance;

    public Integer getIdTarea() { return idTarea; }
    public void setIdTarea(Integer idTarea) { this.idTarea = idTarea; }

    public Integer getIdCampania() { return idCampania; }
    public void setIdCampania(Integer idCampania) { this.idCampania = idCampania; }

    public Integer getIdEstadoTarea() { return idEstadoTarea; }
    public void setIdEstadoTarea(Integer idEstadoTarea) { this.idEstadoTarea = idEstadoTarea; }

    public Integer getIdPrioridadTarea() { return idPrioridadTarea; }
    public void setIdPrioridadTarea(Integer idPrioridadTarea) { this.idPrioridadTarea = idPrioridadTarea; }

    public Integer getIdUsuarioResponsable() { return idUsuarioResponsable; }
    public void setIdUsuarioResponsable(Integer idUsuarioResponsable) { this.idUsuarioResponsable = idUsuarioResponsable; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }

    public LocalDate getFechaLimite() { return fechaLimite; }
    public void setFechaLimite(LocalDate fechaLimite) { this.fechaLimite = fechaLimite; }

    public LocalDate getFechaFinalizacion() { return fechaFinalizacion; }
    public void setFechaFinalizacion(LocalDate fechaFinalizacion) { this.fechaFinalizacion = fechaFinalizacion; }

    public Integer getPorcentajeAvance() { return porcentajeAvance; }
    public void setPorcentajeAvance(Integer porcentajeAvance) { this.porcentajeAvance = porcentajeAvance; }
}
