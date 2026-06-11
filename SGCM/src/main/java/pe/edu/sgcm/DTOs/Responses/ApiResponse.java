/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.sgcm.DTOs.Responses;

import java.util.List;

/**
 *
 * @author alexis
 */
public class ApiResponse<T> {
    
    private boolean success;
    private List<String> errors;
    private T value;

    public ApiResponse(boolean success, List<String> errors, T value) {
        this.success = success;
        this.errors = errors;
        this.value = value;
    }

    public boolean isSuccess() {
        return success;
    }

    public List<String> getErrors() {
        return errors;
    }

    public T getValue() {
        return value;
    }

}
