/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.sgcm.Services.Interface;

import java.util.List;
import pe.edu.sgcm.DTOs.Responses.ReporteTareasPorCampaniaResponse;
import pe.edu.sgcm.DTOs.Responses.ReporteTareasPorEstadoResponse;
import pe.edu.sgcm.DTOs.Responses.ReporteTareasPorUsuarioResponse;

/**
 *
 * @author alexis
 */

public interface IReporteService {

    List<ReporteTareasPorUsuarioResponse> tareasPorUsuario() throws Exception;

    List<ReporteTareasPorCampaniaResponse> tareasPorCampania() throws Exception;

    List<ReporteTareasPorEstadoResponse> tareasPorEstado() throws Exception;
    
    byte[] generarExcelTareasPorUsuario() throws Exception;

    byte[] generarExcelCampaniaEmpresas() throws Exception;

    byte[] generarExcelDetalleEmpresas() throws Exception;
    
}
