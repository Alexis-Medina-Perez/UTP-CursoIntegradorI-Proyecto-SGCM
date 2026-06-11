/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.sgcm.DTOs.Responses;

/**
 *
 * @author alexis
 */
public class ComentarioResponse {
    
    private Integer idComentarioTarea;
    private String comentario;
    private String usuario;

    public Integer getIdComentarioTarea() {
        return idComentarioTarea;
    }

    public void setIdComentarioTarea(Integer idComentarioTarea) {
        this.idComentarioTarea = idComentarioTarea;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

}
