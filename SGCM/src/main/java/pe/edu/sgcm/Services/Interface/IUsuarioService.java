/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.sgcm.Services.Interface;

import java.util.List;
import pe.edu.sgcm.DTOs.Requests.ActualizarEstadoUsuarioRequest;
import pe.edu.sgcm.DTOs.Requests.ActualizarUsuarioRequest;
import pe.edu.sgcm.DTOs.Requests.LoginRequest;
import pe.edu.sgcm.DTOs.Responses.LoginResponse;
import pe.edu.sgcm.DTOs.Requests.UsuarioRegisterRequest;
import pe.edu.sgcm.DTOs.Requests.UsuarioRolRequest;
import pe.edu.sgcm.DTOs.Responses.ActualizarUsuarioResponse;
import pe.edu.sgcm.DTOs.Responses.EliminarUsuarioResponse;
import pe.edu.sgcm.DTOs.Responses.ListarUsuarioResponse;
import pe.edu.sgcm.DTOs.Responses.RegisterResponse;
import pe.edu.sgcm.DTOs.Responses.RolPorUsuarioResponse;

/**
 *
 * @author alexis
 */


public interface IUsuarioService {
    
    LoginResponse loginService(LoginRequest request) throws Exception;

    RegisterResponse registrarUsuario(UsuarioRegisterRequest request) throws Exception;

    List<ListarUsuarioResponse> listarUsuarios() throws Exception;
    
    ActualizarUsuarioResponse actualizarUsuario(ActualizarUsuarioRequest request) throws Exception;

    EliminarUsuarioResponse eliminarUsuario(Integer idUsuario) throws Exception;
    
    ActualizarUsuarioResponse actualizarEstadoUsuario(ActualizarEstadoUsuarioRequest request) throws Exception;

    boolean asignarRol(UsuarioRolRequest request) throws Exception;

    boolean quitarRol(UsuarioRolRequest request) throws Exception;

    List<RolPorUsuarioResponse> listarRolesPorUsuario(Integer idUsuario) throws Exception;

}