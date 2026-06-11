/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.sgcm.Services.Implementation;

import java.util.ArrayList;
import java.util.List;
import pe.edu.sgcm.DTOs.Requests.ActualizarCampaniaRequest;
import pe.edu.sgcm.DTOs.Requests.ActualizarEstadoCampaniaRequest;
import pe.edu.sgcm.DTOs.Requests.CrearCampaniaRequest;
import pe.edu.sgcm.DTOs.Responses.CampaniaResponse;
import pe.edu.sgcm.Models.Campania;
import pe.edu.sgcm.Services.Interface.ICampaniaService;
import pe.edu.sgcm.Repositories.Interface.ICampaniaRepository;
import pe.edu.sgcm.Repositories.Implementation.CampaniaRepository;
import pe.edu.sgcm.Utils.UserContext;

import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author alexis
 */
public class CampaniaService implements ICampaniaService {

    private ICampaniaRepository campaniaRepository = new CampaniaRepository();

    @Override
    public boolean crearCampania(CrearCampaniaRequest request) throws Exception {

        if (UserContext.getUserId() == null)
            throw new Exception("No autenticado");

        if (request == null)
            throw new Exception("Request inválido");

        if (StringUtils.isBlank(request.getNombre()))
            throw new Exception("Nombre obligatorio");

        if (StringUtils.length(request.getNombre()) > 200)
            throw new Exception("Nombre demasiado largo");

        if (StringUtils.isNotBlank(request.getDescripcion()) &&
            request.getDescripcion().length() > 500)
            throw new Exception("Descripción demasiado larga");

        if (request.getIdEstadoCampania() == null)
            throw new Exception("Estado obligatorio");

        if (request.getIdTipoCampania() == null)
            throw new Exception("Tipo de campaña obligatorio");

        String usuario = UserContext.getUsername();

        return campaniaRepository.crearCampania(request, usuario, "127.0.0.1");
    }

    @Override
    public List<CampaniaResponse> listarCampanias() throws Exception {

        List<Campania> lista = campaniaRepository.listarCampanias();
        List<CampaniaResponse> response = new ArrayList<>();

        for (Campania c : lista) {

            CampaniaResponse res = new CampaniaResponse();

            res.setIdCampania(c.getIdCampania());
            res.setNombre(c.getNombre());
            res.setDescripcion(c.getDescripcion());

            res.setEstado(c.getEstado());
            res.setTipo(c.getTipo());
            res.setFechaInicioText(c.getFechaInicioText());
            res.setFechaFinText(c.getFechaFinText());
            res.setPresupuesto(c.getPresupuesto());

            res.setActivo("F".equals(c.getSqlDeleted()));

            response.add(res);
        }

        return response;
    }

    @Override
    public boolean actualizarEstado(ActualizarEstadoCampaniaRequest request) throws Exception {

        if (UserContext.getUserId() == null)
            throw new Exception("No autenticado");

        if (request == null)
            throw new Exception("Request inválido");

        if (request.getIdCampania() == null)
            throw new Exception("IdCampania obligatorio");

        if (request.getActivo() == null)
            throw new Exception("Estado activo es obligatorio");

        String usuario = UserContext.getUsername();

        return campaniaRepository.actualizarEstado(
                request.getIdCampania(),
                request.getActivo(),
                usuario,
                "127.0.0.1"
        );
    }

    @Override
    public boolean actualizarCampania(ActualizarCampaniaRequest request) throws Exception {

        if (UserContext.getUserId() == null)
            throw new Exception("No autenticado");

        if (request == null)
            throw new Exception("Request inválido");

        if (request.getIdCampania() == null)
            throw new Exception("IdCampania obligatorio");

        if (StringUtils.isBlank(request.getNombre()))
            throw new Exception("Nombre obligatorio");

        if (StringUtils.length(request.getNombre()) > 200)
            throw new Exception("Nombre demasiado largo");

        if (StringUtils.isNotBlank(request.getDescripcion()) &&
            request.getDescripcion().length() > 500)
            throw new Exception("Descripción demasiado larga");

        String usuario = UserContext.getUsername();

        return campaniaRepository.actualizarCampania(
                request,
                usuario,
                "127.0.0.1"
        );
    }

}