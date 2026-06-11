/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.sgcm.Controllers;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import pe.edu.sgcm.DTOs.Requests.LoginRequest;
import pe.edu.sgcm.DTOs.Responses.ApiResponse;
import pe.edu.sgcm.DTOs.Responses.LoginResponse;
import pe.edu.sgcm.DTOs.Requests.UsuarioRegisterRequest;
import pe.edu.sgcm.DTOs.Responses.RegisterResponse;
import pe.edu.sgcm.Services.Interface.IUsuarioService;
import pe.edu.sgcm.Services.Implementation.UsuarioService;

import java.util.ArrayList;
import java.util.List;
import pe.edu.sgcm.DTOs.Requests.ActualizarEstadoUsuarioRequest;
import pe.edu.sgcm.DTOs.Requests.ActualizarUsuarioRequest;
import pe.edu.sgcm.DTOs.Requests.UsuarioRolRequest;
import pe.edu.sgcm.DTOs.Responses.ActualizarUsuarioResponse;
import pe.edu.sgcm.DTOs.Responses.EliminarUsuarioResponse;
import pe.edu.sgcm.DTOs.Responses.ListarUsuarioResponse;
import pe.edu.sgcm.Security.TokenCache;


/**
 *
 * @author alexis
 */

@Path("/usuarios")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UsuarioController {

    private IUsuarioService usuarioService = new UsuarioService();

    @GET
    @Path("/ping")
    public String ping() {
        return "ESTOY VIVO NUEVO";
    }

    @POST
    @Path("/login")
    public Response login(LoginRequest request) {

        try {
            LoginResponse loginResponse = usuarioService.loginService(request);

            boolean success = loginResponse.getRespuesta().equals("Credenciales correctas");

            ApiResponse<LoginResponse> response = new ApiResponse<>(
                    success,
                    success ? new ArrayList<>() : java.util.List.of(loginResponse.getRespuesta()),
                    loginResponse
            );

            return Response.ok(response).build();

        } catch (Exception e) {
            ApiResponse<LoginResponse> response = new ApiResponse<>(
                    false,
                    java.util.List.of(e.getMessage()),
                    null
            );

            return Response.status(500).entity(response).build();
        }
    }
    
    @POST
    @Path("/registrar")
    public Response registrar(UsuarioRegisterRequest request) {

        try {
            RegisterResponse responseService = usuarioService.registrarUsuario(request);

            boolean success = responseService.getMensaje().contains("correctamente");

            ApiResponse<RegisterResponse> response = new ApiResponse<>(
                    success,
                    success ? new ArrayList<>() : java.util.List.of(responseService.getMensaje()),
                    responseService
            );

            return Response.ok(response).build();

//        } catch (Exception e) {
//            return Response.status(500)
//                    .entity(new ApiResponse<>(false, java.util.List.of(e.getMessage()), null))
//                    .build();
//        }

        }
catch (Exception e) {
    e.printStackTrace();

    return Response.status(500)
            .entity(new ApiResponse<>(false, java.util.List.of(e.getMessage()), null))
            .build();
}

    }

    @GET
    @Path("/listar")
    public Response listarUsuarios() {

        try {

            var lista = usuarioService.listarUsuarios();

            ApiResponse<List<ListarUsuarioResponse>> response = new ApiResponse<>(
                    true,
                    new ArrayList<>(),
                    lista
            );

            return Response.ok(response).build();

        } catch (Exception e) {

            e.printStackTrace();

            ApiResponse<List<ListarUsuarioResponse>> response = new ApiResponse<>(
                    false,
                    java.util.List.of(e.getMessage()),
                    null
            );

            return Response.status(401).entity(response).build();
        }
    }

    @PUT
    @Path("/actualizar")
    public Response actualizar(ActualizarUsuarioRequest request) {

        try {

            ActualizarUsuarioResponse serviceResponse = usuarioService.actualizarUsuario(request);

            ApiResponse<ActualizarUsuarioResponse> response = new ApiResponse<>(
                    serviceResponse.isActualizado(),
                    serviceResponse.isActualizado()
                            ? new ArrayList<>()
                            : java.util.List.of("No se pudo actualizar"),
                    serviceResponse
            );

            return Response.ok(response).build();

        } catch (Exception e) {

            e.printStackTrace();

            ApiResponse<ActualizarUsuarioResponse> response = new ApiResponse<>(
                    false,
                    java.util.List.of(e.getMessage()),
                    null
            );

            return Response.status(500).entity(response).build();
        }
    }

    @DELETE
    @Path("/eliminar/{id}")
    public Response eliminar(@PathParam("id") Integer id) {

        try {

            EliminarUsuarioResponse serviceResponse = usuarioService.eliminarUsuario(id);

            ApiResponse<EliminarUsuarioResponse> response = new ApiResponse<>(
                    serviceResponse.isEliminado(),
                    serviceResponse.isEliminado()
                            ? new ArrayList<>()
                            : java.util.List.of("No se pudo eliminar"),
                    serviceResponse
            );

            return Response.ok(response).build();

        } catch (Exception e) {

            e.printStackTrace();

            ApiResponse<EliminarUsuarioResponse> response = new ApiResponse<>(
                    false,
                    java.util.List.of(e.getMessage()),
                    null
            );

            return Response.status(500).entity(response).build();
        }
    }

    @PUT
    @Path("/estado")
    public Response actualizarEstado(ActualizarEstadoUsuarioRequest request) {

        try {

            ActualizarUsuarioResponse serviceResponse =
                    usuarioService.actualizarEstadoUsuario(request);

            ApiResponse<ActualizarUsuarioResponse> response = new ApiResponse<>(
                    serviceResponse.isActualizado(),
                    serviceResponse.isActualizado()
                            ? new ArrayList<>()
                            : java.util.List.of("No se pudo actualizar estado"),
                    serviceResponse
            );

            return Response.ok(response).build();

        } catch (Exception e) {

            e.printStackTrace();

            return Response.status(500)
                    .entity(new ApiResponse<>(false, java.util.List.of(e.getMessage()), null))
                    .build();
        }
    }

    @POST
    @Path("/roles/asignar")
    public Response asignarRol(UsuarioRolRequest request) {

        try {

            boolean ok = usuarioService.asignarRol(request);

            return Response.ok(
                    new ApiResponse<>(ok, 
                            ok ? new ArrayList<>() : List.of("Error al asignar rol"),
                            ok)
            ).build();

        } catch (Exception e) {

            e.printStackTrace();

            return Response.status(500)
                    .entity(new ApiResponse<>(false, List.of(e.getMessage()), null))
                    .build();
        }
    }

    @DELETE
    @Path("/roles/quitar")
    public Response quitarRol(UsuarioRolRequest request) {

        try {

            boolean ok = usuarioService.quitarRol(request);

            return Response.ok(
                    new ApiResponse<>(ok, 
                            ok ? new ArrayList<>() : List.of("No se pudo quitar rol"),
                            ok)
            ).build();

        } catch (Exception e) {

            e.printStackTrace();

            return Response.status(500)
                    .entity(new ApiResponse<>(false, List.of(e.getMessage()), null))
                    .build();
        }
    }

    @GET
    @Path("/{id}/roles")
    public Response listarRolesUsuario(@PathParam("id") Integer id) {

        try {

            var lista = usuarioService.listarRolesPorUsuario(id);

            return Response.ok(
                    new ApiResponse<>(true, new ArrayList<>(), lista)
            ).build();

        } catch (Exception e) {

            e.printStackTrace();

            return Response.status(500)
                    .entity(new ApiResponse<>(false, List.of(e.getMessage()), null))
                    .build();
        }
    }

    @POST
    @Path("/logout")
    public Response logout(@HeaderParam("Authorization") String authHeader) {

        String token = authHeader.replace("Bearer ", "");

        TokenCache.invalidarToken(token);

        return Response.ok("Logout exitoso").build();
    }


}
