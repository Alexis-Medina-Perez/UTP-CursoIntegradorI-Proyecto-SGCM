/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.sgcm.Services.Implementation;

import java.util.ArrayList;
import java.util.List;
import pe.edu.sgcm.DTOs.Requests.ActualizarEstadoUsuarioRequest;
import pe.edu.sgcm.DTOs.Requests.ActualizarUsuarioRequest;
import pe.edu.sgcm.DTOs.Requests.LoginRequest;
import pe.edu.sgcm.DTOs.Responses.LoginResponse;
import pe.edu.sgcm.Repositories.Interface.IUsuarioRepository;
import pe.edu.sgcm.Repositories.Implementation.UsuarioRepository;
import pe.edu.sgcm.Services.Interface.IUsuarioService;
import pe.edu.sgcm.Utils.JwtUtil;
import pe.edu.sgcm.Utils.PasswordUtil;
import pe.edu.sgcm.Models.Usuario;
import pe.edu.sgcm.Utils.UserContext;
import pe.edu.sgcm.DTOs.Requests.UsuarioRegisterRequest;
import pe.edu.sgcm.DTOs.Requests.UsuarioRolRequest;
import pe.edu.sgcm.DTOs.Responses.ActualizarUsuarioResponse;
import pe.edu.sgcm.DTOs.Responses.EliminarUsuarioResponse;
import pe.edu.sgcm.DTOs.Responses.ListarUsuarioResponse;
import pe.edu.sgcm.DTOs.Responses.RegisterResponse;
import pe.edu.sgcm.DTOs.Responses.RolPorUsuarioResponse;

import org.apache.commons.lang3.StringUtils;

import pe.edu.sgcm.Security.TokenCache;

/**
 *
 * @author alexis
 */


public class UsuarioService implements IUsuarioService {

    private IUsuarioRepository usuarioRepository = new UsuarioRepository();

    @Override
    public LoginResponse loginService(LoginRequest request) throws Exception {

    try {

            if (request == null)
                return new LoginResponse("Request inválido", null);

            if (StringUtils.isBlank(request.getUsername()))
                return new LoginResponse("Username obligatorio", null);

            if (StringUtils.isBlank(request.getPassword()))
                return new LoginResponse("Password obligatorio", null);

            Usuario usuario = usuarioRepository.obtenerUsuarioPorUsername(request.getUsername());

            if (usuario == null) {
                return new LoginResponse("Usuario no encontrado", null);
            }

            String passwordBD = PasswordUtil.decrypt(usuario.getPasswordHash());

            if (!passwordBD.equals(request.getPassword())) {
                return new LoginResponse("Contraseña incorrecta", null);
            }

            String token = JwtUtil.generateToken(usuario.getIdUsuario(), usuario.getUsername());

            TokenCache.guardarToken(token, usuario.getIdUsuario());

            return new LoginResponse("Credenciales correctas", token);

        } catch (Exception e) {

            e.printStackTrace();

            return new LoginResponse("Error interno en login", null);
        }

    }

    @Override
    public RegisterResponse registrarUsuario(UsuarioRegisterRequest request) throws Exception {

        if (request == null)
            return new RegisterResponse("Request inválido");

        if (StringUtils.isBlank(request.getNombres()))
            return new RegisterResponse("Nombres obligatorios");

        if (StringUtils.isBlank(request.getApellidos()))
            return new RegisterResponse("Apellidos obligatorios");

        if (StringUtils.isBlank(request.getCorreo()))
            return new RegisterResponse("Correo obligatorio");

        if (!StringUtils.contains(request.getCorreo(), "@"))
            return new RegisterResponse("Correo inválido");

        if (StringUtils.isBlank(request.getUsername()))
            return new RegisterResponse("Username obligatorio");

        if (StringUtils.length(request.getUsername()) < 4)
            return new RegisterResponse("Username debe tener mínimo 4 caracteres");

        if (StringUtils.isBlank(request.getPassword()))
            return new RegisterResponse("Password obligatorio");

        if (StringUtils.length(request.getPassword()) < 6)
            return new RegisterResponse("Password muy corto");

        if (UserContext.getUserId() == null)
            throw new Exception("No autenticado");

        String passwordEncrypt = PasswordUtil.encrypt(request.getPassword());

        request.setUsuarioCreacion(UserContext.getUsername());
        request.setIpCreacion("127.0.0.1");

        boolean result = usuarioRepository.registrarUsuario(request, passwordEncrypt);

        return result
                ? new RegisterResponse("Usuario registrado correctamente")
                : new RegisterResponse("Error al registrar");
    }

