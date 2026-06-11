/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.sgcm.DTOs.Responses;

/**
 *
 * @author alexis
 */

public class LoginResponse {

    private String respuesta;
    private String token;

    public LoginResponse(String respuesta, String token) {
        this.respuesta = respuesta;
        this.token = token;
    }

    public String getRespuesta() { return respuesta; }
    public String getToken() { return token; }
}
