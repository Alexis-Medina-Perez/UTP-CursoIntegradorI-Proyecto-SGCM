/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.sgcm.Controllers;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;
import pe.edu.sgcm.DTOs.Responses.ApiResponse;
import pe.edu.sgcm.Services.Implementation.HistorialTareaService;
import pe.edu.sgcm.Services.Interface.IHistorialTareaService;

/**
 *
 * @author alexis
 */

@Path("/historial")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class HistorialTareaController {

    private IHistorialTareaService service = new HistorialTareaService();

    @GET
    @Path("/tarea/{id}")
    public Response listar(@PathParam("id") Integer id) throws Exception {

        return Response.ok(
                new ApiResponse<>(true, new ArrayList<>(), service.listar(id))
        ).build();
    }
}