/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.sgcm.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pe.edu.sgcm.Services.Implementation.CampaniaService;
import pe.edu.sgcm.DTOs.Requests.CrearCampaniaRequest;
import pe.edu.sgcm.Utils.UserContext;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author alexis
 */

public class CampaniaServiceTest {

    private CampaniaService service;

    @BeforeEach
    void setup() {
        service = new CampaniaService();
        UserContext.setUser(1, "admin");
    }

    @Test
    void crearCampania_debeFallarSiNombreVacio() {

        CrearCampaniaRequest request = new CrearCampaniaRequest();
        request.setNombre("");

        Exception ex = assertThrows(Exception.class, () -> {
            service.crearCampania(request);
        });

        assertEquals("Nombre obligatorio", ex.getMessage());
    }

    @Test
    void crearCampania_debeFallarSiEstadoNull() {

        CrearCampaniaRequest request = new CrearCampaniaRequest();
        request.setNombre("Campaña Test");

        Exception ex = assertThrows(Exception.class, () -> {
            service.crearCampania(request);
        });

        assertEquals("Estado obligatorio", ex.getMessage());
    }
}