    @Override
    public List<ListarUsuarioResponse> listarUsuarios() throws Exception {

        if (UserContext.getUserId() == null) {
            throw new Exception("No autenticado");
        }

        List<Usuario> lista = usuarioRepository.listarUsuarios();
        List<ListarUsuarioResponse> responseList = new ArrayList<>();

        for (Usuario u : lista) {
            ListarUsuarioResponse res = new ListarUsuarioResponse();

            res.setIdUsuario(u.getIdUsuario());
            res.setNombres(u.getNombres());
            res.setApellidos(u.getApellidos());
            res.setCorreo(u.getCorreo());
            res.setUsername(u.getUsername());
            res.setActivo("F".equals(u.getSqlDeleted()));

            responseList.add(res);
        }

        return responseList;
    }

    @Override
    public ActualizarUsuarioResponse actualizarUsuario(ActualizarUsuarioRequest request) throws Exception {

        if (UserContext.getUserId() == null)
            throw new Exception("No autenticado");

        if (request == null)
            throw new Exception("Request inválido");

        if (request.getIdUsuario() == null)
            throw new Exception("IdUsuario obligatorio");

        if (StringUtils.isBlank(request.getNombres()))
            throw new Exception("Nombres obligatorios");

        if (StringUtils.isBlank(request.getApellidos()))
            throw new Exception("Apellidos obligatorios");

        if (StringUtils.isBlank(request.getCorreo()))
            throw new Exception("Correo obligatorio");

        if (!StringUtils.contains(request.getCorreo(), "@"))
            throw new Exception("Correo inválido");

        String usuarioMod = UserContext.getUsername();

        boolean actualizado = usuarioRepository.actualizarUsuario(
                request,
                usuarioMod,
                "127.0.0.1"
        );

        ActualizarUsuarioResponse response = new ActualizarUsuarioResponse();
        response.setActualizado(actualizado);

        return response;
    }

    @Override
    public EliminarUsuarioResponse eliminarUsuario(Integer idUsuario) throws Exception {

        if (UserContext.getUserId() == null) {
            throw new Exception("No autenticado");
        }

        if (idUsuario == null) {
            throw new Exception("IdUsuario obligatorio");
        }

        String usuarioMod = UserContext.getUsername();

        boolean eliminado = usuarioRepository.eliminarUsuario(
                idUsuario,
                usuarioMod,
                "127.0.0.1"
        );

        EliminarUsuarioResponse response = new EliminarUsuarioResponse();
        response.setEliminado(eliminado);

        return response;
    }

    @Override
    public ActualizarUsuarioResponse actualizarEstadoUsuario(ActualizarEstadoUsuarioRequest request) throws Exception {

        if (UserContext.getUserId() == null) {
            throw new Exception("No autenticado");
        }

        if (request.getIdUsuario() == null) {
            throw new Exception("IdUsuario obligatorio");
        }

        if (request.getActivo() == null) {
            throw new Exception("Estado obligatorio");
        }

        String usuarioMod = UserContext.getUsername();

        boolean actualizado = usuarioRepository.actualizarEstadoUsuario(
                request.getIdUsuario(),
                request.getActivo(),
                usuarioMod,
                "127.0.0.1"
        );

        ActualizarUsuarioResponse response = new ActualizarUsuarioResponse();
        response.setActualizado(actualizado);

        return response;
    }

    @Override
    public boolean asignarRol(UsuarioRolRequest request) throws Exception {

        if (UserContext.getUserId() == null)
            throw new Exception("No autenticado");

        if (request == null)
            throw new Exception("Request inválido");

        if (request.getIdUsuario() == null)
            throw new Exception("IdUsuario obligatorio");

        if (request.getIdRol() == null)
            throw new Exception("IdRol obligatorio");

        if (usuarioRepository.existeAsignacion(request.getIdUsuario(), request.getIdRol()))
            throw new Exception("El usuario ya tiene ese rol");

        String usuario = UserContext.getUsername();

        return usuarioRepository.asignarRol(request, usuario, "127.0.0.1");
    }

    @Override
    public boolean quitarRol(UsuarioRolRequest request) throws Exception {

        if (UserContext.getUserId() == null)
            throw new Exception("No autenticado");

        return usuarioRepository.quitarRol(request.getIdUsuario(), request.getIdRol());
    }

    @Override
    public List<RolPorUsuarioResponse> listarRolesPorUsuario(Integer idUsuario) throws Exception {

        return usuarioRepository.listarRolesUsuario(idUsuario);
    }

}