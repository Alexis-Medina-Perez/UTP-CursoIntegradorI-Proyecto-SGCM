/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.sgcm.Services.Interface;

import java.util.List;
import pe.edu.sgcm.DTOs.Requests.ActualizarEmpresaRequest;
import pe.edu.sgcm.DTOs.Requests.CrearEmpresaRequest;
import pe.edu.sgcm.DTOs.Responses.EmpresaResponse;

/**
 *
 * @author alexis
 */

public interface IEmpresaService {

    boolean crear(CrearEmpresaRequest request) throws Exception;

    boolean actualizar(ActualizarEmpresaRequest request) throws Exception;

    List<EmpresaResponse> listar() throws Exception;

    boolean cambiarEstado(Integer idEmpresa, Boolean activo) throws Exception;
}
