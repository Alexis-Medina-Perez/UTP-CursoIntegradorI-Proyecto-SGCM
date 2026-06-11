/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.sgcm.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pe.edu.sgcm.Services.Implementation.UsuarioService;
import pe.edu.sgcm.DTOs.Requests.LoginRequest;
import pe.edu.sgcm.DTOs.Requests.UsuarioRegisterRequest;
import pe.edu.sgcm.DTOs.Responses.LoginResponse;
import pe.edu.sgcm.Utils.UserContext;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author alexis
 */
public class UsuarioServiceTest {

    private UsuarioService service;

    @BeforeEach
    void setup() {
        service = new UsuarioService();
        UserContext.setUser(1, "admin");
    }

    @Test
    void login_debeFallarSiUsernameVacio() throws Exception {

        LoginRequest request = new LoginRequest();
        request.setUsername("");
        request.setPassword("123456");

        LoginResponse response = service.loginService(request);

        assertEquals("Username obligatorio", response.getRespuesta());
    }

    @Test
    void login_debeFallarSiPasswordVacio() throws Exception {

        LoginRequest request = new LoginRequest();
        request.setUsername("admin");
        request.setPassword("");

        LoginResponse response = service.loginService(request);

        assertEquals("Password obligatorio", response.getRespuesta());
    }

    @Test
    void registrarUsuario_debeFallarSiCorreoInvalido() throws Exception {

        UsuarioRegisterRequest request = new UsuarioRegisterRequest();
        request.setNombres("Juan");
        request.setApellidos("Perez");
        request.setCorreo("correo-invalido");
        request.setUsername("juan123");
        request.setPassword("123456");

        var response = service.registrarUsuario(request);

        assertEquals("Correo inválido", response.getMensaje());
    }
}
