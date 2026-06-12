/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.sgcm.Services.Implementation;

import java.util.List;
import pe.edu.sgcm.DTOs.Requests.ActualizarEstadoTareaRequest;
import pe.edu.sgcm.DTOs.Requests.ActualizarTareaRequest;
import pe.edu.sgcm.DTOs.Requests.CrearHistorialRequest;
import pe.edu.sgcm.DTOs.Requests.CrearTareaRequest;
import pe.edu.sgcm.DTOs.Responses.TareaResponse;
import pe.edu.sgcm.Services.Interface.ITareaService;
import pe.edu.sgcm.Repositories.Interface.ITareaRepository;
import pe.edu.sgcm.Repositories.Implementation.TareaRepository;
import pe.edu.sgcm.Services.Interface.IHistorialTareaService;
import pe.edu.sgcm.Utils.UserContext;

import pe.edu.sgcm.Services.Interface.INotificacionService;
import pe.edu.sgcm.DTOs.Requests.CrearNotificacionRequest;

import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author alexis
 */
public class TareaService implements ITareaService {
    
    public ITareaRepository tareaRepository = new TareaRepository();
    public IHistorialTareaService historialTareaService = new HistorialTareaService();
    private INotificacionService notificacionService = new NotificacionService();
    
    @Override
    public boolean crearTarea(CrearTareaRequest request) throws Exception {

        if (UserContext.getUserId() == null)
            throw new Exception("No autenticado");

        if (request == null)
            throw new Exception("Request inválido");

        if (StringUtils.isBlank(request.getNombre()))
            throw new Exception("Nombre obligatorio");

        if (StringUtils.length(request.getNombre()) > 200)
            throw new Exception("Nombre demasiado largo");

        if (request.getIdCampania() == null)
            throw new Exception("Campaña obligatoria");

        if (request.getIdEstadoTarea() == null)
            throw new Exception("Estado obligatorio");

        if (request.getIdPrioridadTarea() == null)
            throw new Exception("Prioridad obligatoria");

        if (request.getIdUsuarioResponsable() == null)
            throw new Exception("Responsable obligatorio");

        String usuario = UserContext.getUsername();


        boolean ok = tareaRepository.crearTarea(request, usuario, "127.0.0.1");

        if (ok) {

            CrearNotificacionRequest noti = new CrearNotificacionRequest();
            noti.setIdUsuario(request.getIdUsuarioResponsable());
            noti.setTitulo("Nueva tarea asignada");
            noti.setMensaje("Se te asignó la tarea: " + request.getNombre());

            notificacionService.crear(noti);
        }

        return ok;
        
    }

    @Override
    public List<TareaResponse> listarTareas() throws Exception {

        return tareaRepository.listarTareas();
    }

    @Override
    public boolean actualizarEstado(ActualizarEstadoTareaRequest request) throws Exception {

        if (UserContext.getUserId() == null)
            throw new Exception("No autenticado");

        if (request == null)
            throw new Exception("Request inválido");

        if (request.getIdTarea() == null)
            throw new Exception("IdTarea obligatorio");

        if (request.getIdEstadoTarea() == null)
            throw new Exception("Estado obligatorio");

        String usuario = UserContext.getUsername();

        boolean ok = tareaRepository.actualizarEstado(
                request.getIdTarea(),
                request.getIdEstadoTarea(),
                usuario,
                "127.0.0.1"
        );

        if (ok) {

            CrearHistorialRequest h = new CrearHistorialRequest();
            h.setIdTarea(request.getIdTarea());
            h.setIdEstadoTarea(request.getIdEstadoTarea());
            h.setObservacion("Cambio de estado");

            historialTareaService.registrar(h);
        }

        return ok;
    }

    @Override
    public boolean actualizarTarea(ActualizarTareaRequest request) throws Exception {

        if (UserContext.getUserId() == null)
            throw new Exception("No autenticado");

        if (request == null)
            throw new Exception("Request inválido");

        if (request.getIdTarea() == null)
            throw new Exception("IdTarea obligatorio");

        if (StringUtils.isBlank(request.getNombre()))
            throw new Exception("Nombre obligatorio");

        if (StringUtils.length(request.getNombre()) > 200)
            throw new Exception("Nombre demasiado largo");

        if (request.getPorcentajeAvance() != null) {
            if (request.getPorcentajeAvance() < 0 || request.getPorcentajeAvance() > 100) {
                throw new Exception("Porcentaje debe estar entre 0 y 100");
            }
        }

        String usuario = UserContext.getUsername();

        return tareaRepository.actualizarTarea(
                request,
                usuario,
                "127.0.0.1"
        );
    }

}
