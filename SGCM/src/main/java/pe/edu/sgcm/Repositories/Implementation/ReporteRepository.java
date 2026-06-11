/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.sgcm.Repositories.Implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import pe.edu.sgcm.DTOs.Responses.ReporteCampaniaEmpresaResponse;
import pe.edu.sgcm.DTOs.Responses.ReporteDetalleEmpresaResponse;
import pe.edu.sgcm.DTOs.Responses.ReporteTareasPorCampaniaResponse;
import pe.edu.sgcm.DTOs.Responses.ReporteTareasPorEstadoResponse;
import pe.edu.sgcm.DTOs.Responses.ReporteTareasPorUsuarioResponse;
import pe.edu.sgcm.Repositories.Interface.IReporteRepository;
import pe.edu.sgcm.Utils.ConexionBD;

/**
 *
 * @author alexis
 */
public class ReporteRepository implements IReporteRepository {

    @Override
    public List<ReporteTareasPorUsuarioResponse> tareasPorUsuario() throws Exception {

        String sql = "SELECT u.username, COUNT(t.id_tarea) AS total " +
                     "FROM sgcm.tarea t " +
                     "JOIN sgcm.usuario u ON u.id_usuario = t.id_usuario_responsable " +
                     "GROUP BY u.username";

        List<ReporteTareasPorUsuarioResponse> lista = new ArrayList<>();

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                ReporteTareasPorUsuarioResponse r = new ReporteTareasPorUsuarioResponse();

                r.setUsuario(rs.getString("username"));
                r.setTotalTareas(rs.getInt("total"));

                lista.add(r);
            }
        }

        return lista;
    }

    @Override
    public List<ReporteTareasPorCampaniaResponse> tareasPorCampania() throws Exception {

        String sql = "SELECT c.nombre, COUNT(t.id_tarea) AS total " +
                     "FROM sgcm.tarea t " +
                     "JOIN sgcm.campania c ON c.id_campania = t.id_campania " +
                     "GROUP BY c.nombre";

        List<ReporteTareasPorCampaniaResponse> lista = new ArrayList<>();

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                ReporteTareasPorCampaniaResponse r = new ReporteTareasPorCampaniaResponse();

                r.setCampania(rs.getString("nombre"));
                r.setTotal(rs.getInt("total"));

                lista.add(r);
            }
        }

        return lista;
    }

    @Override
    public List<ReporteTareasPorEstadoResponse> tareasPorEstado() throws Exception {

        String sql = "SELECT e.nombre, COUNT(t.id_tarea) AS total " +
                     "FROM sgcm.tarea t " +
                     "JOIN sgcm.estado_tarea e ON e.id_estado_tarea = t.id_estado_tarea " +
                     "GROUP BY e.nombre";

        List<ReporteTareasPorEstadoResponse> lista = new ArrayList<>();

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                ReporteTareasPorEstadoResponse r = new ReporteTareasPorEstadoResponse();

                r.setEstado(rs.getString("nombre"));
                r.setTotal(rs.getInt("total"));

                lista.add(r);
            }
        }

        return lista;
    }

    @Override
    public List<ReporteCampaniaEmpresaResponse> empresasPorCampania() throws Exception {

        String sql = "SELECT c.nombre AS campania, " +
                     "COUNT(e.id_empresa) AS total_empresas, " +
                     "COUNT(*) FILTER (WHERE e.es_cliente = true) AS clientes, " +
                     "COUNT(*) FILTER (WHERE e.es_cliente = false) AS prospectos " +
                     "FROM sgcm.empresa e " +
                     "JOIN sgcm.campania c ON c.id_campania = e.id_campania " +
                     "WHERE e.sql_deleted = 'F' " +
                     "GROUP BY c.nombre";

        List<ReporteCampaniaEmpresaResponse> lista = new ArrayList<>();

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                ReporteCampaniaEmpresaResponse r = new ReporteCampaniaEmpresaResponse();

                r.setCampania(rs.getString("campania"));
                r.setTotalEmpresas(rs.getInt("total_empresas"));
                r.setClientes(rs.getInt("clientes"));
                r.setProspectos(rs.getInt("prospectos"));

                lista.add(r);
            }
        }

        return lista;
    }

    @Override
    public List<ReporteDetalleEmpresaResponse> detalleEmpresas() throws Exception {

        String sql = "SELECT " +
                     "e.id_empresa, " +
                     "e.razon_social, " +
                     "e.nombre_comercial, " +
                     "e.ruc, " +
                     "e.contacto_principal, " +
                     "e.correo_contacto, " +
                     "e.telefono_contacto, " +
                     "e.direccion, " +
                     "e.fecha_captacion, " +
                     "e.es_cliente, " +
                     "c.nombre AS campania, " +
                     "est.nombre AS estado " +
                     "FROM sgcm.empresa e " +
                     "JOIN sgcm.campania c ON c.id_campania = e.id_campania " +
                     "JOIN sgcm.estado_empresa est ON est.id_estado_empresa = e.id_estado_empresa " +
                     "WHERE e.sql_deleted = 'F'";

        List<ReporteDetalleEmpresaResponse> lista = new ArrayList<>();

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                ReporteDetalleEmpresaResponse r = new ReporteDetalleEmpresaResponse();

                r.setIdEmpresa(rs.getInt("id_empresa"));
                r.setRazonSocial(rs.getString("razon_social"));
                r.setNombreComercial(rs.getString("nombre_comercial"));
                r.setRuc(rs.getString("ruc"));
                r.setContacto(rs.getString("contacto_principal"));
                r.setCorreo(rs.getString("correo_contacto"));
                r.setTelefono(rs.getString("telefono_contacto"));
                r.setDireccion(rs.getString("direccion"));
                r.setFechaCaptacion(rs.getTimestamp("fecha_captacion") != null 
                    ? rs.getTimestamp("fecha_captacion").toString() : null);
                r.setEsCliente(rs.getBoolean("es_cliente"));
                r.setCampania(rs.getString("campania"));
                r.setEstado(rs.getString("estado"));

                lista.add(r);
            }
        }

        return lista;
    }

}
