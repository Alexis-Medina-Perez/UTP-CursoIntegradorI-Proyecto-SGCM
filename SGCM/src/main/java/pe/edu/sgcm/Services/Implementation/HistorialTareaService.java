/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.sgcm.Services.Implementation;

import java.util.List;
import pe.edu.sgcm.DTOs.Requests.CrearHistorialRequest;
import pe.edu.sgcm.DTOs.Responses.HistorialTareaResponse;
import pe.edu.sgcm.Repositories.Implementation.HistorialTareaRepository;
import pe.edu.sgcm.Repositories.Interface.IHistorialTareaRepository;
import pe.edu.sgcm.Services.Interface.IHistorialTareaService;
import pe.edu.sgcm.Utils.UserContext;

/**
 *
 * @author alexis
 */

public class HistorialTareaService implements IHistorialTareaService {

    private IHistorialTareaRepository repository = new HistorialTareaRepository();

    @Override
    public boolean registrar(CrearHistorialRequest request) throws Exception {

        return repository.registrar(request, UserContext.getUsername(), "127.0.0.1");
    }

    @Override
    public List<HistorialTareaResponse> listar(Integer idTarea) throws Exception {

        return repository.listarPorTarea(idTarea);
    }
}
