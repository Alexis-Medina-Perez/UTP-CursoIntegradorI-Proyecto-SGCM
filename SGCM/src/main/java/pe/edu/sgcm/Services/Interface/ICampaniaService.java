/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.sgcm.Services.Interface;

import java.util.List;
import pe.edu.sgcm.DTOs.Requests.ActualizarCampaniaRequest;
import pe.edu.sgcm.DTOs.Requests.ActualizarEstadoCampaniaRequest;
import pe.edu.sgcm.DTOs.Requests.CrearCampaniaRequest;
import pe.edu.sgcm.DTOs.Responses.CampaniaResponse;

/**
 *
 * @author alexis
 */
public interface ICampaniaService {

    boolean crearCampania(CrearCampaniaRequest request) throws Exception;

    List<CampaniaResponse> listarCampanias() throws Exception;

    boolean actualizarEstado(ActualizarEstadoCampaniaRequest request) throws Exception;
    
    boolean actualizarCampania(ActualizarCampaniaRequest request) throws Exception;

}
