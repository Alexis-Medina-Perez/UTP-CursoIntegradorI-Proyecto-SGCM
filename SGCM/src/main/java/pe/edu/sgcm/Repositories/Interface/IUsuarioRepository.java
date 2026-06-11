/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.sgcm.Repositories.Interface;

import java.util.List;
import pe.edu.sgcm.DTOs.Requests.ActualizarUsuarioRequest;
import pe.edu.sgcm.DTOs.Requests.UsuarioRegisterRequest;
import pe.edu.sgcm.DTOs.Requests.UsuarioRolRequest;
import pe.edu.sgcm.DTOs.Responses.RolPorUsuarioResponse;
import pe.edu.sgcm.Models.Usuario;

/**
 *
 * @author alexis
 */

public interface IUsuarioRepository {

    public Usuario obtenerUsuarioPorUsername(String username) throws Exception;

    String obtenerPassword(String username) throws Exception;
    
    boolean registrarUsuario(UsuarioRegisterRequest request, String passwordEncrypt) throws Exception;

    List<Usuario> listarUsuarios() throws Exception;
    
    boolean actualizarUsuario(ActualizarUsuarioRequest request, String usuarioMod, String ip) throws Exception;
    
    boolean eliminarUsuario(Integer idUsuario, String usuarioMod, String ip) throws Exception;

    boolean actualizarEstadoUsuario(Integer idUsuario, Boolean activo, String usuarioMod, String ip) throws Exception;

    boolean asignarRol(UsuarioRolRequest request, String usuario, String ip) throws Exception;

    boolean existeAsignacion(Integer idUsuario, Integer idRol) throws Exception;

    boolean quitarRol(Integer idUsuario, Integer idRol) throws Exception;

    List<RolPorUsuarioResponse> listarRolesUsuario(Integer idUsuario) throws Exception;


}

