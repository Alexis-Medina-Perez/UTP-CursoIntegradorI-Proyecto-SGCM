/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.sgcm.Services.Implementation;

import java.util.List;
import pe.edu.sgcm.DTOs.Requests.CrearNotificacionRequest;
import pe.edu.sgcm.DTOs.Requests.MarcarLeidoRequest;
import pe.edu.sgcm.DTOs.Responses.NotificacionResponse;
import pe.edu.sgcm.Repositories.Implementation.NotificacionRepository;
import pe.edu.sgcm.Repositories.Interface.INotificacionRepository;
import pe.edu.sgcm.Services.Interface.INotificacionService;
import pe.edu.sgcm.Utils.UserContext;

/**
 *
 * @author alexis
 */

public class NotificacionService implements INotificacionService {

    private INotificacionRepository repository = new NotificacionRepository();

    @Override
    public boolean crear(CrearNotificacionRequest request) throws Exception {

        if (UserContext.getUserId() == null)
            throw new Exception("No autenticado");

        return repository.crear(request, UserContext.getUsername(), "127.0.0.1");
    }

    @Override
    public List<NotificacionResponse> listarPorUsuario(Integer idUsuario) throws Exception {

        return repository.listarPorUsuario(idUsuario);
    }

    @Override
    public boolean marcarLeido(MarcarLeidoRequest request) throws Exception {

        return repository.marcarLeido(
                request.getIdNotificacion(),
                UserContext.getUsername(),
                "127.0.0.1"
        );
    }
}
