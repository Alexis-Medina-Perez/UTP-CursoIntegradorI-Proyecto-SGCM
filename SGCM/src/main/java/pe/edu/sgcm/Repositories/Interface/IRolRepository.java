/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.sgcm.Repositories.Interface;

import java.util.List;
import pe.edu.sgcm.DTOs.Requests.CrearRolRequest;
import pe.edu.sgcm.Models.Rol;

/**
 *
 * @author alexis
 */
public interface IRolRepository {
    
    List<Rol> listarRoles() throws Exception;

    boolean crearRol(CrearRolRequest request, String usuario, String ip) throws Exception;

    boolean actualizarEstadoRol(Integer idRol, Boolean activo, String usuario, String ip) throws Exception;

}
