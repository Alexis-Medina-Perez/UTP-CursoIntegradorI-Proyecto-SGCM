/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.sgcm.Models;

/**
 *
 * @author alexis
 */

public class ComentarioTarea extends Auditoria {

    private Integer idComentarioTarea;
    private Integer idTarea;
    private Integer idUsuario;

    private String comentario;

    public Integer getIdComentarioTarea() { return idComentarioTarea; }
    public void setIdComentarioTarea(Integer idComentarioTarea) { this.idComentarioTarea = idComentarioTarea; }

    public Integer getIdTarea() { return idTarea; }
    public void setIdTarea(Integer idTarea) { this.idTarea = idTarea; }

    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }

    public String getComentario() { return comentario; }
    public void setComentario(String comentario) { this.comentario = comentario; }
}
