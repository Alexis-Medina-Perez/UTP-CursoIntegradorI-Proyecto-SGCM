/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.sgcm.Controllers;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;
import pe.edu.sgcm.DTOs.Requests.ActualizarEmpresaRequest;
import pe.edu.sgcm.DTOs.Requests.CrearEmpresaRequest;
import pe.edu.sgcm.DTOs.Responses.ApiResponse;
import pe.edu.sgcm.Services.Implementation.EmpresaService;
import pe.edu.sgcm.Services.Interface.IEmpresaService;

/**
 *
 * @author alexis
 */

@Path("/empresas")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EmpresaController {

    private IEmpresaService service = new EmpresaService();

    @POST
    @Path("/crear")
    public Response crear(CrearEmpresaRequest request) throws Exception {

        boolean ok = service.crear(request);

        return Response.ok(new ApiResponse<>(ok, new ArrayList<>(), ok)).build();
    }

    @GET
    @Path("/listar")
    public Response listar() throws Exception {

        return Response.ok(
                new ApiResponse<>(true, new ArrayList<>(), service.listar())
        ).build();
    }

    @PUT
    @Path("/actualizar")
    public Response actualizar(ActualizarEmpresaRequest request) throws Exception {

        boolean ok = service.actualizar(request);

        return Response.ok(new ApiResponse<>(ok, new ArrayList<>(), ok)).build();
    }

    @PUT
    @Path("/estado")
    public Response estado(@QueryParam("id") Integer id,
                           @QueryParam("activo") Boolean activo) throws Exception {

        boolean ok = service.cambiarEstado(id, activo);

        return Response.ok(new ApiResponse<>(ok, new ArrayList<>(), ok)).build();
    }
}
