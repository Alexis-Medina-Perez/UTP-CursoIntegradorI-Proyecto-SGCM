/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.sgcm.Services.Interface;

import java.util.List;
import pe.edu.sgcm.DTOs.Requests.CrearHistorialRequest;
import pe.edu.sgcm.DTOs.Responses.HistorialTareaResponse;

/**
 *
 * @author alexis
 */

public interface IHistorialTareaService {

    boolean registrar(CrearHistorialRequest request) throws Exception;

    List<HistorialTareaResponse> listar(Integer idTarea) throws Exception;
}
