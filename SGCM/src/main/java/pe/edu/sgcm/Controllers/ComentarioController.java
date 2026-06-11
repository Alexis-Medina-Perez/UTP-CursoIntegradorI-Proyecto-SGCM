/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.sgcm.Controllers;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;
import pe.edu.sgcm.DTOs.Requests.ActualizarComentarioRequest;
import pe.edu.sgcm.DTOs.Requests.CrearComentarioRequest;
import pe.edu.sgcm.DTOs.Responses.ApiResponse;
import pe.edu.sgcm.Services.Implementation.ComentarioService;
import pe.edu.sgcm.Services.Interface.IComentarioService;


/**
 *
 * @author alexis
 */

@Path("/comentarios")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ComentarioController {

    private IComentarioService comentarioService = new ComentarioService();

    @POST
    @Path("/crear")
    public Response crear(CrearComentarioRequest request) throws Exception {

        boolean ok = comentarioService.crear(request);

        return Response.ok(new ApiResponse<>(ok, new ArrayList<>(), ok)).build();
    }

    @PUT
    @Path("/actualizar")
    public Response actualizar(ActualizarComentarioRequest request) throws Exception {

        boolean ok = comentarioService.actualizar(request);

        return Response.ok(new ApiResponse<>(ok, new ArrayList<>(), ok)).build();
    }

    @DELETE
    @Path("/eliminar/{id}")
    public Response eliminar(@PathParam("id") Integer id) throws Exception {

        boolean ok = comentarioService.eliminar(id);

        return Response.ok(new ApiResponse<>(ok, new ArrayList<>(), ok)).build();
    }

    @GET
    @Path("/tarea/{id}")
    public Response listar(@PathParam("id") Integer id) throws Exception {

        return Response.ok(
                new ApiResponse<>(true, new ArrayList<>(), comentarioService.listarPorTarea(id))
        ).build();
    }
}
