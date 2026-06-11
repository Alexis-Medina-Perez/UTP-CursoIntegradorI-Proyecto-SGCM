/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.sgcm.Controllers;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;
import pe.edu.sgcm.DTOs.Requests.CrearNotificacionRequest;
import pe.edu.sgcm.DTOs.Requests.MarcarLeidoRequest;
import pe.edu.sgcm.DTOs.Responses.ApiResponse;
import pe.edu.sgcm.Services.Implementation.NotificacionService;
import pe.edu.sgcm.Services.Interface.INotificacionService;

/**
 *
 * @author alexis
 */

@Path("/notificaciones")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class NotificacionController {

    private INotificacionService service = new NotificacionService();

    @POST
    @Path("/crear")
    public Response crear(CrearNotificacionRequest request) throws Exception {

        boolean ok = service.crear(request);

        return Response.ok(new ApiResponse<>(ok, new ArrayList<>(), ok)).build();
    }

    @GET
    @Path("/usuario/{id}")
    public Response listar(@PathParam("id") Integer id) throws Exception {

        return Response.ok(
                new ApiResponse<>(true, new ArrayList<>(), service.listarPorUsuario(id))
        ).build();
    }

    @PUT
    @Path("/leido")
    public Response leido(MarcarLeidoRequest request) throws Exception {

        boolean ok = service.marcarLeido(request);

        return Response.ok(new ApiResponse<>(ok, new ArrayList<>(), ok)).build();
    }
}
