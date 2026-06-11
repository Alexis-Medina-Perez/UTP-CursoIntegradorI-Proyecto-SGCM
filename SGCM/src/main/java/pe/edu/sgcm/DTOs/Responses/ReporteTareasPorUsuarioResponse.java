/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.sgcm.DTOs.Responses;

/**
 *
 * @author alexis
 */

public class ReporteTareasPorUsuarioResponse {

    private String usuario;
    private Integer totalTareas;

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public Integer getTotalTareas() { return totalTareas; }
    public void setTotalTareas(Integer totalTareas) { this.totalTareas = totalTareas; }
}
