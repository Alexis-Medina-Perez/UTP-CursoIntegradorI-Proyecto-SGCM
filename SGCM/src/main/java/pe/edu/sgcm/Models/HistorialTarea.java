/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.sgcm.Models;

import java.time.LocalDateTime;

/**
 *
 * @author alexis
 */

public class HistorialTarea extends Auditoria {

    private Integer idHistorialTarea;
    private Integer idTarea;
    private Integer idUsuario;
    private Integer idEstadoTarea;

    private String observacion;
    private LocalDateTime fechaCambio;

    public Integer getIdHistorialTarea() { return idHistorialTarea; }
    public void setIdHistorialTarea(Integer idHistorialTarea) { this.idHistorialTarea = idHistorialTarea; }

    public Integer getIdTarea() { return idTarea; }
    public void setIdTarea(Integer idTarea) { this.idTarea = idTarea; }

    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }

    public Integer getIdEstadoTarea() { return idEstadoTarea; }
    public void setIdEstadoTarea(Integer idEstadoTarea) { this.idEstadoTarea = idEstadoTarea; }

    public String getObservacion() { return observacion; }
    public void setObservacion(String observacion) { this.observacion = observacion; }

    public LocalDateTime getFechaCambio() { return fechaCambio; }
    public void setFechaCambio(LocalDateTime fechaCambio) { this.fechaCambio = fechaCambio; }
}
