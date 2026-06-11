/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.sgcm.Services.Interface;

import java.util.List;
import pe.edu.sgcm.DTOs.Requests.ActualizarEstadoRolRequest;
import pe.edu.sgcm.DTOs.Requests.CrearRolRequest;
import pe.edu.sgcm.DTOs.Responses.RolResponse;

/**
 *
 * @author alexis
 */
public interface IRolService {

    List<RolResponse> listarRoles() throws Exception;

    Boolean crearRol(CrearRolRequest request) throws Exception;

    Boolean actualizarEstadoRol(ActualizarEstadoRolRequest request) throws Exception;

}
