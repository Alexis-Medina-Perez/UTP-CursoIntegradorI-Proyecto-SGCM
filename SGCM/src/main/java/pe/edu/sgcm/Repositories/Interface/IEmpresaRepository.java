/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.sgcm.Repositories.Interface;

import java.util.List;
import pe.edu.sgcm.DTOs.Requests.ActualizarEmpresaRequest;
import pe.edu.sgcm.DTOs.Requests.CrearEmpresaRequest;
import pe.edu.sgcm.DTOs.Responses.EmpresaResponse;

/**
 *
 * @author alexis
 */

public interface IEmpresaRepository {

    boolean crear(CrearEmpresaRequest request, String usuario, String ip) throws Exception;

    boolean actualizar(ActualizarEmpresaRequest request, String usuario, String ip) throws Exception;

    List<EmpresaResponse> listar() throws Exception;

    boolean cambiarEstado(Integer idEmpresa, Boolean activo, String usuario, String ip) throws Exception;
}
