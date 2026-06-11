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
import pe.edu.sgcm.Services.Implementation.ReporteService;
import pe.edu.sgcm.Services.Interface.IReporteService;

/**
 *
 * @author alexis
 */
@Path("/reportes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ReporteController {

    private IReporteService reporteService = new ReporteService();

    @GET
    @Path("/tareas-por-usuario")
    public Response tareasPorUsuario() throws Exception {

        return Response.ok(
                new ApiResponse<>(true, new ArrayList<>(), reporteService.tareasPorUsuario())
        ).build();
    }

    @GET
    @Path("/tareas-por-campania")
    public Response tareasPorCampania() throws Exception {

        return Response.ok(
                new ApiResponse<>(true, new ArrayList<>(), reporteService.tareasPorCampania())
        ).build();
    }

    @GET
    @Path("/tareas-por-estado")
    public Response tareasPorEstado() throws Exception {

        return Response.ok(
                new ApiResponse<>(true, new ArrayList<>(), reporteService.tareasPorEstado())
        ).build();
    }

    @GET
    @Path("/tareas-por-usuario/excel")
    @Produces("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    public Response exportarExcel() {

        try {

            byte[] data = reporteService.generarExcelTareasPorUsuario();

            return Response.ok(data)
                    .header("Content-Disposition", "attachment; filename=reporte_tareas_usuario.xlsx")
                    .build();

        } catch (Exception e) {

            e.printStackTrace();

            return Response.status(500)
                    .entity("Error al generar excel")
                    .build();
        }
    }

    @GET
    @Path("/empresas-por-campania/excel")
    @Produces("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    public Response excelCampania() throws Exception {

        byte[] data = reporteService.generarExcelCampaniaEmpresas();

        return Response.ok(data)
                .header("Content-Disposition", "attachment; filename=reporte_campanias.xlsx")
                .build();
    }

    @GET
    @Path("/empresas-detalle/excel")
    @Produces("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    public Response excelEmpresasDetalle() throws Exception {

        byte[] data = reporteService.generarExcelDetalleEmpresas();

        return Response.ok(data)
                .header("Content-Disposition", "attachment; filename=empresas_detalle.xlsx")
                .build();
    }

}
