/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.sgcm.Services.Implementation;

import java.util.List;
import pe.edu.sgcm.DTOs.Requests.ActualizarEmpresaRequest;
import pe.edu.sgcm.DTOs.Requests.CrearEmpresaRequest;
import pe.edu.sgcm.DTOs.Responses.EmpresaResponse;
import pe.edu.sgcm.Repositories.Implementation.EmpresaRepository;
import pe.edu.sgcm.Repositories.Interface.IEmpresaRepository;
import pe.edu.sgcm.Services.Interface.IEmpresaService;
import pe.edu.sgcm.Utils.UserContext;

/**
 *
 * @author alexis
 */

public class EmpresaService implements IEmpresaService {

    private IEmpresaRepository repository = new EmpresaRepository();

    @Override
    public boolean crear(CrearEmpresaRequest request) throws Exception {

        return repository.crear(request, UserContext.getUsername(), "127.0.0.1");
    }

    @Override
    public boolean actualizar(ActualizarEmpresaRequest request) throws Exception {

        return repository.actualizar(request, UserContext.getUsername(), "127.0.0.1");
    }

    @Override
    public List<EmpresaResponse> listar() throws Exception {

        return repository.listar();
    }

    @Override
    public boolean cambiarEstado(Integer idEmpresa, Boolean activo) throws Exception {

        return repository.cambiarEstado(idEmpresa, activo, UserContext.getUsername(), "127.0.0.1");
    }
}
