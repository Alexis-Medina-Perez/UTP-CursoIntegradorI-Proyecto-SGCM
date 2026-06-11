/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.sgcm.Services.Implementation;

import java.util.List;
import pe.edu.sgcm.DTOs.Requests.ActualizarComentarioRequest;
import pe.edu.sgcm.DTOs.Requests.CrearComentarioRequest;
import pe.edu.sgcm.DTOs.Responses.ComentarioResponse;
import pe.edu.sgcm.Repositories.Implementation.ComentarioRepository;
import pe.edu.sgcm.Repositories.Interface.IComentarioRepository;
import pe.edu.sgcm.Services.Interface.IComentarioService;
import pe.edu.sgcm.Utils.UserContext;

/**
 *
 * @author alexis
 */

public class ComentarioService implements IComentarioService{

    private IComentarioRepository repository = new ComentarioRepository();

    public boolean crear(CrearComentarioRequest request) throws Exception {

        if (UserContext.getUserId() == null)
            throw new Exception("No autenticado");

        return repository.crear(request, UserContext.getUsername(), "127.0.0.1");
    }

    public boolean actualizar(ActualizarComentarioRequest request) throws Exception {

        return repository.actualizar(request, UserContext.getUsername(), "127.0.0.1");
    }

    public boolean eliminar(Integer id) throws Exception {

        return repository.eliminar(id, UserContext.getUsername(), "127.0.0.1");
    }

    public List<ComentarioResponse> listarPorTarea(Integer idTarea) throws Exception {

        return repository.listarPorTarea(idTarea);
    }
}
