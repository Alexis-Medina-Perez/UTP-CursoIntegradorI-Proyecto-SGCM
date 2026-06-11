/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.sgcm.Services.Implementation;

import java.util.ArrayList;
import java.util.List;
import pe.edu.sgcm.DTOs.Requests.ActualizarEstadoRolRequest;
import pe.edu.sgcm.DTOs.Requests.CrearRolRequest;
import pe.edu.sgcm.DTOs.Responses.RolResponse;
import pe.edu.sgcm.Models.Rol;
import pe.edu.sgcm.Repositories.Implementation.RolRepository;
import pe.edu.sgcm.Repositories.Interface.IRolRepository;
import pe.edu.sgcm.Services.Interface.IRolService;
import pe.edu.sgcm.Utils.UserContext;

/**
 *
 * @author alexis
 */
public class RolService implements IRolService {
    
    private IRolRepository rolRepository = new RolRepository();

    @Override
    public List<RolResponse> listarRoles() throws Exception {

        List<Rol> lista = rolRepository.listarRoles();
        List<RolResponse> responseList = new ArrayList<>();

        for (Rol r : lista) {
            RolResponse res = new RolResponse();

            res.setIdRol(r.getIdRol());
            res.setNombre(r.getNombre());
            res.setDescripcion(r.getDescripcion());
            res.setActivo("F".equals(r.getSqlDeleted()));

            responseList.add(res);
        }

        return responseList;
    }

    @Override
    public Boolean crearRol(CrearRolRequest request) throws Exception {

        if (UserContext.getUserId() == null)
            throw new Exception("No autenticado");

        String usuario = UserContext.getUsername();

        return rolRepository.crearRol(request, usuario, "127.0.0.1");
    }

    @Override
    public Boolean actualizarEstadoRol(ActualizarEstadoRolRequest request) throws Exception {

        if (UserContext.getUserId() == null)
            throw new Exception("No autenticado");

        String usuario = UserContext.getUsername();

        return rolRepository.actualizarEstadoRol(
                request.getIdRol(),
                request.getActivo(),
                usuario,
                "127.0.0.1"
        );
    }


}
