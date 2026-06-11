/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.sgcm.Controllers;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;
import pe.edu.sgcm.DTOs.Requests.ActualizarEstadoRolRequest;
import pe.edu.sgcm.DTOs.Requests.CrearRolRequest;
import pe.edu.sgcm.DTOs.Responses.ApiResponse;
import pe.edu.sgcm.Services.Implementation.RolService;
import pe.edu.sgcm.Services.Interface.IRolService;


/**
 *
 * @author alexis
 */

@Path("/roles")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RolController {

    private IRolService rolService = new RolService();

    @GET
    @Path("/listar")
    public Response listar() throws Exception {
        return Response.ok(
                new ApiResponse<>(true, new ArrayList<>(), rolService.listarRoles())
        ).build();
    }

    @POST
    @Path("/crear")
    public Response crear(CrearRolRequest request) throws Exception {

        boolean ok = rolService.crearRol(request);

        return Response.ok(
                new ApiResponse<>(ok, ok ? new ArrayList<>() : List.of("Error"), ok)
        ).build();
    }

    @PUT
    @Path("/estado")
    public Response estado(ActualizarEstadoRolRequest request) throws Exception {

        boolean ok = rolService.actualizarEstadoRol(request);

        return Response.ok(
                new ApiResponse<>(ok, ok ? new ArrayList<>() : List.of("Error"), ok)
        ).build();
    }


}
