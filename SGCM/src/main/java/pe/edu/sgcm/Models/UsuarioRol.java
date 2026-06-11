/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.sgcm.Models;

/**
 *
 * @author alexis
 */

public class UsuarioRol extends Auditoria {

    private Integer idUsuarioRol;
    private Integer idUsuario;
    private Integer idRol;

    public Integer getIdUsuarioRol() { return idUsuarioRol; }
    public void setIdUsuarioRol(Integer idUsuarioRol) { this.idUsuarioRol = idUsuarioRol; }

    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }

    public Integer getIdRol() { return idRol; }
    public void setIdRol(Integer idRol) { this.idRol = idRol; }
}
