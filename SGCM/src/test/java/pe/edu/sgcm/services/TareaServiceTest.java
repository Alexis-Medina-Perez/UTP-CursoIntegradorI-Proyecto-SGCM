/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.sgcm.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pe.edu.sgcm.Services.Implementation.TareaService;
import pe.edu.sgcm.DTOs.Requests.ActualizarTareaRequest;
import pe.edu.sgcm.DTOs.Requests.CrearTareaRequest;
import pe.edu.sgcm.Utils.UserContext;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author alexis
 */

public class TareaServiceTest {

    private TareaService service;

    @BeforeEach
    void setup() {
        service = new TareaService();
        UserContext.setUser(1, "admin");
    }

    @Test
    void crearTarea_debeFallarSiNombreVacio() {

        CrearTareaRequest request = new CrearTareaRequest();
        request.setNombre("");

        Exception exception = assertThrows(Exception.class, () -> {
            service.crearTarea(request);
        });

        assertEquals("Nombre obligatorio", exception.getMessage());
    }

    @Test
    void actualizarTarea_debeFallarSiPorcentajeInvalido() {

        ActualizarTareaRequest request = new ActualizarTareaRequest();
        request.setIdTarea(1);
        request.setNombre("Tarea Test");
        request.setPorcentajeAvance(150);

        Exception exception = assertThrows(Exception.class, () -> {
            service.actualizarTarea(request);
        });

        assertTrue(exception.getMessage().contains("Porcentaje"));
    }
}
