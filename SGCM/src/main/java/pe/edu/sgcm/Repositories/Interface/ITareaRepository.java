/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.sgcm.Repositories.Interface;

import java.util.List;
import pe.edu.sgcm.DTOs.Requests.ActualizarTareaRequest;
import pe.edu.sgcm.DTOs.Requests.CrearTareaRequest;
import pe.edu.sgcm.DTOs.Responses.TareaResponse;

/**
 *
 * @author alexis
 */

public interface ITareaRepository {

    boolean crearTarea(CrearTareaRequest request, String usuario, String ip) throws Exception;

    List<TareaResponse> listarTareas() throws Exception;

    boolean actualizarEstado(Integer idTarea, Integer idEstado, String usuario, String ip) throws Exception;
    
    boolean actualizarTarea(ActualizarTareaRequest request, String usuario, String ip) throws Exception;

}
