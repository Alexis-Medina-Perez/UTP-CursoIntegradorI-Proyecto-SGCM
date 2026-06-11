/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.sgcm.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pe.edu.sgcm.Services.Implementation.EmpresaService;
import pe.edu.sgcm.DTOs.Requests.CrearEmpresaRequest;
import pe.edu.sgcm.Utils.UserContext;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author alexis
 */

public class EmpresaServiceTest {

    private EmpresaService service;

    @BeforeEach
    void setup() {
        service = new EmpresaService();
        UserContext.setUser(1, "admin");
    }

    @Test
    void crearEmpresa_noDebeFallarSiDatosValidos() {

        CrearEmpresaRequest request = new CrearEmpresaRequest();

        request.setRazonSocial("Empresa Test");
        request.setNombreComercial("Empresa Test");
        request.setRuc("20" + (System.currentTimeMillis() % 1000000000));
        request.setIdCampania(2);
        request.setIdEstadoEmpresa(1);
        request.setEsCliente(true);

        assertDoesNotThrow(() -> {
            service.crear(request);
        });
    }

}

