/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.sgcm.Repositories.Interface;

import java.util.List;
import pe.edu.sgcm.DTOs.Requests.ActualizarCampaniaRequest;
import pe.edu.sgcm.DTOs.Requests.CrearCampaniaRequest;
import pe.edu.sgcm.Models.Campania;

/**
 *
 * @author alexis
 */
public interface ICampaniaRepository {
    
    boolean crearCampania(CrearCampaniaRequest request, String usuario, String ip) throws Exception;

    List<Campania> listarCampanias() throws Exception;

    boolean actualizarEstado(Integer idCampania, Boolean activo, String usuario, String ip) throws Exception;

    boolean actualizarCampania(ActualizarCampaniaRequest request, String usuario, String ip) throws Exception;
                           
}
