/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.sgcm.Repositories.Interface;

import java.util.List;
import pe.edu.sgcm.DTOs.Requests.ActualizarComentarioRequest;
import pe.edu.sgcm.DTOs.Requests.CrearComentarioRequest;
import pe.edu.sgcm.DTOs.Responses.ComentarioResponse;

/**
 *
 * @author alexis
 */

public interface IComentarioRepository {

    boolean crear(CrearComentarioRequest request, String usuario, String ip) throws Exception;

    boolean actualizar(ActualizarComentarioRequest request, String usuario, String ip) throws Exception;

    boolean eliminar(Integer idComentario, String usuario, String ip) throws Exception;

    List<ComentarioResponse> listarPorTarea(Integer idTarea) throws Exception;
}
