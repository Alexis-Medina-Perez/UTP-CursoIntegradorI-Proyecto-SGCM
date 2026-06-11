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
import pe.edu.sgcm.DTOs.Requests.ActualizarEstadoTareaRequest;
import pe.edu.sgcm.DTOs.Requests.CrearTareaRequest;
import pe.edu.sgcm.DTOs.Requests.ActualizarTareaRequest;
import pe.edu.sgcm.DTOs.Responses.ApiResponse;
import pe.edu.sgcm.Services.Implementation.TareaService;
import pe.edu.sgcm.Services.Interface.ITareaService;


/**
 *
 * @author alexis
 */

@Path("/tareas")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TareaController {

    private ITareaService tareaService = new TareaService();

    @POST
    @Path("/crear")
    public Response crear(CrearTareaRequest request) {

        try {

            boolean ok = tareaService.crearTarea(request);

            return Response.ok(
                    new ApiResponse<>(ok, new ArrayList<>(), ok)
            ).build();

        } catch (Exception e) {

            e.printStackTrace();

            return Response.status(500)
                    .entity(new ApiResponse<>(false, List.of(e.getMessage()), null))
                    .build();
        }
    }

    @GET
    @Path("/listar")
    public Response listar() throws Exception {

        return Response.ok(
                new ApiResponse<>(true, new ArrayList<>(), tareaService.listarTareas())
        ).build();
    }

    @PUT
    @Path("/estado")
    public Response estado(ActualizarEstadoTareaRequest request) {

        try {

            boolean ok = tareaService.actualizarEstado(request);

            return Response.ok(
                    new ApiResponse<>(ok, new ArrayList<>(), ok)
            ).build();

        } catch (Exception e) {

            e.printStackTrace();

            return Response.status(500)
                    .entity(new ApiResponse<>(false, List.of(e.getMessage()), null))
                    .build();
        }
    }

    @PUT
    @Path("/actualizar")
    public Response actualizar(ActualizarTareaRequest request) {

        try {

            boolean ok = tareaService.actualizarTarea(request);

            return Response.ok(
                    new ApiResponse<>(ok,
                            ok ? new ArrayList<>() : List.of("No se pudo actualizar"),
                            ok)
            ).build();

        } catch (Exception e) {

            e.printStackTrace();

            return Response.status(500)
                    .entity(new ApiResponse<>(false, List.of(e.getMessage()), null))
                    .build();
        }
    }

}
