/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.sgcm.Repositories.Interface;

import java.util.List;
import pe.edu.sgcm.DTOs.Requests.CrearHistorialRequest;
import pe.edu.sgcm.DTOs.Responses.HistorialTareaResponse;

/**
 *
 * @author alexis
 */

public interface IHistorialTareaRepository {

    boolean registrar(CrearHistorialRequest request, String usuario, String ip) throws Exception;

    List<HistorialTareaResponse> listarPorTarea(Integer idTarea) throws Exception;
}

