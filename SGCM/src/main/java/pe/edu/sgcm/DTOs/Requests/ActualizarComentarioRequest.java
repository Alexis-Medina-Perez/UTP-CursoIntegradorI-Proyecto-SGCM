/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.sgcm.DTOs.Requests;

/**
 *
 * @author alexis
 */
public class ActualizarComentarioRequest {
    
    private Integer idComentarioTarea;
    private String comentario;

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

}
