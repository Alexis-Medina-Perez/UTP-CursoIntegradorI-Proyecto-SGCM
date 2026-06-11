/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.sgcm.DTOs.Responses;

/**
 *
 * @author alexis
 */
public class ReporteCampaniaEmpresaResponse {
    
    private String campania;
    private Integer totalEmpresas;
    private Integer clientes;
    private Integer prospectos;

    public String getCampania() {
        return campania;
    }

    public void setCampania(String campania) {
        this.campania = campania;
    }

    public Integer getTotalEmpresas() {
        return totalEmpresas;
    }

    public void setTotalEmpresas(Integer totalEmpresas) {
        this.totalEmpresas = totalEmpresas;
    }

    public Integer getClientes() {
        return clientes;
    }

    public void setClientes(Integer clientes) {
        this.clientes = clientes;
    }

    public Integer getProspectos() {
        return prospectos;
    }

    public void setProspectos(Integer prospectos) {
        this.prospectos = prospectos;
    }

}
