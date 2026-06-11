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
import pe.edu.sgcm.DTOs.Requests.ActualizarCampaniaRequest;
import pe.edu.sgcm.DTOs.Requests.ActualizarEstadoCampaniaRequest;
import pe.edu.sgcm.DTOs.Requests.CrearCampaniaRequest;
import pe.edu.sgcm.DTOs.Responses.ApiResponse;
import pe.edu.sgcm.Services.Implementation.CampaniaService;
import pe.edu.sgcm.Services.Interface.ICampaniaService;



/**
 *
 * @author alexis
 */


@Path("/campanias")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CampaniaController {

    private ICampaniaService campaniaService = new CampaniaService();

    @POST
    @Path("/crear")
    public Response crear(CrearCampaniaRequest request) {

        try {

            boolean ok = campaniaService.crearCampania(request);

            return Response.ok(new ApiResponse<>(ok, new ArrayList<>(), ok)).build();

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
                new ApiResponse<>(true, new ArrayList<>(), campaniaService.listarCampanias())
        ).build();
    }

    @PUT
    @Path("/estado")
    public Response estado(ActualizarEstadoCampaniaRequest request) {

        try {

            boolean ok = campaniaService.actualizarEstado(request);

            return Response.ok(new ApiResponse<>(ok, new ArrayList<>(), ok)).build();

        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(500)
                    .entity(new ApiResponse<>(false, List.of(e.getMessage()), null))
                    .build();
        }
    }

    @PUT
    @Path("/actualizar")
    public Response actualizar(ActualizarCampaniaRequest request) {

        try {

            boolean ok = campaniaService.actualizarCampania(request);

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
