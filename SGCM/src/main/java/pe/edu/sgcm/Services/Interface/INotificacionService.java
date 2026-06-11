/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.sgcm.Services.Interface;

import java.util.List;
import pe.edu.sgcm.DTOs.Requests.CrearNotificacionRequest;
import pe.edu.sgcm.DTOs.Requests.MarcarLeidoRequest;
import pe.edu.sgcm.DTOs.Responses.NotificacionResponse;

/**
 *
 * @author alexis
 */

public interface INotificacionService {

    boolean crear(CrearNotificacionRequest request) throws Exception;

    List<NotificacionResponse> listarPorUsuario(Integer idUsuario) throws Exception;

    boolean marcarLeido(MarcarLeidoRequest request) throws Exception;
}
